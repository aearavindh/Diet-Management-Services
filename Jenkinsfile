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
                sh 'docker login -u aearavindh -p ${DOCKER_PASSWORD}'
				sh 'docker build -t aearavindh/DietServices:latest'
				sh 'docker push aearavindh/DietServices:latest'
            }
        }
		stage('Deployment') {
            steps {
                sh 'sshpass -p ${DEPLOYMENT_PASSWORD} ssh -v -o StrictHostKeyChecking=no ${DEPLOYMENT_HOST} \"docker login -u aearavindh -p ${DOCKER_PASSWORD};docker pull aearavindh/DietServices:latest;docker container stop DietServices;docker container rm DietServices;docker container run -d -p 8080:8080 --name bmi aearavindh/DietServices:latest\"'
            }
        }
    }
   

}