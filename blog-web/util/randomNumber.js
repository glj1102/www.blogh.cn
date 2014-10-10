var dateFormat = require('dateformat');
exports.getFileName = function getFileName(){
	var date = new Date();
	var num=1;
	var random = Math.random();
	if(random < 0.1) {
		random = random + 0.1;
	}
	num = Math.floor(random * 100000000);
	return dateFormat(date,"yyyymmddHHMMss")+num;
};