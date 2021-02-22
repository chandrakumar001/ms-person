pipeline {
  agent any
    // auto triggers
    triggers {
        cron('H/5 * * * *')
    }
    stages {
        // Build
        stage('Build') {
            steps {
                // sh mvn clean install
                bat 'mvn clean compile'
            }
        }
        // Build
        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post{
              always{
                junit "**/target/surefire-reports/TEST-*.xml"
              }
            }
        }
        // Build
        stage('Package') {
            steps {
                bat 'mvn package -Dmaven.test.skip=true'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        // Deploy
        stage('Deploy') {
            steps {
                echo 'Deploying'
            }
        }
        //end
    }
  }
