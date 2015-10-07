# Beluga Service
Beluga를 이용한 멀티테넌트 App 서비스포털

### 설치시 설정.

1. WAS구동시 아래의 설정을 넣어준다.

ubuntu14.04 에서 tomcat7은 /etc/default/tomcat7 파일에 추가.

```
-Dbeluga.endpoint=<Beluga IP>:<Beluga Port>/<Cluster ID>
```

예)

```
JAVA_OPTS="$JAVA_OPTS -Dbeluga.endpoint=beluga.kloudrun.com:9000/sample"
```



2. service.db.beluga 를 hosts파일에 등록
management노드의 IP와 매핑시킨다.

$ sudo vi /etc/hosts
```
xxx.xxx.xxx.xxx service.db.beluga
```

