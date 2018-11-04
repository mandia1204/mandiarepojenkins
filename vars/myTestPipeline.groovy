import restaurant.sample.*

def call(Map params) {
    pipeline {
        agent { label 'slave01' }
        tools {nodejs "node"}
        stages {
            stage('Install dependencies') {
                steps {
                    script {
                        sshUtil = new restaurant.sample.SSHUtil()
                    }
                    hello('marvin2')
                    echo "my build num: ${env.BUILD_NUMBER}"
                }
            }
            stage('Build') {
                steps {
                    echo "Building... ${params.myMessage}"
                    script {
                        def p = new Person()
                        def r = p.hello()
                        println(r)
                    }
                }
            }
            stage('deploy') {
                steps {
                    copyFiles()
                    script {
                        imageTag = '123121212_abcd'
                        appName= 'security'
                        sh "~/restaurant/deploy/./test.sh -t ${imageTag} -a ${appName}"
                    }
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
            stage('Test') {
                when { expression { return params.runTest } }
                steps {
                    echo 'Testing...'
                }
            }
        }
    }
}