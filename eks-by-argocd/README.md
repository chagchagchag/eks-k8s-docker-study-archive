이번 문서에서는 ArgoCD의 개념에 대해서는 정리를 하지 않기로 결정(시간이 없음 ㅠㅠ)<br>

**CloudFormation vs 웹콘솔**<br>

alb controller 나 eks 클러스터, IAM, Security Rule 이런 것들을 Cloud Formation 으로 하면 좋고 이전에 만들어둔 예제도 있어서 가져다 쓰면 되기는 하지만, 이번에 Cloud 9 을 처음 사용해보기도 했고, 웹 콘솔에서 캡처 뜨고 이런것도 귀찮지만 웹 콘솔에서 어떤 화면이 있는지도 정리하면 좋을 것 같아서 이번 실습은 웹 콘솔에서의 메뉴 접근 위주로 작성함.<br>

<br>



## Step 1. EKS 클러스터 접근환경, 클러스터 생성 및 alb controller 로 트래픽 개방 

접근환경 구성

- Cluster, ArgoCD 접근 용도의 IAM 계정 생성
  - 실습 용도의 IAM 계정 생성

  - 실습을 위해 간단하게 `AdministratorAccess` 권한을 가진 사용자 그룹, 사용자 생성

- AWS Cloud 9 셋업 
  - 개발 PC OS 및 환경에 따라 생기는 불일치를 해결하기 위해 선택

- EKS,k8s 접근 도구 셋업

  - kubectl 설치
  - eksctl 설치
  - helm 설치

- Cloud 9의 Access Key, Access Key Secret 새로고침 문제 해결방식

<br>



클러스터 생성

- eksctl 을 사용해 EKS Cluster 생성 
- eksctl 을 사용해 EKS Nodegroup 생성
- Managed Node Group 삭제 (비용문제)
- EKS 추가기능 (VPC CNI, EBS CSI 드라이버, kube-proxy, CoreDNS)
- oidc 조회 및 클러스터 service account 생성
- helm 을 이용해 aws-load-balancer-controller 생성

<br>



## Step 2. ArgoCD 구축

- argoCD 공식 github 제공 yml 매니페스트 파일을 이용해 argoCD 설치
- argoCD 에 HTTP 허용 
  - 상용인증서를 통해 https 인증을 aws alb 계층에서 ACM과 함께 연동하기엔 비용문제 발생가능. 따라서 개발 버전으로 외부의 HTTP 트래픽을 허용하는 ArgoCD 구축
  - ArgoCD Deployment 내의 args 에 `--insecure` 옵션 추가 후 재배포
  - Worker Node 들이 속한 Security Rule 수정
  - 80 포트 nodeport 생성 (service.yml 작성 → apply)
  - 80 포트 Rule 이 적용된 Security Rule ID 를 명시한 ingress 생성 (ingress.yml 작성 → apply)
- 80 포트 허용된 argoCD 에 ingressURL 을 통해 접속해보기
- ArgoCD 의 Default Password 변경

<br>



## Step 3. Backend 2 tier Application Dockerfile 구성 및 docker-compose 테스트

nginx, spring boot 기반

- 간단한 helloworld spring boot application 을 Nginx 와 연동 후 docker-compose 로 약식 테스트
- k8s 적용시 또 다른 이야기가 되기에 docker-compose 테스트가 무의미해질 수 있기는 하지만 일단은 이렇게 결정

<br>



## Step 4. Frontend 2 tier Application Dockerfile 구성 및 docker-compose 테스트

nginx, react 기반

- nuxt.js, next.js 이런 것도 개인적으로 스터디 하고 있기에, 적용해볼까 하고 생각했었다. 

- 하지만 가급적 예제의 간단함을 위해 간단한 nginx helloworld 페이지를 활용하기로 함.

<br>



## Step 5. kustomize 적용



## Step 6. ArgoCD 적용





