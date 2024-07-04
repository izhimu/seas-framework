@echo off
set SERVER_NAME=宇航微系统质量保证数字化平台
set POSTGRESQL_SERVER_NAME=postgresql-x64-16

echo.
echo -- Seas Framework App Launcher --
echo.

echo [%time%] 正在停止%SERVER_NAME%...
taskkill /FI "WINDOWTITLE eq %SERVER_NAME%"
echo [%time%] 已停止%SERVER_NAME%

echo [%time%] 正在停止Nginx服务...
taskkill /T /F /FI "imagename eq nginx.exe"
echo [%time%] 已停止Nginx服务

echo [%time%] 正在停止PostgreSQL服务...
net stop %POSTGRESQL_SERVER_NAME%
echo [%time%] 已停止PostgreSQL服务
echo.

pause