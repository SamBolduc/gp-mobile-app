package ca.cshawi.myinventory.api.callbacks

import ca.cshawi.myinventory.MainActivity
import ca.cshawi.myinventory.MyInventory
import ca.cshawi.myinventory.ShowItemsActivity
import ca.cshawi.myinventory.boxes.Box
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetCallback() : Callback<List<Box>> {

    override fun onFailure(call: Call<List<Box>>, t: Throwable) {
        t.printStackTrace()
    }

    override fun onResponse(
        call: Call<List<Box>>,
        response: Response<List<Box>>
    ) {
        when (val activity = MyInventory.CURRENT) {
            is MainActivity -> {
                activity.boxes.clear()
                response.body()?.forEach { activity.boxes.add(it) }
                activity.adapter.notifyDataSetChanged();
            }
            is ShowItemsActivity -> {
                activity.box.items.clear()
                response.body()?.filter { box -> box.id == activity.box.id }?.forEach {
                    activity.box.items.addAll(it.items)
                }
                activity.adapter.notifyDataSetChanged();
            }
        }
    }
}