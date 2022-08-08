pipeline {
    agent { label 'java && docker' }

    options {
        timeout(time: 1, unit: 'HOURS') 
    }
    stages {
        stage('Build') {
            steps {
                checkout scm

                script {
                    TAG_SELECTOR = readMavenPom().getVersion()
                }
                echo("TAG_SELECTOR=${TAG_SELECTOR}")

                echo 'Building..'
            }
        }
        stage('Push image') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy to pre prod') {
            agent { 
                label 'k8s'
            }
            when {
                not{
                    branch 'main'
                }
            }
            steps {
                echo 'Deploying'
            }
        }
        stage('Deploy to prod') {
            agent { 
                label 'k8s'
            }
            when {
                beforeInput true
                branch 'main'
            }
            input {
                message "Deploy to production?"
                id "simple-input"
            }
            steps {
                echo 'Deploying'
            }
        }
    }
}

def getGitBranchName() {
    return scm.branches[0].name
}