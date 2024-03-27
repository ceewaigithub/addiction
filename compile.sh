#!/bin/bash

# Create the class directory if it doesn't exist
mkdir -p class

# Compile the Java files
echo "Compiling Java files..."
javac -d class src/**/*.java