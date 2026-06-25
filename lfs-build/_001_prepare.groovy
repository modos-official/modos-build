
def call() {
    prepareBuildEnvironment()
}


def prepareLfsDir() {
    
    env.LFS = '/mnt/lfs'
    
    sh 'echo "准备构建环境"'

    sh '''

        mkdir -pv $LFS/sources

        cp -r /container/data/* $LFS/sources/

        ls -l $LFS/sources
    '''
}


def prepareBuildEnvironment() {

    env.LFS = '/mnt/lfs'
    
    sh 'echo "准备构建环境"'

    sh '''

        mkdir -pv $LFS/sources

        cp -r /container/data/* $LFS/sources/

        ls -l $LFS/sources
    '''
}

// 4.2 创建lfs系统工作目录,以root用户身份运行
def prepareLfsTargetDir() {

    sh '''
        mkdir -pv $LFS/{etc,var} $LFS/{bin,lib,sbin}
        
        for i in bin lib sbin; do
            ln -sv usr/$i $LFS/$i
        done

        case $(uname -m) in
            x86_64)
                mkdir -pv $LFS/lib64 ;;
        esac

        mkdir -pv $LFS/tools
    '''
}


return this
