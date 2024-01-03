kubectl -n fibonacci create secret generic fibonacci-cache-secret --from-literal=api-key=abcd-efgh-ijkl-1111
kubectl apply -f ../fibonacci-cache-log-pvc.yml
kubectl apply -f ../fibonacci-cache-config.yml
kubectl apply -f ../fibonacci-cache-service.yml
kubectl apply -f ../fibonacci-cache-ingress.yml
kubectl apply -f ../fibonacci-cache-deploy.yml