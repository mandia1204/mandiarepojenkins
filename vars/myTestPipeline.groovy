import restaurant.sample.*

def call(Map params) {
    pipeline {
        agent { label 'slave01' }
        tools {nodejs "node"}
        stages {
            stage('copy artifact to ws') {
                steps {
                    copyFiles()
                    sh "pwd"
                    sh "ls -l dist"
                }
            }
            stage('Backup existing dist and copy new from ws') {
                steps {
                    script {
                        appName= 'security'
                        sh "~/restaurant/deploy/./copy-artifact.sh -w ${env.WORKSPACE} -a ${appName}"
                    }
                    sh "~/restaurant/deploy/./test.sh"
                }
            }
        }
    }
}