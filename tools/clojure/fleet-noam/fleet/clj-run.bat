@echo off

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

:begin
@rem Determine what directory it is in.
set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.\


set BASEDIR=%DIRNAME%lib

set CLASSPATH=%BASEDIR%\clojure-1.4.0.jar
set CLASSPATH=%BASEDIR%\groovy-all-2.0.7.jar;%CLASSPATH%
set CLASSPATH=%BASEDIR%\clj-http-lite-0.2.0.jar;%CLASSPATH%
set CLASSPATH=%BASEDIR%\fs-1.3.3.jar;%CLASSPATH%
set CLASSPATH=%BASEDIR%\common-tools-0.2.0.jar;%CLASSPATH%
set CLASSPATH=%BASEDIR%;%CLASSPATH%
set CLASSPATH=%DIRNAME%;%CLASSPATH%

set oldcd=%cd%
cd %BASEDIR%
call %DIRNAME%\groovy-run.bat %DIRNAME%\wget.groovy http://nexushost/nexus/service/local/repo_groups/releases/content/org/clojure/clojure/1.4.0/clojure-1.4.0.jar
call %DIRNAME%\groovy-run.bat %DIRNAME%\wget.groovy http://nexushost/nexus/service/local/repo_groups/releases/content/fs/fs/1.3.3/fs-1.3.3.jar
call %DIRNAME%\groovy-run.bat %DIRNAME%\wget.groovy http://nexushost/nexus/service/local/repo_groups/releases/content/clj-http-lite/clj-http-lite/0.2.0/clj-http-lite-0.2.0.jar
cd %oldcd%

%JAVA_HOME%\bin\java -cp %CLASSPATH% clojure.main %*

@rem End local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" endlocal
%COMSPEC% /C exit /B %ERRORLEVEL%

