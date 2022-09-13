const pool = require("./database");

// get the current score of the user in practice sections
const getPractiveActivity = (activityName, level, id) => {
  const columnName = activityName + "lvl" + level;
  return new Promise((resolve, reject) => {
    const query = `SELECT ${columnName} FROM achievements WHERE user_id = ${id}`;
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

// update score for user in practice sections
const updatePracticeActivityPlusOne = (activityName, level, id) => {
  const columnName = activityName + "lvl" + level;
  return new Promise((resolve, reject) => {
    const query = `UPDATE achievements SET ${columnName} = ${columnName} + 1 WHERE user_id = ${id}`;
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

module.exports = {
  getPractiveActivity,
  updatePracticeActivityPlusOne
};
