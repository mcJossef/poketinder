package com.munoz.jossef.poketinder

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel (val context: Context
): ViewModel() {
    val inputsError = MutableLiveData<Boolean>()
    val emailFormatError = MutableLiveData<Boolean>()
    val passwordsMismatchError = MutableLiveData<Boolean>()
    val authError = MutableLiveData<Boolean>()
    val registerSuccess = MutableLiveData<Boolean>()

    private var sharedPreferencesRepository: SharedPreferencesRepository =
        SharedPreferencesRepository().also {
            it.setSharedPreference(context)
        }

    fun validateInputs(email: String, password: String, confirmPassword: String){
        if(isEmptyInputs(email,password, confirmPassword)){
            inputsError.postValue(true)
            return
        }
        if (!isValidEmail(email)) {
            emailFormatError.postValue(true)
            return
        }
        if (password != confirmPassword) {
            passwordsMismatchError.postValue(true)
            return
        }

        val emailSharedPreferences=sharedPreferencesRepository.getUserEmail()
        val passwordSharedPreferences=sharedPreferencesRepository.getUserPassword()

        if (emailSharedPreferences==email&&passwordSharedPreferences==password){

        }
    }
    private fun isEmptyInputs(email: String, password: String, confirmPassword: String) =
        email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}