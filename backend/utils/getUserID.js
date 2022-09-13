const con = require("./database");

const getId = (uid) => {
  return new Promise((resolve, reject) => {
    const query = `select id from users where uid = "${uid}"`;
    con.query(query, (err, result) => {
      if (err) reject(err);
      resolve(result);
    });
  });
};

module.exports = getId;
