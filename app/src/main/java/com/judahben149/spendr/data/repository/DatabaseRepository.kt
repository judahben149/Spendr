package com.judahben149.spendr.data.repository

import android.content.Context
import org.json.JSONArray

interface DatabaseRepository {

    fun loadJsonArray(context: Context): JSONArray?

    suspend fun prefillDefaultCategories(context: Context)
}