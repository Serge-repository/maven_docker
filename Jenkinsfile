// Jenkinsfile version to be used on Windows or MAC as a slave
pipeline {
    // master executor should be set to 0
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
                bat "docker build -t='serge11elzar/maven_docker' ."
            }
        }
        stage('Push Image') {
            steps {
            //pass your dockerhub username and password
			    withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'pass', usernameVariable: 'user')]) {
                    //sh instead bat for MAC
			        bat "docker login --username=${user} --password=${pass}"
			        bat "docker push vinsdocker/selenium-docker:latest"
			    }                           
            }
        }
    }
}