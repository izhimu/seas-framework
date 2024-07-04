@echo off
reg add HKEY_CURRENT_USER\Console /v QuickEdit /t REG_DWORD /d 00000000 /f >nul
set SERVER_NAME=�΢ϵͳ������֤���ֻ�ƽ̨
set BASE_PATH=C:\Seas\package
set APP_PATH=%BASE_PATH%/lib/seas-project-warranty-1.0.1.jar
set JAVA_OPTION=-Xms512m -Xmx512m
set NGINX_PATH=C:\Seas\nginx-1.27.0
set POSTGRESQL_SERVER_NAME=postgresql-x64-16

title Seas Framework App Launcher

echo.
echo -- Seas Framework App Launcher --
echo.

echo [%time%] ���PostgreSQL����
sc query "%POSTGRESQL_SERVER_NAME%" | find "RUNNING" >nul 2>&1
if "%ERRORLEVEL%"=="0" (
    echo [%time%] PostgreSQL������������
) else (
    echo [%time%] ��������PostgreSQL����...
    net start "%POSTGRESQL_SERVER_NAME%"
    echo [%time%] PostgreSQL���������ɹ�
)

echo [%time%] ���Nginx����
tasklist /FI "imagename eq nginx.exe" 2>nul | find /I /N "nginx.exe">nul
if "%ERRORLEVEL%"=="0" (
    echo [%time%] Nginx������������
) else (
    echo [%time%] ��������Nginx����...
    cd %NGINX_PATH%
    start "nginx" nginx.exe
    echo [%time%] Nginx���������ɹ�
)

echo [%time%] ���%SERVER_NAME%
tasklist /FI "imagename eq %SERVER_NAME%" 2>nul | find /I /N "%SERVER_NAME%">nul
if "%ERRORLEVEL%"=="0" (
    echo [%time%] %SERVER_NAME%��������
) else (
    echo [%time%] ��������%SERVER_NAME%...
    start "%SERVER_NAME%" java %JAVA_OPTION% -jar %APP_PATH%
    echo [%time%] %SERVER_NAME%�����ɹ�
)

pause
