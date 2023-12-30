## Downward API 란?

클러스터 내에서 운영 중인 Pod 을 고유하게 인식할 수 있도록 하고 컨테이너로 배포된 애플리케이션에서도 Pod 이름을 수집해야 하는 경우가 있다. 이렇게 Pod 이름과 같은 정보들은 Downward API 를 통해 접근 가능하다.

Downward API 는 실행 중인 컨테이너에 Pod 또는 컨테이너의 필드를 노출하는 API 를 의미한다. Downward API 를 사용하는 방식에는 대표적으로 [환경변수](https://kubernetes.io/ko/docs/tasks/inject-data-application/environment-variable-expose-pod-information/), [볼륨파일](https://kubernetes.io/ko/docs/tasks/inject-data-application/downward-api-volume-expose-pod-information/) 을 이용하는 방식들이 있다.<br>

<br>



## 참고

- [쿠버네티스 공식 문서 - 다운워드(Downward) API](https://kubernetes.io/ko/docs/concepts/workloads/pods/downward-api/#downwardapi-fieldRef)
- [jib 사용시 jvm 옵션을 동적으로 설정하는 방법](https://tangoblog.tistory.com/18) 

<br>



## 요약

흔히 개발작업을 하다보면, [jib 사용시 jvm 옵션을 동적으로 설정하는 방법](https://tangoblog.tistory.com/18) 과 같은 문서를 참고해서 jib 빌드 시에 주입되어야 하는 환경변수들을 쿠버네티스의 특정 필드를 통해서 주입하는 경우가 있다. 예를 들어 [jib 사용시 jvm 옵션을 동적으로 설정하는 방법](https://tangoblog.tistory.com/18) 에서 사용하는 yaml 내에서 사용되는 필드 들 중 `status.podIP`, `metadata.name` 이 그 경우다.<br>

`status.podIP`, `metadata.name` 과 같은 필드들은 쿠버네티스의 사용자 레벨에서 필요한 정보이기에 쿠버네티스 개발팀은 이런 정보를 매니페스트 파일 내에 환경변수로 명시하거나 볼륨파일에 정의해서 외부에서 참조할 수 있도록 따로 Downward API 라는 API로 외부에 명시해두었다.<br>

API 라고 해서 사용자가 API를 배워야 하는 것은 아니고, YAML 명세서에 Downward API 의 필드명을 명시하면, 쿠버네티스 엔진에 `kubectl` 등과 같은 커맨드와 yaml 데이터를 받았을 때 필드명을 값으로 대체해주는 작업을 수행하게 된다.<br>

<br>



### 다운워드 API (Downward API)를 사용가능한 방법들

Pod 내에 Container 의 형태로 애플리케이션이 구동되는 시점에 Pod 또는 Container 에 관련된 주요 메타정보들을 다운워드(Downward) API 를 통해서 접근 가능한데, 아래의 방식 또는 시점에 전달해줄 수 있다. 

- [환경 변수](https://kubernetes.io/ko/docs/tasks/inject-data-application/environment-variable-expose-pod-information/)
- [볼륨 파일](https://kubernetes.io/ko/docs/tasks/inject-data-application/downward-api-volume-expose-pod-information/)

<br>



쉽게 설명하면 Pod, Container 형식으로 kubernetes 에 배포해서 구동되는 애플리케이션에는 `환경변수` 형식으로 주입이 가능하고 또는 `볼륨 파일` 에 주입해두면 애플리케이션이 볼륨 파일을 통해서 접근하게 되는 것을 의미한다.<br>

<br>



### 다운워드 API 의 상위 필드 - `fieldRef`, `resourceFieldRef`

이때 Pod 의 주요 정보들은 `fieldRef` 필드를 통해 접근가능하고 Container 의 주요 정보들은 `resourceFieldRef` 필드를 통해 접근 가능하다.<br>

- Pod 의 주요정보 : `fieldRef`
- Container 의 주요정보 : `resourceFieldRef`<br>

<br>



## Downward API 가 제공하는 필드들

쿠버네티스 API 필드 들 중 `fieldRef`, `resourceFieldRef` 와 같은 필드들은 Downward API 를 통해 접근 가능하다.<br>

`fieldRef` :

- 사용가능한 Pod 에 대한 정보를 나타내는 Pod 필드에 대한 정보는 `fieldRef` 를 통해 전달된다.

`resourceFieldRef` :

- 사용가능한 Container 필드에 대한 정보는 `resourceFieldRef` 를 통해 전달된다.

<br>

참고

- Pod 의 `spec` 필드는 항상 하나 이상의 [컨테이너](https://kubernetes.io/docs/reference/kubernetes-api/workload-resources/pod-v1/#Container) 를 정의한다.

<br>



### `fieldRef` 로 접근 가능한 Pod 필드에 대한 정보들

- **`metadata.name`**
  - 파드의 이름
- **`metadata.namespace`**
  - 파드가 속한 [네임스페이스](https://kubernetes.io/ko/docs/concepts/overview/working-with-objects/namespaces/)
- **`metadata.uid`**
  - 파드의 고유 ID
- **`metadata.annotations['<KEY>']`**
  - 파드의 [어노테이션](https://kubernetes.io/ko/docs/concepts/overview/working-with-objects/annotations)에서 `<KEY>`에 해당하는 값 (예를 들어, `metadata.annotations['myannotation']`)
- **`metadata.labels['<KEY>']`**
  - 파드의 [레이블](https://kubernetes.io/ko/docs/concepts/overview/working-with-objects/labels)에서 `<KEY>`에 해당하는 문자열 (예를 들어, `metadata.labels['mylabel']`)
- **`spec.serviceAccountName`**
  - 파드의 [서비스 어카운트](https://kubernetes.io/docs/tasks/configure-pod-container/configure-service-account/)
- **`spec.nodeName`**
  - 파드가 실행중인 [노드](https://kubernetes.io/ko/docs/concepts/architecture/nodes/)명
- **`status.hostIP`**
  - 파드가 할당된 노드의 기본 IP 주소
- **`status.podIP`**
  - 파드의 기본 IP 주소 (일반적으로 IPv4 주소)
  - 주의) 추가적으로 아래 필드는 **환경 변수가 아닌**, `다운워드 API` 볼륨의 `fieldRef`로만 접근 가능하다.
- **`metadata.labels`**
  - 파드의 모든 레이블로, 한 줄마다 하나의 레이블을 갖는(`label-key="escaped-label-value"`) 형식을 취함
- **`metadata.annotations`**
  - 파드의 모든 어노테이션으로, 한 줄마다 하나의 어노테이션을 갖는(`annotation-key="escaped-annotation-value"`) 형식을 취함

<br>



### `resourceFieldRef` 를 통해 접근 가능한 컨테이너 정보들

컨테이너에 관련되어 CPU, 메모리 등과 같은 리소스에 대한 요청(Request) 또는 제한(Limit) 값을 제공한다.<br>

참고) 컨테이너의 CPU, 메모리 제한을 명시하지 않고 다운워드 API 로 이 정보들을 제공할 경우 kubelet 은 기본적으로 [노드의 할당 가능량](https://kubernetes.io/docs/tasks/administer-cluster/reserve-compute-resources/#node-allocatable)에 기반하여 CPU와 메모리에 할당 가능한 최댓값을 노출시킨다.<br>

<br>



- **`resource: limits.cpu`**
  - 컨테이너의 CPU 제한
- **`resource: requests.cpu`**
  - 컨테이너의 CPU 요청
- **`resource: limits.memory`**
  - 컨테이너의 메모리 제한
- **`resource: requests.memory`**
  - 컨테이너의 메모리 요청
- **`resource: limits.hugepages-\*`**
  - 컨테이너의 hugepage 제한 (`DownwardAPIHugePages` [기능 게이트](https://kubernetes.io/ko/docs/reference/command-line-tools-reference/feature-gates/)가 활성화 된 경우)
- **`resource: requests.hugepages-\*`**
  - 컨테이너의 hugepage 요청 (`DownwardAPIHugePages` [기능 게이트](https://kubernetes.io/ko/docs/reference/command-line-tools-reference/feature-gates/)가 활성화 된 경우)
- **`resource: limits.ephemeral-storage`**
  - 컨테이너의 임시 스토리지 제한
- **`resource: requests.ephemeral-storage`**
  - 컨테이너의 임시 스토리지 요청