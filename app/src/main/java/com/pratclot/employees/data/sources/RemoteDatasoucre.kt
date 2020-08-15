package com.pratclot.employees.data.sources

import com.pratclot.employees.domain.DataItem
import com.pratclot.employees.service.EmployeeApi
import javax.inject.Inject

class RemoteDatasoucre @Inject constructor(private val employeeApi: EmployeeApi) {
    suspend fun getAll(): List<DataItem.Employee> {
        return employeeApi.getAll().response
    }
}
