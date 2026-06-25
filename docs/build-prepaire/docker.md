
####  本教程使用ubuntu 24.04进行构建


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
docker tag 786a8b558f7b registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:24.04-base

docker push registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:24.04
docker push registry.cn-beijing.aliyuncs.com/modos-official/ubuntu:24.04-base
```