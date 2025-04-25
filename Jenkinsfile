pipeline {
    agent any

    environment {
        registry    = 'fhurai/parcinfo'
        registryTag = 'latest'
    }

    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'github_access',
                    url: 'https://github.com/Fhurai/ParcInfo.git'
            }
        }

        stage('Build Maven') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${registry}:${registryTag}", '-f Dockerfile .')
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', 'dockerhub-credentials') {
                        docker.image("${registry}:${registryTag}").push()
                    }
                }
            }
        }

        stage('Deploy docker-compose') {
            steps {
                bat 'docker-compose up -d --build --force-recreate --remove-orphans'
            }
        }
    }

    post {
        always {
           script {
              office365ConnectorSend webhookUrl: "https://afpadevpompey.webhook.office.com/webhookb2/3d85d65d-751e-46e5-9364-a8dcaa81559e@4909a83b-31e2-41e6-8281-41681652175f/IncomingWebhook/2382f891bf184384a19ff814ba66eba8/d192b800-2eb2-4a7a-a780-1f1411683c91/V2oNWRpOVYoyrQvjQhojY4jyM8fn8vOfDNbf5uhV559do1",
                  message: ${jobName},
                  status: ${buildStatus},
                  adaptiveCards: true
           }
        }
    }
}
