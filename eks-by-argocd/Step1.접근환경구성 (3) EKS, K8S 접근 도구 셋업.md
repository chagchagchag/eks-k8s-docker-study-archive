## Step1.접근환경구성 (3) EKS, K8S 접근 도구 셋업

<br>

## kubectl 설치

kubectl 을 다운로드하고 설치하는 방법은 [docs.aws.amazon.com - kubectl 설치 또는 업데이트](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/install-kubectl.html) 에 자세히 설명되어 있다.<br>

실습용 kubernetes 클러스터는 1.28 버전을 사용하기로 했고, kubectl 역시 1.28 버전으로 똑같이 맞춰줬다.(딱히 상관은 없지만 맞춰줌)<br>

아래 코드는 모두 [docs.aws.amazon.com - kubectl 설치 또는 업데이트](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/install-kubectl.html) 명시되어 있는 명령어들이다. 설명은 직접 작성.

```bash
## kubectl 바이너리 다운로드
$ curl -O https://s3.us-west-2.amazonaws.com/amazon-eks/1.28.3/2023-11-14/bin/linux/amd64/kubectl

## 체크섬 다운로드 (다운로드한 바이너리 확인 용도)
$ curl -O https://s3.us-west-2.amazonaws.com/amazon-eks/1.28.3/2023-11-14/bin/linux/amd64/kubectl.sha256

## SHA-256 체크섬 확인
$ sha256sum -c kubectl.sha256
kubectl: OK

## kubectl 바이너리에 실행권한 추가
$ chmod +x ./kubectl

## $HOME 내에 bin 디렉터리 생성해서 kubectl 바이너리를 이동시키고, $HOME/bin 을 $PATH 에 등록
$ mkdir -p $HOME/bin && cp ./kubectl $HOME/bin/kubectl && export PATH=$HOME/bin:$PATH

## 셸 초기화 파일에 $HOME/bin 경로를 추가해서 셸을 열때마다 PATH 가 등록되도록 설정
$ echo 'export PATH=$HOME/bin:$PATH' >> ~/.bashrc

## kubectl 설치 확인 (kubectl 버전 확인)
$ kubectl version --client
Client Version: v1.28.3-eks-e71965b
Kustomize Version: v5.0.4-0.20230601165947-6ce0bf390ce3

## 다운로드 받았던 바이너리들을 정리해준다. (내 경우에는 download/kubectl 에 이동시켜줬다.)
## (내가 백업병이 걸려서 백업해둔 것일 뿐, 사실 필요가 없기에 삭제해줘도 된다.)
$ mkdir -p download/kubectl && mv kubectl* download/kubectl/
```

<br>



만약 현재 eks 클러스터가 이미 있고 이 클러스터를 연결 시킬 것이라면 아래의 명령어를 수행한다.

```bash
$ aws eks update-kubeconfig --region region-code --name my-cluster
```

<br>



## eksctl 설치

eksctl 은 kubernetes 를 배포하기 위해 필요한 CLI 다.

이번 설치 내용은 아래의 자료들을 참고했다

- [docs.aws.amazon.com - eksctl 설치 또는 업데이트](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/eksctl.html)
- [eksctl.io/installation - Installation](https://eksctl.io/installation/)
    - `docs.aws.amazon.com - eksctl 설치 또는 업데이트` 에서 안내해준 링크다. eksctl 공식 가이드 문서인 듯 해보인다. 아마도 eksctl 문서를 github 내에서 관리하는게 더 효율적이기에 이렇게 정적문서 블로그로 개설해둔 듯 해보인다.

<br>

실습하고 있는 PC가 윈도우든, 맥이든, 리눅스든 어차피 Cloud9 에 접속해서 설치하면 되는 것이기에 그냥 Linux (amd64) 에 맞춰서 설치하면 된다.<br>

개발 PC에서 aws cli 를 사용하면 매번 profile 을 지정해야 하고, profile 을 잘못 지정하면 인프라에 반영되기에 꽤 스트레스를 받을때도 있는데 이런 점은 Cloud 9 의 장점인 것 같다.<br>

<br>

```bash
## 환경변수 추가 (다운로드시에 환경변수를 사용하기 위해 추가한 것)
$ ARCH=amd64
$ PLATFORM=$(uname -s)_$ARCH

## eksctl 바이너리 다운로드. 
## 위에서 설정해둔 환경변수인 PLATFORM 을 기준으로 플랫폼에 맞는 다운로드를 진행
$ curl -sLO "https://github.com/eksctl-io/eksctl/releases/latest/download/eksctl_$PLATFORM.tar.gz"

## 다운로드 받은 eksctl 바이너리 파일의 체크섬 확인
$ curl -sL "https://github.com/eksctl-io/eksctl/releases/latest/download/eksctl_checksums.txt" | grep $PLATFORM | sha256sum --check
eksctl_Linux_amd64.tar.gz: OK

## 다운로드 받은 ecksctl 압축파일을 /tmp 에 압축을 풀고 압축파일은 삭제한다. 
$ tar -xzf eksctl_$PLATFORM.tar.gz -C /tmp && rm eksctl_$PLATFORM.tar.gz

## /tmp 에 압축을 푼 eksctl 은 /usr/local/bin 에 이동시켜준다.
$ sudo mv /tmp/eksctl /usr/local/bin

## 정상설치 되었는지 확인
$ ec2-user:~/environment $ eksctl
The official CLI for Amazon EKS

Usage: eksctl [command] [flags]

Commands:
... 
```

<br>



## helm 설치

helm 설치에 대한 명령어는 [helm.sh - Installing Helm ## From Script](https://helm.sh/docs/intro/install/#from-script) 의 내용을 발췌해다.<br>

이번 예제 프로젝트에서는 ALB Ingress Controller 를 helm 을 이용해서 설치 예정이다. 따라서 Cloud9 에 helm 을 설치해야 한다.<br>

```bash
## helm.sh 라는 이름으로 헬름 설치 파일을 curl 로 다운로드
$ curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3

## 다운로드 받은 파일 확안
$ ls
README.md  download  get_helm.sh  minimum-cluster.yml

## 실행 권한 변경
$ chmod 0700 get_helm.sh

## 설치파일 실행
$ ./get_helm.sh 
Downloading https://get.helm.sh/helm-v3.13.1-linux-amd64.tar.gz
Verifying checksum... Done.
Preparing to install helm into /usr/local/bin
helm installed into /usr/local/bin/helm
```

<br>









