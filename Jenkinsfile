pipeline {
    agent any
    tools {
        maven "Maven"   
    }   
    stages {
        stage('Compile-Build-Test ') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
   

}