/* Initialize connecting to MySQL Database */
require('dotenv').config({path: '/Users/rmscoal/GitHub/ABA/backend/utils/.env'});

const mysql = require('mysql')

const con = mysql.createConnection({
    host: process.env.DB_HOST, // <This  is development database>
    user: process.env.DB_USER_NAME, // <This  is development database>
    password: process.env.DB_DATABASE_PASSWORD, // <This is development database>is development database>
    database: process.env.DB_DATABASE_NAME, // <This is development database>is development database>
})

module.exports = con;
