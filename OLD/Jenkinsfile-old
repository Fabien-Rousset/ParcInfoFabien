pipeline {
    agent any

    environment {
        // Détails du registre Docker
        registry    = 'fhurai/parcinfo'
        registryTag = 'latest'
        
        // URL du webhook Discord pour les notifications
        DISCORD_WEBHOOK_URL = credentials('discord-webhook')
    }

    stages {
        stage('Nettoyer l\'espace de travail') {
            steps {
                // Nettoyer l'espace de travail pour s'assurer qu'il n'y a pas de fichiers restants des builds précédents
                cleanWs()
            }
        }

        stage('Récupération Git') {
            steps {
                // Cloner le dépôt depuis GitHub en utilisant la branche et les identifiants spécifiés
                git branch: 'main',
                    credentialsId: 'github_access',
                    url: 'https://github.com/Fhurai/ParcInfo.git'
            }
        }

        stage('Construire avec Maven') {
            steps {
                // Compiler et empaqueter l'application avec Maven
                bat 'mvn clean package'
            }
        }

        stage('Construire l\'image Docker') {
            steps {
                script {
                    // Construire l'image Docker en utilisant le Dockerfile spécifié
                    docker.build("${registry}:${registryTag}", '-f Dockerfile .')
                }
            }
        }

        stage('Pousser sur Docker Hub') {
            steps {
                script {
                    // Pousser l'image Docker sur Docker Hub en utilisant les identifiants enregistrés
                    docker.withRegistry('', 'dockerhub-credentials') {
                        docker.image("${registry}:${registryTag}").push()
                    }
                }
            }
        }

        stage('Déployer avec docker-compose') {
            steps {
                // Déployer l'application en utilisant docker-compose
                bat 'docker-compose up -d --build --force-recreate --remove-orphans'
            }
        }
    }

    post {
        always {
            script {
                // Envoyer une notification avec le statut du build à Microsoft Teams
                def jobName     = env.JOB_NAME
                def buildNumber = env.BUILD_NUMBER
                def buildStatus = currentBuild.currentResult

                def message = "${jobName} build #${buildNumber} terminé avec le statut : ${buildStatus}"

                office365ConnectorSend(
                    webhookUrl: "https://afpadevpompey.webhook.office.com/webhookb2/3d85d65d-751e-46e5-9364-a8dcaa81559e@4909a83b-31e2-41e6-8281-41681652175f/IncomingWebhook/2382f891bf184384a19ff814ba66eba8/d192b800-2eb2-4a7a-a780-1f1411683c91/V2oNWRpOVYoyrQvjQhojY4jyM8fn8vOfDNbf5uhV559do1",
                    message: message,
                    status: buildStatus,
                    adaptiveCards: true
                )
            }
        }
    }
}
