# Garuda Service
Garuda를 이용한 멀티테넌트 App 서비스포털

### 설치시 설정.

WAS구동시 아래의 설정을 넣어준다.

ubuntu14.04 에서 tomcat7은 /etc/default/tomcat7 파일에 추가.

```
-Dgaruda.endpoint=<Garuda IP>:<Garuda Port>/<Cluster ID>
```



