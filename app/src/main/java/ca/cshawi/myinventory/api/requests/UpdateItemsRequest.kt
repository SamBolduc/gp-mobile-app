package ca.cshawi.myinventory.api.requests

import ca.cshawi.myinventory.boxes.items.Item
import com.google.gson.Gson

class UpdateItemsRequest(val boxId: Int, val items: List<Item>) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}