@echo off

REM Create the class directory if it doesn't exist
mkdir class

REM Compile the Java files
echo Compiling Java files...
javac -d class src/**/*.java

REM List the contents of the class directory
REM echo Contents of the class directory:
REM dir /s class

REM Run the Main class
echo Running project...
java -cp class Main