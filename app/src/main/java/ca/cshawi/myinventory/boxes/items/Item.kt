package ca.cshawi.myinventory.boxes.items

data class Item(
    var id: Int,
    var name: String,
    var description: String,
    var currentAmount: Int = 0,
    var maxAmount: Int = 0,
    var barCode: Int
) {
}