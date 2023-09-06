# HTTP 관련 로컬 에러

일단, 이번 eks 예제에서는 HTTPS 관련해서 지원할 계획은 아직은 없다.

그래서 클라에서 서버로 https 로 api 요청을 실수로 할 경우에 아래와 같은 에러 메시지를 접하게 된다.

> **Invalid character found in method name. HTTP method names must be tokens**

<br>



이것과 관련해서 수정할 때 클라에서 서버로 요청 시에 axios 나 fetch API 에서 요청하는 URL 을 

http로 요청을 하도록 수정해주자.

<br>