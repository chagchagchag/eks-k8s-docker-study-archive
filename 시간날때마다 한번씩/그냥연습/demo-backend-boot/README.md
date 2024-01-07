## 참고

Kind Cluster 로 추가한 `nodeport-30080-cluster` 는 아래와 같이 `30080` 만을 허용하고 노드는 마스터노드 하나만 존재하는 단순 예제용도의 클러스터입니다.<br>

만약 80, 443 포트도 허용하고 싶다면 아래파일의 주석을 해제하시면 됩니다. 예제용도로 여러가지의 kind 클러스터를 개발 PC에서 돌리고 있기에 이번 예제에서 사용하는 클러스터는 `30080` 처럼 잘 안쓰는 포트를 사용하기로 했습니다.<br>

<br>



`nodeport-30080-cluster.yml`

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
  # - containerPort: 80
  #   hostPort: 80
  #   protocol: TCP
  # - containerPort: 443
  #   hostPort: 443
  #   protocol: TCP
  - containerPort: 30080
    hostPort: 30080
    protocol: TCP
```

<br>



## Command

```bash
$ source create-cluster.sh
...


$ kubectl apply -f demo-backend-boot-k8s.yml
namespace/demo-backend-boot created
service/demo-backend-boot-nodeport created
deployment.apps/demo-backend-boot-app created
...


$ kubectl -n demo-backend-boot get all
NAME                                         READY   STATUS    RESTARTS   AGE
pod/demo-backend-boot-app-59dfd6d99f-9hsp5   1/1     Running   0          23s
pod/demo-backend-boot-app-59dfd6d99f-ngsd2   1/1     Running   0          23s

NAME                                 TYPE       CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
service/demo-backend-boot-nodeport   NodePort   10.96.103.162   <none>        8080:30080/TCP   23s

NAME                                    READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/demo-backend-boot-app   2/2     2            2           23s

NAME                                               DESIRED   CURRENT   READY   AGE
replicaset.apps/demo-backend-boot-app-59dfd6d99f   2         2         2       23s

...



$ curl http://localhost:30080/hello
안녕하세요. 미니큐브 예제입니다.
```

