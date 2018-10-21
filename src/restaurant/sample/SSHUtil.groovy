package restaurant.sample

def copyFiles() {
    step([  $class: 'CopyArtifact',
            fingerprintArtifacts: true,
            projectName: 'rs-security-build/master',
            selector: [$class: 'LastCompletedBuildSelector']
    ])
}

return this
