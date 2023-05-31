package com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.MapperImpl
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.presentation.add_cash_entry.CategoryListState
import com.judahben149.spendr.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: CashFlowRepositoryImpl): ViewModel() {

    private var _categoryState: MutableLiveData<CategoryListState> = MutableLiveData(
        CategoryListState()
    )
    val categoryState: LiveData<CategoryListState> = _categoryState


    fun getCategoryIconList(): List<Int> {
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
        Timber.tag(Constants.TIMBER_TAG).d("Category  is income to $isIncomeSelected")
        _categoryState.value = _categoryState.value!!.copy(
            isIncomeSelected = isIncomeSelected
        )
    }

    fun getCategories() {
        Timber.tag(Constants.TIMBER_TAG).d("Get categories called")
        setCategoryLoading(true)

        viewModelScope.launch {
            val categoryList = repository.getCategories().collect { categoryEntityList ->
                val categoryList = categoryEntityList.map { categoryEntity ->
                    MapperImpl().categoryEntityToCategory(categoryEntity)
                }

                _categoryState.value = _categoryState.value!!.copy(categoryList = categoryList)
                Timber.tag(Constants.TIMBER_TAG).d("Category list - $categoryList")
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

            val categoryEntity = MapperImpl().categoryToCategoryEntity(category)
            repository.saveNewCategory(categoryEntity)
        }
    }

    fun setCategoryLoading(isLoading: Boolean) {
        Timber.tag(Constants.TIMBER_TAG).d("Category loading - $isLoading")
        _categoryState.value = _categoryState.value!!.copy(
            isLoading = isLoading
        )
    }
}