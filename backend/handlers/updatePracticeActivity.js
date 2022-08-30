const winston = require("winston");
const { LoggingWinston } = require("@google-cloud/logging-winston");

/**
 * @logging initiate winston and google cloud logging to log activities.
 */
const loggingWinston = new LoggingWinston();
const logger = winston.createLogger({
  level: "info",
  transports: [
    new winston.transports.Console(),
    loggingWinston
  ]
});

/**
 * @import
 */
const { getPractiveActivity, updatePracticeActivityPlusOne } = require("../utils/updatePracticeActivityDatabaseQuery");
const currentPractiveActivitiesJSON = require("./resource/currentPracticeActivities.json");

/**
 * updatePracticeActivity() is used to update users progress on Latihan Menyusun Huruf
 * features. On the execution of this function, it will do a query to the database to
 * update the scores from the users. The function does not process any file. It only
 * the ID of the given user and the parameter from the URL. To check whether the given
 * parameter from client-side are correct, the function will read a JSON file from the
 * folder './resources/' which contains a list of the current level of the feature.
 * Once validated, it will then execute a query to update the specific needs. Once, the
 * query has been executed, it will return a specified response if the user has reached
 * high score for the current level or the user has not. To validate whether the user has
 * reached high score for the specified level, this function will check with the JSON file
 * which also contains the highest score for each level.
 * @param {*} req request from client-side
 * @param {*} res response to client-side
 * @param {*} next currently not implemented
 * @returns res
 */
const updatePracticeActivity = async (req, res, _next) => {
  const { activityName, level } = req.params;

  // If activityName is not in the JSON file activities, then return error.
  if (!Object.keys(currentPractiveActivitiesJSON).includes(activityName)) {
    return res.status(404).json({
      status: "fail",
      type: "server/param-not-found",
      message: "Param for activity name is not recognized!"
    });
  }

  // If the level of the activityName is not in the JSON file activities, then return error.
  if (!Object.keys(currentPractiveActivitiesJSON[`${activityName}`].level).includes(level)) {
    return res.status(404).json({
      status: "fail",
      type: "server/param-not-found",
      message: "Param for level is not recognized!"
    });
  }

  // Next, if req.user does not exist, then return error.
  if (!req.user) {
    return res.status(401).json({
      status: "fail",
      type: "user/user-unidentified",
      message: "We found that 'req.user' does not exist! It is required to query data."
    });
  }

  const { id } = req.user;

  getPractiveActivity(activityName, level, id)
    .then(async (resultQuery) => {
      // The following result shows that there are no data found for the current ID.
      if (resultQuery.length < 1) {
        // Logs activity to winston then sends back to user.
        logger.error("user/user-data-not-found");
        return res.status(444).json({
          status: "fail",
          type: "user/user-data-not-found",
          message: "Empty result for user's query!"
        });
      }

      // Get the current user's achievement.
      const result = resultQuery[0][activityName + "lvl" + level];

      // With the result obtained from the query, now checks with the JSON file.
      if (result === currentPractiveActivitiesJSON[`${activityName}`].level[`${level}`]) {
        return res.status(200).json({
          status: "success",
          message: "Score has been updated. User has reached highest accomplishment on the level.",
          highScore: true
        });
      }

      try {
        const resultUpdate = await updatePracticeActivityPlusOne(activityName, level, id);
        if (resultUpdate.changedRows < 1 && resultUpdate.affectedRows < 1) {
          // Logs error to winston.
          logger.error("database/no-affected-rows");
          return res.status(400).json({
            status: "fail",
            type: "database/no-affected-rows",
            message: "No rows are being affected on this query.",
            updated: false
          });
        }
        // Logs succession to winston.
        logger.info("Request update score for ID: " + id);
        // sends back response to user
        return res.status(200).json({
          status: "success",
          message: "Score has been updated.",
          updated: true,
          highScore: false
        });
      } catch (err) {
        // Log error to winston.
        logger.error("database/fail-to-query");
        return res.status(500).json({
          status: "fail",
          type: "database/fail-to-query",
          message: err.message,
          updated: false
        });
      }
    })
    .catch((err) => {
      // Logs error to winston.
      logger.error("database/fail-to-query");
      return res.status(500).json({
        status: "fail",
        type: "database/fail-to-query",
        message: err.message
      });
    });
};

module.exports = updatePracticeActivity;
