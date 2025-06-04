@Library('my-jenkins-shared-library') _

pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                buildApplication([
                    imageName: 'order-service',
                    tag: 'v1.0'
                ])
            }
        }
        stage('Test') {
            steps {
                runTests([
                    imageName: 'order-service'
                ])
            }
        }
        stage('Deploy') {
            steps {
                deployToEnvironment([
                    imageName: 'order-service',
                    tag: 'v1.0',
                    environment: 'dev'
                ])
            }
        }
    }
}
