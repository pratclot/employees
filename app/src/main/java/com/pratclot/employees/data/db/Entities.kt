package com.pratclot.employees.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pratclot.employees.domain.DataItem

@Entity(
    tableName = "employees", primaryKeys = [
        "f_name", "l_name", "birthday"
    ]
)
class EmployeeEntry(
    @ColumnInfo(name = "f_name")
    val f_name: String,
    @ColumnInfo(name = "l_name")
    val l_name: String,
    @ColumnInfo(name = "birthday")
    val birthday: String,
    @ColumnInfo(name = "avatr_url")
    val avatr_url: String?,
//    @ForeignKey(
//        entity = SpecialtyEntry::class,
//        parentColumns = ["specialty_id"],
//        childColumns = ["specialty"]
//    )
    @ColumnInfo(name = "specialty")
    val specialty: String
) {
    fun asDomainModel(): DataItem.Employee {

        return DataItem.Employee(
            f_name,
            l_name,
            birthday,
            avatr_url,
            specialty.split(":").flatMap {
                listOf(DataItem.Specialty(0, it))
            }
        )
    }
}

@Entity(tableName = "specialties")
class SpecialtyEntry(
    @PrimaryKey
    @ColumnInfo(name = "specialty_id")
    val specialty_id: Int,
    @ColumnInfo(name = "name")
    val name: String
) {
    fun asDomainModel(): DataItem.Specialty {
        return DataItem.Specialty(
            specialty_id,
            name
        )
    }
}

@Entity(
    tableName = "empl_spec", primaryKeys = [
        "f_name", "l_name", "birthday", "specialty_id"]
)
class EmployeeJunction(
    val f_name: String,
    val l_name: String,
    val birthday: String,
    val specialty_id: String
)
