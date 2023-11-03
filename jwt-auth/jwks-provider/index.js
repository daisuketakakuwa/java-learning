const express = require('express');
const keyList = require('./keyPairs.json');

const app = express();

app.get('/jwks', (req, res) => {
    res.send(keyList);
});

app.listen(3001, () => {
    console.log('jwks server listening on 3001');
});