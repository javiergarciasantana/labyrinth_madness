FROM eclipse-temurin:21-jdk-jammy
ENV DEBIAN_FRONTEND=noninteractive

# Install our graphical stack
RUN apt-get update && apt-get install -y \
    xvfb \
    x11vnc \
    matchbox-window-manager \
    novnc \
    websockify \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy your source code, compiled classes, and scripts
COPY . /app

# (Optional) If you haven't compiled locally, you can compile inside Docker
# Assuming core.jar is placed in the /app directory:
# RUN javac -cp core.jar src/*.java

# Make the startup script executable
RUN chmod +x /app/start.sh

# Expose the noVNC web port
EXPOSE 8080

CMD ["/app/start.sh"]