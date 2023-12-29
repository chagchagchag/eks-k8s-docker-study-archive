## Deployment 와 Statefulset, 그리고 Headless Service 란?

일반적인 Service 를 사용 시에는 Service 가 로드밸런싱, proxy 기능을 제공해준다. Deployment 는 Service 를 이용해서 관리된다.<br>

반면, Headless Service 는 Pod 각각이 고정된 DNS 를 가지도록 하고, 로드밸런싱, proxy 기능은 제공되지 않는다. Statefulset 은 Headless Service 로 관리된다.<br>

Statefulset 은 Deployment 와 쓰이는 용도가 조금 다르다.<br>

일반적으로 Deployment 는 random 하게 pod 을 선택한다. Pod 의 IP가 고정되어 있어서 다른 Pod IP 의 고정된 IP를 지정해서 요청을 할 필요가 없고 같은 역할의 Pod 들이 분산배포되어 있기에 Service 가 로드밸런싱, proxy 기능만 제공해주면 된다. 따라서 Deployment 형식의 배포에는 Headless Service 를 적용하지 않는다.<br>

반면, Statefulset 은 특정 Pod 의 IP를 지정해서 요청해야 하는 경우에 사용된다. 따라서 Pod 들 각각의 역할이 정해져 있는 경우에 Statefulset 으로 정의한다. Pod 들의 역할이 정해져 있기에 다른 Pod에 어떤 요청을 하려면 각 Pod 들의 IP를 알수 있어야 IP를 지정해서 요청할 수 있어야 하기 때문이다.  Statefulset 은 Headless Service 를 사용한다. Headless Service 는 고정된 DNS 를 가지기 때문에 이런 요구사항이 충족되기 때문이다.<br>

<br>



## Headless Service 사용 시의 DNS

Headless Service 를 사용하면 Pod 마다 고정된 DNS를 생성가능해진다.

Headless Service 로 묶인 Pod 들에 대해 생성되는 고정된 DNS 는 아래와 같은 형식을 갖는다.

- `[pod 이름].[svc].[namespace].svc.cluster.local`

Headless Services 는 service.yml 에 `spec.clusterIP` 를 None 으로 정의해서 생성 가능하다.

<br>



## 참고

- [왜 StatefulSet 을 사용할까 (feat. deployment 와의 차이점)](https://ltlkodae.tistory.com/54)

<br>