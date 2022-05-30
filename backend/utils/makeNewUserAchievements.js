/* 
  This file is defined to be used to insert data (insert into) for a given uid 
  and sets the achievements to its default value. 

  Here, it will be written as a Promise function. 
*/

const con = require('./database');

const makeNewUserAchievements = (user_id) => {
  return new Promise((resolve, reject) => {
    var query = `INSERT INTO achievements (id, user_id, eksplor_huruf, eksplor_angka, latMenyusunKatalvl1, latMenyusunKatalvl2, latMenyusunKatalvl3, latMengejaHuruflvl1, latMengejaHuruflvl2, latMengejaHuruflvl3) VALUES (NULL, ${user_id}, '{}', '{}', '0', '0', '0', '{}', '{}', '{}')`;
    con.query(query, (err, result) => {
      if (err) reject(err);
      resolve(result);
    })
  })
}

module.exports = makeNewUserAchievements;
