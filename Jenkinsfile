pipeline {

    agent any
	
    triggers { 
     pollSCM 'H/5 * * * *' 
     cron '0 0 * * *'
    } 

	parameters {
        booleanParam(name: "RELEASE",
                description: "Build a release from current commit.",
                defaultValue: false)
    }
	
    stages {
        stage('Scm'){
            steps {            
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/15avr74/test-toolbox.git']]])
            }
        }
        
        stage('Build') {
            steps {
            	
            	//Use withMaven for maven settings file 
                withMaven(maven:'maven-3.6',jdk:'jdk-8',publisherStrategy: 'EXPLICIT' ){
					sh "mvn -DskipTests clean deploy"
                }
            }
        }
        
        stage('Test') {
        	parallel {
				stage('Junit'){
            		steps {
            	       withMaven(maven:'maven-3.6',jdk:'jdk-8' ){
                		    sh "mvn test"
                		}
            		}
            	}
            	stage('Dbunit'){
            		steps {
		                echo 'Dummy'
		            }

            	}
            }
       }
        
     	
    }
}
