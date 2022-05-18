const express = require('express');
const router = express.Router(); 

const updateEksplorHurufHandler = require('../handlers/updateEksplorHurufHandler');
const updateEksplorAngkaHandler = require('../handlers/updateEksplorAngkaHandler');
const updatePracticeActivity = require('../handlers/updatePracticeActivity');

router.route('/eksplorHuruf')
    .put(updateEksplorHurufHandler);

router.route('/eksplorAngka')
    .put(updateEksplorAngkaHandler);

    // \d is a digit (a character in the range 0-9), and + means 1 or more times. So, \d+ is 1 or more digits.
    // Because the regular expression is usually part of a literal string, be sure to escape any \ characters with an additional backslash, for example \\d+.
    // if error, try (\d+) instead of (\\d+) or delete it. 
router.route('/latihan/:activityName/level/:level(\\d+)') 
    .put(updatePracticeActivity);

module.exports = router; 
