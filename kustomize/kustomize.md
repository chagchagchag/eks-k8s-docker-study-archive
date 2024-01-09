## kustomize



## 참고

- [kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/)
  - [Kustomize - kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/references/kustomize/)
  - [Guides - kubectl.docs.kubernetes.io/guides](https://kubectl.docs.kubernetes.io/guides/)
  - [Commands - kubectl.docs.kubernetes.io](https://kubectl.docs.kubernetes.io/references/kustomize/cmd/)
- [kustomize.io - https://kustomize.io/](https://kustomize.io/)
- [malwareanalysis.tistory.com/402](https://malwareanalysis.tistory.com/402)

<br>



## kustomize 란?

kustomize 는 kubectl 내에 내장된 도구.

kustomization.yml 파일이 위치한 디렉터리에서는 kustomize 명령어로 이미지의 오버레이를 수행할 수 있다. 다른 yml 파일이 없더라도 kustomziation.yml 이 있다면 kustomize 는 동작 가능하다. 

kustomization.yml 은 kustomize 가 실행될 때 어떤 필드를 재정의 할 것인가를 설정하는 파일이다.

kustomization.yml 파일에는 kustomize 실행시 어떤 필드를 재정의할지를 적어둔다. 원래 작성한 yml 파일이 있는데, kustomization.yml 파일내에 재정의한 값이 있다면 원래 값을 재정의 한 값으로 덮어쓰게 된다. <br>

쿠버네티스 자료들 전반적으로 overlay 라는 용어가 굉장히 많이 쓰이는데, 원래 정의해둔 기본 이미지 위에 새로운 이미지를 덮어서 올려놓듯 overlay 라는 용어는 기존 이미지 파일의 내용은 그대로 한 채로 새로 적용한 이미지 파일에서 바뀐부분만 업데이트 하는 것을 의미한다. 이렇게 overlay 하는 것의 장점은 매번 같은 내용이 적용되어야 하는 base 매니페스트 yml 파일을 두고 세세하게 달라지는 부분은 kustomization 을 통해 필요한 부분만 재정의 할 수 있다는 장점이 있다.<br>

또 한가지 장점은 배포환경마다 kustomization.yml 파일만 확인하면 어느부분이 달라지는지 정확하게 확인할 수 있다는 점이다.<br>

<br>



