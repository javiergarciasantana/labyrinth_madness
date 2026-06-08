FROM eclipse-temurin:21-jdk-jammy
ENV DEBIAN_FRONTEND=noninteractive

# The graphical env is installed, wget to download libs and fontconfig for fonts
RUN apt-get update && apt-get install -y \
    xvfb \
    x11vnc \
    matchbox-window-manager \
    novnc \
    websockify \
    libxext6 \
    libxrender1 \
    python3-numpy \
    libxtst6 \
    libxi6 \
    wget \
    fontconfig \
    fonts-liberation \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# The code gets copied to the container
COPY . /app

# core.jar gets downloaded to use processing
RUN wget https://repo1.maven.org/maven2/org/processing/core/4.5.3/core-4.5.3.jar -O core.jar

# 1. The code gets compiled using core.jar
RUN javac -cp core.jar -d . src/*.java

# 2. Everithing gets packed in the .jar
RUN jar cvf LabyrinthApp.jar labyrinth_madness/src/*.class

# We give the scipt the required permissions
RUN chmod +x /app/start.sh

# Port gets exposed
EXPOSE 8080

CMD ["/app/start.sh"]