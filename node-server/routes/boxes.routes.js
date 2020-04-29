const router = require("express").Router();
const Boxes = require("../database/models/boxes.model");

router.get("/", async (req, res) => {
    const boxes = await Boxes.find({});
    res.json(boxes);
});

router.post("/items", async (req, res) => {
    const data = JSON.parse(req.body.data);

    await Boxes.findOneAndUpdate(
        { id: data.boxId },
        { 
            modif: 0,
            items: data.items
        },
        { upsert: true }
    );
    res.json({ success: true });
});

router.post("/new", async (req, res) => {
    const data = JSON.parse(req.body.data);

    const box = await Boxes.create({
        title: data.title,
        description: data.description
    });
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
