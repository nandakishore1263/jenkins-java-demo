pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    environment {
        APP_NAME    = 'jenkins-java-demo'
        BUILD_DIR   = 'target'
        VERSION     = '1.0-SNAPSHOT'
    }

    triggers {
        pollSCM('H/5 * * * *')   // check GitHub every 5 mins for new commits
    }

    stages {

        stage('Checkout') {
            steps {
                echo "========== CHECKOUT =========="
                echo "Build #${BUILD_NUMBER} started"
                echo "Job: ${JOB_NAME}"
                echo "Workspace: ${WORKSPACE}"
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                echo "========== COMPILE =========="
                echo "Compiling ${APP_NAME}..."
                sh 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                echo "========== TEST =========="
                sh 'mvn test'
            }
            post {
                always {
                    // publish test results visually in Jenkins
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Quality') {
            steps {
                echo "========== CODE QUALITY =========="
                // checkstyle checks if code follows standards
                sh 'mvn checkstyle:check'
            }
        }

        stage('Package') {
            steps {
                echo "========== PACKAGE =========="
                echo "Creating JAR for ${APP_NAME} v${VERSION}..."
                sh 'mvn package -DskipTests'
            }
        }

        stage('Archive Artifact') {
            steps {
                echo "========== ARCHIVE =========="
                archiveArtifacts artifacts: "${BUILD_DIR}/*.jar",
                                 fingerprint: true
            }
        }

        stage('Build Summary') {
            steps {
                echo "========== SUMMARY =========="
                echo "App     : ${APP_NAME}"
                echo "Version : ${VERSION}"
                echo "Build # : ${BUILD_NUMBER}"
                echo "Branch  : ${GIT_BRANCH}"
                echo "Commit  : ${GIT_COMMIT}"
                echo "JAR     : ${BUILD_DIR}/${APP_NAME}-${VERSION}.jar"
            }
        }
    }

    post {
        success {
            echo "✅ ${APP_NAME} v${VERSION} build #${BUILD_NUMBER} PASSED!"
        }
        failure {
            echo "❌ ${APP_NAME} v${VERSION} build #${BUILD_NUMBER} FAILED!"
        }
        unstable {
            echo "⚠️ ${APP_NAME} build #${BUILD_NUMBER} is UNSTABLE!"
        }
        always {
            echo "Total stages: Checkout → Compile → Test → Quality → Package → Archive"
        }
    }
}
