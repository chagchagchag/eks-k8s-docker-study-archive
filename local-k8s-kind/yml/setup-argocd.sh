echo ""
echo "[create] namsepace 'argocd'"
kubectl create namespace argocd

echo ""
echo "[install] kubectl apply -f argoprj/argo-cd/.../install.yaml"
kubectl -n argocd apply -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

echo ""
echo "[setup] ingress (argocd-server-ingress)"
kubectl -n argocd apply -f argocd-ingress.yml

echo ""
echo "[setup] nodeport (argocd-server-nodeport)"
kubectl apply -f argocd-server-nodeport.yml

# echo ""
# echo "[setup] ingress (argocd-server-ingress)"
# kubectl -n argocd apply -f argocd-ingress-only.yml

echo ""
echo "[configure] --insecure configure"
kubectl -n argocd patch deployment argocd-server --type json -p='[{"op":"replace","path":"/spec/template/spec/containers/0/args","value":["/usr/local/bin/argocd-server","--insecure"]}]'

echo ""
echo "[status] kubectl -n argocd get all"
kubectl -n argocd get all

echo ""
echo "[password!!!] your password
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d
