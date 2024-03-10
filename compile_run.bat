@echo off

REM Set the current directory to the script's directory
cd /d "%~dp0"

REM Compile the Java files
echo Compiling Java files...
javac -d class src\**\*.java

REM Run the Main class
echo Running project...
java -cp class world.Main