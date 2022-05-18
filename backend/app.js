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
app = express();

app.use(middlewareFirebase.decodeToken); // use firebaseAdmin that is exported as a class
app.use(express.json());
app.use(express.urlencoded({extended: false}));

/* 
  @ ROUTES
*/
app.use('/user', userRoute);
app.use('/achievements', achievementRoute);

/* 
  @ START APP 
*/
const httpServer = http.createServer(app);
httpServer.listen(port, () => {
  console.log(`App is listening in ${hostname} on ${port}!`);
})