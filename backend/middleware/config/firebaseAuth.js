var admin = require("firebase-admin");

var serviceAccount = require("./ABAServiceAccount.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});
