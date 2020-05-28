pipeline {
    agent any
    tools {
        maven "Maven"   
    }   
    stages {
        stage('Compile-Build-Test') {
            steps {
                sh 'mvn clean package'
            }
        }
		stage('Docker build') {
            steps {
			  withCredentials([string(credentialsId: 'DOCKER_PASSWORD', variable: 'docker_password')]){
                sh 'docker login -u aearavindh -p ${docker_password}'
				sh 'docker build -t aearavindh/diet-services:latest'
				sh 'docker push aearavindh/diet-services:latest'
              }
            }
        }
		stage('Deployment') {
            steps {
			  withCredentials([string(credentialsId: 'DOCKER_PASSWORD', variable: 'docker_password'),string(credentialsId: 'DEPLOYMENT_PASSWORD', variable: 'deployment_password'), string(credentialsId: 'DEPLOYMENT_HOST', variable: 'deployment_host')]){
                sh 'sshpass -p ${deployment_password} ssh -v -o StrictHostKeyChecking=no ${deployment_host} \"docker login -u aearavindh -p ${docker_password};docker pull aearavindh/diet-services:latest;docker container stop diet-services;docker container rm diet-services;docker container run -d -p 8080:8080 --name diet-services aearavindh/diet-services:latest\"'
              }
			}
        }
    }
   

}