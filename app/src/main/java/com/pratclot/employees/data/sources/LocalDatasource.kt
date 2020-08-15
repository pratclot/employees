package com.pratclot.employees.data.sources

import com.pratclot.employees.data.db.EmplDbDao
import com.pratclot.employees.domain.DataItem
import com.pratclot.employees.domain.asJunctionList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDatasource @Inject constructor(private val emplDbDao: EmplDbDao) {
    fun getSpecialties(): Flow<List<DataItem.Specialty>> {
        return emplDbDao.getSpecialties().map {
            it.map { it.asDomainModel() }
        }
    }

    fun insertEmployees(employeesList: List<DataItem.Employee>) {
        employeesList.map {
            emplDbDao.insertEmployee(it.asDbModel())
        }
    }

    fun insertSpecialties(employeesList: List<DataItem.Employee>) {
        val specialties = employeesList.flatMap {
            it.specialty.flatMap {
                setOf(it)
            }
        }
        specialties.map {
            emplDbDao.insertSpecialty(it.asDbModel())
        }
    }

    suspend fun getEmployeesBySpec(spec: DataItem.Specialty): List<DataItem.Employee> {
        return emplDbDao.getEmployeesBySpec("%${spec.specialty_id}%").map { it.asDomainModel() }
    }

    fun insertJunction(employeesList: List<DataItem.Employee>) {
        emplDbDao.insertJunction(employeesList.asJunctionList())
    }
}
