목차 작성 중<br>

예제 애플리케이션을 ArgoCD 로 연동해서 k8s와 웹애플리케이션을 ArgoCD로 관리하는 예제는 어떤 웹 애플리케이션 예제를 쓸지 고민 중에 시간이 아까워서 고민을 하면서 다른 설치 문서화 작업도 진행하기로 결정.<br>

이번 문서에서는 ArgoCD의 개념에 대해서는 정리를 하지 않기로 결정(시간이 없음 ㅠㅠ)<br>

<br>



**CloudFormation vs 웹콘솔**<br>

alb controller 나 eks 클러스터, IAM, Security Rule 이런 것들을 Cloud Formation 으로 하면 좋고 이전에 만들어둔 예제도 있어서 가져다 쓰면 되기는 하지만, 이번에 Cloud 9 을 처음 사용해보기도 했고, 웹 콘솔에서 캡처 뜨고 이런것도 귀찮지만 웹 콘솔에서 어떤 화면이 있는지도 정리하면 좋을 것 같아서 이번 실습은 웹 콘솔에서의 메뉴 접근 위주로 작성함.

<br>



## Step 1. EKS 클러스터 접근환경, 클러스터 생성 및 alb controller 로 트래픽 개방 

접근환경 구성

- Cluster, ArgoCD 접근 용도의 IAM 계정 생성
  - 실습 용도의 IAM 계정 생성
- Security Rule (보안 정책) 생성
  - 실습을 위해 간단하게 `AdministratorAccess` 권한을 가진 Security Rule 생성
- AWS Cloud 9 셋업 
  - 개발 PC OS 및 환경에 따라 생기는 불일치를 해결하기 위해 선택
  - kubectl 설치
  - eksctl 설치
  - helm 설치



클러스터 생성

- eksctl 을 사용해 EKS Cluster 생성 
- eksctl 을 사용해 EKS Nodegroup 생성
- Managed Node Group 삭제 (비용문제)
- EKS 추가기능 (VPC CNI, EBS CSI 드라이버, kube-proxy, CoreDNS)
- oidc 조회 및 클러스터 service account 생성
- helm 을 이용해 aws-load-balancer-controller 생성

<br>



## Step 2. ArgoCD 구축

- helm 을 이용해 argoCD 설치
- argoCD 에 HTTP 허용 
  - 상용인증서를 통해 https 인증을 aws alb 계층에서 ACM과 함께 연동하기엔 비용문제 발생가능. 따라서 개발 버전으로 외부의 HTTP 트래픽을 허용하는 ArgoCD 구축
  - ArgoCD Deployment 내의 args 에 `--insecure` 옵션 추가 후 재배포
  - Worker Node 들이 속한 Security Rule 수정
  - 80 포트 nodeport 생성 (service.yml 작성 → apply)
  - 80 포트 Rule 이 적용된 Security Rule ID 를 명시한 ingress 생성 (ingress.yml 작성 → apply)
- 80 포트 허용된 argoCD 에 ingressURL 을 통해 접속해보기
- ArgoCD 의 Default Password 변경

<br>



## Step 3. 웹 애플리케이션과 k8s 를 ArgoCD 로 관리하기



