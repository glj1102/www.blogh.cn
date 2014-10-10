LOGDIR=/home/zhanghuibing/nodejs/iwrong-student-online/
logtime=`date +%Y%m%d%H%M%S`
logname=node.log
rm -rf *.node.log
forever start  -l $LOGDIR$logtime.$logname  app.js

