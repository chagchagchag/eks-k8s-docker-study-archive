## Downward API 란?

클러스터 내에서 운영 중인 Pod 을 고유하게 인식할 수 있도록 하고 컨테이너로 배포된 애플리케이션에서도 Pod 이름을 수집해야 하는 경우가 있다. 이렇게 Pod 이름과 같은 정보들은 Downward API 를 통해 접근 가능하다.

Downward API 는 실행 중인 컨테이너에 Pod 또는 컨테이너의 필드를 노출하는 API 를 의미한다. Downward API 를 사용하는 방식에는 대표적으로 [환경변수](https://kubernetes.io/ko/docs/tasks/inject-data-application/environment-variable-expose-pod-information/), [볼륨파일](https://kubernetes.io/ko/docs/tasks/inject-data-application/downward-api-volume-expose-pod-information/) 을 이용하는 방식들이 있다.<br>

<br>



## 요약

다운워드(Downward) API 를 통해서 Pod 또는 Container 의 주요 메타정보들을 애플리케이션이 구동되는 시점에 아래의 방식으로 전달해줄 수 있다.

- [환경 변수](https://kubernetes.io/ko/docs/tasks/inject-data-application/environment-variable-expose-pod-information/)
- [볼륨 파일](https://kubernetes.io/ko/docs/tasks/inject-data-application/downward-api-volume-expose-pod-information/)

<br>



쉽게 설명하면 Pod, Container 형식으로 kubernetes 에 배포해서 구동되는 애플리케이션에는 `환경변수` 형식으로 주입이 가능하고 또는 `볼륨 파일` 에 주입해두면 애플리케이션이 볼륨 파일을 통해서 접근하게 되는 것을 의미한다.<br>

이때 Pod 의 주요 정보들은 `fieldRef` 속성을 통해 접근가능하고 Container 의 주요 정보들은 `resourceFieldRef` 를 통해 접근 가능하다.<br>

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