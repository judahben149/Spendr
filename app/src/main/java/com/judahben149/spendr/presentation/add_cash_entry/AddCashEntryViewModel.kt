package com.judahben149.spendr.presentation.add_cash_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.MapperImpl
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

    private var _categoryState: MutableLiveData<CategoryListState> = MutableLiveData(CategoryListState())
    val categoryState: LiveData<CategoryListState> = _categoryState

    private var _selectedCategoryState: MutableLiveData<SelectedCategoryState> = MutableLiveData(SelectedCategoryState())
    val selectedCategoryState: LiveData<SelectedCategoryState> get() = _selectedCategoryState

    fun getCategories() {
        setCategoryLoading(true)

        viewModelScope.launch {
            val categoryList = repository.getCategories().collect { categoryEntityList ->
                val categoryList = categoryEntityList.map { categoryEntity ->
                    MapperImpl().categoryEntityToCategory(categoryEntity)
                }

                _categoryState.value = _categoryState.value!!.copy(categoryList = categoryList)
                setCategoryLoading(false)
            }
        }
    }

    fun updateAmount(amount: Int) {
        _state.value = _state.value?.copy(
            amount = amount
        )
    }

    fun saveEntry() {
        viewModelScope.launch {
            val cashEntry = CashEntry(
                amount = _state.value!!.amount.toDouble(),
                isIncome = _state.value!!.isIncome,
                transactionDate =_state.value!!.date
            )

            val entity = MapperImpl().cashEntryToCashEntryEntity(cashEntry)
            repository.saveEntry(entity)
        }
    }

    fun saveNewCategory(categoryName: String) {
        viewModelScope.launch {
            val category = Category(
                categoryId = 0,
                categoryName = categoryName
            )

            val categoryEntity = MapperImpl().categoryToCategoryEntity(category)
            repository.saveNewCategory(categoryEntity)
        }
    }

    fun updateSelectedCategoryId(categoryId: Int) {
        val previousSelectedId = _selectedCategoryState.value?.currentSelectedId ?: -2

        _selectedCategoryState.value = _selectedCategoryState.value!!.copy(
            currentSelectedId = categoryId,
            previousSelectedId = previousSelectedId
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
        _categoryState.value = _categoryState.value!!.copy(
            isIncomeSelected = isIncome
        )

        _state.value = _state.value?.copy(
            isIncome = isIncome
        )
    }
}