/* THIS FILE IS FOR PREDICTING SIMPLE WORDS IN THE APPLICATION */

/* 
    @ IMPORT MODULES
*/
const winston = require('winston');
const {LoggingWinston} = require('@google-cloud/logging-winston');
const tf = require('@tensorflow/tfjs-node');
const path = require('path');
const fs = require('fs');

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
    @ IMPORT MODULES FROM OTHER FILES
*/
const getMFCCDataKata = require('../utils/getMFCCDataKata');

// currently next() is not implemented
const predictKataHandler = async (req, res, next) => {
    if (!req.user) {
        //sends back response to user
        return res.status(401).json({
            status: 'fail',
            type: 'user/user-unidentified',
            message: 'req.user does not exist!'
        })
    }

    // get the id of the user
    const {id} = req.user;
    // get the word to predict for 
    const {word} = req;

    // checks the header content-type
    if (!req.is('multipart/form-data')) return res.status(400).json({
        status: 'fail',
        type: 'server/wrong-header-type',
        message: 'Please speicfy the header content-type to be \'multipart/form-data.\''
    })

    // get the file path of the uploaded wav file
    let file = req.files[0];
    
    // if file does not exist
    if (!file) return res.status(500).json({
        status: 'fail',
        type: 'server/file-not-found',
        message: 'Something went wrong! File is not found!'
    })

    var type = file.mimetype.split('/')[0]; // get the file type
    var ext = file.mimetype.split('/')[1]; // get the extension

    // checks the extension of the file that is uploaded
    if (type !== 'audio') {
        fs.unlinkSync(file.path); // deletes the uploaded file
        // send response to user
        return res.status(400).json({
            status: 'fail',
            type: 'server/file-not-supported',
            message: 'We only receive audio file type. You\'re file type was: ' + type
        })
    }

    // check if the extension is .wav or not 
    else if (ext !== 'wave') {
        if (ext === 'mp4') {
            const converter = require('../utils/converter'); // get the converter module
            var temp = Object.assign({},file); // store the temporary path of the file here
            var converterBool = 1; // set the converter bool to 1. This means that converting process took place
            try {
                // get the new path of the converted
                file.path = await converter(temp.path, temp.filename.split('.')[0], (errorMessage) => {

                }, null, function () {
                    console.log('Successfully converted to .wav file!');
                });
            } catch (err) {
                // deletes the uploaded file
                fs.unlinkSync(file.path);
                // sends back response to user
                return res.status(500).json({
                    status: 'fail',
                    type: 'server/convert-error',
                    message: 'There was an error while converting. Here is the error message: ' + err
                })   
            }
        } else {
            // deletes the uploaded file
            fs.unlinkSync(file.path);
            // sends back response to the user 
            return res.status(400).json({
                status: 'fail',
                type: 'server/file-not-supported',
                message: 'We only receive wave/m4a extensions. You\'re extension was: ' + ext 
            })
        }
    }

   // load the model according to the alphabet requested
   const model = await tf.loadLayersModel('file://' +  path.join(__dirname, '..', 'models', `${word.toUpperCase()}`, 'model.json')); 
   
   // run getting mfccdatakata
   getMFCCDataKata(file.path)
    .then(async (response) => {
        try {
            if (response) {
                // deletes the file from path
                fs.unlinkSync(file.path);
                // read array from resultKata.json
                var arr = fs.readFileSync(path.join(__dirname, '..', 'utils', 'mfccsResult', 'resultKata.json'));
                // parse the output to json
                var jsonArr = JSON.parse(JSON.parse(arr));
                // get the array value from the parsed json
                var numpyArr = jsonArr.array; 
                // convert array into tensor 4 dimension
                const tensor = tf.tensor4d(numpyArr, [1,88,13,1], 'float32');
                // use the tensor for prediction using the model
                const predict = await model.predict(tensor).data();
                // deletes file if it goes through converter function
                if (converterBool) {
                    fs.unlinkSync(temp.path);
                }
                // log to winston_log
                logger.info('Predict success and sent to id: ' + id);
                // sends back response to user
                return res.status(200).json({
                    status: 'success',
                    message: 'We have successfully predict your kata recording. Here is the result!',
                    result: predict[0]
                })
            }
        } catch (err) { // this catches file deleting error
            // read array from resultKata.json
            var arr = fs.readFileSync(path.join(__dirname, '..', 'utils', 'mfccsResult', 'resultKata.json'));
            // parse the output to json
            var jsonArr = JSON.parse(JSON.parse(arr));
            // get the array value from the parsed json
            var numpyArr = jsonArr.array; 
            // convert array into tensor 4 dimension
            const tensor = tf.tensor4d(numpyArr, [1,88,13,1], 'float32');
            // use the tensor for prediction using the model
            const predict = await model.predict(tensor).data(); 
            // log to winston_log
            logger.info('Predict success and sent to id: ' + id);
            // sends back response to user
            return res.status(200).json({
                status: 'success',
                message: 'We have successfully predict your kata recording. Here is the result!',
                result: predict[0]
            })
        }
    })
    .catch((err) => {
        // log to winston_log
        logger.error('server/fail-to-predict');
        // send back response to user
        return res.status(500).json({
            status: 'fail',
            type: 'server/fail-to-predict',
            message: 'We failed to predict your kata recording due to: ' + err
        })
    })
}

module.exports = predictKataHandler;