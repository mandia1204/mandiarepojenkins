pipeline {
    agent any
    tools {nodejs "node"}
    stages {
        stage('SCM') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '**']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CleanBeforeCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '4f76a7e0-edb5-4eff-85ce-8a34892de927', url: 'https://github.com/mandia1204/mandiarepo']]])
            }
        }
        stage('Install dependencies') {
            steps {
				echo 'Installing...'
            }
        }
        stage('Build') {
            steps {
                echo 'Building project...'
            }
        }
        stage('Test') {
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