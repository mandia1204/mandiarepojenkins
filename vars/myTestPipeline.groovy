import restaurant.sample.*

def call(Map params) {
    pipeline {
        agent { label 'slave01' }
        tools {nodejs "node"}
        stages {
            stage('deploy') {
                steps {
                    copyFiles()
                    sh "pwd"
                    sh "ls -l"
                }
            }
            stage('Backup existing dist and copy new from ws') {
                steps {
                    script {
                        def fileName = "${(new Date()).format('MMddyyyyHHmmss')}.tar.gz"
                        sh "tar -czvf ${fileName} ~/restaurant/${params.appName}/dist"
                        sh "mv ${fileName} ~/restaurant/${params.appName}/backup"
                        sh "rm -rf ~/restaurant/${params.appName}/dist"
                        sh "mv ${env.WORKSPACE}/dist ~/restaurant/${params.appName}"
                    }
                }
            }
        }
    }
}