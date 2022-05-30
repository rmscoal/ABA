/* 
This file is defined to update latMengejaHuruf during predictHandler.js predicts the score for the speech recognizitions
of the alphabet. This

Here it will written as a Promise.
*/


const con = require('./database');

/* This function will be used for the req.body.eksplorHurufData is an object with string keys. 
For example, the req.body is: 
  {
    eksplorHurufData: {
      a: true,
      b: true,
      c: true 
    }
  }
*/

const updateSpeechRecogLevel = (level, obj, id) => {
    var queryObjString = '';
    for (const [key, value] of Object.entries(obj)) {
        queryObjString += `"${key}"` + ':' + value;
    }
    return new Promise((resolve, reject) => {
        var columnName = 'latMengejaHuruflvl' + level;
        var query = `UPDATE achievements SET ${columnName} = JSON_MERGE_PATCH(${columnName}, '{${queryObjString}}') WHERE achievements.user_id = ${id}`;
        con.query(query, (err, result) => {
            if (err) reject(err);
            resolve(result);
        })
    })
}

module.exports = updateSpeechRecogLevel;

