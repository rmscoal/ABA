/* 
This file is defined to update latMengejaHuruf during predictHandler.js predicts the score for the speech recognizitions
of the alphabet. This

Here it will written as a Promise.
*/

const pool = require('./database');

const updateSpeechRecogLevel = (level, obj, id) => {
    var queryObjString = '';
    for (const [key, value] of Object.entries(obj)) {
        queryObjString += `"${key}"` + ':' + value;
    }
    return new Promise((resolve, reject) => {
        var columnName = 'latMengejaHuruflvl' + level;
        var query = `UPDATE achievements SET ${columnName} = JSON_MERGE_PATCH(${columnName}, '{${queryObjString}}') WHERE achievements.user_id = ${id}`;
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

module.exports = updateSpeechRecogLevel;

