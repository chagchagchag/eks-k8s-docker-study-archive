## 여러개의 worker node 를 kind 클러스터에서 구동하고 싶을 때



아래와 같이 작성해준다.

**argocd-30009-cluster.yml**

```yaml
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
  - containerPort: 30009
    hostPort: 30009
    protocol: TCP
- role: worker
- role: worker
- role: worker
```



`-role: worker` 라는 부분이 3개 있는 것을 볼 수 있다. 이 것은 worker node 를 3개 두겠다는 의미다.



위에서 작성한 **argocd-30009-cluster.yml** 파일에 정의한 클러스터 정의를 통해 클러스터를 생성하려면 아래와 같은 명령어를 실행한다.

```bash
$ kind create cluster --name argocd-cluster --config=argocd-cluster.yml
```

<br>

