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
			    sh 'sudo docker service restart'
                sh 'sudo docker login -u aearavindh -p ${docker_password}'
				sh 'sudo docker build -t aearavindh/diet-services:${BUILD_NUMBER} .'
				sh 'sudo docker push aearavindh/diet-services:${BUILD_NUMBER}'
              }
            }
        }
		stage('Deployment') {
            steps {
			  withCredentials([string(credentialsId: 'DOCKER_PASSWORD', variable: 'docker_password'),string(credentialsId: 'DEPLOYMENT_PASSWORD', variable: 'deployment_password'), string(credentialsId: 'DEPLOYMENT_HOST', variable: 'deployment_host'), string(credentialsId: 'MAIL_PASSWORD', variable: 'mail_password')]){
                sh 'sshpass -p ${deployment_password} ssh -v -o StrictHostKeyChecking=no ${deployment_host} \"sudo docker container rm -f diet-services;sudo docker container run -e MAIL_PASSWORD=${mail_password} -d -p 8000:8000 --name diet-services aearavindh/diet-services:${BUILD_NUMBER}\"'
              }
			}
        }
    }
   

}