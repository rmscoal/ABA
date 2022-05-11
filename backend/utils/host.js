const domain = process.env.DOMAIN || '<Nama domain jika perlu>';
const hostname = process.env.NODE_ENV !== 'production' ?
    'localhost' : domain;

module.exports = hostname;
