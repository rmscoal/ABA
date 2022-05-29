const tf = require('@tensorflow/tfjs-node');
const path = require('path');
const fs = require('fs');

const getMFCCData = require('../utils/getMFCCData');

// current next() is not implemented
const predictHandler = async (req,res,next) => {
    const {letter} = req.params; // get the params to know which alphabet to predict

    let file = req.file; // get the file path of the uploaded wav file

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

    // checks the extension of the file that is uploaded
    else if (file.mimetype !== 'audio/wave') return res.status(404).json({
        status: 'fail',
        type: 'server/wrong-file-type',
        message: 'File type is not supported!'
    })

    const model = await tf.loadLayersModel('file://' +  path.join(__dirname, '..', 'models', `${letter.toUpperCase()}`, 'model.json'));

    getMFCCData(file.path)
        .then(async (response) => {
            try {
                if (response) {
                    fs.unlinkSync(file.path); // delete file after result is obtained
                    var arr = fs.readFileSync(path.join(__dirname, '..', 'utils', 'mfccsResult', 'result.json'));
                    var jsonArr = JSON.parse(JSON.parse(arr));
                    var numpyArr = jsonArr.array; 
                    const tensor = tf.tensor4d(numpyArr, [1,32,13,1], 'float32');
                    const predict = await model.predict(tensor).data();
                    return res.status(200).json({
                        status: 'success',
                        message: 'We have successfully predict the wav file!',
                        result: predict[0]
                    })
                }
            } catch (err) {
                // catch errors during deleting the wav file
                var arr = fs.readFileSync(path.join(__dirname, '..', 'utils', 'mfccsResult', 'result.json'));
                var jsonArr = JSON.parse(JSON.parse(arr));
                var numpyArr = jsonArr.array; 
                const tensor = tf.tensor4d(numpyArr, [1,32,13,1], 'float32');
                const predict = await model.predict(tensor).data();
                return res.status(500).json({
                    status: 'fail',
                    type: 'server/fail-to-delete',
                    message: 'Failed to delete wav file after getting result! This is error message: ' + err + '. However we got you\'re prediction!',
                    result: predict[0]
                });
            }
        })
        .catch((err) => {
            return res.status(404).json({
                status: 'fail',
                type: 'server/fail-to-predict',
                message: 'Something went wrong when predicting the wav file: ' + err
            })
        })
}

module.exports = predictHandler;