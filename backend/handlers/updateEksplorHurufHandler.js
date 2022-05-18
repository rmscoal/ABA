/* Handler to update/PUT achievements in eksplor-huruf section */

/* 
  @ IMPORT MODULES FROM UTILS TO QUERY THE DATABASE
*/ 
const updateEksplorHurufDatabaseQuery = require('../utils/updateEksplorHurufDatabaseQuery');

// currently next() is not implemented
// EH stands for eksplor huruf
const updateEH = async (req, res, next) => {
    if (!req.user) {
        // return a bad request error
        return res.status(400).json({
            status: 'fail', 
            type: 'user/user-unidentified',
            message: 'req.user does not exist! It is required to query data.'
        })
    }
    
    const { id } = req.user; // get id from the user
    const { eksplorHurufData } = req.body // get data from the request body as an object
    /* 
        example req.body
        {
            eksplorHurufData: {
                a: true,
                b: true,
                etc... 
            }
        }
    */
    updateEksplorHurufDatabaseQuery(id, eksplorHurufData)
        // resultQuery returns an object 
        .then((resultQuery) => {
            // handles no rows being affected from the query
            if (resultQuery.changedRows < 1 || resultQuery.affectedRows < 1) {
                return res.status(400).json({
                    status: 'fail',
                    type: 'database/no-affected-rows',
                    message: 'No rows are being affected on this query.'
                })
            }
            return res.status(200).json({
                status: 'success',
                message: 'User\'s achievements on eksplor huruf successfully updated'
            })
        })
        .catch((err) => {
            return res.status(500).json({
                status: 'fail',
                type: 'database/fail-to-query',
                message: err.message
            })
        })

}

module.exports = updateEH; 
