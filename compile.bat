@echo off
setlocal enabledelayedexpansion

:: clean bin if exists
if exist bin rmdir /s /q bin
mkdir bin

:: set javafx paths
set PATH_TO_FX=lib
set CLASSPATH="%PATH_TO_FX%/*;src;bin"
set JAVAC_OPTS=--module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -d bin -cp %CLASSPATH%

:: Echo configuration
echo Building with following settings:
echo JavaFX path: %PATH_TO_FX%
echo Classpath: %CLASSPATH%
echo.

:: compile in correct order
echo Compiling Java files...

javac %JAVAC_OPTS% src/com/grading/model/Student.java

javac %JAVAC_OPTS% src/com/grading/model/Database.java

javac %JAVAC_OPTS% ^
    src/com/grading/util/SortingAlgorithms.java ^
    src/com/grading/view/AddStudentDialog.java ^
    src/com/grading/view/SortResultDialog.java ^
    src/com/grading/view/MainView.java ^
    src/com/grading/Main.java

:: check for errors
if errorlevel 1 (
    echo.
    echo Compilation failed! Check the errors above.
    pause
    exit /b 1
) else (
    echo.
    echo Compilation successful!
    echo.
    dir /s /b bin\com\grading\*.class
)

pause