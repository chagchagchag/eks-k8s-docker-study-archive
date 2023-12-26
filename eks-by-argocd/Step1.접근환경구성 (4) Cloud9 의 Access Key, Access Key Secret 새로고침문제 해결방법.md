## Step1.접근환경구성 (4) Cloud9 의 Access Key, Access Key Secret 새로고침문제 해결방법

Cloud9 의 경우 따로 IAM User 를 따로 생성할 필요 없이 Cloud9 자체가 스스로 권한을 가지고 있다. AWS CLI 를 사용할 수 있도록 Credentials (`~/.aws/credentials`) 를 미리 세팅해서 제공해준다.<br>

그런데 Cloud9 에서 미리 세팅된 환경으로 제공해주는 credential 은 부족한 권한들도 있다. Cloud 9 내의 Credentials (`~/.aws/credentials` ) 는 Cloud 9 내부적으로 주기적으로 secret key, secret access, session token 을 업데이트한다. 따라서 이 것들을 수정해서 권한이 높은 계정으로 바꿔준다거나 하는 작업을 하면 토큰 값들을 업데이트하고 하는 것들이 불가능하다.<br>

이번 문서에서는 이 문제를 해결하기 위해 직접 관리자 권한(AdministratorAccess)을 가진 IAM User 로부터 Access Key, Access Key Secret 을 환경변수로 덮어 쓰는 쉘 스크립트를 작성한다.<br>

<br>

우선 `export-access-key-gitops-study-argocd.sh` 라는 파일을 만들어서 아래와 같은 내용을 작성한다.<br>

<br>



**export-access-key-gitops-study-argocd.sh**

```bash
export AWS_ACCESS_KEY_ID=액세스키
export AWS_SECRET_ACCESS_KEY=시크릿 키
export AWS_DEFAULT_REGION=ap-northeast-2
```

<br>



그리고 이것을 아래와 같이 Cloud 9 로그인 시마다 실행시켜준다.

```bash
source export-access-key-gitops-study-argocd.sh
```

<br>



물론 `~/.bashrc` 등에 입력해두고 사용하는 것이 좋은 선택일 수 있지만, 나는 그냥 수동으로 입력하는 방법을 선택했다.

<br>





