var http = require('http');
http.globalAgent.maxSockets = 50000;

exports.request = function request(options, callback,params) {
	var post_data = "";
    var logInfo = "http请求，url:" + "http://" + options.host + ":"
        + options.port + options.path;
    if(params){
        post_data = JSON.stringify(params);
        logInfo = logInfo +",params:"+post_data;
    }
	var post_req = http.request(options, function(result) {
		console.log(logInfo);
		var data = '';
        result.setEncoding("utf8");
		result.on("data", function(chunk) {
			data += chunk;
		});
		result.on('end', function() {
//			console.log("请求返回结果，result:" + data.toString());
			callback(data);
		});
	});

	post_req.setTimeout(Url.time_out, function() {
		post_req.abort();
        callback(new Error('Request timeout'));
	});
	post_req.on('error', function(e) {
		callback(new Error("httpGet : exception " + e.message));
	});
    if(params){
        post_req.write(post_data);
    }
	post_req.end();
};


