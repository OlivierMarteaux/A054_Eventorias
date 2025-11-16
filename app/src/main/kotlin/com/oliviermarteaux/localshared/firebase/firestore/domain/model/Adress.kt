package com.oliviermarteaux.localshared.firebase.firestore.domain.model

data class Address(
    val street: String = "",
    val district: String = "",
    val city: String = "",
    val zipCode: String = "",
    val country: String = ""
)
