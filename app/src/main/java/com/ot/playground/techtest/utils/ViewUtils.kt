package com.ot.playground.techtest.utils

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafe(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.let { navigate(direction) }
}

fun <T> MutableLiveData<T>.update(updater: (T?) -> T?) {
    updater(this.value)?.let {
        postValue(it)
    }
}