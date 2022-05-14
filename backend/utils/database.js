/* A file for connecting to MySQL database */

const mysql = require('mysql') 

const con = mysql.createConnection({
    host: 'localhost',
    user: 'root', 
    password: '',
    database: 'aba'
})

module.exports = con;
