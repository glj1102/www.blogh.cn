
/**
 * Module dependencies.
 */
var routes = require('./routes')
  , port = require('./util/url.js');
var express = require('express')
  , http = require('http')
  , path = require('path')
  , flash = require('connect-flash')
  , engine = require('ejs-locals')
  , groupHandlers = require('express-group-handlers');

var commonService = require("./service/commonService.js");
var http_url = require('./util/http_url.js');



var app = express();
groupHandlers.setup(app);

function createSID() {
    var pre = "SESS";
    var time = (new Date()).getTime() + '';
    var id = pre + '_' + (time).substring(time.length - 6) + '_' + (Math.round(Math.random() * 1000));
    return id;
}
app.configure(function(){
	  app.set('port', process.env.PORT || port.local_port);
	  app.set('views', __dirname + '/views');
	  app.engine('html',engine);
	  app.set('view engine', 'html');
	  app.use(express.favicon());
	  app.use(express.logger('dev'));
	  app.use(express.bodyParser());
	  app.use(express.methodOverride());
	  app.use(flash());
	  app.use(express.cookieParser());
	  //session
	  app.use(express.session({ secret: 'keyboard cat', key:createSID(),cookie: { secure: true},cookie: { maxAge: 60*60*1000}}));

     //  app.use(partials());
      app.use(app.router);

      app.use(express.static(path.join(__dirname, 'public')));
});

//404
app.use(function(req, res, next){
	  res.render('404', {  status: 404, url: req.url  });
	});
//500
app.use(function(err, req, res, next){
	  res.render('500', {
	      status: err.status || 500
	    , error: err
	  });
});
app.configure('development', function(){
  app.use(express.errorHandler());
});
//首页
app.get('/', routes.index);
app.get('/indexload',routes.indexload);
app.get('/category',routes.category);
app.get('/catone',routes.getone);
app.get('/catwo',routes.gettwo);
app.get('/check',routes.check);
app.get('/send',routes.send);
app.get('/register',routes.register);
app.get('/write',routes.writeView);
app.post('/write',routes.write);
//app.get('/blog',routes.blog);
app.get('/show',routes.show);
app.get('/comment',routes.comment);
app.post('/comment',routes.create_comment);
app.get('/viewcount',routes.viewcount);

app.get('/hot',routes.hot);

app.get('/search',routes.search);


http = http.createServer(app);
http.listen(app.get('port'), function(){
    console.log("Express server listening on port " + app.get('port'));
})



