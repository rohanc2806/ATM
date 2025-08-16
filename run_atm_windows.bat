@echo off
echo ================================================
echo        ATM Simulator - Compilation Script
echo ================================================

echo 🔧 Creating build directory...
if not exist build mkdir build

echo ⚙️ Compiling Java files...
javac -d build *.java

if %ERRORLEVEL% EQU 0 (
    echo ✅ Compilation successful!
    echo 🚀 Starting ATM Simulator...
    echo.
    java -cp build ATMSimulator
) else (
    echo ❌ Compilation failed! Please check your Java installation.
    pause
)