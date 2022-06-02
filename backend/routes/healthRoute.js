/* 
This file is used for health checks in load balancer in GCP
*/

const express = require('express');
const router = express.Router();

/* 
    @ IMPORT HANDLERS
*/
const healthcheckHandler = require('../handlers/healthcheckHandler');

/* 
    @ ROUTES
*/
router.route('/')
    .get(healthcheckHandler);

module.exports = router;
