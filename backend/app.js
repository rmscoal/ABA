const express = require("express");
const http = require("http");
const path = require("path");
require("dotenv").config({
  path: path.join(__dirname, "config.env")
});

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
const allRoutes = require("./routes/allRoutes");
app.use("/users", middlewareFirebase.decodeToken, allRoutes.userRoute);
app.use("/achievements", middlewareFirebase.decodeToken, allRoutes.achievementRoute);
app.use("/predictions", middlewareFirebase.decodeToken, allRoutes.predictionRoute);
app.use("/rimakatawords", middlewareFirebase.decodeToken, allRoutes.rimakataRoute);
app.use("/healthchecks", allRoutes.healthRoute);

const httpServer = http.createServer(app);
httpServer.listen(port, () => {
  console.log(`App is listening in ${hostname} on ${port}!`);
});
