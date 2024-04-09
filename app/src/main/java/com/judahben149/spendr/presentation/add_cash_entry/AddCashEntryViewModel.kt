package com.judahben149.spendr.presentation.add_cash_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.CashEntryMapperImpl
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCashEntryViewModel @Inject constructor(private val repository: CashFlowRepositoryImpl) :
    ViewModel() {

    private var _state: MutableLiveData<AddCashEntryState> = MutableLiveData(AddCashEntryState())
    val state: LiveData<AddCashEntryState> = _state

    private var _categoryState: MutableLiveData<CategoryListState> =
        MutableLiveData(CategoryListState())
    val categoryState: LiveData<CategoryListState> = _categoryState

    private var _selectedCategoryId: MutableLiveData<Int> = MutableLiveData(-1)
    val selectedCategoryId: LiveData<Int> get() = _selectedCategoryId

    private var _selectedCategoryList: MutableLiveData<List<Category>> =
        MutableLiveData(emptyList())
    val selectedCategoryList: LiveData<List<Category>> get() = _selectedCategoryList

    private var _selectedCategory: MutableLiveData<Category> = MutableLiveData(Category())
    val selectedCategory: LiveData<Category> get() = _selectedCategory


    init {
        getCategoryList()
    }

    fun updateAmount(amount: Int) {
        _state.value = _state.value?.copy(
            amount = amount
        )
    }

    fun updateReason(reason: String) {
        _state.value = _state.value?.copy(
            reason = reason
        )
    }

    fun saveEntry() {
        viewModelScope.launch {
            val cashEntry = CashEntry(
                amount = _state.value!!.amount.toDouble(),
                isIncome = _state.value!!.isIncome,
                transactionDate = _state.value!!.date,
                categoryId = _selectedCategory.value!!.categoryId,
                categoryName = _state.value!!.categoryName,
                categoryIconId = _state.value!!.categoryIconId,
                isIncomeCategory = _state.value!!.isIncomeCategory,
                reason = _state.value!!.reason
            )

            val entity = CashEntryMapperImpl().cashEntryToCashEntryEntity(cashEntry)
            repository.saveEntry(entity)
        }
    }

    fun getCategoryList() {
        viewModelScope.launch {
            repository.getCategories().collect { categoryEntityList ->
                val categoryList = categoryEntityList.map { categoryEntity ->
                    CashEntryMapperImpl().categoryEntityToCategory(categoryEntity)
                }

                _selectedCategoryList.value = categoryList
            }
        }
    }


    fun updateSelectedCategoryId(category: Category) {
        _selectedCategoryId.value = category.categoryId

        //If the entry had no category, initialize it with a default empty one
        val currentCategory = _selectedCategoryList.value?.find { currentCategory ->
            currentCategory.categoryId == category.categoryId
        } ?: Category()

        _selectedCategory.value = currentCategory

        _state.value = _state.value?.copy(
            categoryName = category.categoryName,
            categoryIconId = category.categoryIconId,
            categoryId = category.categoryId,
            isIncomeCategory = category.isIncomeCategory
        )
    }

    fun setCurrentDate(currentDate: Long) {
        _state.value = _state.value!!.copy(
            date = currentDate
        )
    }

    fun setCategoryLoading(isLoading: Boolean) {
        _categoryState.value = _categoryState.value!!.copy(
            isLoading = isLoading
        )
    }

    fun setCashEntryType(isIncome: Boolean) {
        clearSelectedCategory()

        _categoryState.value = _categoryState.value!!.copy(
            isIncomeSelected = isIncome
        )

        _state.value = _state.value?.copy(
            isIncome = isIncome
        )
    }

    fun reset() {
        _state.value = AddCashEntryState()
        _categoryState.value = CategoryListState()
        _selectedCategoryId.value = -1
        _selectedCategoryList.value = emptyList()
        _selectedCategory.value = Category()
        getCategoryList()
    }

    private fun clearSelectedCategory() {
        _categoryState.value = CategoryListState()
        _selectedCategory.value = Category()
    }

    fun updateCashEntry(
        cashEntry: CashEntry
    ) {
        viewModelScope.launch {

            val entity = CashEntryMapperImpl().cashEntryToCashEntryEntity(cashEntry)
            repository.updateEntry(entity)
        }
    }
}