const router = require("express").Router();
const Boxes = require("../database/models/boxes.model");

router.get("/", async (req, res) => {
    const boxes = await Boxes.find({});
    res.json(boxes);
});

router.post("/items", async (req, res) => {
    await Boxes.findOneAndUpdate(
        { id: req.body.boxId },
        { items: req.body.items },
        { upsert: true }
    );
    res.json({ success: true });
});

router.post("/new", async (req, res) => {
    const box = await Boxes.create({
        title: req.body.title,
        description: req.body.description,
    });
    res.json(box);
});

module.exports = router;
