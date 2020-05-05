const router = require("express").Router();
const Boxes = require("../database/models/boxes.model");

router.get("/", async (req, res) => {
    let boxes = await Boxes.find({}).sort({ id: 1 });
    res.render("simulateur", { armoirs: boxes });
});

router.post("/", async (req, res) => {
    let id = req.body.type;
    let box = await Boxes.findOne({ id });

    if (req.body.state == "open") {
        box.modif++;
        box.open = true;
        await box.save();

        res.render("includes/Armoir", { armoir: box });
    } else {
        box.open = false;
        await box.save();

        res.render("includes/Armoir", { armoir: bxo });
    }
});

module.exports = router;
