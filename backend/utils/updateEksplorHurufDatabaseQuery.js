/* 
  This file is defined to be used to update achievements (update where) data for a given uid. 

  Here, it will be written as a Promise function. 
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
const updateEksplorHurufObj = (id, obj) => {
  var queryObjString = '';
  let i = 0;
  const max = Object.entries(obj).length;
  for (const [key, value] of Object.entries(obj)) {
    if (i === max - 1) {
      queryObjString += `"${key}"`+ ':' + value; 
    } else {
      queryObjString += `"${key}"`+ ':' + value + ',';
    }
    i++;
  }
  // the result of queryObjString would look like this: "a":true, "b":true, "c":true,
  // if necessary (some cases error occurs) we need to delete the last ',' on queryObjString
  // use this code: queryObjString = queryObjString.slice(0, queryObjString.length - 1);
  // however without slicing it works just fine... 
  return new Promise((resolve, reject) => {
    var query = `UPDATE achievements SET eksplor_huruf=JSON_MERGE_PATCH(eksplor_huruf, '{${queryObjString}}') WHERE achievements.user_id=${id}`;
    con.query(query, (err, result) => {
      if (err) reject(err);
      resolve(result);
    })
  })
}

module.exports = updateEksplorHurufObj;
