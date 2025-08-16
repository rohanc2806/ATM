#!/bin/bash
echo "================================================"
echo "       ATM Simulator - Compilation Script"
echo "================================================"

echo "🔧 Creating build directory..."
mkdir -p build

echo "⚙️ Compiling Java files..."
javac -d build *.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo "🚀 Starting ATM Simulator..."
    echo
    java -cp build ATMSimulator
else
    echo "❌ Compilation failed! Please check your Java installation."
fi

