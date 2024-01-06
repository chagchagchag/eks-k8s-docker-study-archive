## EKS 삭제

실습 시작할때 새로 생성해서 시작하는게 비용과금이 덜되기에 정리시작



## 설정해둔 namespace 확인

```bash
kubectl get svc --all-namespaces
```

<Br>



## ArgoCD

모든 리소스 확인

```bash
kubectl -n argocd get all
```



삭제 작업 진행

```bash
kubectl delete deployment --all -n argocd

kubectl delete service --all -n argocd

kubectl delete pod --all -n argocd

kubectl delete statefulset --all -n argocd

eksctl delete cluster --name gitops-study-k8scluster
```

<br>







