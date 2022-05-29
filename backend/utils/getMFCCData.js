const {spawn} = require('child_process');
const path = require('path');

const getMFCCData = (pathToWav) => {
    return new Promise((resolve, reject) => {
        const python = spawn('python3', [path.join(__dirname, 'index.py'), `${pathToWav}`]);

        python.stdout.on('data', (data) => {
            resolve(`${data}`);
        })

        python.stderr.on('data', (error) => {
            reject(error);
        });
    })
}

module.exports = getMFCCData;