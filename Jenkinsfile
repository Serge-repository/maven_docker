// Jenkinsfile version to be used on Windows or MAC as a slave
pipeline {
    // master executor should be set to 0 in Jenkins
    agent {
        //add this tag for node in Jenkins
        node("test-executor")
    }
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
    }
}