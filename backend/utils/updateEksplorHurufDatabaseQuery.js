const pool = require("./database");

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
  let queryObjString = "";
  let i = 0;
  const max = Object.entries(obj).length;
  for (const [key, value] of Object.entries(obj)) {
    if (i === max - 1) {
      queryObjString += `"${key}"` + ":" + value;
    } else {
      queryObjString += `"${key}"` + ":" + value + ",";
    }
    i++;
  }
  // The result of queryObjString would look like this: "a":true, "b":true, "c":true,
  // if necessary (some cases error occurs) we need to delete the last ',' on queryObjString
  // use this code: queryObjString = queryObjString.slice(0, queryObjString.length - 1);
  // however without slicing it works just fine...
  return new Promise((resolve, reject) => {
    const query = `UPDATE achievements SET eksplor_huruf=JSON_MERGE_PATCH(eksplor_huruf, '{${queryObjString}}') WHERE achievements.user_id=${id}`;
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

module.exports = updateEksplorHurufObj;
