/* Dapat UID dari token dan disimpan di req.users*/

const admin = require('./config/firebaseAuth'); 

class Middleware {
    async decodeToken(req,res,next) {
        const token = req.headers.authorization.split(' ')[0]; // req.headers = {"Bearer $.token"} 
        try {
            const userAuthorized  = await admin.auth().verifyIdToken(token); // check if user is authorized

            // on authorized
            if (userAuthorized) {
                const uid = userAuthorized.uid();
                req.user = uid; // set req.user to become the uid
                next(); // TODO: check database if uid has existed or not? 
            }
            
            // on unauthorized id token
            return res.status(401).json({status: "fail", message: "Unauthorized"})
        } catch (err) {
            /*
            on err, such as sent was FMC token instead of id token or token has expired!
            err response: after executing console.log(err)
            {
                errorInfo: {
                  code: 'auth/argument-error',
                  message: 'Decoding Firebase ID token failed. Make sure you passed the entire string JWT which represents an ID token. See https://firebase.google.com/docs/auth/admin/verify-id-tokens for details on how to retrieve an ID token.'
                },
                codePrefix: 'auth'
            }
            or
            {
                errorInfo: {
                    code: 'auth/id-token-expired',
                    message: 'Firebase ID token has expired. Get a fresh ID token from your client app and try again (auth/id-token-expired). See https://firebase.google.com/docs/auth/admin/verify-id-tokens for details on how to retrieve an ID token.'
                },
                codePrefix: 'auth'
            }
            */
            return res.status(400).json({status: "fail", type: err.errorInfo.code, message: err.errorInfo.message}); // returns 404 bad request!
        }
    }
}

module.exports = new Middleware();
