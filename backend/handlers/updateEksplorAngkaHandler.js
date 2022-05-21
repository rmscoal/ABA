/* Handler to update/PUT achievements in eksplor-angka section */

/* 
  @ IMPORT MODULES FROM UTILS TO QUERY THE DATABASE
*/ 
const updateEksplorAngkaDatabaseQuery = require('../utils/updateEksplorAngkaDatabaseQuery');

// currently next() is not implemented
// EA stands for eksplor angka
const updateEA = async (req, res, next) => {
    if (!req.user) {
        // return a bad request error
        return res.status(400).json({
            status: 'fail', 
            type: 'user/user-unidentified',
            message: 'req.user does not exist! It is required to query data.'
        })
    }
    
    const { id } = req.user; // get id from the user
    const { eksplorAngkaData } = req.body // get data from the request body as an object
    // add handler if eksplorAngkaData is undefined, for example frontend sent the wrong format
    /* 
        example req.body
        {
            eksplorAngkaData: {
                nol: true,
                satu: true,
                dua: true,
                etc... 
            }
        }
    */
    updateEksplorAngkaDatabaseQuery(id, eksplorAngkaData)
        // resultQuery returns an object 
        .then((resultQuery) => {
            // handles no rows being affected from the query
            if (resultQuery.changedRows < 1 && resultQuery.affectedRows < 1) {
                return res.status(400).json({
                    status: 'fail',
                    type: 'database/no-affected-rows',
                    message: 'No rows are being affected on this query.'
                })
            }
            return res.status(200).json({
                status: 'success',
                message: 'User\'s achievements on eksplor angka successfully updated!'
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

module.exports = updateEA; 
