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
        stage('Pull latest image') {
            steps {
                bat "docker pull serge11elzar/maven_docker"
            }
        }
        stage('Start Grid') {
            steps {
                bat "docker-compose up -d --scale chrome=2 --scale firefox=2 hub chrome firefox"
            }
        }
        stage('Run Test') {
            steps {
                bat "docker-compose up first-suite-chrome second-suite-firefox"
            }
        }
// if run without Dockerfile and docker image
        //         stage('Allure report') {
        //             steps {
        //                 script {
        //                     allure([
        //                             includeProperties: false,
        //                             jdk: '',
        //                             properties: [],
        //                             reportBuildPolicy: 'ALWAYS',
        //                             results: [[path: 'target/allure-results']]
        //                     ])
        //                 }
        //             }
        //         }
    }
    post{
		always{
			archiveArtifacts artifacts: 'target/**'
			bat "docker-compose stop"
			bat "docker-compose rm --force"
		}
	}
}