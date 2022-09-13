const con = require("./database");

const getSpecificUser = (id) => {
  return new Promise((resolve, reject) => {
    const query = `select users.nama_user, achievements.* from achievements join users on users.id=achievements.user_id where users.id=${id}`;
    con.query(query, (err, result) => {
      if (err) reject(err);
      resolve(result);
    });
  });
};

module.exports = getSpecificUser;
