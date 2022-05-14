/* Get id (PRIMARY KEY in Users Table) from an uid. This will make query process faster. */
const con = require('./database');

const getId = (uid) => {
    return new Promise ((resolve, reject) => {
        var query = `select id from users where uid = ${uid}`;
        con.query(query, (err, result) => {
        if (err) reject(err);
        resolve(result[0].id);
        })
    })
}

module.exports = getId;
