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
                script {
                    newVersion = readMavenPom file: 'pom.xml'
                    printf('Test Version: %s', newVersion)
                }
            }
        }
        // Build
        stage('Test') {
            steps {
                // bat 'mvn verify'
                sh 'mvn verify -Ddependency-check.skip=true'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        // cucumber
        stage('cucumber') {
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
    //end
    }
}
