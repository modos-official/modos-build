

####  docker-compose安装jenkins


下载二进制: docker-compose

wget -c https://github.com/docker/compose/releases/download/v2.36.2/docker-compose-Linux-x86_64

mv docker-compose-Linux-x86_64  /usr/bin/docker-compose
chmod +x /usr/bin/docker-compose

docker-compose --version



当前jenkins最新lts版本为: 2.555.3 版本。
docker pull jenkins/jenkins:lts

docker images 
docker image inspect e82bbdcffb63  

可以看到镜像版本为: 2.555.3 版本。


docker tag e82bbdcffb63 registry.cn-beijing.aliyuncs.com/modos-official/jenkins:lts-2.555.3

docker push registry.cn-beijing.aliyuncs.com/modos-official/jenkins:lts-2.555.3


创建docker-compose

mkdir -p /opt/soft/jenkins
cd /opt/soft/jenkins
mkdir jenkins_home

vim docker-compose.yml

# docker-compose.yml
version: '3.8'
services:
  jenkins:
    image: registry.cn-beijing.aliyuncs.com/modos-official/jenkins:lts-2.555.3
    container_name: jenkins
    restart: always
    privileged: true
    user: root
    ports:
      - "8080:8080"
      - "50000:50000"
    volumes:
      - ./jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
      - /usr/bin/docker:/usr/bin/docker
      - /usr/lib/x86_64-linux-gnu/libltdl.so.7:/usr/lib/x86_64-linux-gnu/libltdl.so.7

备注:
###### 权限和对外端口
    privileged: true          # ⭐ 允许 DinD（容器内跑 Docker）
    user: root                # ⭐ 避免权限问题
    ports:
      - "8080:8080"           # Web UI
      - "50000:50000"         # JNLP Agent 通信
###### DinD 三件套
    - /var/run/docker.sock:/var/run/docker.sock        # 🔌 Docker 守护进程通信
    - /usr/bin/docker:/usr/bin/docker                  # 🔧 Docker 客户端程序
    - /usr/lib/x86_64-linux-gnu/libltdl.so.7:/usr/lib/x86_64-linux-gnu/libltdl.so.7  # 🔌 动态加载器（没有它 docker 命令直接崩！）


sudo docker-compose up -d

登陆:
http://localhost:8080/

#初始管理员密码（Docker）
docker exec d9f1cefc91d7 cat /var/jenkins_home/secrets/initialAdminPassword


jenkins创建Pipeline Script类型的job

插件安装docker构建主机