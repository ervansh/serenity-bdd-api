pipeline {
    agent any
    stages {
		stage('Initialize'){
			steps{
				echo "Running build #${env.BUILD_ID} on Jenkins ${env.JENKINS_URL}"
			}
		}
		stage('Checkout'){
			steps{
					echo 'Checkout git. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
				    git branch: 'master', url: 'https://github.com/ervansh/serenity-bdd-api.git'
				    echo 'Checkout stage completed. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
			}
		}
        stage('Build') {
            steps {
                 echo "building >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				 bat label: '', script: 'mvn package'
    			 echo "build stage completed. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
            }
        }
        stage('Test') {
            steps {
                 echo "Testing >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				 bat label: '', script: 'mvn verify'
    			 echo "Testing stage completed. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
            }
        }
        stage('Reporting'){
			steps{
				echo 'Publishing report....>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
				publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, includes: '**/*.html, **/*.css', keepAll: false, reportDir: 'target/site/serenity/', reportFiles: 'index.html', reportName: 'Serenitybdd HTML Report', reportTitles: '', useWrapperFileDirectly: true])
							}
		}
    }
   
}