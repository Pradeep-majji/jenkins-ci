pipeline {
    agent any
    
    environment {
        TOMCAT_URL = 'http://localhost:8085'
        TOMCAT_USERNAME = 'admin'
        TOMCAT_PASSWORD = 'root'
        GITHUB_REPO = 'Pradeep-majji/jenkins-ci'
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: "https://github.com/${GITHUB_REPO}.git"
            }
        }
        
        stage('Build') {
            steps {
                withMaven(maven: 'Maven') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }
        
        stage('Deploy to Tomcat') {
            steps {
                script {
                    def warFile = "target/employee-management-0.0.1-SNAPSHOT.war"
                    
                    // Undeploy if exists
                    bat """
                        curl -v -u %TOMCAT_USERNAME%:%TOMCAT_PASSWORD% "http://localhost:8085/manager/text/undeploy?path=/employee-management"
                    """
                    
                    // Deploy
                    bat """
                        curl -v -T "%warFile%" "http://%TOMCAT_USERNAME%:%TOMCAT_PASSWORD%@localhost:8085/manager/text/deploy?path=/employee-management&update=true"
                    """
                    
                    // Verify deployment
                    bat """
                        curl -v -u %TOMCAT_USERNAME%:%TOMCAT_PASSWORD% "http://localhost:8085/manager/text/list"
                    """
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