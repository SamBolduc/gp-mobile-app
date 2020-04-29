package ca.cshawi.myinventory.api.requests

import com.google.gson.Gson

class AddBoxRequest(val title: String, val description: String) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}