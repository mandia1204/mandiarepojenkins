import restaurant.sample.Person
import restaurant.sample.SSHUtil

def call(Map pipelineParams) {
    pipeline {
        agent any
        tools {nodejs "node"}
        stages {
            stage('Install dependencies') {
                steps {
                    echo 'Installing...'
                    hello('marvin2')
                    copyArtifacts fingerprintArtifacts: true, projectName: 'rs-security-build/master', selector: lastSuccessful()
                }
            }
            stage('Build') {
                steps {
                    echo "Building... ${pipelineParams.myMessage}"
                    script {
                        def p = new Person()
                        def r = p.hello()
                        println(r)
                    }
                }
            }
            stage('deploy') {
                steps {
                    script {
                        def util = new SSHUtil()
                        util.copyFiles()
                    }
                }
            }
            stage('Test') {
                when { expression { return pipelineParams.runTest } }
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