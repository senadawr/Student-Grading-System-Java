@echo on

:: exit all java processes
taskkill /F /IM java.exe /T 2>nul
timeout /t 2 /nobreak >nul

:: check for database
if exist students.db (
    echo Using existing database file.
    echo Current database size:
    dir stuudents.db
) else (
    echo No existing database found.
)

:: run program
java ^
--module-path "%cd%\lib" ^
--add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base ^
--add-exports javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED ^
--add-exports javafx.base/com.sun.javafx.reflect=ALL-UNNAMED ^
--add-exports javafx.graphics/com.sun.glass.utils=ALL-UNNAMED ^
--add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED ^
--add-opens javafx.graphics/com.sun.glass.ui=ALL-UNNAMED ^
--add-opens javafx.graphics/com.sun.glass.utils=ALL-UNNAMED ^
-Djava.library.path="%cd%\lib" ^
-Dprism.order=sw ^
-cp "%cd%\bin;%cd%\lib\*" ^
com.grading.Main 2>&1

:: check if crashed
if errorlevel 1 (
    echo.
    echo Program crashed with error level %errorlevel%
    echo.
)

pause