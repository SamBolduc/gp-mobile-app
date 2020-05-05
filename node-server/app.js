require("dotenv").config();

const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const router = require("./routes");
const path = require("path");
const logger = require("./middlewares/logger");

require("./database");

app.use(logger);
app.use(bodyParser.urlencoded({ extended: true }));
app.use("/", router);

app.use(express.static(path.join(__dirname, "public")));
app.set("view engine", "pug");

const url = process.env.SRV_URL;
const port = Number(process.env.SRV_PORT);
app.listen(port, url);
console.log(`Listening on ${url}:${port}`);
