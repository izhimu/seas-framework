
#!/bin/sh
#定义程序名 及jar包的名
SERVER_NAME="Seas"
BASE_PATH=/usr/local/seas
JRE_PATH=${BASE_PATH}/jre/bin/java
APP_PATH=${BASE_PATH}/lib/seas.jar
LOG_PATH=${BASE_PATH}/log/seas.log
JAVA_OPTION="-Xms128m -Xmx128m"
MAX_TIMEOUT=120

chmod +x ${JRE_PATH}

## 编写判断程序是否正在运行的方法
isExist(){
	## 首先查找进程号
    pid=$(ps -ef | grep ${APP_PATH} | grep -v "grep" | awk '{print $2}')
    ## 如果进程号不存在，则返回0 否则返回1
    if [ -z "${pid}" ]; then
    	return 0
    else
    	return 1
    fi
}
## 编写启动程序方法
start(){
	## 调用 判断程序是否正在运行的方法
    isExist
    ## 判断方法返回值是否等于0 ，等于则不存在
    if [ $? -eq "0" ]; then
    	echo "服务启动中 ......"
    	nohup ${JRE_PATH} ${JAVA_OPTION} -jar ${APP_PATH} > ${LOG_PATH} 2>&1 &
     	echo "服务启动成功"
    else
   		echo "服务已存在 - PID: ${pid}"
    fi
}
## 编写停止程序的方法
stop(){
    ## 调用 判断程序是否正在运行
    isExist
    ## 判断是否存在，返回值0不存在
    if [ $? -eq "0" ]; then
   		echo "服务未运行，无需停止"
    else
    	echo "服务停止中 - PID: ${pid} ......"
    	kill -9 ${pid}
    	echo "服务停止成功"
    fi
}
## 编写重启方法
restart(){
    ## 先停止再启动
    quit
    start
}
## 日志查看
log(){
    tail -f ${LOG_PATH}
}
## 编写退出程序的方法
quit(){
    ## 调用 判断程序是否正在运行
    isExist
    ## 判断是否存在，返回值0不存在
    if [ $? -eq "0" ]; then
    	echo "服务未运行，无需停止"
    else
    	echo "服务退出中 - PID: ${pid} "
    	kill -15 ${pid}
		## 等待程序退出
		for((i=0;i<$MAX_TIMEOUT;i++))
		do
		sleep 1
		pid=$(ps -ef | grep ${APP_PATH} | grep -v "grep" | awk '{print $2}')
		if [ -n "$pid" ]; then
			echo -e ".\c"
		else
			echo ""
			break
		fi
		done
		## 未退出，直接终止
		pid=$(ps -ef | grep ${APP_PATH} | grep -v "grep" | awk '{print $2}')
		if [ -n "$pid" ]; then
  			stop
		fi
    	echo "服务退出成功"
    fi
}

echo "${SERVER_NAME} >>>>>>"
 
 
## 程序最开始执行的
## 根据用户输入，判断执行方法
case "$1" in
	"start")
		start
		;;
	"stop")
		stop
		;;
	"restart")
		restart
		;;
	"log")
		log
		;;
	"quit")
		quit
		;;
	*)
		echo "请输入正确的命令: "
		echo "[ start | stop | restart | log | quit ]"
		;;
esac

echo ""
