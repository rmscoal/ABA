/* 
  @ IMPORT MODULES 
*/
const express = require('express');
const http = require('http');
const path = require('path');
require('dotenv').config({path: path.join(__dirname, 'config.env')});

/* 
  @ IMPORT ROUTES FROM OTHER DIRECTORIES 
*/
const userRoute = require('./routes/userRoute');
const achievementRoute = require('./routes/achievementRoute');
const predictionRoute = require('./routes/predictionRoute');
const healthRoute = require('./routes/healthRoute');
const rimakataRoute = require('./routes/rimakataRoute');

/* 
  @ UTILS 
*/
const port = process.env.PORT_HTTP;
const hostname = require('./utils/host');

/* 
  @ IMPORT MIDDLEWARE FOR FIREBASE AUTHORIZATION
*/
const middlewareFirebase = require('./middleware/firebaseAdmin.js');

/* 
  @ APP 
*/
const app = express();

app.use(express.json());
app.use(express.urlencoded({extended: false})); // Don't forget to set the header Content-Type: application/json

/* 
  @ ROUTES
*/
app.use('/users', middlewareFirebase.decodeToken, userRoute);
app.use('/achievements', middlewareFirebase.decodeToken, achievementRoute);
app.use('/predictions', middlewareFirebase.decodeToken, predictionRoute);
app.use('/rimakatawords', middlewareFirebase.decodeToken, rimakataRoute);
app.use('/healthchecks', healthRoute);

/* 
  @ START APP 
*/
const httpServer = http.createServer(app);
httpServer.listen(port, () => {
  console.log(`App is listening in ${hostname} on ${port}!`);
})
