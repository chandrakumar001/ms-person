def newVersion
pipeline {
    agent any
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
                script {
                  def pom = readMavenPom file: 'pom.xml'
                  newVersion=pom.version
                  printf('Test Version: %s', newVersion)
                }
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
        stage('Unit and Integration Test') {
            steps {
                sh 'mvn test -Ddependency-check.skip=true'
                sh 'mvn verify -Dskip.unit.tests=true -Ddependency-check.skip=true'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        stage('Cucumber Report') {
            steps {
                cucumber buildStatus: 'UNSTABLE',
                        fileIncludePattern: '**/feature.*.*.json',
                        jsonReportDirectory: 'target'
            }
        }
        stage('OWASP Dependency-Check Vulnerabilities') {
            steps {
                sh 'mvn dependency-check:check -Dformat=ALL'
                dependencyCheckPublisher pattern: 'target/dependency-check-report.xml'
            }
        }
        // stage('SonarQube analysis') {
        //     steps {
        //     //withSonarQubeEnv(credentialsId: 'test-sonarqube-secret-env', installationName: 'test-sonarqube-server-old') {
        //         withSonarQubeEnv('chandran-edu-sonarqube') {
        //             sh 'mvn sonar:sonar -Dsonar.dependencyCheck.jsonReportPath=target/dependency-check-report.json -Dsonar.dependencyCheck.xmlReportPath=target/dependency-check-report.xml -Dsonar.dependencyCheck.htmlReportPath=target/dependency-check-report.html'
        //         }
        //     }
        // }
        // Package
        stage('Package') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage('Build Docker Image') {

            steps {
                sh 'docker build . -t 139.162.195.118:50000/ms-person:'+newVersion
                sh 'echo the image to docker'
                sh 'docker push 139.162.195.118:50000/ms-project/ms-person:'+newVersion

                sh 'echo the latest image to docker'
                sh 'docker tag 139.162.195.118:50000/ms-person:'+newVersion+' 139.162.195.118:50000/ms-person:latest'
                sh 'docker push 139.162.195.118:50000/ms-person:latest'

                sh 'echo Delete the image from jenkins'
                sh 'docker rmi -f 139.162.195.118:50000/ms-person:'+newVersion+' 139.162.195.118:50000/ms-person:latest'
            }
        }
    //end
    }
}

//139.162.195.118:5000/v2/_catalog