pipeline {

    agent { 
		label 'java'
	}
	
    triggers { 
     pollSCM 'H/5 * * * *' 
     cron '0 0 * * *'

    } 

	options {
		disableConcurrentBuilds()
		buildDiscarder(logRotator(numToKeepStr: '10'))
	}
	
	parameters {
        booleanParam(name: "RELEASE",
                description: "Build a release from current commit.",
                defaultValue: false)
    }
	
    stages {
        stage('Scm'){
            steps {
            
				checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '[maven-release-plugin].*', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: 'fea614ea-f5df-4456-9d5b-5733bbff5c40', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: 'http://sources.etnic.be/svn/javaee7-samples/trunk']], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
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
        
     
        stage('Analysis'){
        	parallel {
			stage('Sonar'){
					steps {
						withMaven(maven:'maven-3.6',jdk:'jdk-8',publisherStrategy: 'EXPLICIT' ){
							sh "mvn sonar:sonar"
						}
					}
				}
				stage('Site'){
					steps {
						withMaven(maven:'maven-3.6',jdk:'jdk-8',publisherStrategy: 'EXPLICIT' ){
							sh "mvn -DskipTests site:site site:deploy"
							publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, includes: '**/*', keepAll: false, reportDir: 'target/site', reportFiles: 'index.html', reportName: 'Maven site', reportTitles: ''])
						}
					}
				}
			}
		}
		
		stage('Browser test') {
        	parallel {
				stage('Ie'){
            		steps {
            	       echo 'Ie'
            		}
            	}
            	stage('Firefox'){
            		steps {
		                echo 'Firefox'
		            }

            	}
            	stage('Chrome'){
            		steps {
		                echo 'Chrome'
		            }

            	}
            }
        }
		
		stage("Release") {
            when {
                expression { params.RELEASE }
            }
            steps {
				withMaven(maven:'maven-3.6',jdk:'jdk-8',publisherStrategy: 'EXPLICIT' ){
                    sh "mvn -B release:prepare release:perform"
				}
            }
        }
		
		stage('Int'){
			steps{
	//			hygieiaDeployPublishStep applicationName: 'portail', artifactDirectory: 'portail-web/target', artifactGroup: 'be.etnic.portail', artifactName: '*.war', artifactVersion: '', buildStatus: 'InProgress', environmentName: 'dev'
	//			hygieiaDeployPublishStep applicationName: 'portail', artifactDirectory: 'portail-api/target', artifactGroup: 'be.etnic.portail', artifactName: '*.war', artifactVersion: '', buildStatus: 'InProgress', environmentName: 'dev'

   				echo 'Deploy In Int'
		    //      withMaven(maven:'maven-3.6',jdk:'jdk-8' ){
			//	sh "mvn -DskipTests wildfly:deploy"
			//	}
	//			hygieiaDeployPublishStep applicationName: 'portail', artifactDirectory: 'portail-web/target', artifactGroup: 'be.etnic.portail', artifactName: '*.war', artifactVersion: '', buildStatus: 'Success', environmentName: 'dev'
	//			hygieiaDeployPublishStep applicationName: 'portail', artifactDirectory: 'portail-api/target', artifactGroup: 'be.etnic.portail', artifactName: '*.war', artifactVersion: '', buildStatus: 'Success', environmentName: 'dev'
		    }
        }
        
  //      stage('Simul'){
//			steps{
//				hygieiaDeployPublishStep applicationName: 'portail', artifactDirectory: 'portail-web/target', artifactGroup: 'be.etnic.portail', artifactName: '*.war', artifactVersion: '', buildStatus: 'InProgress', environmentName: 'simul'
//				hygieiaDeployPublishStep applicationName: 'portail', artifactDirectory: 'portail-api/target', artifactGroup: 'be.etnic.portail', artifactName: '*.war', artifactVersion: '', buildStatus: 'InProgress', environmentName: 'simul'

//				hygieiaDeployPublishStep applicationName: 'portail', artifactDirectory: 'portail-web/target', artifactGroup: 'be.etnic.portail', artifactName: '*.war', artifactVersion: '', buildStatus: 'Success', environmentName: 'simul'
//				hygieiaDeployPublishStep applicationName: 'portail', artifactDirectory: 'portail-api/target', artifactGroup: 'be.etnic.portail', artifactName: '*.war', artifactVersion: '', buildStatus: 'Success', environmentName: 'simul'
//		    }
 //       }
    }

	post {
		changed{
			emailext subject: '$DEFAULT_SUBJECT',
                        body: '$DEFAULT_CONTENT',
                        recipientProviders: [developers(), requestor(), brokenTestsSuspects(), brokenBuildSuspects()]
		}
	
      }
}