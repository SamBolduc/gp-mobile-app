const router = require("express").Router();
const boxesRoute = require("./boxes.routes");

router.use("/boxes", boxesRoute);

module.exports = router;
