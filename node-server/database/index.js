const mongoose = require("mongoose");

const connectionString = process.env.DB_URL;

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
