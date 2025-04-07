!/bin/bash
# Redeploy the application using Maven and Cargo plugin
# Usage: ./redeploy.sh

clear && mvn clean package && mvn cargo:redeploy && mvn checkstyle:checkstyle