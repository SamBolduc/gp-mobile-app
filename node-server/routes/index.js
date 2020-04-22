const router = require("express").Router();
const boxesRoute = require("./boxes.routes");

router.get("/", (req, res) => {
  res.render("home");
});

router.use("/boxes", boxesRoute);

module.exports = router;
