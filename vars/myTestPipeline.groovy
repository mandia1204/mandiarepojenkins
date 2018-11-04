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
                        sh "~/restaurant/deploy/./build-image.sh -t ${imageTag} -a ${appName}"
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
        post {
            always {
                archiveArtifacts 'dist/**/*'
            }
        }
    }
}