pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    environment {
        APP_NAME = 'jenkins-java-demo'
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Build #${BUILD_NUMBER} started"
                checkout scm
            }
        }

        stage('Show Secret') {
            steps {
                withCredentials([
                    string(
                        credentialsId: 'my-first-secret',
                        variable: 'MY_SECRET'
                    )
                ]) {
                    sh 'echo "Secret value is: ${MY_SECRET}"'
                    // Jenkins will print: Secret value is: ****
                }
            }
        }

        stage('Compile') {
            steps {
                echo "Compiling ${APP_NAME}..."
                sh 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
            }
        }
    }

    post {
        success {
            echo "✅ ${APP_NAME} build #${BUILD_NUMBER} successful!"
        }
        failure {
            echo "❌ ${APP_NAME} build #${BUILD_NUMBER} failed!"
        }
        always {
            echo 'Pipeline finished.'
        }
    }
}
