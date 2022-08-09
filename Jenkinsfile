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
                sh 'mvn clean spring-boot:build-image'
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
                script {
                    if(! env.app_dev_namespace){
                           echo "[DEBUG] ${env.app_dev_namespace}"
                           env.app_dev_namespace = "dev"
                    }
                }
                echo 'Deploying'
                sh "kubectl get pods -n ${env.app_dev_namespace}"            }
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
                ok "Yes"
                id "simple-input"
            }
            steps {

                script {
                    if(! env.app_dev_namespace){
                           echo "[DEBUG] ${env.app_staging_namespace}"
                           env.app_dev_namespace = "staging"
                    }
                }
                echo 'Deploying'
                sh "kubectl get pods -n ${env.app_dev_namespace}"
            }
        }
    }
}
