## Minikube 명령어들

- minikube pause
  - 미니큐브 pause
- minikube unpause
  - 미니큐브 pause 된 것을 다시 활성화
- minikube stop
  - 미니큐브 정지
- minikube start
  - 미니큐브 Start
- minikube config set memory 9999
  - 기본 메모리 사이즈를 지정한다. 재시작해야 한다.
- minikube addons list
  - minikube 배포판에의 해 설치된 서비스들이 출력된다.
  - 아무것도 안했는데 별에 별게 다 설치되어 있다.
- minikube start -p aged --kubernetes-version=v1.16.1
  - minikube 를 특정 버전의 쿠버네티스 릴리즈에서 구동한다.
- minikube delete --all
  - 모든 minikube cluster들을 지운다.