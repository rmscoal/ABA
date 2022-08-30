const admin = require("firebase-admin");
const serviceAccount = require("./ABAServiceAccount.json"); // ABAServiceAccount

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

module.exports = admin;
