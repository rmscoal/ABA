const express = require('express');
const router = express.Router(); 

/* 
  @ IMPORT HANDLERS 
*/
const getUserDataHandler = require('../handlers/getUserDataHandler');

/* 
  @ Routes and its handlers
*/
router.route('/')
    .get(getUserDataHandler);

module.exports = router;
