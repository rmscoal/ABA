/* !IMPORT MODULES */
const express = require('express');
const http = require('http');

/* IMPORT FILES FROM OTHER DIRECTORIES */

// UTILS - this might not be necessary/needed!
const hostname = require('./utils/host');

// MIDDLEWARE - this will be used for Firebase authentications!
const middlewareFirebase = require('./middleware/firebaseAdmin.js');

/* APP */
app = express();

app.use(middlewareFirebase);
app.use(express.json());
app.use(express.urlencoded({extended: false}));

/* Starting HTTP App */
const httpServer = http.createServer(app);
httpServer.listen(port, () => {
  console.log(`App is listening in ${hostname} on ${port}!`);
})
