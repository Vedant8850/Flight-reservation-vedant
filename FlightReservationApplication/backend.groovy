pipeline{
    agent any 
    stages{
        stage('Pull'){
            steps{
               git branch: 'main', url: 'https://github.com/Vedant8850/Flight-reservation-vedant.git'
            }
        }

	stage('Build'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    mvn clean package
                '''
            }
        }

	  stage('QA-Test'){
            steps{
                withSonarQubeEnv(installationName: 'sonar', credentialsId: 'sonar-token') {
                    sh '''
                        cd FlightReservationApplication
                        mvn sonar:sonar  -Dsonar.projectKey=flight-reservation
                    '''
                }       
            }
        }

	stage('Docker-build'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    docker build -t vedant8850/flight-reservation-demo:latest .
                    docker push vedant8850/flight-reservation-demo:latest
                    docker rmi vedant8850/flight-reservation-demo:latest
                '''
            }
        }

	     stage('Deploy'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    kubectl apply -f k8s/
                '''
            }
        }
    }
}
	




