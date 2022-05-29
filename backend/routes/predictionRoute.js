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
    .post(upload.single('wav'), predictHandler);

module.exports = router; 
