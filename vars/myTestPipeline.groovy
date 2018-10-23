import restaurant.sample.*

def call(Map params) {
    pipeline {
        agent any
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
                        def imageTag = TagGenerator.generateImageTag("${env.BUILD_NUMBER}")
                        def command = "/restaurant/deploy/./build-image.sh -i ${params.repoName}:${imageTag} -a ${params.appName}"
                        sshUtil.publish configName: 'kube-server', command: command
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