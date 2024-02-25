package com.judahben149.spendr.utils

import com.judahben149.spendr.domain.model.CashEntry

object SmsParser {

    fun parseSms(originatingAddress: String, content: String): CashEntry? {
        val bankType = parseBankType(originatingAddress)

        val parser: BankParser? = when (bankType) {
            BankType.ACCESS_BANK -> AccessBankParser()
            BankType.GT_BANK -> GTBankParser()
            BankType.UNKNOWN -> null
            else -> null
        }

        return parser?.parse(content)
    }

    private fun parseBankType(originatingAddress: String): BankType {
        return when(originatingAddress) {
            "AccessBank" -> BankType.ACCESS_BANK
            "GTBank" -> BankType.GT_BANK
            else -> BankType.UNKNOWN
        }
    }
}

enum class BankType {
    ACCESS_BANK,
    FIRST_BANK,
    UBA_BANK,
    GT_BANK,
    UNKNOWN
}

interface BankParser {
    fun parse(content: String): CashEntry
}

class AccessBankParser: BankParser {

    override fun parse(content: String): CashEntry {
        var smsInfo = SmsInfo()
        val lines = content.split("\n")

        for (line in lines) {
            when {
                line.startsWith("Debit", true) -> { smsInfo = smsInfo.copy(type = AlertType.DEBIT) }

                line.startsWith("Credit", true) -> { smsInfo = smsInfo.copy(type = AlertType.CREDIT) }

                line.startsWith("Amt:") -> {
                    val amount = line.substringAfter("Amt:")
                        .removeSurrounding(" ")
                        .removePrefix("NGN")
                        .replace(",", "")

                    smsInfo = smsInfo.copy(amount = amount)
                }

                line.startsWith("Desc:") -> {
                    val description = line.substringAfter("Desc:")
                    smsInfo = smsInfo.copy(description = description)
                }

                line.startsWith("Time:") -> {
                    val date = line.substringAfter("Time:").removeSurrounding(" ")
                    smsInfo = smsInfo.copy(dateTime = DateUtils.formatAccessBankDate(date))
                }
            }
        }

        val cashEntry = CashEntry(
            amount = smsInfo.amount.toDouble(),
            isIncome = smsInfo.type == AlertType.CREDIT,
            categoryName = smsInfo.description,
            transactionDate = smsInfo.dateTime
        )
        return cashEntry
    }
}

class GTBankParser: BankParser {

    override fun parse(content: String): CashEntry {
        var smsInfo = SmsInfo()
        val lines = content.split("\n")

        for (line in lines) {
            when {
                line.startsWith("Amt:") -> {
                    val amountAndTypeSplit = line.substringAfter("Amt: ").split(" ")
                    val amount = amountAndTypeSplit[0].removeSurrounding(" ").removePrefix("NGN").replace(",", "")
                    val type = amountAndTypeSplit[1].removeSurrounding(" ")

                    val alertType = if (type == "DR") AlertType.DEBIT else if (type == "CR") AlertType.CREDIT else AlertType.DEBIT

                    smsInfo = smsInfo.copy(amount = amount, type = alertType)
                }

                line.startsWith("Desc:") -> {
                    val description = line.substringAfter("Desc: ")

                    smsInfo = smsInfo.copy(description = description)
                }

                line.startsWith("Date:") -> {
                    val date = line.substringAfter("Date:").removeSurrounding(" ")
                    smsInfo = smsInfo.copy(dateTime = DateUtils.formatGTBankDate(date))
                }
            }
        }

        val cashEntry = CashEntry(
            amount = smsInfo.amount.toDouble(),
            isIncome = smsInfo.type == AlertType.CREDIT,
            categoryName = smsInfo.description,
            transactionDate = smsInfo.dateTime
        )

        return cashEntry
    }
}

data class SmsInfo(
    val amount: String = "",
    val type: AlertType = AlertType.DEBIT,
    val description: String = "",
    val dateTime: Long = 0
)

enum class AlertType {
    DEBIT,
    CREDIT
}