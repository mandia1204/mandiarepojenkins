def call() {
    sshPublisher(publishers:
            [sshPublisherDesc(
                    configName: 'kube-server',
                    transfers: [
                            sshTransfer(
                                    cleanRemote: false,
                                    excludes: '',
                                    execCommand: './test.sh',
                                    execTimeout: 120000,
                                    flatten: false,
                                    makeEmptyDirs: false,
                                    noDefaultExcludes: false,
                                    patternSeparator: '[, ]+',
                                    remoteDirectory: 'copied',
                                    remoteDirectorySDF: false,
                                    removePrefix: 'dist',
                                    sourceFiles: 'dist/**')
                    ],
                    usePromotionTimestamp: false,
                    useWorkspaceInPromotion: false,
                    verbose: false)
            ]
    )
}
