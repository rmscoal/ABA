/* Initialize connecting to MySQL Database */
const path = require('path');
require('dotenv').config({path: path.join(__dirname, 'database.env')});

const mysql = require('mysql')

const con = mysql.createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD_DEV,
    database: process.env.DB_DATABASE
})

module.exports = con;
