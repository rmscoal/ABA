/* Get all achievements from user */

/* 
    @ IMPORT MODULES
*/
const winston = require('winston');
const {LoggingWinston} = require('@google-cloud/logging-winston');

// initiate logging winston
const loggingWinston = new LoggingWinston();

const logger = winston.createLogger({
    level: 'info',
    transports: [
        new winston.transports.Console(),
        loggingWinston,
    ],
})

/* 
  @ IMPORT MODULES FROM UTILS TO QUERY THE DATABASE
*/ 
const getSpecificUserData = require('../utils/getSpecificUserData'); // 

// currently next() is not implemented
const getUserDataHandler = async (req,res) => {
    // req.user does not exist 
    if (!req.user) {
        // return a bad request error
        return res.status(401).json({
            status: 'fail', 
            type: 'user/user-unidentified',
            message: 'req.user does not exist! It is required to query data.'
        })
    }
    const { id } = req.user; // get the id of the user
    // handles the promise 
    getSpecificUserData(id)
        // resultQuery returns an array 
        .then((resultQuery) => {
            // handles data not found
            if (resultQuery.length < 1) {
                // log to winston_log
                logger.error('user/user-data-not-found');
                return res.status(404).json({
                    status: 'fail', 
                    type: 'user/user-data-not-found',
                    message: 'Empty result for user\'s query!'
                })
            }
            const result = resultQuery[0]; // sets the result
            const dataToSendResponse = {
                "nama_user": result.nama_user,
                "achv_id": result.achv_id,
                "user_id": result.user_id,
                "eksplor_huruf": Object.keys(JSON.parse(result.eksplor_huruf)),
                "eksplor_angka": Object.keys(JSON.parse(result.eksplor_angka)),
                "latMenyusunKatalvl1": data.latMenyusunKatalvl1,
                "latMenyusunKatalvl2": data.latMenyusunKatalvl2,
                "latMenyusunKatalvl3": data.latMenyusunKatalvl3,
                "latMengejaHuruflvl1": Object.keys(JSON.parse(result.latMengejaHuruflvl1)),
                "latMengejaHuruflvl2": Object.keys(JSON.parse(result.latMengejaHuruflvl2)),
                "latMengejaHuruflvl3": Object.keys(JSON.parse(result.latMengejaHuruflvl3)),
            }
            // log to winston_log
            logger.info('Request on user data for id: ' + id);
            // return the result from the query
            return res.status(200).json({
                status: 'success', 
                message: 'Query user data successfully done!',
                data: dataToSendResponse
            })
        })
        // error handling
        .catch((err) => {
            // log to winston_log
            logger.error('database/fail-to-query');
            // sends back response to user
            return res.status(500).json({
                status: 'fail', 
                type: 'database/fail-to-query',
                message: err.message
            })
        })
}

module.exports = getUserDataHandler;
