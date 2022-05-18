/* 
  This file is defined to be used to update achievements (update where) data for a given uid. 

  Here, it will be written as a Promise function. 
*/

const con = require('./database');

/* This function will be used for the req.body.eksplorHurufData is an object with string keys. 
For example, the req.body is: 
  {
    eksplorAngkaData: {
      nol: true,
      satu: true, 
      dua: true,
      etc 
    }
  }
*/
const updateEksplorAngkaObj = (id, obj) => {
  var queryObjString = '';
  for (const [key, value] of Object.entries(obj)) {
    queryObjString += `"${key}"`+ ':' + value + ',';
  }
  return new Promise((resolve, reject) => {
    var query = `UPDATE achievements SET eksplor_angka=JSON_MERGE_PATCH(eksplor_angka, '{${queryObjString}}') WHERE achievements.user_id=${id}`;
    con.query(query, (err, result) => {
      if (err) reject(err);
      resolve(result);
    })
  })
}

module.exports = updateEksplorAngkaObj;
