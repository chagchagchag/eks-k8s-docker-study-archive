echo "git add ."
git add .

echo ""
echo "git commit -m '$1'"
git commit -m "$1"

echo ""
echo "git push -u origin main"
git push -u origin main
