pipeline {
    agent any
    
    environment {
        TOMCAT_URL = 'http://localhost:8085'
        TOMCAT_USERNAME = 'tomcat'
        TOMCAT_PASSWORD = 'tomcat'
        GITHUB_REPO = 'your-github-username/employee-management'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        
        stage('Deploy to Tomcat') {
            steps {
                sh '''
                    curl -T target/employee-management-0.0.1-SNAPSHOT.war \
                    "http://${TOMCAT_USERNAME}:${TOMCAT_PASSWORD}@localhost:8085/manager/text/deploy?path=/employee-management&update=true"
                '''
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