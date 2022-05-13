/* 
  This file is defined to be used to get data from a given uid (select from)
  and retrieve name and achievements data for the user. 

  Here, it will be written as a Promise function. 
*/
const mysql = require('mysql') 

const con = mysql.createConnection({
    host: 'localhost',
    user: 'root', 
    password: '',
    database: 'aba'
});

const getSpecificUser = (uid) => {
  return new Promise((resolve, reject) => {
    var query = `select * from users where uid = ${uid}`;
    con.query(query, (err, result) => {
      if (err) reject(err);
      resolve(result);
    })
  })
}

module.exports = getSpecificUser;
