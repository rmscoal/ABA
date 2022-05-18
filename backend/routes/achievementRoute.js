const express = require('express');
const router = express.Router(); 

const updateEksplorHurufHandler = require('../handlers/updateEksplorHurufHandler');
const updateEksplorAngkaHandler = require('../handlers/updateEksplorAngkaHandler');

router.route('/eksplorHuruf')
    .put(updateEksplorHurufHandler);

router.route('/eksplorAngka')
    .put(updateEksplorAngkaHandler);

module.exports = router; 