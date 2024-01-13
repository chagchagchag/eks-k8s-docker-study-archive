## kustomize

## 참고

- [kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/)
  - [Kustomize - kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/references/kustomize/)
  - [Guides - kubectl.docs.kubernetes.io/guides](https://kubectl.docs.kubernetes.io/guides/)
  - [Commands - kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/references/kustomize/cmd/)
  - [The Kustomization File - kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/references/kustomize/kustomization/)
- [kustomize.io - https://kustomize.io/](https://kustomize.io/)
- [malwareanalysis.tistory.com/402](https://malwareanalysis.tistory.com/402)

<br>



## kustomize 란?

kustomize 는 kubectl 내에 내장된 도구.

kustomization.yml 파일이 위치한 디렉터리에서는 kustomize 명령어로 이미지의 오버레이를 수행할 수 있다. 다른 yml 파일이 없더라도 kustomziation.yml 이 있다면 kustomize 는 동작 가능하다. 

kustomization.yml 은 kustomize 가 실행될 때 어떤 필드를 재정의 할 것인가를 설정하는 파일이다.

kustomization.yml 파일에는 kustomize 실행시 어떤 필드를 재정의할지를 적어둔다. 원래 작성한 yml 파일이 있는데, kustomization.yml 파일내에 재정의한 값이 있다면 원래 값을 재정의 한 값으로 덮어쓰게 된다. <br>

쿠버네티스 자료들 전반적으로 overlay 라는 용어가 굉장히 많이 쓰이는데, 원래 정의해둔 기본 이미지 위에 새로운 이미지를 덮어서 올려놓듯 overlay 라는 용어는 기본 이미지 파일(base.yml)의 내용은 그대로 한 채로 새로 적용한 이미지 파일에서 바뀐부분만 업데이트 하는 것을 의미한다. 이렇게 overlay 하는 것의 장점은 매번 같은 내용이 적용되어야 하는 base.yml 매니페스트 파일을 두고 세세하게 달라지는 부분은 kustomization.yml 을 통해 필요한 부분만 오버레이 하는 것으로 재정의 할 수 있다는 점이다.<br>

또 한가지 장점은 배포환경마다 kustomization.yml 파일만 확인하면 어느부분이 달라지는지 정확하게 확인할 수 있다는 점이다.<br>

<br>



## Helm vs Kustomize

helm 은 템플릿 기반이다. kustomize 는 kubectl 내장 기능을 통해 이미지를 덮어쓰거나 이런 것이 가능하다. helm, kustomize 모두 운영/개발 환경 등에 적합하게 이미지를 바꿔서 적용하는 것이 가능하다. <br>

helm 의 경우에는 소프트웨어 개발 팀이 선호하는 편이고 kustomize 는 데브옵스 쪽에서 선호하는 편이다.<br>

kustomize 에서는 helm chart 를 가져올수 있고 kustomize 기능을 수행할 수 있다. helm 은 values.yml 에 필드를 선언해서 사용하는 방식이고, values.yml 에 정의된 필드만 수정할 수 있다. kustomize 는 Generator 등을 사용할 수 있기에 helm 의 단점을 일부 보완하기도 한다.<br>

결론만 적어보면 이렇다. <br>

개발버전 및 개발팀에서는 주로 Helm 을 통해 Revision 을 관리한다. 그리고 운영레벨에서는 주로 Kustomize 를 통해 배포를 수행하는 것이 흔하다고 한다. Kustomize 로 helm 을 읽어들여서 배포하는 것이 가능하기도 하고 운영 레벨까지 helm 을 확장해서 사용하기에는 확장성이 조금 떨어지며, 데브옵스 팀과 같은 다른 팀에서는 오히려 템플릿 보다는 kustomize 와 같은 매니페스트 파일 위주로 대조하는게 더 명확해서이지 않을까 싶다.<br>

개발팀, 인프라팀으로 나누어서 설명했지만, 코드가 개발버전의 코드이고 상용릴리즈 할 정도는 아니라면 Helm 이 추천되고 운영 급에서 yaml 매니페스트를 일괄 수정한다든지 이런 것들을 수행할 때에는 kustomize 를 쓴다면 조금 더 유연해진다. 인터넷의 자료들을 보다 보니 아마도 개발자분들도 ArgoCD 에 관심을 갖고 자사 솔루션을 운영하는 것 같다는 생각이 들었다. 이게 왜냐면, namespace 나 label 만 보더라도 수정해야 할 부분이 여러부분일 때 아무리 Helm 으로 하더라도 실수를 하고 Helm 기반으로 dev, cbt, prod 이런식으로 관리한다고 하더라도 kustomize 에 비해 확장성이 떨어져서이지 않을까 싶다.<br>

<br>



## 주요 필드, 개념

kustomize 는 핵심적인 4 종류의 필드가 있다. kustomize 수행 시에 resources → generators → transformers → validator 순으로 수행된다. 

- resources
  - kustomize 적용할 쿠버네티스 리소스 파일 명들을 지정하는 필드
  - e.g. pod.yml, service.yml 등을 가리킬 때 사용
- generators
  - 새로운 쿠버네티스 리소스/필드 를 생성해서 지정하는 필드
  - e.g. configmap, secret 등을 생성하는 데에 사용 
  - 빌트인으로 제공되는 플러그인(built-in Generators) 으로 [ConfigMapGenerator](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_configmapgenerator_), [SecretGenerator](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_configmapgenerator_), [HelmChartInflationGenerator](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_helmchartinflationgenerator_) 등이 있다. 
  - 이 외에도 다양한 플러그인이 있기에 직접 공식문서인 [kubectl.docs.kubernetes.io 레퍼런스](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/) 를 찾아보는 것을 추천한다. 
- transformers
  - 필드의 값을 변경할 때 사용하는 개념
  - e.g. newName, newTag
  - 빌트인으로 제공되는 플러그인(built-in Transformers) 로 [LabelTransformer](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_labeltransformer_) , [NamespaceTransformer](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_namespacetransformer_), [ImageTagTransformer](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_namespacetransformer_) 등이 있다. 
  - 이 외에도 다양한 플러그인이 있기에 직접 공식문서인 [kubectl.docs.kubernetes.io 레퍼런스](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/) 를 찾아보는 것을 추천한다. 
- validators
  - 검증 및 밸리데이션 작업을 수행

<br>



## cross-cutting

kustomize 에서는 cross-cutting 이라고 하는 개념들을 지원한다. 

cross-cutting 은 아래의 두가지 주요 기능들이 있다.

- 연관관계에 있는 다른 필드들도 일괄 업데이트
- commonAnntations, commonLabels 등 복수개의 하위 필드를 동시에 가리켜야 하는 경우 하위 항목들 일괄 업데이트
  - commonLabels, commonAnnotations 에 지정한 데이터로  yaml 상의 label, annotation 에 해당되는 두 필드를 함께 적용
  - e.g. label
    - metadata의 label 은 수정했는데, spec.selector.matchLabels, template.metadata.labels 를 수정하지 않아서 생기는 오류를 방지할 수 있다.
    - 또는 그 반대의 경우를 방지할 수 있다.
  - e.g. annotation
    - metadata 의 annotation 은 수정했는데, spec.template.metadata.annotation 을 수정하지 않아서 생기는 오류를 방지할 수 있다.
    - 또는 그 반대의 경우를  방지할 수 있다.

<br>



## Base/Overlays

<br>



## Helm 으로 마이그레이션

별도의 문서에 따로 정리할 예정.<br>

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



### eg 3\) ConfigMapGenerator 가 생성한 configMap 을 Pod 에 지정

kustomization.yml

```yaml
resources:
  - deployment.yml
configMapGenerator:
- name: configmap-eg3
  files:
    - data.properties
```

deployment.yml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: eg3-deployment
spec:
  selector:
    matchLabels:
      app: eg3-deployment
  template:
    metadata:
      labels:
        app: eg3-deployment
    spec:
      containers:
      - name: eg3-deployment
        image: nginx
        volumeMounts:
        - name: config
          mountPath: /config
      volumes:
      - name: config
        configMap:
          name: configmap-eg3
```



이제 한번 kustomize 를 수행해보자.

```bash
$ kubectl kustomize ./
apiVersion: v1
data:
  data.properties: message=안녕하세요.
kind: ConfigMap
metadata:
      app: eg3-deployment
  template:
    metadata:
      labels:
        app: eg3-deployment
    spec:
      containers:
      - image: nginx
        name: eg3-deployment
        volumeMounts:
        - mountPath: /config
          name: config
      volumes:
      - configMap:
          name: configmap-eg3-bk7m2kd7f4 # 이부분에 실제 configMap 의 이름이 바인딩되었다.
        name: config
```

<br>



### eg 4\) Transformers 를 이용해 이미지 이름, 태그 명 수정되게끔 하기

[ImageTagTransformer](https://kubectl.docs.kubernetes.io/references/kustomize/builtins/#_namespacetransformer_) 를 어떻게 사용하는지 확인하는 예제다. 어렵지 않아요.

<br>

pod.yml

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: jdk
  labels:
    name: jdk
spec:
  containers:
  - name: jdk
    image: amazoncorretto:17
    resources:
      limits:
        memory: "64Mi"
        cpu: "100m"
```

<br>



kustomization.yml

```yaml
resources:
  - pod.yml
images:
  - name: amazoncorretto
    newName: amazoncorretto
    newTag: 21.0.1
```

<br>



이렇게 작성한 결과를 kustomize 해보면 아래와 같이 `amazoncorretto:21.0.1` 로 반영되어 있는 것을 확인 가능하다. 분명 pod.yml 에는 `amazoncorretto:17` 로 명시했지만, 배포시에 커스텀하게 kustomize 하는 kustomization.yml 내에는 `amazoncorretto:21.0.1` 을 사용하게 정의했기 때문에 kustomization.yml 에 명시한 이미지의 버전으로 오버레이 되었음을 확인 가능하다.

```bash
$ kubectl kustomize ./
apiVersion: v1
kind: Pod
metadata:
  labels:
    name: jdk
  name: jdk
spec:
  containers:
  - image: amazoncorretto:21.0.1
    name: jdk
    resources:
      limits:
        cpu: 100m
        memory: 64Mi
```

<br>



### eg 5) cross-cutting

kustomize 의 cross-cutting 은 아래의 두가지 주요 기능들을 지원한다.

- commonAnntations, commonLabels 등 복수개의 하위 필드를 동시에 가리켜야 하는 경우 하위 항목들 일괄 업데이트
- 연관관계에 있는 다른 필드들도 일괄 업데이트 



편의상 위의 두가지를 아래와 같이 부르기로 했다.

- commonAnnotations, commonLabels 등이 가리키는 하위필드 일괄 업데이트
- 연관관계에 있는 다른 필드들도 일괄 업데이트



#### eg 5.1) commonAnnotations, commonLabels 등이 가리키는 하위필드 일괄 업데이트

deployment.yml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nextjs-myapp
spec:
  selector:
    matchLabels:
      app: nextjs-myapp
  template:
    metadata:
      labels:
        app: nextjs-myapp
    spec:
      containers:
      - name: nextjs-myapp
        image: chagchagchag/nextjs-app-ts:v0.0.1
        resources:
          limits:
            memory: "128Mi"
            cpu: "500m"
        ports:
        - containerPort: 3000

```

<br>



kustomization.yml

```yaml
namespace: nextjs-myapp
namePrefix: dev-
nameSuffix: "-Rev01"
commonLabels:
  app: nextjs-myapp-typescript
commonAnnotations:
  gogogo: "홍진호 우승인가요?"
resources:
  - deployment.yml
```

<br>



kustomize 명령 수행을 통해 kustomization 실행시 어떻게 빌드되는지 확인

-  변경된 부분은 아래 코드의 주석 `1)`, `2)` , `3)`, `4)` 으로 표시해두었다. 

```bash
$ kubectl kustomize ./
apiVersion: apps/v1
kind: Deployment
metadata:
  annotations:
    ## 1)
    gogogo: 홍진호 우승인가요?  ## commonAnnotations.gogogo
  labels:
    ## 2) 
    app: nextjs-myapp-typescript ## commonLabels.app = nextjs-myapp-typescript
  name: dev-nextjs-myapp-Rev01 ## 3), 4)
  namespace: nextjs-myapp
spec:
  selector:
    matchLabels:
      ## 2)
      app: nextjs-myapp-typescript ## commonLabels.app = nextjs-myapp-typescript
  template:
    metadata:
      annotations:
        ## 1)
        gogogo: 홍진호 우승인가요? ## commonAnnotations.gogogo
      labels:
        ## 2)
        app: nextjs-myapp-typescript ## commonLabels.app = nextjs-myapp-typescript
    spec:
      containers:
      - image: chagchagchag/nextjs-app-ts:v0.0.1
        name: nextjs-myapp
        ports:
        - containerPort: 3000
        resources:
          limits:
            cpu: 500m
            memory: 128Mi
```

<br>



#### eg 5.2) 연관관계에 있는 다른 필드들도 일괄 업데이트

cross-cutting 은 연관관계에 있는 다른 필드들도 일괄 업데이트한다.<br>

예를 들어서 ingress 가 service name 을 참조하고 있을 때 kustomize 명령 수행시 service name 이 변경되면 ingress 내에서 참조하는 service name 역시도 kustomize 가 바꿔주는 기능을 의미한다.





### eg 6\) Base/Overlay

