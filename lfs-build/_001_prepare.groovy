
def prepareBuildEnvironment() {

    env.LFS = '/mnt/lfs'
    
    sh 'echo "准备构建环境"'

    sh '''

        mkdir -pv $LFS/sources

        cp -r /container/data/* $LFS/sources/

        ls -l $LFS/sources
    '''
}

return this
