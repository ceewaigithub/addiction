@echo off

REM Create the class directory if it doesn't exist
if not exist class mkdir class

REM Compile all Java files in the res folder
echo Compiling Java files...
REM Compile all Java files in the src folder and its subdirectories
for /r src %%G in (*.java) do (
    if not exist class\%%~dpG mkdir class\%%~dpG
    javac -d class -sourcepath src "%%G"
)

REM Run the Main class
echo Running project...
java -cp class world.Main
