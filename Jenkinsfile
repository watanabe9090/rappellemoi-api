pipeline {
    agent any

    stages {
        stage('Build Image') {
            steps {
                script {
                    dockerapp = docker.build("ctc/rappellemoi-api:${env.BUILD_ID}", '-f  ./Dockerfile ./')
                }
            }
        }
    }
}