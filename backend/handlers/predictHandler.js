const tf = require('@tensorflow/tfjs-node');
const path = require('path');
const fs = require('fs');
const _ = require('lodash');

// import getMFCCData to get the array value of the mfcc
const getMFCCData = require('../utils/getMFCCData');
const updateSpeechRecogLevel = require('../utils/updateSpeechRecogLevel');

// current next() is not implemented
const predictHandler = async (req,res,next) => {
    if (!req.user) {
        // sends back response to user
        return res.status(401).json({
            status: 'fail',
            type: 'user/user-unidentified',
            message: 'req.user does not exist. It is required to query data.'
        })
    }

    // get the id of the user
    const {id} = req.user;
    // get the params to know which alphabet to predict
    const {letter} = req;
    
    // checks the header content-type
    if (!req.is('multipart/form-data')) return res.status(404).json({
        status: 'fail',
        type: 'server/wrong-header-type',
        message: 'Please speicfy the header content-type to be \'multipart/form-data.\''
    })

    // get the file path of the uploaded wav file
    let file = req.file;
    
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
            fs.unlinkSync(file.path);
            // sends back response to user
            return res.status(500).json({
                status: 'fail',
                type: 'server/convert-error',
                message: 'There was an error while converting. Here is the error message: ' + err
            })   
        }
    }
    
    // load the model according to the alphabet requested
    const model = await tf.loadLayersModel('file://' +  path.join(__dirname, '..', 'models', `${letter.toUpperCase()}`, 'model.json'));

    // run getMFCCData from utils
    getMFCCData(file.path)
        .then(async (response) => {
            try {
                // run if return form python file is 1
                if (response) {
                    // delete file after result is obtained
                    fs.unlinkSync(file.path); 
                    // read result output from python stored in the json file 
                    var arr = fs.readFileSync(path.join(__dirname, '..', 'utils', 'mfccsResult', 'result.json'));
                    // parse the output to json
                    var jsonArr = JSON.parse(JSON.parse(arr));
                    // get the array value from the parsed json
                    var numpyArr = jsonArr.array; 
                    // convert array into tensor 4 dimension
                    const tensor = tf.tensor4d(numpyArr, [1,32,13,1], 'float32');
                    // use the tensor for prediction using the model
                    const predict = await model.predict(tensor).data();
                    // deletes the temp data if it goes through conversion to wav file
                    if (converterBool) {
                        fs.unlinkSync(temp.path);
                    }

                    // update user's score if predict equals to 1
                    if (predict[0]) {
                        // JSON file to get the levels of the requested alphabet
                        const speechRecogLevels = require('./resource/speechRecogLevels.json');
                        // set the Object values of speechRecogLevels.latMengejaHuruf.level to find the level
                        const arrToFindLevel = Object.values(speechRecogLevels.latMengejaHuruf.level);
                        // get the level of the alphabet requested 
                        var level = _.findIndex(arrToFindLevel, (el) => { 
                            return el.includes(letter);
                        }) + 1;
                        /**
                            @option
                            search for level from the requested letter without using lodash
                            for (let i=0 ; i < arrToFindLevel.length; ++i){
                                if (arr[i].includes(letter)) var level = i+1; break;
                                else continue
                            }
                        */
                        // define the object to be passed through the query
                        const obj = {};
                        // set the object of the letter to true for the purpose of the query
                        obj[`${letter}`] = true; 
                        // run the query function in utils folder 
                        updateSpeechRecogLevel(level, obj, id)
                            // resultQuery returns an object 
                            .then((resultQuery) => {
                                // handles no rows being affected from the query
                                if (resultQuery.changedRows < 1 && resultQuery.affectedRows < 1) {
                                    // sends back response to the user
                                    return res.status(500).json({
                                        status: 'fail',
                                        type: 'database/no-affected-rows',
                                        message: 'No rows are being affected on this query. We were unable to update your score. See you\'re result.',
                                        result: predict[0]
                                    })
                                }
                                // sends back response to user
                                return res.status(200).json({
                                    status: 'success',
                                    message: 'We have succesfully predict your recording. User\'s achievements on latihan mengeja huruf successfully updated!',
                                    result: predict[0]
                                })
                            })
                            .catch((err) => {
                                // sends back response to user
                                return res.status(500).json({
                                    status: 'fail',
                                    type: 'database/fail-to-query',
                                    message: err.message + '. See you\'re result.',
                                    result: predict[0]
                                })
                            })
                    } else {
                        // sends back result if predict equals to 1
                        return res.status(200).json({
                            status: 'success',
                            message: 'We have successfully predict the recording! See you\'re result.',
                            result: predict[0]
                        })
                    }
                }
            } catch (err) { // this error catches fail on deleting the wav file
                // run if return form python file is 1
                if (response) {
                    // outputs problem to analyze
                    console.log('File unsuccessfully deleted!');
                    // read result output from python stored in the json file 
                    var arr = fs.readFileSync(path.join(__dirname, '..', 'utils', 'mfccsResult', 'result.json'));
                    // parse the output to json
                    var jsonArr = JSON.parse(JSON.parse(arr));
                    // get the array value from the parsed json
                    var numpyArr = jsonArr.array; 
                    // convert array into tensor 4 dimension
                    const tensor = tf.tensor4d(numpyArr, [1,32,13,1], 'float32');
                    // use the tensor for prediction using the model
                    const predict = await model.predict(tensor).data();
                    // deletes the temp data if it goes through conversion to wav file
                    if (converterBool) {
                        fs.unlinkSync(temp.path);
                    }

                    // update user's score if predict equals to 1
                    if (predict[0]) {
                        // JSON file to get the levels of the requested alphabet
                        const speechRecogLevels = require('./resource/speechRecogLevels.json');
                        // set the Object values of speechRecogLevels.latMengejaHuruf.level to find the level
                        const arrToFindLevel = Object.values(speechRecogLevels.latMengejaHuruf.level);
                        // get the level of the alphabet requested 
                        var level = _.findIndex(arrToFindLevel, (el) => { 
                            return el.includes(letter);
                        }) + 1;
                        /**
                            @option
                            search for level from the requested letter without using lodash
                            for (let i=0 ; i < arrToFindLevel.length; ++i){
                                if (arr[i].includes(letter)) var level = i+1; break;
                                else continue
                            }
                        */
                        // define the object to be passed through the query
                        const obj = {};
                        // set the object of the letter to true for the purpose of the query
                        obj[`${letter}`] = true; 
                        // run the query function in utils folder 
                        updateSpeechRecogLevel(level, obj, id)
                            // resultQuery returns an object 
                            .then((resultQuery) => {
                                // handles no rows being affected from the query
                                if (resultQuery.changedRows < 1 && resultQuery.affectedRows < 1) {
                                    return res.status(500).json({
                                        status: 'fail',
                                        type: 'database/no-affected-rows',
                                        message: 'No rows are being affected on this query. We were unable to update your score. See you\'re result.',
                                        result: predict[0]
                                    })
                                } 
                                // sends back response to user
                                return res.status(200).json({
                                    status: 'success',
                                    message: 'We have succesfully predict your recording. User\'s achievements on latihan mengeja huruf successfully updated!',
                                    result: predict[0]
                                })
                            })
                            .catch((err) => {
                                // sends back response to user
                                return res.status(500).json({
                                    status: 'fail',
                                    type: 'database/fail-to-query',
                                    message: err.message + '. See you\'re result.',
                                    result: predict[0]
                                })
                            })
                    } else {
                        // sends back result if predict equals to 0
                        return res.status(200).json({
                            status: 'success',
                            message: 'We have successfully predict the recording! See you\'re result.',
                            result: predict[0]
                        })
                    }
                }
            }
        })
        .catch((err) => { // this catches prediction error
            // sends back error to application
            return res.status(500).json({
                status: 'fail',
                type: 'server/fail-to-predict',
                message: 'Something went wrong when predicting the wav file: ' + err.message
            })
        })
}

module.exports = predictHandler;