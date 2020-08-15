package com.pratclot.employees.service

import com.pratclot.employees.domain.Employees
import retrofit2.http.GET

interface EmployeeApi {
    @GET("65gb/static/raw/master/testTask.json")
    suspend fun getAll(): Employees
}