pipeline {
    agent any
    
    triggers {
        pollSCM('* * * * *')  // Poll every minute
    }
    
    environment {
        TOMCAT_URL = 'http://localhost:8085'
        TOMCAT_USERNAME = 'admin'
        TOMCAT_PASSWORD = 'root'
        GITHUB_REPO = 'Pradeep-majji/jenkins-ci'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', 
                    branches: [[name: '*/main']],  
                    userRemoteConfigs: [[url: "https://github.com/${GITHUB_REPO}.git"]]
                ])
            }
        }
        
        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }
        
        stage('Deploy to Tomcat') {
            steps {
                script {
                    def warFile = "target/employee-management-0.0.1-SNAPSHOT.war"
                    def deployUrl = "http://%TOMCAT_USERNAME%:%TOMCAT_PASSWORD%@localhost:8085/manager/text/deploy?path=/employee-management&update=true"
                    
                    // First, try to undeploy if exists
                    bat "curl -v -u %TOMCAT_USERNAME%:%TOMCAT_PASSWORD% \"http://localhost:8085/manager/text/undeploy?path=/employee-management\""
                    
                    // Then deploy
                    bat "curl -v -T \"${warFile}\" \"${deployUrl}\""
                    
                    // Verify deployment
                    bat "curl -v -u %TOMCAT_USERNAME%:%TOMCAT_PASSWORD% \"http://localhost:8085/manager/text/list\""
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Deployment completed successfully!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
} 