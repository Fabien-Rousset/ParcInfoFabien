pipeline {
    agent any
    stages {
        stage('Clean Workspace'){
            steps {
                cleanWs()
            }
        }
        stage('Git Checkout'){
            steps{
                script{
                    git branch: 'main',
                    credentialsId: 'github_access',
                    url: 'https://github.com/Fhurai/ParcInfo.git'
                }
            }
        }
    }
}