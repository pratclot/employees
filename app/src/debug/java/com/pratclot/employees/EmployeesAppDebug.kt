package com.pratclot.employees

import com.facebook.stetho.Stetho

open class EmployeesAppDebug: EmployeesApp() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(applicationContext)
    }
}