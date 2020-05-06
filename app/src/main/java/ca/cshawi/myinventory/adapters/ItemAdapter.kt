package ca.cshawi.myinventory.adapters

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import ca.cshawi.myinventory.R
import ca.cshawi.myinventory.boxes.Item

class ItemAdapter(var items: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.item_card_view)
        val titleView = cardView.findViewById<TextView>(R.id.item_title)
        val quantityView = cardView.findViewById<TextView>(R.id.item_quantity)
        val changedQuantity = cardView.findViewById<TextView>(R.id.item_change_count)
        val itemCode = cardView.findViewById<TextView>(R.id.item_code)
        val increaseButton = cardView.findViewById<ImageButton>(R.id.increase_button)
        val decrease_button = cardView.findViewById<ImageButton>(R.id.decrease_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_list_item, parent, false)

        return ViewHolder(viewItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.cardView.tag = position

        holder.titleView.text = item.name
        holder.quantityView.text = "${item.currentAmount}/${item.maxAmount}"
        holder.changedQuantity.text = (if (item.changedQuantity > 0) "+" else "") + item.changedQuantity
        holder.itemCode.text = "${item.barCode}"

        holder.increaseButton.setOnClickListener {
            item.changedQuantity++;

            holder.changedQuantity.setTextColor(ContextCompat.getColor(holder.cardView.context, if (item.changedQuantity < 0) R.color.red else if (item.changedQuantity > 0) R.color.green else R.color.primaryDarkColor))

            notifyItemChanged(position)
        }

        holder.decrease_button.setOnClickListener {
            item.changedQuantity = if (item.currentAmount + item.changedQuantity - 1 >= 0) item.changedQuantity - 1 else item.changedQuantity

            holder.changedQuantity.setTextColor(ContextCompat.getColor(holder.cardView.context, if (item.changedQuantity < 0) R.color.red else if (item.changedQuantity > 0) R.color.green else R.color.primaryDarkColor))

            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}