const router = require("express").Router();
const Boxes = require("../database/models/boxes.model")

router.get("/", (req, res) => {
  Boxes.find({})
    .then((boxes)=>{
      res.json(boxes)
    })
    .catch((e)=> {
      console.log(e)
      res.end()
    })
});

router.post("/items", (req, res) => {
  const data = JSON.parse(req.body.data);
  Boxes.findOneAndUpdate(
      {id: data.boxId},
      {items: data.items},
      {upsert: true}
    )
    .then((result)=>{
      res.json({success: true});
    })
    .catch((e)=> {  
      console.log(e)
      res.end()
    })
});


module.exports = router;
 