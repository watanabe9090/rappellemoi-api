pipeline {
    agent any

    stages {
        stage('Build Image') {
            steps {
                script {
                    dockerapp = docker.build("watanabe9090/rappellemoi-api:${env.BUILD_ID}", '-f  ./Dockerfile ./')
                }
            }
        }

        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                        dockerapp.push('latest')
                        dockerapp.push("${env.BUILD_ID}")
                    }
                }
            }
        }

        stage('Deploy Kubernetes') {
            steps {
                withKubeConfig([credentialId: 'kubeconfig']) {
                    sh 'kubectl apply -f ./k8s/deployment.yml'
                }
            }
        }
    }
}