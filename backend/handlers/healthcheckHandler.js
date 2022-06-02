/* 
This file is used to send status code 200 for the load balancer health checks
*/

const healthcheckHandler = async (req, res, next) => {
    return res.status(200).send('This VM is healthy!');
}

module.exports = healthcheckHandler;