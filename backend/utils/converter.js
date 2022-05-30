const ffmpeg = require('fluent-ffmpeg');
const path = require('path');

const converter = (file, destinationFileName, error, progress, finish) => {
    return new Promise((resolve, reject) => {
        ffmpeg(file)
            .on('error', (err) => {
                reject(err); 
            })
            .on('progress', (progress) => {
                console.log(progress.targetSize);
            })
            .on('end', () => {
                if (finish) { 
                    finish();
                }
            })
            .save(path.join(__dirname, 'converts', `${destinationFileName}` + '.wav'));
        
        resolve(path.join(__dirname, 'converts', `${destinationFileName}` + '.wav'));
    })
}

module.exports = converter;