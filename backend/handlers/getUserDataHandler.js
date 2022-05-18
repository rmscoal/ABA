/* Get all achievements from user */

/* 
  @ IMPORT MODULES FROM UTILS TO QUERY THE DATABASE
*/ 
const getSpecificUserData = require('../utils/getSpecificUserData'); // 

// currently next() is not implemented
const getUserDataHandler = async (req,res) => {
    // req.user does not exist 
    if (!req.user) {
        // return a bad request error
        return res.status(400).json({
            status: 'fail', 
            type: 'user/user-unidentified',
            message: 'req.user does not exist! It is required to query data.'
        })
    }
    const { id } = req.user; // get the id of the user
    // handles the promise 
    getSpecificUserData(id)
        // resultQuery returns an array 
        .then((resultQuery) => {
            // handles data not found
            if (resultQuery.length < 1) {
                return res.status(404).json({
                    status: 'fail', 
                    type: 'user/user-data-not-found',
                    message: 'Empty result for user\'s query!'
                })
            }
            const result = resultQuery[0]; // sets the result
            // return the result from the query
            return res.status(200).json({
                status: 'success', 
                message: 'Query user data successfully done!',
                data: result
            })
        })
        // error handling
        .catch((err) => {
            return res.status(500).json({
                status: 'fail', 
                type: 'database/fail-to-query',
                message: err.message
            })
        })
}

module.exports = getUserDataHandler;
