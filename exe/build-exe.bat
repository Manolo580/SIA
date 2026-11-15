@echo off
REM ==========================================================
REM  Genera dist\SIA\ con SIA.exe y SIA-Ventana.exe
REM  Requiere JDK 17 o superior (trae jpackage).
REM  El resultado incluye Java: el PC donde se use NO necesita Java.
REM ==========================================================
setlocal
cd /d "%~dp0"

where jpackage >nul 2>nul
if errorlevel 1 (
    echo No se encontro jpackage. Instala un JDK 17 o superior y vuelve a intentar.
    exit /b 1
)

if exist build rmdir /s /q build
if exist dist rmdir /s /q dist
mkdir build\classes build\jar

echo [1/3] Compilando...
javac -encoding UTF-8 --release 8 -d build\classes *.java
if errorlevel 1 exit /b 1

echo [2/3] Creando SIA.jar...
jar --create --file build\jar\SIA.jar --main-class Main -C build\classes .
if errorlevel 1 exit /b 1

echo [3/3] Creando ejecutables...
jpackage --type app-image --name SIA --app-version 1.0 ^
  --input build\jar --main-jar SIA.jar --main-class Main ^
  --win-console ^
  --add-launcher SIA-Ventana=packaging\ventana.properties ^
  --dest dist
if errorlevel 1 exit /b 1

echo.
echo Listo: dist\SIA\SIA.exe (consola) y dist\SIA\SIA-Ventana.exe (ventana)
endlocal
