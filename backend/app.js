/* 
  @ IMPORT MODULES 
*/
const express = require('express');
const http = require('http');

/* 
  @ IMPORT ROUTES FROM OTHER DIRECTORIES 
*/
const userRoute = require('./routes/userRoute');
const achievementRoute = require('./routes/achievementRoute');
const predictionRoute = require('./routes/predictionRoute');

/* 
  @ UTILS 
*/
const port = process.env.PORT_HTTP || 8080;
const hostname = require('./utils/host');

/* 
  @ IMPORT MIDDLEWARE FOR FIREBASE AUTHORIZATION
*/
const middlewareFirebase = require('./middleware/firebaseAdmin.js');
// const { app } = require('firebase-admin');

/* 
  @ APP 
*/
const app = express();

app.use(middlewareFirebase.decodeToken); // use firebaseAdmin that is exported as a class
app.use(express.json());
app.use(express.urlencoded({extended: false})); // Don't forget to set the header Content-Type: application/json

/* 
  @ ROUTES
*/
app.use('/users', userRoute);
app.use('/achievements', achievementRoute);
app.use('/predictions', predictionRoute);

/* 
  @ START APP 
*/
const httpServer = http.createServer(app);
httpServer.listen(port, () => {
  console.log(`App is listening in ${hostname} on ${port}!`);
})
