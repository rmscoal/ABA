/* 
  This file is defined to be used to get data from a given id (select from)
  and retrieve name and achievements data for the user. 

  Here, it will be written as a Promise function. 
*/
const con = require('./database'); 

const getSpecificUser = (id) => {
  return new Promise((resolve, reject) => {
    var query = `select users.nama_user, achievements.* from achievements join users on users.id=achievements.user_id where users.id=${id}`;
    con.query(query, (err, result) => {
      if (err) reject(err);
      resolve(result);
    })
  })
}

module.exports = getSpecificUser;
