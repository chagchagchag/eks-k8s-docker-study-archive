echo ""
echo "[create] namsepace 'argocd'"
kubectl create namespace argocd

echo ""
echo "[install] kubectl apply -f argoprj/argo-cd/.../install.yaml"
kubectl -n argocd apply -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
