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

              STATUS="${{ needs.build.result }}"
                        if [ "$STATUS" = "success" ]; then
                          COLOR=3066993
                          MESSAGE="✅ Parc Info API Pipeline Succeeded"
                          IMAGE_URL="https://c.tenor.com/Ud36Rrav628AAAAC/tenor.gif"
                        else
                          COLOR=15158332
                          MESSAGE="❌ Parc Info API Pipeline Failed"
                          IMAGE_URL="https://c.tenor.com/nsEfkzN30TIAAAAC/tenor.gif"
                        fi

                        PAYLOAD=$(cat <<EOF
                        {
                          "embeds": [{
                            "title": "${MESSAGE}",
                            "description": "**Workflow**: ${{ github.workflow }}\n**Repository**: ${{ github.repository }}\n**Branch**: ${{ github.ref }}\n**Commit**: [${GITHUB_SHA:0:7}](${{ github.server_url }}/${{ github.repository }}/commit/$GITHUB_SHA)",
                            "color": ${COLOR},
                            "image": {
                              "url": "${IMAGE_URL}"
                            },
                            "timestamp": "$(date -u +'%Y-%m-%dT%H:%M:%SZ')"
                          }]
                        }
                        EOF
                        )

                        curl -X POST -H "Content-Type: application/json" -d "$PAYLOAD" "$DISCORD_WEBHOOK_URL"
           }
        }
    }
}
