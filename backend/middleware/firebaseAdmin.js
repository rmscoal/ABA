/* Dapat UID dari token */

const admin = require('./config/firebaseAuth'); 

class Middleware {
    async decodeToken(req,res,next) {
        const token = req.headers.authorization; 
        try {
            const userAuthorized = admin.auth().verifyIdToken(token);
            if (userAuthorized) {
                req.user = userAuthorized.uid;
                // ! probably here check if user uid exist in database? 
                next(); 
            }
            return res.status(401).json({message: 'Unauthorized'});
        } catch (e) {
            return res.status(500).json({message: "Something went wrong!"});
        }
    }
}

module.exports = new Middleware();
