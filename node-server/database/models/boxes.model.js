const mongoose = require("mongoose");

const itemsSchema = new mongoose.Schema({
    id: {
        type: Number,
        requied: true,
        unique: true,
        default: 0,
    },
    name: {
        type: String,
        required: true,
        maxlength: 25,
        trim: true,
    },
    description: {
        type: String,
        required: true,
        maxlength: 50,
        trim: true,
    },
    currentAmount: {
        type: Number,
        default: 0,
    },
    maxAmount: {
        type: Number,
        default: 0,
    },
    barCode: {
        type: Number,
        required: true,
    },
});

const schema = new mongoose.Schema({
    id: {
        type: Number,
        required: true,
        unique: true,
        default: 0,
    },
    title: {
        type: String,
        required: true,
        maxlength: 25,
        trim: true,
    },
    description: {
        type: String,
        required: true,
        maxlength: 50,
        trim: true,
    },
    open: {
        type: Boolean,
        default: false,
    },
    items: [itemsSchema],
});


schema.pre("save", function (next) {
    if (!this.isNew) {
        next();
        return;
    }

    Boxes.getNextBoxId((id) => {
        this.id = id;
        next();
    });
});

schema.statics.getNextBoxId = function(callback){
    this.countDocuments((err, count)=>{
      if(count){
        this.findOne()
          .sort({id: -1})
          .exec((err,box)=>{
            callback(box.id + 1)
          })
      } else {
        callback(0)
      }
    })
  }

  const Boxes = mongoose.model("Boxes", schema);


module.exports = Boxes;
