package com.example.myapplication

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    var name: String?,
    var height: String?, // saved as the index selection in the spinner (0 = 4' tall)
    var weight: String?,
    var age: String?,
    var gender: String?,
    var city: String?,
    var country: String?,
    var imagePath: String?,
    var active: String?,
    var bmr: String?,
    var bmi: String?,
    var regimen: String?,
    var weightGoal: String?,
    var image: Bitmap?,
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Bitmap::class.java.classLoader)
    )

    companion object : Parceler<Profile> {

        override fun Profile.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(height)
            parcel.writeString(weight)
            parcel.writeString(age)
            parcel.writeString(gender)
            parcel.writeString(city)
            parcel.writeString(country)
            parcel.writeString(imagePath)
            parcel.writeString(active)
            parcel.writeString(bmr)
            parcel.writeString(bmi)
            parcel.writeString(regimen)
            parcel.writeString(weightGoal)
            parcel.writeParcelable(image, flags)
        }

        override fun create(parcel: Parcel): Profile {
            return Profile(parcel)
        }
    }
}

