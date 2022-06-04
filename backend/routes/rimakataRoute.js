const express = require('express');
const router = express.Router(); 

/* 
  @ IMPORT HANDLERS 
*/
const rimakataHandler = require('../handlers/rimakataHandler');

/* 
  @ Routes and its handlers
*/
router.route('/')
    .get(rimakataHandler);

module.exports = router;
