//组合
exports.appendParam=function appendParam(returnStr,paramId,paramValue){
	if (returnStr!="") {
		if (paramValue!="") {
			returnStr = returnStr + "&" + paramId + "=" + paramValue;
		}
	} else {
		if (paramValue!="") {
			returnStr = paramId + "=" + paramValue;
		}
	}
	return returnStr;
};