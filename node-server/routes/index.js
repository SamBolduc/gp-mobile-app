const router = require("express").Router();
const boxesRoute = require("./boxes.routes");
const simulateurRoutes = require("./simulateur.routes")

router.get("/", (req, res) => {
    res.render("home");
  });
router.use("/boxes", boxesRoute);
router.use("/simulateur", simulateurRoutes)
module.exports = router;
