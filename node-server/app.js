require("dotenv").config();

const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const router = require("./routes");
const logger = require("./middlewares/logger");

require("./database");

app.use(logger);
app.use(bodyParser.json());
app.use("/", router);

const url = process.env.SRV_URL;
const port = Number(process.env.SRV_PORT);
app.listen(port, url);
console.log(`Listening on ${url}:${port}`);
