## 예제마다 다른 kind 클러스터를 여러개 돌리고 싶을 때

흠... 나도 지금 현재(2024.01)는 kind 를 배워가면서 쓰고 있는 중. 

어? minikube 보다 편하네? 하는 생각이 들었었다.

kind 는 클러스터의 포트를 어떤 포트만 열지를 결정할 수 있다.

오늘 쓰는 문서는 자료 검색 중에 아래의 자료를 보았고 이 내용을 정리하기 위해 글을 쓰기 시작했다.

- [StackOverflow - How to use NodePort with Kind?](https://stackoverflow.com/questions/62432961/how-to-use-nodeport-with-kind)



<br>

아래는 그 예제.

30009 번 포트만 개방하고 있다.

**argocd-30009-cluster.yml**<br>

```bash
kind: Cluster
apiVersion: kind.x-k8s.io/v1alpha4
nodes:
- role: control-plane
  kubeadmConfigPatches:
  - |
    kind: InitConfiguration
    nodeRegistration:
      kubeletExtraArgs:
        node-labels: "ingress-ready=true"
  extraPortMappings:
  # - containerPort: 80
  #   hostPort: 80
  #   protocol: TCP
  # - containerPort: 443
  #   hostPort: 443
  #   protocol: TCP
  - containerPort: 30009
    hostPort: 30009
    protocol: TCP
```

만약 위의 코드에서 주석을 해제하면 80, 443, 30009 포트를 모두 개방하게 된다.<br>

<br>



위에서 작성한 **argocd-30009-cluster.yml** 파일에 정의한 클러스터 정의를 통해 클러스터를 생성하려면 아래와 같은 명령어를 실행한다.

```bash
$ kind create cluster --name argocd-cluster --config=argocd-cluster.yml
```

<br>

