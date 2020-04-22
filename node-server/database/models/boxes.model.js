const mongoose = require("mongoose");

const itemsSchema = new mongoose.Schema({
    id:{
        type: Number,
        requied: true,
        unique: true,
        default: 0
    },
    name:{
        type: String,
        required: true,
        maxlength: 25,
        trim:true
    },
    description:{
        type: String,
        required: true,
        maxlength: 50,
        trim:true
    },
    currentAmount:{
        type: Number,
        default: 0
    },
    maxAmount:{
        type: Number,
        default: 0
    },
    barCode:{
        type: Number,
        required: true
    }
})

const schema = new mongoose.Schema({
    id:{
        type: Number,
        requied: true,
        unique: true,
        default: 0
    },
    title:{
        type: String,
        required: true,
        maxlength: 25,
        trim:true
    },
    open:{
        type: Boolean,
        default: false
    },
    items: [itemsSchema]
});

const Boxes = mongoose.model("Boxes", schema);

module.exports = Boxes;