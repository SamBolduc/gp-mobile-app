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
    const box = await Boxes.create(req.body);
    res.json({ success: true, id: box.id });
});

router.post("/boxes/:id/newItem", async (req, res) => {
    const id = req.params.id;
    const box = await Boxes.findOne({ id });
    box.items.push({ id: box.items.length + 1, ...req.body });
    await box.save();
    res.json({ success: true });
});

module.exports = router;
