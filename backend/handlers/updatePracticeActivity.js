/* Handler to update/PUT achievements in practice section */

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
const {getPractiveActivity, updatePracticeActivityPlusOne} = require('../utils/updatePracticeActivityDatabaseQuery');

/* 
  @ IMPORT JSON FILE FOR CURRENT ACTIVITIES AND LEVELS FOR THE ACTIVITIES
*/ 
const currentPractiveActivitiesJSON = require('./resource/currentPracticeActivities.json');

// currently next() is not implemented
const updatePracticeActivity = async (req, res, next) => {
    const { activityName, level } = req.params;
    
    // check if the activity name is in the JSON file activities
    if (!Object.keys(currentPractiveActivitiesJSON).includes(activityName)) {
        return res.status(404).json({
            status: 'fail',
            type: 'server/param-not-found',
            message: 'Param for activity name is not recognized!'
        })
    }

    // check if the level of the activity is in the JSON file activities
    if (!Object.keys(currentPractiveActivitiesJSON[`${activityName}`].level).includes(level)) {
        return res.status(404).json({
            status: 'fail',
            type: 'server/param-not-found',
            message: 'Param for level is not recognized!'
        })
    }

    // check if req.user exist
    if (!req.user) {
        return res.status(401).json({
            status: 'fail', 
            type: 'user/user-unidentified',
            message: 'req.user does not exist! It is required to query data.'
        })
    }

    const { id } = req.user; // get id from user

    // check the current user's achievements first to decide whether to +1 or not
    getPractiveActivity(activityName, level, id)
        .then(async (resultQuery) => {
            // error for no data found
            if (resultQuery.length < 1) {
                // log to winston_log
                logger.error('user/user-data-not-found');
                // sends back response to user
                return res.status(444).json({
                    status: 'fail', 
                    type: 'user/user-data-not-found',
                    message: 'Empty result for user\'s query!'
                })
            }

            // get the current user's achievement
            const result = resultQuery[0][activityName+'lvl'+level];

            // checks with the JSON file
            if (result === currentPractiveActivitiesJSON[`${activityName}`]['level'][`${level}`]) {
                return res.status(200).json({
                    status: 'success',
                    message: 'Score has been updated. User has reached highest accomplishment on the level.',
                    highScore: true
                })
            }

            // if achievement is not completed
            try {
                var resultUpdate = await updatePracticeActivityPlusOne(activityName, level, id);
                if (resultUpdate.changedRows < 1 && resultUpdate.affectedRows < 1) {
                    // log to winston_log
                    logger.error('database/no-affected-rows');
                    // sends back response to user
                    return res.status(400).json({
                        status: 'fail',
                        type: 'database/no-affected-rows',
                        message: 'No rows are being affected on this query.',
                        updated: false
                    })
                }
                // log to winston_log
                logger.info('Request update score for id: ' + id);
                // sends back response to user
                return res.status(200).json({
                    status: 'success',
                    message: 'Score has been updated.',
                    updated: true,
                    highScore: false
                })

            } catch (err) {
                // log to winston_log
                logger.error('database/fail-to-query');
                // catches query error
                return res.status(500).json({
                    status: 'fail',
                    type: 'database/fail-to-query',
                    message: err.message,
                    updated: false
                })
            }
        })
        .catch((err) => {
            // log to winston_log
            logger.error('database/fail-to-query');
            // catches query error
            return res.status(500).json({
                status: 'fail',
                type: 'database/fail-to-query',
                message: err.message
            })
        })
}

module.exports = updatePracticeActivity;
