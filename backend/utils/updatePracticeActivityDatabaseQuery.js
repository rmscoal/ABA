/* 
  This file is defined to be used to update achievements in practice sections (update where) data for a given id. 
  This file is also defined to be used to get the current score in practice sections for given id.

  Here, it will be written as a Promise function. 
*/

const pool = require('./database');

// get the current score of the user in practice sections
const getPractiveActivity = (activityName, level, id) => {
    var columnName = activityName+'lvl'+level;
    return new Promise((resolve, reject) => {
        var query = `SELECT ${columnName} FROM achievements WHERE user_id = ${id}`; 
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

// update score for user in practice sections
const updatePracticeActivityPlusOne = (activityName, level, id) => {
    var columnName = activityName+'lvl'+level;
    return new Promise((resolve, reject) => {
        var query = `UPDATE achievements SET ${columnName} = ${columnName} + 1 WHERE user_id = ${id}`; 
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

module.exports = {
    getPractiveActivity,
    updatePracticeActivityPlusOne
};
