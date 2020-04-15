package ca.cshawi.myinventory.boxes

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import ca.cshawi.myinventory.R

class BoxAdapter(val boxes: MutableList<Box>, val onClick: View.OnClickListener) :
    RecyclerView.Adapter<BoxAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.box_card_view)
        val titleView = cardView.findViewById<TextView>(R.id.box_title)
        val statusView = cardView.findViewById<TextView>(R.id.box_status)
        val itemsLayout = cardView.findViewById<LinearLayout>(R.id.box_items_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.box_list_item, parent, false)

        return ViewHolder(viewItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val box = boxes[position]
        holder.cardView.setOnClickListener(onClick)
        holder.cardView.tag = position

        val context = holder.titleView.context

        holder.titleView.text = box.title
        holder.statusView.text = if (box.open) "Ouverte" else "Fermée"
        holder.statusView.setTextColor(
            ContextCompat.getColor(
                context,
                if (box.open) R.color.green else R.color.red
            )
        )

        if (holder.itemsLayout.childCount > 0) holder.itemsLayout.removeAllViews()
        for (i in 0..2) {
            if (i >= boxes.size) break
            val item = box.items[i]

            val textView = TextView(context)
            val params = LinearLayout.LayoutParams(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    0F,
                    context.resources.displayMetrics
                ).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1F
            )

            textView.layoutParams = params
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);

            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.text = "• ${item.name}"

            holder.itemsLayout.addView(textView)
        }
    }

    override fun getItemCount(): Int {
        return boxes.size
    }
}