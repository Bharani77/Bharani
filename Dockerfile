# Use an official Node.js runtime as a parent image
FROM node:14

# Install XVFB and other dependencies
RUN apt-get update && apt-get install -y \
    xvfb \
    libgtk2.0-0 \
    libnotify-dev \
    libgconf-2-4 \
    libnss3 \
    libxss1 \
    libasound2 \
    libxtst6 \
    xauth \
    && rm -rf /var/lib/apt/lists/*

# Set up a working directory
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code to the working directory
COPY . .

# Expose any necessary ports
EXPOSE 3000

# Set environment variables, if needed
# ENV NODE_ENV production

# Run XVFB and start the Node.js application in the background
CMD Xvfb :99 -screen 0 1024x768x16 && \
    export DISPLAY=:99 && \
    npm start
