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
 * @import module from the database queries folder.
 */
const getSpecificUserData = require("../utils/getSpecificUserData");

/**
 * execFunction() is the executor function for getUserDataHandler() function
 * to make code structure clean and organized. The task of execFunction() is
 * to query data using the requested id and returns response beginning in int
 * to identify whether it has succeeded or failed during the query process.
 * @param {*} id of the user on requesting data.
 * @returns a tuple of (code, result) where:
 *
 * 1 indicates that it has successfully retrieve user data from database,
 * -1 indicates that user data is not found in the database, and
 * -2 indicates that there is an error during database query.
 */
const execFunction = async (id) => {
  // With the ID obtained, query user data from the database.
  getSpecificUserData(id)
    .then((resultQuery) => {
      if (resultQuery.length < 1) {
        logger.error("user/user-data-not-found");
        // eslint-disable-next-line prefer-const
        let err = "user-data-not-found";
        return (-1, err);
      };

      const result = resultQuery[0];
      const dataToSendResponse = {
        nama_user: result.nama_user,
        achv_id: result.achv_id,
        user_id: result.user_id,
        eksplor_huruf: Object.keys(JSON.parse(result.eksplor_huruf)),
        eksplor_angka: Object.keys(JSON.parse(result.eksplor_angka)),
        latMenyusunKatalvl1: result.latMenyusunKatalvl1,
        latMenyusunKatalvl2: result.latMenyusunKatalvl2,
        latMenyusunKatalvl3: result.latMenyusunKatalvl3,
        latMengejaHuruflvl1: Object.keys(JSON.parse(result.latMengejaHuruflvl1)),
        latMengejaHuruflvl2: Object.keys(JSON.parse(result.latMengejaHuruflvl2)),
        latMengejaHuruflvl3: Object.keys(JSON.parse(result.latMengejaHuruflvl3))
      };

      logger.info("Requested user data on ID: " + id);
      return (1, dataToSendResponse);
    })
    .catch((err) => {
      logger.error("database/fail-to-query");
      return (-2, err.message);
    });
};

/**
 * getUserDataHandler() handles specific user data query from the database.
 * To run this function, the user request must firstly be authenticated via
 * the middleware. With that, we can use the user.id to retrieve its data from
 * the database. This function takes advantage of execFunction to the do job
 * on querying from the database. Using this structure makes the code cleaner
 * and managable.
 * @param {*} req the request from client-side.
 * @param {*} res the response it will give to the client.
 * @param {*} _next currently next() is not implemented
 * @returns res
 */
const getUserDataHandler = async (req, res, _next) => {
  // If req.user does not exist returns a fail response.
  if (!req.user) {
    return res.status(401).json({
      status: "fail",
      type: "user/user-unidentified",
      message: "req.user does not exist! It is required to query data.",
      time: Date.now()
    });
  };

  const { id } = req.user;
  const { code, result } = await execFunction(id);
  switch (code) {
    case 1:
      return res.status(200).json({
        status: "fail",
        message: "QUERY USER DATA SUCCESFULLY DONE!",
        data: result,
        time: Date.now()
      });

    case -1:
      return res.status(404).json({
        status: "fail",
        type: "user/user-data-npt-found",
        message: "EMPTY RESULT FOR USER'S QUERY!",
        time: Date.now()
      });

    case -2:
      return res.status(500).json({
        status: "fail",
        type: "database/fail-to-query",
        message: result,
        time: Date.now()
      });
  }
};

module.exports = getUserDataHandler;
