const {spawn} = require('child_process');
const path = require('path');

const rimakataPythonRunner = () => {
    return new Promise((resolve, reject) => {
        const python = spawn('python3', [path.join(__dirname, 'rimakata.py')]);

        python.stdout.on('data', (data) => {
            resolve(`${data}`);
        })

        python.stderr.on('data', (error) => {
            reject(error);
        });    
    })
}

module.exports = rimakataPythonRunner;