package restaurant.sample

class SSHUtil {
    def copyFiles() {
        copyArtifacts fingerprintArtifacts: true, projectName: 'rs-security-build/master', selector: lastSuccessful()
    }
}
