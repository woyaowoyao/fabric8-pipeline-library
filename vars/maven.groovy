#!/usr/bin/groovy

def call(parameters, body) {

    def mavenImage = parameters.get('mavenImage', 'fabric8/maven-builder:2.2.297')
    def profiles = parameters.get('profiles', 'base')

    def label = "buildpod.${env.JOB_NAME}.${env.BUILD_NUMBER}".replace('-', '_').replace('/', '_')
    podTemplate(label: label, inhertiFrom: "${profiles}", containers: [
            [name: 'maven', image: "${mavenImage}", command: 'cat', ttyEnabled: true, envVars: [
                    [key: 'MAVEN_OPTS', value: '-Duser.home=/root/']]]
    ]) {
        node(label) {
            body()
        }
    }
}
