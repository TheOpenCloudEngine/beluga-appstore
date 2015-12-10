# Beluga Appstore
벨루가 앱스토어

### 설치시 설정.

1 . WAS구동시 아래의 설정을 넣어준다.

ubuntu14.04 에서 tomcat7은 /etc/default/tomcat7 파일에 추가.

##### beluga.endpoint 와 db.endpoint
```
-Dbeluga.endpoint=<Beluga IP>:<Beluga Port>/<Cluster ID>
-Ddb.endpoint=<MYSQL IP>:<MYSQL Port>/<MYSQL Name>
```

예)

```
JAVA_OPTS="$JAVA_OPTS -Dbeluga.endpoint=beluga.kloudrun.com:9000/sample" -Ddb.endpoint=localhost:3306/appstore
```

** MySQL 스키마는 다음SQL로 생성한다. 

https://github.com/TheOpenCloudEngine/beluga-appstore/blob/master/src/main/resources/beluga-appstore.sql

** MySQL서버는 따로 준비할 필요없이 클러스터생성시 만들어진 Management Node내의 MySQL을 사용한다. 계정은 기본적으로 root / beluga123:) 이다.

그리고 아래라인을 찾아서 tomcat의 메모리를 최대 768m으로 수정한다.
```
JAVA_OPTS="-Djava.awt.headless=true -Xmx768m -XX:+UseConcMarkSweepGC"
```


2 . 도메인설정

도메인 관리페이지(각 제공사마다 상이함) 에 들어가서 A 레코드를 설정한다.

예를들어 도메인이 `mydomain.com`이라고 하면,

1) `mydomain.com` : Beluga-service 서버 IP 주소

2) `*.mydomain.com` : Proxy node 서버 IP 주소

3)(옵션사항) `beluga.mydomain.com` : Beluga 서버 IP 주소 

1번설정을 통해 `http://mydomain.com:8080` 접속시 서비스페이지가 열리게 되며,

2번설정을 통해 webapp을 `http://<appId>.mydomain.com` 으로 접근이 가능하게 된다.

3번설정은 옵션사항이나, 설정해두면, `http://beluga.mydomain.com` 으로 클러스터 관리페이지에 바로 접속가능하다.

기본적으로 mesos 관리도구는 `http://mesos.mydomain.com:8080`으로, marathon 관리도구는 `http://marathon.mydomain.com:8080` 으로 접속이 가능하다.

