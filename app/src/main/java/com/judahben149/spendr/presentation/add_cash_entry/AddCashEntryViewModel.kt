package com.judahben149.spendr.presentation.add_cash_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.MapperImpl
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddCashEntryViewModel @Inject constructor(private val repository: CashFlowRepositoryImpl) :
    ViewModel() {

    private var _state: MutableLiveData<AddCashEntryState> = MutableLiveData(AddCashEntryState())
    val state: LiveData<AddCashEntryState> = _state

    private var _categoryState: MutableLiveData<CategoryListState> = MutableLiveData(CategoryListState())
    val categoryState: LiveData<CategoryListState> = _categoryState

    private var _selectedCategoryId: MutableLiveData<Int> = MutableLiveData(-1)
    val selectedCategoryId: LiveData<Int> get() = _selectedCategoryId


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


    fun updateSelectedCategoryId(categoryId: Int) {
        _selectedCategoryId.value = categoryId
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