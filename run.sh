#!/bin/bash

# Clear the terminal screen to provide a clean output view
clear 

# Run Maven tests and start the Spring Boot application
set -a && source .env && set +a && clear && mvn clean package spring-boot:repackage