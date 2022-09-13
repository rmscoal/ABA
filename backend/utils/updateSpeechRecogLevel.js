const pool = require("./database");

const updateSpeechRecogLevel = (id, level, obj) => {
  let queryObjString = "";
  for (const [key, value] of Object.entries(obj)) {
    queryObjString += `"${key}"` + ":" + value;
  }
  return new Promise((resolve, reject) => {
    const columnName = "latMengejaHuruflvl" + level;
    const query = `UPDATE achievements SET ${columnName} = JSON_MERGE_PATCH(${columnName}, '{${queryObjString}}') WHERE achievements.user_id = ${id}`;
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

module.exports = updateSpeechRecogLevel;
