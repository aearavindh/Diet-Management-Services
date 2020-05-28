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
				sh 'docker build -t aearavindh/DietServices:latest'
				sh 'docker push aearavindh/DietServices:latest'
              }
            }
        }
		stage('Deployment') {
            steps {
			  withCredentials([string(credentialsId: 'DOCKER_PASSWORD', variable: 'docker_password'),string(credentialsId: 'DEPLOYMENT_PASSWORD', variable: 'deployment_password'), string(credentialsId: 'DEPLOYMENT_HOST', variable: 'deployment_host')]){
                sh 'sshpass -p ${deployment_password} ssh -v -o StrictHostKeyChecking=no ${deployment_host} \"docker login -u aearavindh -p ${docker_password};docker pull aearavindh/DietServices:latest;docker container stop DietServices;docker container rm DietServices;docker container run -d -p 8080:8080 --name bmi aearavindh/DietServices:latest\"'
              }
			}
        }
    }
   

}