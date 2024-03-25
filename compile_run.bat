@echo off

REM Create the class directory if it doesn't exist
if not exist class mkdir class

REM Compile all Java files in the src folder and its subdirectories
echo Compiling Java files...
for /r src %%F in (*.java) do (
    echo Compiling %%F...
    javac -d class -cp class "%%F"
)

REM Run the Main class
echo Running project...
java -cp class world.Main