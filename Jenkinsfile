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
    }
    post {
            always {
                script {
                    def discordWebhookUrl = 'https://discord.com/api/webhooks/1362093021692301513/dmNmJ4KADsuBVvuKzGi-x7-j1_yIQjWqNy1G21-ZfKGECgq632sz5DpTeKAeJ7y7bVQJ'
                    def jobName = env.JOB_NAME
                    def buildNumber = env.BUILD_NUMBER
                    def buildUrl = env.BUILD_URL
                    def buildStatus = currentBuild.currentResult

                    def message = """
                    {
                        "content": "**${jobName}** build #${buildNumber} finished with status: **${buildStatus}**\n<${buildUrl}>"
                    }
                    """

                    sh """
                        curl -H "Content-Type: application/json" \
                             -X POST \
                             -d '${message.replaceAll("'", "'\\''")}' \
                             ${discordWebhookUrl}
                    """
                }
            }
        }
}
