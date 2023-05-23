package com.judahben149.spendr.data.repository

import android.content.Context
import android.util.Log
import com.judahben149.spendr.R
import com.judahben149.spendr.data.local.CashFlowDao
import com.judahben149.spendr.data.local.entity.CategoryEntity
import org.json.JSONArray
import java.io.BufferedReader
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val cashFlowDao: CashFlowDao): DatabaseRepository {


    override fun loadJsonArray(context: Context): JSONArray? {
        val inputStream = context.resources.openRawResource(R.raw.default_category_data)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }

    override suspend fun prefillDefaultCategories(context: Context) {
        Log.d("TAGM", "prefillDefaultCategories: Prefilling now")
        try {
            val defaultCategories = loadJsonArray(context)

            if (defaultCategories != null) {
                for (categoryIndex in 0 until defaultCategories.length()) {
                    val categoryItem = defaultCategories.getJSONObject(categoryIndex)

                    val categoryId = categoryItem.getInt("category-id")
                    val categoryName = categoryItem.getString("category-name")
                    val isIncomeCategory = categoryItem.getBoolean("is-income-category")

                    val categoryEntity = CategoryEntity(
                        categoryId,
                        categoryName,
                        isIncomeCategory
                    )

                    cashFlowDao.saveNewCategory(categoryEntity)
                    Log.d("TAGM", "prefillDefaultCategories: Just saved new category")
                }
            }
        }
        catch (exception: Exception) {
            Log.d("TAGM", "prefillDefaultCategories: Error on prefilling categories")
        }
    }
}