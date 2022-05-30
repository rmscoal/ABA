const tf = require('@tensorflow/tfjs-node');
const path = require('path');
const fs = require('fs');

// import getMFCCData to get the array value of the mfcc
const getMFCCData = require('../utils/getMFCCData');

// current next() is not implemented
const predictHandler = async (req,res,next) => {
    // get the params to know which alphabet to predict
    const {letter} = req.params;

    // get the file path of the uploaded wav file
    let file = req.file;

    // checks the header content-type
    if (!req.is('multipart/form-data')) return res.status(404).json({
        status: 'fail',
        type: 'server/wrong-header-type',
        message: 'Please speicfy the header content-type to be \'multipart/form-data.\''
    })

    // if file does not exist
    else if (!file) return res.status(500).json({
        status: 'fail',
        type: 'server/file-not-found',
        message: 'Something went wrong! File is not found!'
    })

    var type = file.mimetype.split('/')[0]; // get the file type
    var ext = file.mimetype.split('/')[1]; // get the extension

    // checks the extension of the file that is uploaded
    if (type !== 'audio') {
        fs.unlinkSync(file.path); // deletes the uploaded file
        return res.status(404).json({
            status: 'fail',
            type: 'server./file-not-supported',
            message: 'We only receive audio file type. You\'re file type was: ' + type
        })
    }

    // check if the extension is .wav or not 
    else if (ext !== 'wave') {
        const converter = require('../utils/converter');
        var temp = Object.assign({},file); // store the temporary path of the file here
        var converterBool = 1; // set the converter bool to 1. This means that converting process took place
        try {
            // get the new path of the converted
            file.path = await converter(temp.path, temp.filename.split('.')[0],  function (errorMessage) {

            }, null, function () {
                console.log('Successfully converted to .wav file!');
            });
        } catch (err) {
            fs.unlinkSync(file.path);
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
                    if (converterBool) {
                        fs.unlinkSync(temp.path);
                    }
                    // sends back result
                    return res.status(200).json({
                        status: 'success',
                        message: 'We have successfully predict the wav file!',
                        result: predict[0]
                    })
                }
            } catch (err) { // this error catches fail on deleting the wav file
                // outputs problem to analyze
                console.log('File unsuccessfully deleted!')
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
                // sends back the result but the file is not deleted
                return res.status(500).json({
                    status: 'fail',
                    type: 'server/fail-to-delete',
                    message: 'Failed to delete wav file after getting result! This is error message: ' + err + '. However we got you\'re prediction!',
                    result: predict[0]
                });
            }
        })
        .catch((err) => { // this catches prediction error
            fs.unlinkSync(file.path);
            // sends back error to application
            return res.status(404).json({
                status: 'fail',
                type: 'server/fail-to-predict',
                message: 'Something went wrong when predicting the wav file: ' + err
            })
        })
}

module.exports = predictHandler;