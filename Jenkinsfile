@Library('my-jenkins-shared-library') _

pipeline {
  agent any

  environment {
    VERSION = generateVersion()
  }

  parameters {
    choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging', 'production'], description: 'Select deployment environment')
  }

  options {
    timestamps()
    ansiColor('xterm')
  }

  stages {

    stage('Build') {
      steps {
        buildApplication([
          appName     : 'order-service',
          buildTool   : 'maven',
          dockerImage : 'maven:3.9.3-eclipse-temurin-17',
          skipTests   : false
        ])
      }
    }

    stage('Test') {
      steps {
        runTests([
          testType    : 'unit',
          buildTool   : 'maven',
          testReports : true
        ])
      }
    }

    stage('Deploy') {
      when {
        expression { isMainBranch() }
      }
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
      sendNotification("📦 Build for *order-service* completed. Version: ${VERSION}")
    }
    failure {
      sendNotification("❌ Build FAILED for *order-service*. Check Jenkins logs.")
    }
  }
}
