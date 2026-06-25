
####  本教程使用ubuntu 24.04进行构建

准备ubuntu 24.04基础镜像
```
sudo apt install docker.io
sudo systemctl status docker

由于墙的原因，我们选择阿里云作为docker官方镜像仓库

配置阿里云加速器
https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors

sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": [
    "https://docker.1ms.run",
    "https://ynn4r95o.mirror.aliyuncs.com"
    ]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker

sudo docker pull ubuntu:24.04


将镜像推送到阿里云
docker login --username=fengjunhua_001 registry.cn-beijing.aliyuncs.com
docker tag [ImageId] registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:[镜像版本号]
docker push registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:[镜像版本号]
docker pull registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:[镜像版本号]


使用阿里云镜像作为构建容器
docker tag 786a8b558f7b registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:24.04

docker push registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:24.04

```

准备docker基础构建镜像

这里我们只安装构建环境所需要的软件包,环境变量的配置我们放在构建流程里

```
docker run -it registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:24.04 /bin/bash

apt install wget curl vim iproute2 net-tools iputils-ping git -y


将version-check.sh脚本复制到docker容器环境中
vim version-check.sh
chmod +x version-check.sh
/bin/bash version-check.sh

根据提示缺少的依赖包信息,分别安装缺少的相关库或依赖:

apt install binutils bison gawk build-essential texinfo python3 -y

rm -rf /bin/sh 
ln -s /usr/bin/bash /bin/sh

```


为docker构建镜像添加lfs用户

```
用户名:lfs,密码:lfs
groupadd lfs
useradd -s /bin/bash -g lfs -m -k /dev/null lfs
passwd lfs

```

清除缓存,制作镜像
```
apt clean
rm -rf /var/lib/apt/lists/*


docker commit ac226b63494d registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:lfs-base
docker push registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:lfs-base
```

配置用户环境变量
```
root用户:
vim .bashrc
在文件最后添加:

export LFS=/mnt/lfs
umask 022

lfs用户:
vim .bashrc
在文件最后添加:
set +h
umask 022
LFS=/mnt/lfs
LC_ALL=POSIX
LFS_TGT=$(uname -m)-lfs-linux-gnu
PATH=/usr/bin
if [ ! -L /bin ]; then PATH=/bin:$PATH; fi
PATH=$LFS/tools/bin:$PATH
CONFIG_SITE=$LFS/usr/share/config.site
export LFS LC_ALL LFS_TGT PATH CONFIG_SITE

docker commit ac226b63494d registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:lfs-base
docker push registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:lfs-base
```
