@echo off

:: compile
call compile.bat

:: check if successful
if errorlevel 1 (
    echo Compilation failed, not running program.
    pause
    exit /b 1
)

:: run if successful
call run.bat 