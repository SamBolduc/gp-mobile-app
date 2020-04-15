package ca.cshawi.myinventory.boxes.items

import android.os.Parcel
import android.os.Parcelable

data class Item(
    var id: Int,
    var name: String,
    var description: String,
    var currentAmount: Int = 0,
    var maxAmount: Int = 0,
    var barCode: Int,
    var changedQuantity: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(currentAmount)
        parcel.writeInt(maxAmount)
        parcel.writeInt(barCode)
        parcel.writeInt(changedQuantity)
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