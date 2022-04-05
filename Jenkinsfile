// Jenkinsfile version to be used on Windows or MAC as a slave
pipeline {
    // master executor should be set to 0 in Jenkins
    agent any
    stages {
        stage('Build Jar') {
            steps {
                //sh instead bat for MAC
                bat "mvn clean package -DskipTests"
            }
        }
        stage('Build Image') {
            steps {
                //sh instead bat for MAC
                bat "docker build -t=serge11elzar/maven_docker ."
            }
        }
        stage('Push Image') {
            steps {
			    withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'pass', usernameVariable: 'user')]) {
                    //sh instead bat for MAC
			        bat "docker login --username=${user} --password=${pass}"
			        bat "docker push serge11elzar/maven_docker:latest"
			    }                           
            }
        }

        stage('Start Grid') {
             steps {
                 bat "docker-compose up -d hub chrome firefox"
             }
        }
        stage('Run Test') {
             steps {
                 bat "docker-compose up first-suite-chrome second-suite-firefox"
             }
        }
    }
    post{
        always{
        	archiveArtifacts artifacts: 'target/**'
        		bat "docker-compose down"
        }
    }
}