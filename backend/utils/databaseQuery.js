const path = require("path");
require("dotenv").config({ path: path.join(__dirname, "database.env") });
const mysql = require("mysql");

const pool = mysql.createPool({
  connectionLimit: 10,
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD_DEV,
  database: process.env.DB_DATABASE
});

class DatabaseQuery {
  /**
   * Get user's data using the ID. There are two tables to join to gather the user's data.
   * This function will be called whenever the users need to gathers it owns data in the
   * dashboard of the application.
   * @param {string} id is obtained from the request token.
   * @returns Promise
   */
  getSpecifiedUserData (id) {
    return new Promise((resolve, reject) => {
      const query = `SELECT users.nama_user, achievements.* FROM achievements JOIN users on users.id=achievements.user_id WHERE users.id=${id}`;
      pool.getConnection((err, connection) => {
        if (err) reject(err);
        connection.query(query, (err, result) => {
          connection.release();
          if (err) reject(err);
          resolve(result);
        });
      });
    });
  }

  /**
   * Make a new user using the user's ID and Name that is obtained from the request
   * token. Make new user is called during the Middleware firebase process and only
   * will be called if there are no users with the given uid.
   * @param {string} id is obtained from the request token.
   * @param {string} name is obtained from the request token.
   * @returns Promise
   */
  makeNewUser (id, name) {
    return new Promise((resolve, reject) => {
      const query = `INSERT INTO users (uid, nama_user) VALUES ("${id}", ${name}")`;
      pool.getConnection((err, connection) => {
        if (err) reject(err);
        connection.query(query, (err, result) => {
          connection.release();
          if (err) reject(err);
          resolve(result);
        });
      });
    });
  }

  /**
   * This function will be called after a new user has been called. The purpose of this function
   * is to make a new row to the achievements tables related to the new user.
   * @param {int} userID the ID of the user.
   * @returns Promise
   */
  makeNewUserAchievements (userID) {
    return new Promise((resolve, reject) => {
      const query = `INSERT INTO achievements (achv_id, user_id, eksplor_huruf, eksplor_angka, latMenyusunKatalvl1, latMenyusunKatalvl2, latMenyusunKatalvl3, latMengejaHuruflvl1, latMengejaHuruflvl2, latMengejaHuruflvl3) VALUES (NULL, ${userID}, '{}', '{}', '0', '0', '0', '{}', '{}', '{}')`;
      pool.getConnection((err, connection) => {
        if (err) reject(err);
        connection.query(query, (err, result) => {
          connection.release();
          if (err) reject(err);
          resolve(result);
        });
      });
    });
  }

  /**
   * getID will be used to get the ID from UID in the users table.
   * @param {string} uid obtained from firebase in the request token.
   * @returns Promise
   */
  getID (uid) {
    return new Promise((resolve, reject) => {
      const query = `SELECT id FROM users WHERE uid = "${uid}"`;
      pool.query(query, (err, result) => {
        if (err) reject(err);
        resolve(result);
      });
    });
  };

  /**
   * This function will be called to update users Eksplor Angka progress.
   * @param {int} id of user in the database.
   * @param {Object} obj given from the handlers.
   * @returns Promise
   */
  updateEksplorAngkaObj (id, obj) {
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
    // The result of queryObjString would look like this: "nol":true, "satu":true, "dua":true,
    // if necessary (some cases error occurs) we need to delete the last ',' on queryObjString
    // use this code: queryObjString = queryObjString.slice(0, queryObjString.length - 1);
    // however without slicing it works just fine...
    return new Promise((resolve, reject) => {
      const query = `UPDATE achievements SET eksplor_angka=JSON_MERGE_PATCH(eksplor_angka, '{${queryObjString}}') WHERE achievements.user_id=${id}`;
      pool.getConnection((err, connection) => {
        if (err) reject(err);
        connection.query(query, (err, result) => {
          connection.release();
          if (err) reject(err);
          resolve(result);
        });
      });
    });
  }

  /**
   * This function will be called to update users Eksplor Huruf progress.
   * @param {int} id of user in the database.
   * @param {Object} obj given from the handlers.
   * @returns Promise
   */
  updateEksplorHurufObj (id, obj) {
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
  }

  /**
   * Get pracice acitivity with the associate level from the database.
   * @param {string} activityName the name of the activity.
   * @param {string} level the level of acitivityName.
   * @param {int} id of the user in the database.
   * @returns Promise.
   */
  getPracticeActivity (activityName, level, id) {
    const columnName = activityName + "lvl" + level;
    return new Promise((resolve, reject) => {
      const query = `SELECT ${columnName} FROM achievements WHERE user_id = ${id}`;
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

  updatePracticeActivityPlusOne (activityName, level, id) {
    const columnName = activityName + "lvl" + level;
    return new Promise((resolve, reject) => {
      const query = `UPDATE achievements SET ${columnName} = ${columnName} + 1 WHERE user_id = ${id}`;
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

  updateSpeechRecogLevel (id, level, obj) {
    let queryObjString = "";
    for (const [key, value] of Object.entries(obj)) {
      queryObjString += `"${key}"` + ":" + value;
    }
    return new Promise((resolve, reject) => {
      const columnName = "latMengejaHuruflvl" + level;
      const query = `UPDATE achievements SET ${columnName} = JSON_MERGE_PATCH(${columnName}, '{${queryObjString}}') WHERE achievements.user_id = ${id}`;
      pool.getConnection((err, connection) => {
        if (err) reject(err);
        connection.query(query, (err, result) => {
          connection.release();
          if (err) reject(err);
          resolve(result);
        });
      });
    });
  }
}

module.exports = new DatabaseQuery();
