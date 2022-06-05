const express = require('express');
const router = express.Router();
const multer = require('multer');
const path = require('path');

/* 
  @ MULTER CONFIG CODE
*/
const fileStorageEngine = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, path.join(__dirname, '..', 'utils', 'uploads'))
    },
    filename: (req, file, cb) => {
        cb(null, Date.now() + '--' + file.originalname)
    }
})

const upload = multer({storage: fileStorageEngine});

/* 
  @ IMPORT HANDLERS 
*/
const predictHurufHandler = require('../handlers/predictHurufHandler');
const predictKataHandler = require('../handlers/predictKataHandler');

/* 
  @ ROUTES AND ITS HANDLERS 
*/
router.route('/huruf/:letter')
    .post(upload.any(), async (req,res,next) => {
        var {letter} = req.params; 

        // generate an array of A-Z
        const alpha = Array.from(Array(26)).map((e, i) => i + 65);
        const alphabet = alpha.map((x) => String.fromCharCode(x));

        // check the parameter with the generated array
        if (!alphabet.includes(letter.toUpperCase())){
            return res.status(404).json({
                status: 'fail',
                type: 'server/param-not-found',
                message: 'You have inserted the wrong parameter for this URI.'
            })
        } else {
            if (letter === letter.toUpperCase()) {
                req.letter = letter.toLowerCase();
                return next();
            } else {
                req.letter = letter;
                return next();
            }
        }
    }, predictHurufHandler);

router.route('/kata/:word')
    .post(upload.any(), async (req, res, next) => {
        // get the word from the parameter
        var {word} = req.params;
        // array to check the parameter given
        const arr = ['ADIK', 'AYAH', 'IBU', 'KAKAK', 'KELUARGA'];
        // check the parameter with the given array
        if (!arr.includes(word.toUpperCase())) {
            // sends back response to user
            return res.status(404).json({
                status: 'fail',
                type: 'server/param-not-found',
                message: 'You have inserted the wrong parameter for this URI.'
            })
        } else {
            if (word === word.toUpperCase()) {
                req.word = word.toLowerCase();
                return next();
            } else {
                req.word = word;
                return next();
            }
        }
    }, predictKataHandler);

module.exports = router; 
