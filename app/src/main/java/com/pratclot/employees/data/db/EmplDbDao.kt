package com.pratclot.employees.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmplDbDao {
    @Query("SELECT * FROM specialties")
    abstract fun getSpecialties(): Flow<List<SpecialtyEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEmployee(it: EmployeeEntry): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSpecialty(it: SpecialtyEntry): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertJunction(it: List<EmployeeJunction>): List<Long>

    @Query(
        """
            SELECT es.f_name, es.l_name, es.birthday, e.avatr_url, e.specialty as filter, group_concat(s.name, ":") as specialty FROM empl_spec es
            INNER JOIN specialties s ON es.specialty_id = s.specialty_id
            INNER JOIN employees e ON es.f_name = e.f_name AND es.l_name = e.l_name AND es.birthday = e.birthday
            WHERE filter LIKE :spec
            GROUP BY es.f_name, es.l_name, es.birthday
            """
    )
    suspend fun getEmployeesBySpec(spec: String): List<EmployeeEntry>
}