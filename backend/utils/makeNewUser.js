/* 
  This file is defined to be used to insert data (insert into) for a given uid 
  and sets the achievements to default = 0.

  Here, it will be written as a Promise function. 
*/

const con = require('./database');

const makeNewUser = (uid,name) => {
  return new Promise((resolve, reject) => {
    var query = `insert into users (uid,nama_user) values ('${uid}','${name}')`;
    con.query(query, (err, result) => {
      if (err) reject(err);
      resolve(result);
    })
  })
}

module.exports = makeNewUser;
