#!/bin/bash

# 1. Start the virtual monitor
# We use 800x800 to comfortably fit the 605x700 MySketch canvas.
echo "Starting Xvfb virtual monitor..."
Xvfb :0 -screen 0 800x800x16 &
export DISPLAY=:0

# 2. Start the window manager to strip borders and center the app
echo "Starting Matchbox window manager..."
matchbox-window-manager -use_titlebar no &

# 3. Start the VNC server (no cache, raw pixels)
echo "Starting x11vnc..."
x11vnc -display :0 -nopw -listen localhost -xkb -forever &

# 4. Start the noVNC web bridge
echo "Starting noVNC..."
/usr/share/novnc/utils/launch.sh --vnc localhost:5900 --listen 8080 &

# Give the display server a moment to initialize
sleep 2 

# 5. Run the Labyrinth Madness app
# Note: We include both the current directory (.) and core.jar in the classpath
echo "Starting MySketch application..."
java -cp .:core.jar labyrinth_madness.src.Main