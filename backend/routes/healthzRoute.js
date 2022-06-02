const express = require('express');
const router = express.Router(); 

/* 
  @ IMPORT HANDLERS 
*/
const healthzHandler = require('../handlers/healthzHandler');

/* 
  @ Routes and its handlers
*/
router.route('/')
    .get(healthzHandler);

module.exports = router;
