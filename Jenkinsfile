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
                    VER_TAG_SELECTOR = readMavenPom().getVersion()
                }
                echo("VER_TAG_SELECTOR=${VER_TAG_SELECTOR}")

                echo 'Building..'
                sh 'mvn clean spring-boot:build-image'
            }
        }
        stage('Push image') {
            steps {
                script {
                    DOCKER_REPO = "${env.app_docker_repo}"
                    ARTIFACT_ID = readMavenPom().getArtifactId()
                }

                echo 'Testing..'
                sh 'docker images'
                sh "docker tag ${ARTIFACT_ID}:${VER_TAG_SELECTOR} ${DOCKER_REPO}/${ARTIFACT_ID}:${VER_TAG_SELECTOR}"

                // sh "docker push ${DOCKER_REPO}/${ARTIFACT_ID}:${VER_TAG_SELECTOR}"
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
                sh "kubectl get pods -n ${env.app_dev_namespace}"            
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
                ok "Yes"
                id "simple-input"
            }
            steps {

                script {
                    if(! env.app_staging_namespace){
                           echo "[DEBUG] ${env.app_staging_namespace}"
                           env.app_dev_namespace = "staging"
                    }
                }
                echo 'Deploying'
                sh "kubectl get pods -n ${env.app_staging_namespace}"
            }
        }
    }
    post {
        always {
            echo 'One way or another, I have finished'
            // deleteDir() /* clean up our workspace */
        }
        success {
            echo 'I succeeded!'

            snsPublish(topicArn:"${env.build_notification_sns}", 
                       subject:"BUILD  ${env.BUILD_TAG} SUCCESS", 
                       message:"This is your success message. \n ${env.BUILD_URL}")
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
        changed {
            echo 'Things were different before...'
        }
    }
}
