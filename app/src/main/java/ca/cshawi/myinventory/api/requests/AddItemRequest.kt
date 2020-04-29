package ca.cshawi.myinventory.api.requests

import com.google.gson.Gson

class AddItemRequest(
    val name: String,
    val description: String,
    val currentAmount: Int,
    val maxAmount: Int,
    val barCode: Int
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}