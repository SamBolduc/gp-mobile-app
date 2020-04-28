const mongoose = require("mongoose");

const protocol = "mongodb://";
const url = "127.0.0.1";
const params = "?retryWrites=true&w=majority";
const username = "webserver";
const password = "webserver";
const database = "webserver";

const connectionString = `${protocol}${username}:${password}@${url}/${database}${params}`;

const options = {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: false,
    useCreateIndex: true,
};

mongoose
    .connect(connectionString, options)
    .then(() => {
        console.log("Connecté avec succès");
    })
    .catch((err) => {
        console.error(err);
    });
