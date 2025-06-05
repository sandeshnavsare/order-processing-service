@Library('my-jenkins-shared-library@main') _

pipeline {
  agent { label 'vinod' }

  environment {
    VERSION = generateVersion()
  }

  parameters {
    choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging', 'production'], description: 'Select deployment environment')
  }

  

  stages {
    stage('Checkout') {
     steps {
       checkout([$class: 'GitSCM', branches: [[name: '*/main']]])
       }
     }

    stage('Build') {
      steps {
        buildApplication([
          appName     : 'order-service',
          buildTool   : 'maven',
          dockerImage : 'maven:3.9.3-eclipse-temurin-17',
          skipTests   : true
        ])
      }
    }

    stage('Test') {
      steps {
        runTests([
          testType    : 'unit',
          buildTool   : 'maven',
          testReports : false
          
        ])
      }
    }

    stage('Deploy') {
      
      steps {
        deployToEnvironment([
          appName    : 'order-service',
          version    : "${VERSION}",
          environment: "${params.DEPLOY_ENV}"
          // namespace, replicas, etc. will be auto-loaded from Environment.groovy
        ])
      }
    }
  }

  post {
    always {
      
      archiveArtifacts artifacts: 'target/*.jar', onlyIfSuccessful: true
      
         
      }
    failure {
    sendNotification("❌ Build FAILED for *${params.appName}*. Check Jenkins logs.")
  }
  }
}
