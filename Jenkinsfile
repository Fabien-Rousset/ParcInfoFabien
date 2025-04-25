pipeline {
    agent any
    environment{
        registry= 'fhurai/parcinfo'
        registryTag= 'latest'
    }
    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }
        stage('Git Checkout') {
            steps {
                script {
                    git branch: 'main',
                    credentialsId: 'github_access',
                    url: 'https://github.com/Fhurai/ParcInfo.git'
                }
            }
        }
        stage('Build Maven') {
            steps {
                bat 'mvn clean package'
            }
        }
        stage('Build Docker Image'){
            steps {
                script {
                    docker.build("${registry}:${registryTag}", '-f Dockerfile .');
                }
            }
        }
        stage('Push to Docker Hub'){
            steps {
                script {
                    docker.withRegistry('', 'dockerhub-credentials') {
                        docker.image("${registry}:${registryTag}").push();
                    }
                }
            }
        }

        stage('Deploy docker-compose') {
            steps {
                script {
                    bat 'docker-compose up -d --build --force-recreate --remove-orphans'
                }
            }
        }
         stage('Notify Discord') {
            steps {
                discordSend(
                    webhookURL: 'https://discord.com/api/webhooks/1362093021692301513/dmNmJ4KADsuBVvuKzGi-x7-j1_yIQjWqNy1G21-ZfKGECgq632sz5DpTeKAeJ7y7bVQJ',
                    title: env.JOB_NAME,
                    link: env.BUILD_URL,
                    description: "Build ${env.BUILD_NUMBER} finished with status: ${currentBuild.currentResult}",
                    footer: "Jenkins CI",
                    result: currentBuild.currentResult
                )
            }
        }
    }
}
