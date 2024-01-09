## kustomize

## 참고

- [kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/)
  - [Kustomize - kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/references/kustomize/)
  - [Guides - kubectl.docs.kubernetes.io/guides](https://kubectl.docs.kubernetes.io/guides/)
  - [Commands - kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/references/kustomize/cmd/)
- [kustomize.io - https://kustomize.io/](https://kustomize.io/)
- [malwareanalysis.tistory.com/402](https://malwareanalysis.tistory.com/402)

<br>



## kustomize 란?

kustomize 는 kubectl 내에 내장된 도구.

kustomization.yml 파일이 위치한 디렉터리에서는 kustomize 명령어로 이미지의 오버레이를 수행할 수 있다. 다른 yml 파일이 없더라도 kustomziation.yml 이 있다면 kustomize 는 동작 가능하다. 

kustomization.yml 은 kustomize 가 실행될 때 어떤 필드를 재정의 할 것인가를 설정하는 파일이다.

kustomization.yml 파일에는 kustomize 실행시 어떤 필드를 재정의할지를 적어둔다. 원래 작성한 yml 파일이 있는데, kustomization.yml 파일내에 재정의한 값이 있다면 원래 값을 재정의 한 값으로 덮어쓰게 된다. <br>

쿠버네티스 자료들 전반적으로 overlay 라는 용어가 굉장히 많이 쓰이는데, 원래 정의해둔 기본 이미지 위에 새로운 이미지를 덮어서 올려놓듯 overlay 라는 용어는 기존 이미지 파일의 내용은 그대로 한 채로 새로 적용한 이미지 파일에서 바뀐부분만 업데이트 하는 것을 의미한다. 이렇게 overlay 하는 것의 장점은 매번 같은 내용이 적용되어야 하는 base 매니페스트 yml 파일을 두고 세세하게 달라지는 부분은 kustomization 을 통해 필요한 부분만 재정의 할 수 있다는 장점이 있다.<br>

또 한가지 장점은 배포환경마다 kustomization.yml 파일만 확인하면 어느부분이 달라지는지 정확하게 확인할 수 있다는 점이다.<br>

<br>



## Helm vs Kustomize

helm 은 템플릿 기반이다. kustomize 는 kubectl 내장 기능을 통해 이미지를 덮어쓰거나 이런 것이 가능하다. helm, kustomize 모두 운영/개발 환경 등에 적합하게 이미지를 바꿔서 적용하는 것이 가능하다. <br>

helm 의 경우에는 소프트웨어 개발 팀이 선호하는 편이고 kustomize 는 데브옵스 쪽에서 선호하는 편이다.<br>

kustomize 에서는 helm chart 를 가져올수 있고 kustomize 기능을 수행할 수 있다. helm 은 values.yml 에 필드를 선언해서 사용하는 방식이고, values.yml 에 정의된 필드만 수정할 수 있다. kustomize 는 Generator 등을 사용할 수 있기에 helm 의 단점을 일부 보완하기도 한다.<br>

<br>



## 주요 필드

kustomize 는 핵심적인 4 종류의 필드가 있다. kustomize 수행 시에 resources → generators → transformers → validator 순으로 수행된다. 

- resources
  - kustomize 적용할 쿠버네티스 리소스 파일 명들을 지정하는 필드
  - e.g. pod.yml, service.yml 등을 가리킬 때 사용
- generators
  - 새로운 쿠버네티스 리소스/필드 를 생성해서 지정하는 필드
  - e.g. configmap, secret 등을 생성하는 데에 사용 
  - 빌트인으로 제공되는 플러그인(built-in Generators) 으로 [ConfigMapGenerator](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_configmapgenerator_), [SecretGenerator](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_configmapgenerator_), [HelmChartInflationGenerator](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_helmchartinflationgenerator_) 등이 있다.
- transformers
  - 필드의 값을 변경할 때 사용하는 개념
  - e.g. newName, newTag
- validators
  - 검증 및 밸리데이션 작업을 수행

<br>



## 주요 명령어

kubectl kustomize `[디렉터리]`

- 지정한 디렉터리에 존재하는 kustomization.yaml 파일을 읽어들여서 전체 yaml 파일을 만들어낸다. 

kubectl apply -f -

- 표준 입력(Stdin) 으로 들어온 yaml 타입의 문자열을 받아서 쿠버네티스 클러스터에 반영(apply)하려고 할 때 사용한다.

kubectl delete -f -

- 표준 입력(Stdin) 으로 들어온 yaml 타입의 문자열을 받아서 쿠버네티스 클러스터에서 삭제 하려고 할때 사용한다.

<br>





## 주요 예제 

### eg 1\) 표준입출력으로 kustomize 생성 후 클러스터 반영

pod.yml

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
    name: nginx
spec:
  containers:
  - name: nginx
    image: nginx:latest
    resources:
      limits:
        memory: "64Mi"
        cpu: "100m"

```



kustomization.yml

```yaml
resources:
  - pod.yml
images:
  - name: nginx
    newName: nginx
    newTag: 1.24.0-alpine
```

<br>



```bash
$ kubectl kustomize ./
### 출력결과
apiVersion: v1
kind: Pod
metadata:
  labels:
    name: nginx
  name: nginx
spec:
  containers:
  ### 변경된 부분
  - image: nginx:1.24.0-alpine
    name: nginx
    resources:
      limits:
        cpu: 100m
        memory: 64Mi


## 클러스터에 배포
$ kubectl kustomize ./ | kubectl apply -f -
pod/nginx created

## 클러스터에서 삭제
$ kubectl kustomize ./ | kubectl delete -f -
pod "nginx" deleted
```

<br>



### eg 2\) ConfigMapGenerator 를 이용해 properties 파일 읽어들이기

kustomization.yml

```yaml
configMapGenerator:
- name: configmap-eg3
  files:
    - data.properties
```

data.properties

```properties
message=안녕하세요.
```

<br>



위와 같이 작성된 파일들은 kustomize 명령어로 읽어들이는게 가능하다. 신기하게도 별다른 코드 없이 configMapGenerator 리소스만 정의해줬고 파일명만 kustomization.yml 인데 읽혀들인다.

```bash
$ kubectl kustomize ./
apiVersion: v1
data:
  data.properties: message=안녕하세요.
kind: ConfigMap
metadata:
  name: configmap-eg3-bk7m2kd7f4
```

<br>



### eg 3\) 