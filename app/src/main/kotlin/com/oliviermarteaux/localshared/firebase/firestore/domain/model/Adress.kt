package com.oliviermarteaux.localshared.firebase.firestore.domain.model

import java.io.Serializable

data class Address(
    val street: String = "",
    val district: String = "",
    val city: String = "",
    val zipCode: String = "",
    val country: String = ""
): Serializable{

    var fullAddress: String = ""
        get() = field.ifEmpty { computeFullAddress() }

    private fun computeFullAddress() = listOf(street, district, city, zipCode, country)
        .filter { it.isNotBlank() }
        .joinToString(", ")
    // fun getFullAddress(): String = fullAddress?: "$street, $district, $city, $zipCode, $country"
}
