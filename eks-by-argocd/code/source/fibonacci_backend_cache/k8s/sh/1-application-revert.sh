kubectl delete -f ../fibonacci-cache-deploy.yml
kubectl delete -f ../fibonacci-cache-ingress.yml
kubectl delete -f ../fibonacci-cache-service.yml
kubectl delete -f ../fibonacci-cache-config.yml
kubectl delete -f ../fibonacci-cache-log-pvc.yml
kubectl -n fibonacci delete secret fibonacci-cache-secret
