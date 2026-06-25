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
        stage('下载代码') {
            steps {
                sh 'echo "下载代码"'
            }
        }

        stage('准备软件包') {
            steps {
                sh 'ls -l /container/data'
            }
        }
        
        stage('构建软件包') {
            steps {
                sh 'echo "构建软件包"'
                sh 'sleep 20'
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