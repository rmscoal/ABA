/* Initialize connecting to MySQL Database */

const mysql = require('mysql') 

const con = mysql.createConnection({
    host: 'localhost', // <This  is development database>
    user: 'root', // <This  is development database>
    password: '', // <This  is development database>
    database: 'aba' // <This  is development database>
})

module.exports = con;
