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
				sh 'docker build -t aearavindh/diet-services:latest .'
				sh 'docker push aearavindh/diet-services:latest'
              }
            }
        }
		stage('Deployment') {
            steps {
			  withCredentials([string(credentialsId: 'DOCKER_PASSWORD', variable: 'docker_password'),string(credentialsId: 'DEPLOYMENT_PASSWORD', variable: 'deployment_password'), string(credentialsId: 'DEPLOYMENT_HOST', variable: 'deployment_host')]){
                sh 'sshpass -p ${deployment_password} ssh -v -o StrictHostKeyChecking=no ${deployment_host} \"sudo docker login -u aearavindh -p ${docker_password};sudo docker pull aearavindh/diet-services:latest; sudo docker container stop diet-services;sudo docker container rm diet-services;sudo docker container run -d -p 8000:8000 --name diet-services aearavindh/diet-services:latest\"'
              }
			}
        }
    }
   

}