const mongoose = require("mongoose");

const itemsSchema = new mongoose.Schema({
    id: {
        type: Number,
        required: true,
        unique: true,
        default: 0
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
},{ _id : false });

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
        default:"",
        maxlength: 50,
        trim: true,
    },
    open: {
        type: Boolean,
        default: false,
    },
    side: 
    {
        type: String,
        maxlength: 1,
        trim: true,
        default: "R",
    },
    modif: 
    {
        type: Number,
        default:0,
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
