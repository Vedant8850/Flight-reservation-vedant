pipeline{
    agent any 
    stages{
        stage('Pull'){
            steps{
               git branch: 'main', url: 'https://github.com/Vedant8850/Flight-reservation-vedant.git'
            }
        }
   }
}

