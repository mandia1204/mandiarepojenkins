import restaurant.sample.Person

def call(Map pipelineParams) {
    pipeline {
        agent any
        tools {nodejs "node"}
        stages {
            stage('Install dependencies') {
                steps {
                    hello('marvin2')
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
                    copyFiles()
                    publishFiles()
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