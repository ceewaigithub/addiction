@echo off

REM Create the class directory if it doesn't exist
mkdir class

REM Compile the Java files
echo Compiling Java files...
javac -d class src\**\*.java

REM Run the Main class
echo Running project...
java -cp class world.Main