@echo off

REM Set the current directory to the script's directory
cd /d "%~dp0"

REM Create the class directory if it doesn't exist
mkdir class

REM Compile the Java files
echo Compiling Java files...
for /r src %%f in (*.java) do (
    javac -d class "%%f"
)

REM Run the Main class
echo Running project...
java -cp class world.Main