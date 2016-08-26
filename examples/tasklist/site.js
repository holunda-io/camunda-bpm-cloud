var express = require('express'),
    app = express();

app.use(express.static(__dirname + '/public'));

var server = app.listen(1338, function() {
  console.log('Listening on port %d', server.address().port);
});

app.all('*', function(req, res, next) {
  // CORS headers
  res.header("Access-Control-Allow-Origin", "*"); // restrict it to the required domain
  res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS');
  // Set custom headers for CORS
  res.header('Access-Control-Allow-Headers', 'Content-type,Accept,X-Access-Token,X-Key');
  if (req.method == 'OPTIONS') {
    res.status(200).end();
  } else {
    next();
  }
});
