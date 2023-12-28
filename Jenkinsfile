pipeline {
    agent any

    options {
        timestamps()
        timeout(time: 15, unit: 'MINUTES') 
        gitLabConnection('gitlab')
    }

    triggers {
        gitlab(triggerOnPush: true, triggerOnMergeRequest: true, branchFilterType: 'All')    
    }


    stages {
        stage('Build and Deploy') {
            steps {
                script {
                    checkout scm
                    sh 'mvn clean install'
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

