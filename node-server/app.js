const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const router = require("./routes");

require("./database");

app.use(bodyParser.json());
app.use("/", router);

app.listen(3000, "192.168.2.17");
