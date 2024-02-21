pipeline {
    agent any

    environment {
        // 환경 변수 설정
        JAVA_HOME = "/usr/lib/jvm/java-1.8.0"
        JBOSS_HOME = "/path/to/jboss-eap-7"
    }

    stages {
        stage('Git Checkout') {
            steps {
                // 자격 증명을 사용하여 Git에서 소스 코드 체크아웃
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [
                        [
                            credentialsId: 'c7094128-0692-4e71-a439-a282c60e8cf2', // 자격 증명 ID 사용
                            url: 'http://127.0.0.1:8082/02z90976/spring-maven.git'
                        ]
                    ]
                ])
            }
        }

        stage('Nexacro Generate') {
            steps {
                // 커스텀 스크립트 실행
                script {
                    // 예를 들어, 환경 체크 스크립트
                    echo "Running pre-build script..."
                    // 예시 스크립트 실행: 환경 변수 확인
                    sh "printenv | grep JAVA_HOME"
                    // 또는 다른 커스텀 스크립트를 실행할 수 있음
                    // sh "./check_services.sh"
                }
            }
        }

        stage('Maven Build') {
            steps {
                // Maven을 사용하여 프로젝트 빌드
                sh 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                // JBoss EAP에 배포
                script {
                    def warFile = findFiles(glob: '**/*.war')[0].path
                    sh "cp ${warFile} ${JBOSS_HOME}/standalone/deployments/"
                }
            }
        }
    }

    post {
        // 빌드 후 작업
        success {
            echo 'Build and deployment successful.'
        }
        failure {
            echo 'Build or deployment failed.'
        }
    }
}