const express = require("express");
const http = require("http");
const path = require("path");
require("dotenv").config({
  path: path.join(__dirname, "config.env")
});

/**
 * @import available routes in the application
 */
const userRoute = require("./routes/userRoute");
const achievementRoute = require("./routes/achievementRoute");
const predictionRoute = require("./routes/predictionRoute");
const healthRoute = require("./routes/healthRoute");
const rimakataRoute = require("./routes/rimakataRoute");

const port = process.env.PORT_HTTP;
const hostname = require("./utils/host");

/**
 * @middleware
 */
const middlewareFirebase = require("./middleware/firebaseAdmin.js");

/**
 * @server
 */
const app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

/**
 * @routes
 */
app.use("/users", middlewareFirebase.decodeToken, userRoute);
app.use("/achievements", middlewareFirebase.decodeToken, achievementRoute);
app.use("/predictions", middlewareFirebase.decodeToken, predictionRoute);
app.use("/rimakatawords", middlewareFirebase.decodeToken, rimakataRoute);
app.use("/healthchecks", healthRoute);

const httpServer = http.createServer(app);
httpServer.listen(port, () => {
  console.log(`App is listening in ${hostname} on ${port}!`);
});
