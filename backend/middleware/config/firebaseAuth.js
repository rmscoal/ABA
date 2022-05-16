var admin = require("firebase-admin");

var serviceAccount = require("./exampleServiceAccountt.json"); // ABAServiceAccount

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

module.exports = admin;
