/* Dapat UID dari token dan disimpan di req.users */

const admin = require('./config/firebaseAuth'); // import admin from firebase initializeApp
const getId = require('../utils/getUserID'); // module to get userId form MySQL database
const makeNewUser = require('../utils/makeNewUser'); // module to make a new user into MySQL database


class Middleware {
    async decodeToken(req,res,next) {
        // get authorization from the headers
        const { authorization } = req.headers; 

        // check if the authorization headers are well configured
        // this includes checking if headers.authorization exist
        // then if the format in headers.authorization matches with the configured
        if (!authorization) return res.status(403).json({
            status: 'fail', 
            type: 'server/missing-authorization',
            message: 'Missing req.headers.authorization on request to the server. This is need for authorization!'
        })

        else if (!authorization.startWith('Bearer')) return res.status(400).json({
            status: 'fail', 
            type: 'server/missing-bearer',
            message: 'Missing Bearer in req.headers.authorization on request to the server. This is needed to extract the token!'
        })

        else if (authorization.split(' ').length !== 2) return res.status(400).json({
            status: 'fail',
            type: 'server/bearer-unrecognized',
            message: 'Bearer in req.headers.authorization is not well configured. This is need to extract the token!'
        })
        // after passing the authorization header checks, now checks the token
        const token = authorization.split(' ')[1] // req.headers = {"Bearer $.token"} 
        admin.auth().verifyIdToken(token)
            .then((decodedToken) => {
                const {uid, name} = decodedToken; // get uid and name from the token
                // TODO: query to get the user id such that it can be stored in req.user = id
                try {
                    // !important : if this produces a bug due to await is only available in async function
                    // and must be on top, then just return next() and be the next handler to get the user id
                    const result = await getId(uid); // getId to get the id of the user regarding the uid
                    // check if exist uid in the database
                    if (result.length < 1) {
                        // if not make a new user
                        const result = await makeNewUser(uid, name); // make new user from the given uid and name
                        const id = result.insertId; // get the id of the new user
                        req.user = {id: id, name: name}; // set id and name to req.user
                        return next();
                    }
                    const id = result[0].id; // getId to get the id of the user from the result query since uid exist
                    req.user = {id: id, name: name}; // set id and name to req.user 
                    return next();
                } catch (err) {
                    return res.status(500).json({
                        status: 'fail',
                        type: 'database/fail-to-query',
                        message: err.message
                    })
                }
            })
            .catch((err) => {
                /*
                on err for firebase tokens, such as sent was FMC token instead of id token or token has expired and many others!
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
                if (err.errorInfo.code === 'auth/internal-error') var statusCode = 500;
                else var statusCode = 400; 
                return res.status(statusCode).json({status: "fail", type: err.errorInfo.code, message: err.errorInfo.message}); // return with status codes
            })
    }
}

module.exports = new Middleware();
