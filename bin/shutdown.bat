@echo off
set SERVER_NAME=�΢ϵͳ������֤���ֻ�ƽ̨
set POSTGRESQL_SERVER_NAME=postgresql-x64-16

echo.
echo -- Seas Framework App Launcher --
echo.

echo [%time%] ����ֹͣ%SERVER_NAME%...
taskkill /FI "WINDOWTITLE eq %SERVER_NAME%"
echo [%time%] ��ֹͣ%SERVER_NAME%

echo [%time%] ����ֹͣNginx����...
taskkill /T /F /FI "imagename eq nginx.exe"
echo [%time%] ��ֹͣNginx����

echo [%time%] ����ֹͣPostgreSQL����...
net stop %POSTGRESQL_SERVER_NAME%
echo [%time%] ��ֹͣPostgreSQL����
echo.

pause