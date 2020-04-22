const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const path = require("path");
const router = require("./routes");
require("./database");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, "public")));
app.use("/", router);

app.listen(3000, '192.168.2.17');
