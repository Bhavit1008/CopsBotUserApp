package com.example.copsbotuserapp.models.viewmodels

import androidx.lifecycle.LiveData

interface AuthListner {
    fun success(response: LiveData<String>)
    fun failure()
}