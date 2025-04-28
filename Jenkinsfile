pipeline {
    agent any


    environment {

        //Nom de mon image pour le dockerHub
        registry = "fabien003/WebApp"

        //Compte DockerHub paramétré sur le serveur Jenkins dans la rubrique Credentials de l admin serveur
        registryCredential = 'GITHUB-JENKINS'

        dockerImage= ''
    }


    stages {

        //nettoyage du workspace avant de recopier le dépôt
                stage('Clean workspace') {
                    steps {
                        cleanWs()
                    }
                }


        // Cloner le dépôt depuis GitHub en utilisant la branche et les identifiants spécifiés
        stage('Git Checkout') {
            steps {
                script {
                    git branch: 'main',
                    credentialsId: 'GITHUB-JENKINS',
                    url: 'https://github.com/Fabien-Rousset/ParcInfoFabien.git'
                }
            }
        }


        //Création du build et recupération du .jar
        stage('Build Maven') {
            steps {
                bat 'mvn clean package'
            }
        }

        //Construction de l'image Docker à partir du DockerFile
        //-f Dockerfile : indique explicitement le chemin (relatif) du fichier Dockerfile.
        //     . : définit le contexte de build (le répertoire courant).
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build('fabien003/WebApp:latest', ' -f Dockerfile .')
                }
            }
        }

        //Push de l'image dans le dockerHub
        //Premier argument ('') : l’URL du registre. Vide ici, ce qui fait qu’on utilise le registre Docker Hub par défaut.

         //Deuxième argument (registryCredential) : l’ID d’une « Jenkins Credential » de type « Docker Registry »
         //qui contient  nom d’utilisateur et mot de passe (ou token) Docker Hub.
        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        docker.image('fabien003/webapp:latest')
                    }
                }
            }
        }















    }
}