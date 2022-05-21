var admin = require("firebase-admin");

var serviceAccount = require("./ABAServiceAccount.json"); // ABAServiceAccount

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

module.exports = admin;
