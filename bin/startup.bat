@echo off
reg add HKEY_CURRENT_USER\Console /v QuickEdit /t REG_DWORD /d 00000000 /f >nul
set SERVER_NAME=宇航微系统质量保证数字化平台
set BASE_PATH=C:\Seas\package
set APP_PATH=%BASE_PATH%/lib/seas-project-warranty-1.0.1.jar
set JAVA_OPTION=-Xms512m -Xmx512m
set NGINX_PATH=C:\Seas\nginx-1.27.0
set POSTGRESQL_SERVER_NAME=postgresql-x64-16

title Seas Framework App Launcher

echo.
echo -- Seas Framework App Launcher --
echo.

echo [%time%] 检查PostgreSQL服务
sc query "%POSTGRESQL_SERVER_NAME%" | find "RUNNING" >nul 2>&1
if "%ERRORLEVEL%"=="0" (
    echo [%time%] PostgreSQL服务正在运行
) else (
    echo [%time%] 正在启动PostgreSQL服务...
    net start "%POSTGRESQL_SERVER_NAME%"
    echo [%time%] PostgreSQL服务启动成功
)

echo [%time%] 检查Nginx服务
tasklist /FI "imagename eq nginx.exe" 2>nul | find /I /N "nginx.exe">nul
if "%ERRORLEVEL%"=="0" (
    echo [%time%] Nginx服务正在运行
) else (
    echo [%time%] 正在启动Nginx服务...
    cd %NGINX_PATH%
    start "nginx" nginx.exe
    echo [%time%] Nginx服务启动成功
)

echo [%time%] 检查%SERVER_NAME%
tasklist /FI "imagename eq %SERVER_NAME%" 2>nul | find /I /N "%SERVER_NAME%">nul
if "%ERRORLEVEL%"=="0" (
    echo [%time%] %SERVER_NAME%正在运行
) else (
    echo [%time%] 正在启动%SERVER_NAME%...
    start "%SERVER_NAME%" java %JAVA_OPTION% -jar %APP_PATH%
    echo [%time%] %SERVER_NAME%启动成功
)

pause
