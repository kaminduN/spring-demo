pipeline {
    agent { label 'java' }

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
                sh 'mvn spring-boot:build-image'
            }
        }
        stage('Push image') {
            steps {
                echo 'Testing..'
                sh 'docker images'
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
                echo 'Deploying to preprod'
                sh 'kubectl get pods'
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
                sh 'kubectl get pods'
            }
        }
    }
}
