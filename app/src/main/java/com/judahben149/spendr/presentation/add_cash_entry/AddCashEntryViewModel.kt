package com.judahben149.spendr.presentation.add_cash_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.MapperImpl
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
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

    fun saveEntry() {
        viewModelScope.launch {
            val cashEntry = CashEntry(
                amount = _state.value!!.amount.toDouble(),
                isIncome = _state.value!!.isIncome,
                transactionDate = _state.value!!.date,
                categoryId = _selectedCategory.value!!.categoryId
            )

            val entity = MapperImpl().cashEntryToCashEntryEntity(cashEntry)
            repository.saveEntry(entity)
        }
    }

    fun getCategoryList() {
        viewModelScope.launch {
            repository.getCategories().collect { categoryEntityList ->
                val categoryList = categoryEntityList.map { categoryEntity ->
                    MapperImpl().categoryEntityToCategory(categoryEntity)
                }

                _selectedCategoryList.value = categoryList
            }
        }
    }


    fun updateSelectedCategoryId(categoryId: Int) {
        _selectedCategoryId.value = categoryId
        _selectedCategory.value = _selectedCategoryList.value?.find { category ->
            category.categoryId == categoryId
        }
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