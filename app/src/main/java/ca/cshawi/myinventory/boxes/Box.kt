package ca.cshawi.myinventory.boxes

import ca.cshawi.myinventory.boxes.items.Item

data class Box(
    var id: Int = -1,
    var title: String = "",
    var open: Boolean = false,
    val items: MutableList<Item> = mutableListOf()
) {}