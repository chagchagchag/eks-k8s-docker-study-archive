## helm

Helm 은 정리할 게 많다... 아직 다른 작업을 하고 있는 중이라서 손을 못댄부분도 많지만.. 일단 필수적인 부분만 정리해봤다.<br>

<br>



## helm 사용시 장점

- value, template : helm 을 사용하면 패키징 구조를 만들고 빌드 도구를 이용해 패키지를 생성가능
- helm 을 이용해서 직접 kubernetes 클러스터에 객체를 적용 가능하다.
  - kubectl 을 raw 하게 사용하면 실수로 인해 운영환경에 영향을 주게 될 경우도 있는데 이런 부분들에 대해 필요한 부분들만을 수정할 수 있도록 나름대로의 보수적인 접근 방식을 제공해준다.
- release : helm 을 이용해 배포버전과 롤백을 컨트롤
- subchart : 공통의 템플릿을 사용해 여러 프로젝트를 관리

<br>



## helm 이란? (짧게)

helm 을 사용하면 배포시 마다 새로운 Release 가 생성되어서 쿠버네티스에 적용된다.

심지어 롤백도 하나의 Release 단위가 된다. (이전 버전 기준으로 새로운 버전을 만들어서 적용)

비슷한 소프트웨어로는 Kustomize 가 있다.<br>

한번 배포했던 내용과 Rollback 했던 내용들은 모두 helm 디렉터리 내에서 Revision 으로 관리된다.<br>

12 Factor App 에서는 릴리즈 단계가 명시적으로 구분되어야 한다는 원칙 역시 있었다. Helm 에서는 이런 원칙이 명시적으로 잘 지켜져 있다.<br>

<br>

- Helm 의 주요 4 요소는 아래와 같다.
- Chart Metadata : Chart 의 기본적인 정의
- Template : Kubernetes 객체를 만들어내기 위한 템플릿
- Value : 템플릿의 변수들에 들어갈 값
  - 파일 형태로 변수들이 템플릿에 결합될 때 사용
  - 빌드 과정에서 helm 에 argument 형태로 전달하는 경우
- Subchart : 해당 차트와 Value 를 공유할 하위 차트들
  - 하위 차트는 상위 차트의 Value 를 모두 상속받는다.
  - 차트가 여러개 생겨나면서 중복이 심해지면, 공통적인 부분 외에 달라지는 부분만 Subchart 로 분리해서 정의한다.

<br>



## 주요 명령어 (자주쓰는...)



## 설치

윈도우

```bash
choco install kubernetes-helm
```

또는

```bash
scoop install helm
```

다른 OS : 자세한 내용은 [여기](https://helm.sh/docs/intro/install/#through-package-managers) 를 참고

<br>



## 시작

helm 차트 디렉터리 생성 

```bash
helm -n fibonacci create helm-fibonacci-backend-web
```

<br>



로컬에서 수정한 차트 적용

```bash
$ helm -n fibonacci install helm-fibonacci-backend-web helm-fibonacci-backend-web/
NAME: helm-fibonacci-backend-web
LAST DEPLOYED: Fri Jan  5 17:31:19 2024
NAMESPACE: fibonacci
STATUS: deployed
REVISION: 1
NOTES:
1. Get the application URL by running these commands:
  export POD_NAME=$(kubectl get pods --namespace fibonacci -l "app.kubernetes.io/name=helm-fibonacci-backend-web,app.kubernetes.io/instance=helm-fibonacci-backend-web" -o jsonpath="{.items[0].metadata.name}")
  export CONTAINER_PORT=$(kubectl get pod --namespace fibonacci $POD_NAME -o jsonpath="{.spec.containers[0].ports[0].containerPort}")
  echo "Visit http://127.0.0.1:8080 to use your application"
  kubectl --namespace fibonacci port-forward $POD_NAME 8080:$CONTAINER_PORT
```

<br>



또는 아래와 같이 적용

```bash
$ cd helm-fibonacci-backend-web
$ helm -n fibonacci install helm-fibonacci-backend-web .
```

<br>



## Helm 을 이용한 빌드, 배포