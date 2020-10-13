def COLOR_MAP = ['SUCCESS': 'good', 'FAILURE': 'danger', 'UNSTABLE': 'danger', 'ABORTED': 'danger']
pipeline {
    environment {
        BUILD_USER = ''
    }
    agent any

    parameters {
        string(defaultValue: "10.1.1.6", description: 'Controller (UDF URL / IP Address)', name: 'controllerUrl')
        string(defaultValue: "admin@test.com", description: 'Controller Username', name: 'username')
        password(defaultValue: "", description: 'Controller Password', name: 'password')
    }

    tools {
        nodejs "node"
    }

    stages {
        stage('Create Artifacts within Controller') {
            steps {
                sh '''
                set +x
                ./Scripts/CreateArtifact.sh $controllerUrl $username $password
                set -x
                '''
            }
        }
    }
}
