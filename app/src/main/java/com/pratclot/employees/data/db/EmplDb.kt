package com.pratclot.employees.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        EmployeeEntry::class,
        SpecialtyEntry::class,
        EmployeeJunction::class
    ],
    version = 8
)
abstract class EmplDb : RoomDatabase() {
    abstract val emplDbDao: EmplDbDao
}