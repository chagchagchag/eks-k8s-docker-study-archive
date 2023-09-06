# npm ERR! gyp ERR! find VS msvs_version not set from command line or npm config

npm ERR! gyp ERR! find VS msvs_version not set from command line or npm config



MS 윈도우에서 개발하기 요즘 많이 좋아졌다고 해도 Node.js 계열의 라이브러리를 빌드할 때 깨지는 멘탈충격은 여전하다. 오늘 겪은 에러는 아래와 같다.

```plain
npm ERR! gyp ERR! find VS msvs_version not set from command line or npm config
```

<br>



이것과 관련한 해결책은 [StackOverflow](https://stackoverflow.com/questions/57879150/how-can-i-solve-error-gypgyp-errerr-find-vsfind-vs-msvs-version-not-set-from-c) 에서 찾았다.

- [Download Visual Studio](https://learn.microsoft.com/en-us/visualstudio/install/install-visual-studio?view=vs-2019#step-2---download-visual-studio)
  - Visual Studio 를 다운로드 받고 install 한다.
  - 나는 Community 버전을 다운로드 받았다.



인스톨러를 실행해보면 개발 종속성들을 선택하는 화면이 나온다.

Node.js, c++ 라이브러리만 선택했는데도 용량이 9GB를 넘는다. ㄷㄷㄷ

![](./img/msvc-error/1.png)

<br>



이렇게 설치를 마무리 한 후에 npm install 을 하면 잘 해결된다.

 Mac OS 에서도 엄청 오래전에는 아마 이런 비슷한 문제는 App Studio 였던가 하는 것을 설치해야 개발종속성이 깨지지 않는 경우도 있었던 것을 경험했던 것 같다. 최근에는 안그럴듯.