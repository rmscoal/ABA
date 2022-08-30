const express = require("express");
const router = express.Router();

/**
 * AllRoutes class contains all the routes for the back-end applications.
 * Defining it as a class will create a cleaner and readable code.
 */
class AllRoutes {
  userRoute () {
    const getUserDataHandler = require("../handlers/getUserDataHandler");

    router.route("/")
      .get(getUserDataHandler);
  }

  achievementRoute () {
    const updateEksplorHurufHandler = require("../handlers/updateEksplorHurufHandler");
    const updateEksplorAngkaHandler = require("../handlers/updateEksplorAngkaHandler");
    const updatePracticeActivity = require("../handlers/updatePracticeActivity");

    router.route("/eksplorHuruf")
      .put(updateEksplorHurufHandler);
    router.route("/eksplorAngka")
      .put(updateEksplorAngkaHandler);

    /**
     * '\d' is a digit (a character in the range 0-9), and '+' means 1 or more times.
     * So '\d+' is 1 or more digits. Because the regular expressions is usually part
     * if a literal string, be sure to escape any '\' characters with an additional
     * backslash. For example: '\\d+'. If error try '\d+' instead of '\\d+' or delete.
     */
    router.route("/latihan/:activityName/level/:level(\\d+)")
      .put(updatePracticeActivity);
  }

  /**
   * predictionRoute() will be used for speech recognition predictions. It uses multer
   * to upload audio file which later then be used for predictions. Currently there are
   * limited features for the speech recognition, which are alphabets from A-Z and several
   * words such as "Ayah", "Ibu", "Adik", "Kakak", and "Keluarga".
   */
  predictionRoute () {
    const multer = require("multer");
    const path = require("path");

    /**
     * @multer configurations.
    */
    const fileStorageEngine = multer.diskStorage({
      destination: (req, file, cb) => {
        cb(null, path.join(__dirname, "..", "utils", "uploads"));
      },
      filename: (req, file, cb) => {
        cb(null, Date.now() + "--" + file.originalname);
      }
    });

    const upload = multer({ storage: fileStorageEngine });
    const predictHurufHandler = require("../handlers/predictHurufHandler");
    const predictKataHandler = require("../handlers/predictKataHandler");

    router.route("/huruf/:letter")
      .post(upload.any(), async (req, res, next) => {
        // Firstly, gets the 'letter' from the parameter.
        // Next, generate an Array from A-Z to check
        // whether the 'letter' from the parameter given
        // is included in the Array. If it is included
        // then returns next() for further stage. Meanwhile,
        // returns an error to client-side.

        const { letter } = req.params;
        const alpha = Array.from(Array(26)).map((e, i) => i + 65);
        const alphabet = alpha.map((x) => String.fromCharCode(x));

        if (!alphabet.includes(letter.toUpperCase())) {
          return res.status(404).json({
            status: "fail",
            type: "server/param-not-found",
            message: "You have inserted the wrong parameter for this URI."
          });
        } else {
          if (letter === letter.toUpperCase()) {
            req.letter = letter.toLowerCase();
            return next();
          } else {
            req.letter = letter;
            return next();
          }
        }
      }, predictHurufHandler);

    router.route("/kata/:word")
      .post(upload.any(), async (req, res, next) => {
        // Firstly, get the :word from the parameter.
        // Next, checks whether the word is included
        // in the list of the current speech recognition
        // words. Then, returns an error response if not
        // included and returns next() if included to be
        // processed in the next stage.

        const { word } = req.params;
        const arr = ["ADIK", "AYAH", "IBU", "KAKAK", "KELUARGA"];

        if (!arr.includes(word.toUpperCase())) {
          return res.status(404).json({
            status: "fail",
            type: "server/param-not-found",
            message: "You have inserted the wrong parameter for this URI."
          });
        } else {
          if (word === word.toUpperCase()) {
            req.word = word.toLowerCase();
            return next();
          } else {
            req.word = word;
            return next();
          }
        }
      }, predictKataHandler);
  }

  rimakataRoute () {
    const rimakataHandler = require("../handlers/rimakataHandler");

    router.route("/")
      .get(rimakataHandler);
  }

  healthRoute () {
    const healthcheckHandler = require("../handlers/healthCheckHandler");

    router.route("/")
      .get(healthcheckHandler);
  }
};

module.exports = new AllRoutes();
