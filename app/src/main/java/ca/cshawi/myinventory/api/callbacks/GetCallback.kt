package ca.cshawi.myinventory.api.callbacks

import ca.cshawi.myinventory.MainActivity
import ca.cshawi.myinventory.MyInventory
import ca.cshawi.myinventory.activities.ShowItemsActivity
import ca.cshawi.myinventory.boxes.Box
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetCallback() : Callback<List<Box>> {

    override fun onFailure(call: Call<List<Box>>, t: Throwable) {
        t.printStackTrace()
    }

    override fun onResponse(call: Call<List<Box>>, response: Response<List<Box>>) {
        when (val activity = MyInventory.CURRENT) {
            is MainActivity -> {
                activity.boxes.clear()
                response.body()?.forEach { activity.boxes.add(it) }
                activity.adapter.notifyDataSetChanged();
            }
            is ShowItemsActivity -> {
                val oldItems = activity.box.items.toList()
                activity.box.items.clear()

                response.body()?.filter { box -> box.id == activity.box.id }?.forEach {
                    it.items.forEach { item ->
                        val oldItem = oldItems.firstOrNull { oldItem -> oldItem.id == item.id }
                        if (oldItem != null) item.changedQuantity = oldItem.changedQuantity
                        else activity.adapter.notifyDataSetChanged()
                        activity.box.items.add(item.copy())
                    }
                }

                activity.adapter.items = activity.box.items
            }
        }
    }
}