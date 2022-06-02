/* 
    THIS FILE IS USED FROM HEALTH CHECKS IN GCP
*/

const healthzHandler = async (req, res, next) => {
    return res.status(200).send('I\'m healthy!');
}

module.exports = healthzHandler;