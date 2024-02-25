package com.judahben149.spendr.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.CashEntryMapperImpl
import com.judahben149.spendr.utils.SmsParser
import com.judahben149.spendr.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver: BroadcastReceiver() {

    @Inject
    lateinit var repository: CashFlowRepositoryImpl

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != null && intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras

            if (bundle != null) {
                try {
                    val pdus = bundle["pdus"] as Array<Any>?

                    pdus?.let {
                        for (pdu in pdus) {
                            val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)

                            val sender = smsMessage.originatingAddress
                            val content = smsMessage.messageBody

                            val cashEntry = SmsParser.parseSms(sender.toString(), content)

                            cashEntry?.let {
                                coroutineScope.launch {
                                    repository.saveEntry(CashEntryMapperImpl().cashEntryToCashEntryEntity(it))
                                }
                            }
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }
}