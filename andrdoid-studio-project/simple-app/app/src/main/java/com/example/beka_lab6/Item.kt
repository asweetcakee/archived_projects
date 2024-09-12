package com.example.beka_lab6

import android.os.Parcel
import android.os.Parcelable

data class Item(
    val id: Int,
    val image: String,
    val title: String,
    val description: String,
    val text: String,
    val price: Double,
    var quantityInCart: Int = 0,
    var totalItemPrice: Double = 0.0) :
    Parcelable {
    // Implements Parcelable methods
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt()
    )

    // Writes data from an Item object to a Parcel object
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(text)
        parcel.writeDouble(price)
        parcel.writeInt(quantityInCart)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}