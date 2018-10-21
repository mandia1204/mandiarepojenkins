def call() {
    step([  $class: 'CopyArtifact',
            fingerprintArtifacts: true,
            projectName: 'rs-security-build/master',
            selector: [$class: 'LastCompletedBuildSelector']
    ])
}
