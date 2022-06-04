/* 
  This file is defined to be used to insert data (insert into) user for a given uid.

  Here, it will be written as a Promise function. 
*/

const pool = require('./database');

const makeNewUser = (uid,name) => {
  return new Promise((resolve, reject) => {
    var query = `insert into users (uid,nama_user) values ('${uid}','${name}')`;
    pool.getConnection((err, connection) => {
      if (err) reject(err);
      connection.query(query, (err, result) => {
        connection.release();
        if (err) reject(err);
        resolve(result);
      })
    })
  })
}

module.exports = makeNewUser;
