/* Initialize connecting to MySQL Database */
const path = require('path');
require('dotenv').config({path: path.join(__dirname, 'database.env')});

const mysql = require('mysql')

const pool = mysql.createPool({
    connectionLimit: 10,
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE
})

module.exports = pool;
