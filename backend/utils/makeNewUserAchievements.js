const pool = require("./database");

const makeNewUserAchievements = (userID) => {
  return new Promise((resolve, reject) => {
    const query = `INSERT INTO achievements (achv_id, user_id, eksplor_huruf, eksplor_angka, latMenyusunKatalvl1, latMenyusunKatalvl2, latMenyusunKatalvl3, latMengejaHuruflvl1, latMengejaHuruflvl2, latMengejaHuruflvl3) VALUES (NULL, ${userID}, '{}', '{}', '0', '0', '0', '{}', '{}', '{}')`;
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

module.exports = makeNewUserAchievements;
