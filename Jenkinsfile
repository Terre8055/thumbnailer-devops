pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'openjdk-8'
    }

    options {
        timestamps()
        timeout(time: 15, unit: 'MINUTES') 
        gitLabConnection('gitlab')
    }

    triggers {
        gitlab(triggerOnPush: true, triggerOnMergeRequest: true, branchFilterType: 'All')    
    }



    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Maven Deploy(build -> test -> package -> publish)') {
            steps {
                script {
                    withMaven(mavenSettingsConfig: 'artifactory-settings') { 
                        sh 'mvn clean install -U'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deploy successful'
            // Add post-build actions as needed
        }
        failure {
            echo 'Build and deploy failed'
            // Add failure handling or notifications
        }
    }
}

