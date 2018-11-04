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
                        def status = sh(returnStatus: true, script: "~/restaurant/deploy/./test.sh")
                        println status
                    }
                }
            }
        }
    }
}