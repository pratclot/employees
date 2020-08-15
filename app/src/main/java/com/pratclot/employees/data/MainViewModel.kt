package com.pratclot.employees.data

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.pratclot.employees.domain.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val repo: Repo
) : ViewModel() {

    private val _specialties: Flow<List<DataItem.Specialty>> = repo.getSpecialties()
    val specialties = _specialties.asLiveData()

    private var _pros = MutableLiveData<List<DataItem.Employee>>()
    val pros: LiveData<List<DataItem.Employee>>
        get() = _pros

    fun updateDb() {
        viewModelScope.launch(Dispatchers.IO) { repo.updateDb() }
    }

    fun getProsBySpec(spec: DataItem.Specialty) {
        viewModelScope.launch(Dispatchers.IO) { _pros.postValue(repo.getEmployeesBySpec(spec)) }
    }
}