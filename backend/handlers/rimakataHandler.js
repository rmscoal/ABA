const fs = require("fs");
const path = require("path");
const winston = require("winston");
const { LoggingWinston } = require("@google-cloud/logging-winston");

/**
 * @logging initiate winston and google cloud logging to log activities.
 */
const loggingWinston = new LoggingWinston();
const logger = winston.createLogger({
  level: "info",
  transports: [
    new winston.transport.Console(),
    loggingWinston
  ]
});

/**
 * @import module for running Python codes.
 */
const rimakataPythonRunner = require("../utils/rimakataPythonRunner.js");

/**
 * rimakataHandler() is used to get rimakata from the Python code. The
 * Python code stores the result as a JSON in the utils folder. Thus after
 * running the Python code, rimakataHandler will extract the results from
 * the JSON file and sent it back to the user.
 * @param {*} req the request from client-side
 * @param {*} res the response to client-side
 * @param {*} next currently next() is not implemented.
 * @returns res
 */
const rimakataHandler = async (req, res, _next) => {
  // If req.user does not exist returns a fail response.
  if (!req.user) {
    return res.status(400).json({
      status: "fail",
      type: "user/user-unidentified",
      message: "req user does not exist"
    });
  };

  const { id } = req.user;

  rimakataPythonRunner()
    .then(async (response) => {
      try {
        if (response) {
          // Reading the JSON file executed by the Python code.
          const mudahJSONFile = fs.readFileSync(path.join(__dirname, "..", "utils", "rimakataResult", "resultMudah.json"));
          const sedangJSONFile = fs.readFileSync(path.join(__dirname, "..", "utils", "rimakataResult", "resultSedang.json"));
          const sulitJSONFile = fs.readFileSync(path.join(__dirname, "..", "utils", "rimakataResult", "resultSulit.json"));

          // Parsing as JSON.
          const mudahObject = JSON.parse(mudahJSONFile);
          const sedangObject = JSON.parse(sedangJSONFile);
          const sulitObject = JSON.parse(sulitJSONFile);

          // Logs succession to winston.
          logger.info("KBBI REQUESTED TO ID: " + id);

          // After the result has successfully been retrieve, it sends back to client-side.
          res.status(200).json({
            status: "success",
            message: "Here are the data!",
            data: {
              mudah: mudahObject,
              sedang: sedangObject,
              sulit: sulitObject
            }
          });
        }
      } catch (err) {
        // Logs error to winston. This error might be cause by readFileSync().
        logger.error("ENCOUNTERED AN ERROR WITH MESSAGE: " + err.message);

        // Sends error to client-side.
        return res.status(500).json({
          status: "fail",
          type: "server/internal-error",
          message: "ENCOUNTERED AN ERROR WITH MESSAGE: " + err
        });
      }
    })
    .catch((err) => {
      // Logs error to winston. This error is due to internal server error.
      logger.error("ENCOUNTERED AN ERROR WITH MESSAGE: " + err.message);

      // Sends error to client-side.
      return res.status(500).json({
        status: "fail",
        type: "server/internal-error",
        message: "ENCOUNTERED AN ERROR WITH MESSAGE: " + err
      });
    });
};

module.exports = rimakataHandler;
