/* Get rimakata and send to user */

/* 
    @ IMPORT MODULES
*/
const fs = require('fs');
const path = require('path');
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
    @ IMPORT MODULES FROM UTILS TO RUN RIMAKATA PYTHON 
*/
const rimakataPythonRunner = require('../utils/rimakataPythonRunner.js');

// currently next() is not implemented
const rimakataHandler = async (req, res, next) => {
    // check if req.user exist 
    if (!req.user) {
        return res.status(400).json({
            status: 'fail', 
            type: 'user/user-unidentified',
            message: 'req user does not exist'
        })
    }

    // get the id of the user
    const {id} = req.user;

    // run the rimakataPythonRunner
    rimakataPythonRunner()
        .then(async (response) => {
            try {
                if (response) {
                    // read json files from the result by python
                    const mudahJSONFile = fs.readFileSync(path.join(__dirname, '..', 'utils', 'rimakataResult', 'resultMudah.json'));
                    const sedangJSONFile = fs.readFileSync(path.join(__dirname, '..', 'utils', 'rimakataResult', 'resultSedang.json'));
                    const sulitJSONFile = fs.readFileSync(path.join(__dirname, '..', 'utils', 'rimakataResult', 'resultSulit.json'));
                    
                    // parse the json 
                    const mudahObject = JSON.parse(mudahJSONFile); 
                    const sedangObject = JSON.parse(sedangJSONFile);
                    const sulitObject = JSON.parse(sulitJSONFile);
   
                    // log to winston_log
                    logger.info('KBBI requested to id: ' + id) 
                    // sends back response to user
                    res.status(200).json({
                        status: 'success', 
                        message: 'Here are the data!', 
                        data: {
                            mudah: mudahObject,
                            sedang: sedangObject,
                            sulit: sulitObject
                        }
                    })
                    
                }        
            } catch (err) {
                // log to winston_log
                logger.error('Error. Here is the message: ' + err.message);
                // sends back response to user
                return res.status(500).json({
                    status: 'fail', 
                    type: 'server/internal-error', 
                    message: 'Something wrong happened! ' + err
                })
            }
        })
        .catch((err) => {
            // log to winston_log
            logger.error('Error. Here is the message: ' + err)
            // sends back response to user
            return res.status(500).json({
                status: 'fail', 
                type: 'server/internal-error', 
                message: 'Something wrong happened! ' + err
            })
        })
}

module.exports = rimakataHandler;
