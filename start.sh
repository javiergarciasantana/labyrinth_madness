#!/bin/bash

# 1. Limpiamos cualquier bloqueo fantasma de ejecuciones anteriores
rm -f /tmp/.X0-lock /tmp/.X11-unix/X0

echo "Starting Xvfb virtual monitor..."
Xvfb :0 -screen 0 608x700x24 
&export DISPLAY=:0

# --- CRUCIAL: Damos 1 segundo para que Xvfb termine de encender la pantalla ---
sleep 1

echo "Starting Matchbox window manager..."
matchbox-window-manager -use_titlebar no &

echo "Starting x11vnc..."
x11vnc -display :0 -nopw -listen localhost -xkb -forever &

echo "Starting noVNC..."
/usr/share/novnc/utils/launch.sh --vnc localhost:5900 --listen 8080 &

# --- Damos 2 segundos para que arranque el servidor web antes de lanzar Java ---
sleep 2 

echo "Starting MySketch application..."
java -cp LabyrinthApp.jar:core.jar labyrinth_madness.src.Main