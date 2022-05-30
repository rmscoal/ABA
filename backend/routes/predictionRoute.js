const express = require('express');
const router = express.Router();
const multer = require('multer');
const path = require('path');


const fileStorageEngine = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, path.join(__dirname, '..', 'utils', 'uploads'))
    },
    filename: (req, file, cb) => {
        cb(null, Date.now() + '--' + file.originalname)
    }
})

const upload = multer({storage: fileStorageEngine});

const predictHandler = require('../handlers/predictHandler');

router.route('/:letter')
    .post(upload.single('predict'), async (req,res,next) => {
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
    }, predictHandler);

module.exports = router; 
