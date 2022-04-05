// Jenkinsfile version to be used on Windows or MAC as a slave
pipeline {
    // master executor should be set to 0 in Jenkins
//agent any
    agent {
        node("DOCKER1")
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

//         stage('Start Grid') {
//              steps {
//                  bat "docker-compose up -d hub chrome firefox"
//              }
//         }
        stage('Run Test') {
             steps {
                 bat "docker-compose up --scale chrome=2 --scale firefox=2 first-suite-chrome second-suite-firefox"
             }
        }
    }
    post{
        always{
        	archiveArtifacts artifacts: 'target/**'

        	publishHTML (target: [
        	    allowMissing: true,
        	    alwaysLinkToLastBuild: true,
        	    keepAll: true,
        	    reportDir: 'target/report/',
        	    reportFiles: 'index.html',
        	    reportName: "Test Report"
        	])

//         		bat "docker-compose down"

        		bat "docker-compose stop"
        		bat "docker-compose rm --force"
        }
    }
}