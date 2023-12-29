## Pod 의 일반적인 DNS 규칙

> 조금 더 자세한 내용은 [시간날 때마다 한번씩/Deployment vs Statefulset, 그리고 Headless Service란.md](https://github.com/chagchagchag/eks-k8s-docker-study-archive/blob/main/%EC%8B%9C%EA%B0%84%EB%82%A0%EB%95%8C%EB%A7%88%EB%8B%A4%20%ED%95%9C%EB%B2%88%EC%94%A9/Deployment%20vs%20Statefulset%2C%20%EA%B7%B8%EB%A6%AC%EA%B3%A0%20Headless%20Service%20%EB%9E%80.md) 에 정리되어 있다.

<br>



## 참고

- [왜 StatefulSet 을 사용할까 (feat. deployment 와의 차이점)](https://ltlkodae.tistory.com/54)

<br>



## Headless Service 단위로 배포된 Pod 들의 DNS 규칙 

Headless Service 단위로 배포된 Pod 들은 아래와 같은 형식의 DNS 를 가진다.

- `[pod 이름].[svc].[namespace].svc.cluster.local`

Headless Service 단위로 배포된 Pod 들은 일반 Service 를 통해 수행되는 로드밸런싱, proxy 를 통해 접근되지 않지만, Pod 마다 고정된 DNS 를 위와 같은 형식으로 가질 수 있다.

<br>



## 일반 Service 로 배포된 Pod 들의 DNS 규칙 

Headless Service 단위로 배포되지 않은 일반 Service 로 배포된 Pod 들 역시 아래와 같은 형식의 DNS 를 가진다. 맨 앞에 있는 pod ip 주소는 상황에 따라 유동적으로 변할 수 있기에 고정적인 주소가 아니라는 점에 주의해야 한다.

- `[pod ip 주소].[namespace].pod.cluster.local`

자세히 살펴보면 DNS 에 Pod IP 가 포함된다.(`[pod-ip-address` )

그런데 Pod IP 는 클러스터 내에서 고정된 DNS가 아니고 끊임없이 상황에 따라 유동적으로 변한다. (최소/최대 스케일링 등에 의해 살았다가 죽었다가를 반복하기에)

<br>





