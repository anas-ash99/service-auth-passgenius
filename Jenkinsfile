import org.jenkinsci.plugins.pipeline.modeldefinition.Utils
@Library('jenkins-scripts-passgenius') _

properties([
    parameters([
            booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Should build docker image and deploy it?')
    ])
])

def IMAGE_TAG_NAME = generateTagName()
def IMAGE_TAG = 'aashraf756/service-auth-passgenius'
def DEPLOYMENT_FILE_PATH = "overlays\\dev\\auth"
def shouldDeploy =  "${params.DEPLOY}".toBoolean()

pipeline {
    agent any
    stages {

        stage('Build And Test') {
            steps {
                echo "Building the app ..."
                configFileProvider([configFile(fileId: 'df11f9a7-ff71-4d6b-80d7-f390bc7e79d6', variable: 'MAVEN_SETTINGS')]) {
                    bat 'mvnw -s %MAVEN_SETTINGS% clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    if (env.BRANCH_NAME == 'main' || shouldDeploy) {
                        echo "Building the image..."
                        bat "docker build -t ${IMAGE_TAG}:${IMAGE_TAG_NAME} ."
                    }else {
                        Utils.markStageSkippedForConditional( STAGE_NAME )
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    if (env.BRANCH_NAME == 'main' || shouldDeploy) {
                        try {
                            docker.withRegistry('', 'aba091eb-3857-489f-8115-2993e248f42c') { // login into Docker Hub
                                echo 'Pushing docker image...'
                                bat  "docker push ${IMAGE_TAG}:${IMAGE_TAG_NAME}"
                            }
                        }finally {
                            echo 'Removing docker image...'
                            bat "docker rmi ${IMAGE_TAG}:${IMAGE_TAG_NAME}"
                        }

                    }else {
                        Utils.markStageSkippedForConditional( STAGE_NAME )
                    }
                }
            }
        }

        stage('Update Kubernetes Manifest') {
            steps {
                echo 'Updating manifest ...'
                script {
                    if (env.BRANCH_NAME == 'main' || shouldDeploy) {
                        withCredentials([usernamePassword(credentialsId: 'Github-token', usernameVariable: 'GIT_CREDENTIALS_USR', passwordVariable: 'GIT_CREDENTIALS_PSW')]) {
                            // Example usage:
                           updateManifest(
                                   DEPLOYMENT_FILE_PATH,
                                   IMAGE_TAG,
                                   IMAGE_TAG_NAME,
                                   GIT_CREDENTIALS_USR,
                                   GIT_CREDENTIALS_PSW
                           )
                        }
                    }else {
                        Utils.markStageSkippedForConditional( STAGE_NAME )
                    }

                }
            }
        }
    }
}
