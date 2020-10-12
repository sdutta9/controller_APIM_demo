def COLOR_MAP = ['SUCCESS': 'good', 'FAILURE': 'danger', 'UNSTABLE': 'danger', 'ABORTED': 'danger']
pipeline {
    agent any

    parameters {
        string(defaultValue: "10.1.1.6", description: 'Controller (UDF URL / IP Address)', name: 'controllerUrl')
        string(defaultValue: "admin@test.com", description: 'Controller Username', name: 'username')
        password(description: 'Controller Password', name: 'password')
    }

    tools {
        nodejs "node"
    }

    stages {
        stage('Clean up Controller') {
            steps {
                sh '''
                set +x
                ./Scripts/CleanupArtifact.sh $controllerUrl $username $password
                set -x
                '''
            }
        }
    }
    post {
        always{
            slackSend channel: '#apim-garage-day',
                color: COLOR_MAP[currentBuild.currentResult],
                message: "*${currentBuild.currentResult}:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}"
        }
    }
}
