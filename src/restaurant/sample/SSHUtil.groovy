package restaurant.sample

class SSHUtil {
    def copyFiles() {
        step([  $class: 'CopyArtifact',
                fingerprintArtifacts: true,
                projectName: 'rs-security-build/master',
                selector: [$class: 'LastCompletedBuildSelector']
        ])
    }
}
