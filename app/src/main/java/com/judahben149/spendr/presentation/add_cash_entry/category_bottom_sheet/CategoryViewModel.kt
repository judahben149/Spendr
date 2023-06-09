package com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.CashEntryMapperImpl
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.presentation.add_cash_entry.CategoryListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: CashFlowRepositoryImpl): ViewModel() {

    private var _categoryState: MutableLiveData<CategoryListState> = MutableLiveData(CategoryListState())
    val categoryState: LiveData<CategoryListState> = _categoryState


    init {
        getCategories()
    }

    fun getCategoryIconList(): List<Int> {
        //These are the ids of the icons provided for selection. They are static
        val categoryIconsList = listOf<Int>(
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            15,
            16,
            17,
            18,
            19,
            20,
            21,
            22,
            23,
            24
        )
        return categoryIconsList
    }

    fun setCategorySelected(isIncomeSelected: Boolean) {
        _categoryState.value = _categoryState.value!!.copy(
            isIncomeSelected = isIncomeSelected
        )
    }

    fun getCategories() {
        setCategoryLoading(true)

        viewModelScope.launch {
            repository.getCategories().collect { categoryEntityList ->
                val categoryList = categoryEntityList.map { categoryEntity ->
                    CashEntryMapperImpl().categoryEntityToCategory(categoryEntity)
                }

                _categoryState.value = _categoryState.value!!.copy(categoryList = categoryList)
                setCategoryLoading(false)
            }
        }
    }

    fun saveNewCategory(categoryName: String, categoryIconId: Int) {
        viewModelScope.launch {
            val category = Category(
                categoryId = 0,
                categoryName = categoryName,
                categoryIconId = categoryIconId,
                isIncomeCategory = categoryState.value!!.isIncomeSelected
            )

            val categoryEntity = CashEntryMapperImpl().categoryToCategoryEntity(category)
            repository.saveNewCategory(categoryEntity)
        }
    }

    fun setCategoryLoading(isLoading: Boolean) {
        _categoryState.value = _categoryState.value!!.copy(
            isLoading = isLoading
        )
    }
}