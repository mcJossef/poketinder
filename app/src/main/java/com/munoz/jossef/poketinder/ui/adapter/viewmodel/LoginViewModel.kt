package com.munoz.jossef.poketinder.ui.adapter.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.munoz.jossef.poketinder.database.SharedPreferencesRepository

class LoginViewModel (
    val context: Context
): ViewModel() {
    val inputsError = MutableLiveData<Boolean>()
    val emailFormatError = MutableLiveData<Boolean>()
    val authError = MutableLiveData<Boolean>()
    val loginSuccess = MutableLiveData<Boolean>()

    private var sharedPreferencesRepository: SharedPreferencesRepository =
        SharedPreferencesRepository().also {
            it.setSharedPreference(context)
        }

    fun validateInputs(email: String, password: String){
        if(isEmptyInputs(email,password)){
            inputsError.postValue(true)
            return
        }
        if (!isValidEmail(email)) {
            emailFormatError.postValue(true)
            return
        }

        val emailSharedPreferences=sharedPreferencesRepository.getUserEmail()
        val passwordSharedPreferences=sharedPreferencesRepository.getUserPassword()

        if (emailSharedPreferences==email&&passwordSharedPreferences==password){

        }
    }
    fun isEmptyInputs(email: String, password: String)=email.isEmpty()||password.isEmpty()
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}