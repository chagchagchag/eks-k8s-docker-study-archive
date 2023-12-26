## (에러대응) error: You must be logged in to the server (the server has asked for the client to provide credentials) 

## 참고

- https://public-cloud.tistory.com/73

<br>



## 증상

```bash
$ kubectl create namespace argocd
error: You must be logged in to the server (Unauthorized)
```

<br>



## 원인

aws cloud 9 내에는 `~/.aws/credentials` 라는 파일이 있는데 이 곳에서 아래의 환경변수들을 주기적으로 업데이트 한다.

- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`
- `AWS_DEFAULT_REGION`

AWS Cloud9 내의 기본 credentials 는 주기적으로 업데이트 되기 때문에, AdministratorAccess 가 포함된 cluster 사용자의 권한을 부여했던 액세스 키, 액세스 키 시크릿이 일정 시간이 지나면 사라지는 것이다.

<br>



## 해결방법

일정 권한을 가진 IAM User 를 생성한다. (내 경우에는 AdministratorAccess 를 가진 IAM 사용자 추가가 및 액세스 키, 액세스 시크릿 등을 발급해서 Cloud9 내의 환경변수로 export 해줬다.)



예를 들어 아래와 같은 쉘 스크립트를 통해 EKS가 내부적으로 환경변수로 인식하는 환경변수로 액세스 키 관련 환경변수를 선언해준다.

`export-access-key-gitops-study-argocd.sh`

```bash
export AWS_ACCESS_KEY_ID={액세스키 ID}
export AWS_SECRET_ACCESS_KEY={액세스키 시크릿}
export AWS_DEFAULT_REGION=ap-northeast-2
```

<br>



환경변수 활성화

```bash
$ source export-acess-key-gitops-study-argocd.sh
```

<br>

