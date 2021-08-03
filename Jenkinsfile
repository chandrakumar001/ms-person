def newVersion
pipeline {
  agent any
     // auto triggers
    //${newPomVersion} ::: linux Placeholder
    // %newPomVersion% :::windows Placeholder
//     tools {
//      // jdk 'Java-11'
//       maven 'maven-3.8.1'
//     }  
    triggers {
        pollSCM('H/5 * * * *')
    }

    stages {
        // Build
        stage('Build') {
            steps {
                // Clean before build
                cleanWs()
                // We need to explicitly checkout from SCM here
                checkout scm
                sh 'mvn clean compile'
               // bat 'mvn clean compile'
            }
            post {
                // Clean after build
                always {
                    cleanWs(cleanWhenNotBuilt: false,
                            deleteDirs: true,
                            disableDeferredWipeout: true,
                            notFailBuild: true,
                            patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                                       [pattern: '.propsfile', type: 'EXCLUDE']])
                }
            }
        }
        stage('Info') {
          steps {
            script{
                  def pom = readMavenPom file: 'pom.xml'
                  newVersion=pom.version
                  printf("Test Version: %s", pom.version)
            }
          }
        }
        // Build
        stage('Test') {
            steps {
               // bat 'mvn verify'
               sh 'mvn verify -Ddependency-check.skip=true'
            }
            post{
              always{
                junit "**/target/surefire-reports/TEST-*.xml"
              }
            }
        }
        // cucumber
       stage('cucumber') {
         steps {
                cucumber buildStatus: "UNSTABLE",
                fileIncludePattern: "**/feature.*.*.json",
                jsonReportDirectory: "target"
          }
       }
        // Package
        stage('Package') {
            steps {
               // bat 'mvn package -Dmaven.test.skip=true'
                sh 'mvn package -Dmaven.test.skip=true'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
      stage ('OWASP Dependency-Check Vulnerabilities') {
            steps {
              
              sh 'mvn dependency-check:check'             

              dependencyCheckPublisher pattern: 'target/dependency-check-report.xml'
            }
        }
//         stage('Build Docker Image') {

//             steps {
//                 sh 'docker build . -t localhost:50000/ms-project/ms-person:'+newVersion
//                 sh 'docker build . -t localhost:50000/ms-project/ms-person:'+newVersion
//                 sh 'echo the image to docker'
//                 sh 'docker push localhost:50000/ms-project/ms-person:'+newVersion

//                 sh 'echo the latest image to docker'
//                 sh 'docker tag localhost:50000/ms-project/ms-person:'+newVersion+' localhost:50000/ms-project/ms-person:latest'
//                 sh 'docker push localhost:50000/ms-project/ms-person:latest'

//                 sh 'echo Delete the image from jenkins'
//                 sh 'docker rmi -f localhost:50000/ms-project/ms-person:'+newVersion+' localhost:50000/ms-project/ms-person:latest'
//             }
//         }
        // Deploy
//         stage('Deploy') {
//             steps {
//                 sh 'kubectl set image deployment/ms-person ms-person=localhost:50000/ms-project/ms-person:'+newVersion
//             }
//         }
        //end
    }
  }
