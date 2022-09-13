const pool = require("./database");

const makeNewUser = (uid, name) => {
  return new Promise((resolve, reject) => {
    const query = `insert into users (uid,nama_user) values ('${uid}','${name}')`;
    pool.getConnection((err, connection) => {
      if (err) reject(err);
      connection.query(query, (err, result) => {
        connection.release();
        if (err) reject(err);
        resolve(result);
      });
    });
  });
};

module.exports = makeNewUser;
