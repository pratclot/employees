package com.pratclot.employees.data

import com.pratclot.employees.data.sources.LocalDatasource
import com.pratclot.employees.data.sources.RemoteDatasoucre
import com.pratclot.employees.domain.DataItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repo @Inject constructor(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: RemoteDatasoucre
) {
    fun getSpecialties(): Flow<List<DataItem.Specialty>> {
        return localDatasource.getSpecialties()
    }

    suspend fun updateDb() {
        val employeesList = remoteDatasource.getAll()
        localDatasource.insertEmployees(employeesList)
        localDatasource.insertSpecialties(employeesList)
        localDatasource.insertJunction(employeesList)
    }

    suspend fun getEmployeesBySpec(spec: DataItem.Specialty): List<DataItem.Employee> {
        return localDatasource.getEmployeesBySpec(spec)
    }
}