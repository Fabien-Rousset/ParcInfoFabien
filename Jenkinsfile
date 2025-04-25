pipeline {
    agent any

    triggers {
        // Trigger on push to the repository
        githubPush() // For GitHub
    }

    environment {
        registry    = 'fhurai/parcinfo'
        registryTag = 'latest'
        DISCORD_WEBHOOK_URL = credentials('discord-webhook')
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
                def jobName     = env.JOB_NAME
                def buildNumber = env.BUILD_NUMBER
                def buildStatus = currentBuild.currentResult

                def message = "${jobName} build #${buildNumber} finished with status: ${buildStatus}"

                office365ConnectorSend(
                    webhookUrl: "https://afpadevpompey.webhook.office.com/webhookb2/3d85d65d-751e-46e5-9364-a8dcaa81559e@4909a83b-31e2-41e6-8281-41681652175f/IncomingWebhook/2382f891bf184384a19ff814ba66eba8/d192b800-2eb2-4a7a-a780-1f1411683c91/V2oNWRpOVYoyrQvjQhojY4jyM8fn8vOfDNbf5uhV559do1",
                    message: message,
                    status: buildStatus,
                    adaptiveCards: true
                )

                // Determine the Discord notification content based on build status
                def color
                def messageContent
                def imageUrl

                if (buildStatus == 'SUCCESS') {
                    color = 3066993
                    messageContent = "✅ Parc Info API Pipeline Succeeded"
                    imageUrl = "https://c.tenor.com/Ud36Rrav628AAAAC/tenor.gif"
                } else {
                    color = 15158332
                    messageContent = "❌ Parc Info API Pipeline Failed"
                    imageUrl = "https://c.tenor.com/nsEfkzN30TIAAAAC/tenor.gif"
                }

                def payload = """
                {
                    "embeds": [{
                        "title": "${messageContent}",
                        "description": "${jobName} build #${buildNumber} finished with status: ${buildStatus}",
                        "color": ${color},
                        "image": {
                            "url": "${imageUrl}"
                        },
                        "timestamp": "${new Date().format('yyyy-MM-dd\'T\'HH:mm:ss\'Z\'', TimeZone.getTimeZone('UTC'))}"
                    }]
                }
                """

                // Send notification to Discord
                sh "curl -X POST -H 'Content-Type: application/json' -d '${payload}' '${DISCORD_WEBHOOK_URL}'"
            }
        }
    }
}
