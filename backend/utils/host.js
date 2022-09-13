const path = require("path");
require("dotenv").config({ path: path.join(__dirname, "..", "config.env") });

const domain = process.env.DOMAIN || "<aba-vm>";
const hostname = process.env.NODE_ENV !== "production" ? "localhost" : domain;

module.exports = hostname;
