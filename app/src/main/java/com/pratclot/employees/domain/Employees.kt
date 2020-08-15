package com.pratclot.employees.domain

import android.os.Parcelable
import com.pratclot.employees.data.db.EmployeeEntry
import com.pratclot.employees.data.db.EmployeeJunction
import com.pratclot.employees.data.db.SpecialtyEntry
import kotlinx.android.parcel.Parcelize

data class Employees(
    val response: List<DataItem.Employee>
)

sealed class DataItem: Parcelable {
    @Parcelize
    class Employee(
        val f_name: String,
        val l_name: String,
        val birthday: String?,
        val avatr_url: String?,
        val specialty: List<Specialty>
    ) : DataItem() {
        fun asDbModel(): EmployeeEntry {
            return EmployeeEntry(
                f_name,
                l_name,
                birthday ?: "-",
                avatr_url ?: "unspecified",
                specialty.joinToString(":") {
                    it.specialty_id.toString()
                }
            )
        }

        fun asEmployeeJunction(): List<EmployeeJunction> {
            return specialty.map {
                EmployeeJunction(
                    f_name,
                    l_name,
                    birthday ?: "-",
                    it.specialty_id.toString()
                )
            }
        }
    }

    @Parcelize
    class Specialty(
        val specialty_id: Int,
        val name: String
    ) : DataItem() {
        fun asDbModel(): SpecialtyEntry {
            return SpecialtyEntry(
                specialty_id,
                name
            )
        }
    }
}

fun List<DataItem.Employee>.asJunctionList(): List<EmployeeJunction> {
    return this.flatMap {
        it.asEmployeeJunction()
    }
}
