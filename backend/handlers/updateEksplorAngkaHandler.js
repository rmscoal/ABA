const winston = require("winston");
const { LoggingWinston } = require("@google-cloud/logging-winston");

// initiate logging winston
const loggingWinston = new LoggingWinston();

const logger = winston.createLogger({
  level: "info",
  transports: [
    new winston.transports.Console(),
    loggingWinston
  ]
});

const updateEksplorAngkaDatabaseQuery = require("../utils/updateEksplorAngkaDatabaseQuery");

/**
 * This action is used to update eksplor angka in activity.
 * @param {*} req request from client-side.
 * @param {*} res response to client-side
 * @param {*} next currently not implemented.
 * @returns res.
 */
const updateEA = async (req, res, _next) => {
  if (!req.user) {
    // return a bad request error
    return res.status(401).json({
      status: "fail",
      type: "user/user-unidentified",
      message: "req.user does not exist! It is required to query data."
    });
  };

  const { id } = req.user; // get id from the user
  const { eksplorAngkaData } = req.body; // get data from the request body as an object

  updateEksplorAngkaDatabaseQuery(id, eksplorAngkaData)
  // resultQuery returns an object
    .then((resultQuery) => {
      // handles no rows being affected from the query
      if (resultQuery.changedRows < 1 && resultQuery.affectedRows < 1) {
        // log to winston_log
        logger.error("database/no-affected-rows");
        // sends back response to user
        return res.status(400).json({
          status: "fail",
          type: "database/no-affected-rows",
          message: "No rows are being affected on this query."
        });
      }
      // log to winston_log
      logger.info("Request update score for id: " + id);
      // sends back response to user
      return res.status(200).json({
        status: "success",
        message: "User's achievements on eksplor angka successfully updated!"
      });
    })
    .catch((err) => {
      // log to winston_log
      logger.error("database/fail-to-query");
      // sends back response to user
      return res.status(500).json({
        status: "fail",
        type: "database/fail-to-query",
        message: err.message
      });
    });
};

module.exports = updateEA;
