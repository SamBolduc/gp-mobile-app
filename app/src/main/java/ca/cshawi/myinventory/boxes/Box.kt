package ca.cshawi.myinventory.boxes

import android.os.Parcel
import android.os.Parcelable
import ca.cshawi.myinventory.boxes.items.Item

data class Box(
    var id: Int = -1,
    var title: String = "",
    var open: Boolean = false,
    var modif: Int = 0,
    var items: MutableList<Item> = mutableListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    ) {
        parcel.readList(items, Item::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeByte(if (open) 1 else 0)
        parcel.writeInt(modif)
        parcel.writeList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Box> {
        override fun createFromParcel(parcel: Parcel): Box {
            return Box(parcel)
        }

        override fun newArray(size: Int): Array<Box?> {
            return arrayOfNulls(size)
        }
    }
}