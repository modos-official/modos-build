

pipeline {
    agent {
        docker {
            image 'registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:24.04'
            // 使用 args 添加挂载卷
            // 格式: -v <主机路径>:<容器内路径>
            args '-v /mnt/lfs/sources:/container/data'
        }
    }
    
    stages {
        stage('准备构建环境') {
            steps {
                script {
                    def prepareBuildEnvironment = load 'lfs-build/_001_prepare.groovy'
                    prepareBuildEnvironment.prepareBuildEnvironment()
                }
            }

        }
        
        stage('构建软件包') {
            steps {
                sh 'echo "构建软件包"'
                sh 'sleep 10'
            }
        }
        
        stage('构建镜像') {
            steps {
                sh '''
                    echo "构建结束"
                '''
            }
        }
    }
}