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
                    newVersion = readMavenPom file: 'pom.xml'
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
        stage('Unit Test') {
            steps {
                sh 'mvn test -Ddependency-check.skip=true'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        stage('Integration Test') {
            steps {
                sh 'mvn verify -Dskip.unit.tests=true -Ddependency-check.skip=true'
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
    //end
    }
}
