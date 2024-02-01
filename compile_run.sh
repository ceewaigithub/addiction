#!/bin/bash

# Create the class directory if it doesn't exist
mkdir -p class

# Compile the Java files
echo "Compiling Java files..."
javac -d class src/**/*.java

# List the contents of the class directory
# echo "Contents of the class directory:"
# ls -R class

# Run the Main class
echo "Running project..."
java -cp class world.Main