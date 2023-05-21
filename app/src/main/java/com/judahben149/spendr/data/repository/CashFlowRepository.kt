package com.judahben149.spendr.data.repository

import com.judahben149.spendr.data.local.entity.CashEntryEntity

interface CashFlowRepository {

    suspend fun saveEntry(cashEntryEntity: CashEntryEntity)
}