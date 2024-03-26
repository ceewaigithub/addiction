@echo off

REM Create the class directory if it doesn't exist
if not exist class mkdir classs

REM Compile all Java files in the src folder and its subdirectories
echo Compiling Java files...
javac -d class -cp src src/world/Main.java