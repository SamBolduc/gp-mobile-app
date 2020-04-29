const router = require("express").Router();
const ArmoirDB = require("../database/models/boxes.model");


//#region -->(ok) GET de simulateur.pug
router.get("/", (req, res) => {
  ArmoirDB
    .find({})
    .sort({id:1})
    .then((ArmoirsDB) => {
      //console.log("ARMOIRS : ", ArmoirsDB);
      res.render("simulateur", { armoirs: ArmoirsDB });
    })

    .catch((error) => {
      console.log(error);
    });
});
//#endregion


//#region -->(ok) POST de homeTasks
router.post("/", (req, res) => {
  let id = req.body.type;

  //#region -->(ok) Partie du post par BTN OPEN
  if (req.body.state == "open")
  {
    ArmoirDB
      .findOne({id : id})
      
      .then(item => {
          item.modif++;
          item.open=true;
          item.save()

          res.render("includes/Armoir", {armoir: item});
      })
      
      .catch(e => {
          console.log(e)
          res.end()
      })
  }
  //#endregion
  

  //#region -->(ok) Partie du post par BTN CLOSE
  else
  {
    ArmoirDB
      .findOne({id : id})
      
      .then(item => {
          item.open=false;
          item.save()

          res.render("includes/Armoir", {armoir: item});
      })
      
      .catch(e => {
          console.log(e)
          res.end()
      })
  }
  //#endregion
   
});
//#endregion

module.exports = router;