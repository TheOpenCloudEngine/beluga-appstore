# Beluga Appstore [![Build Status](https://travis-ci.org/TheOpenCloudEngine/beluga-appstore.png)](https://travis-ci.org/TheOpenCloudEngine/beluga-appstore)
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
JAVA_OPTS="$JAVA_OPTS -Dbeluga.endpoint=10.0.1.200:9000/sample -Ddb.endpoint=10.0.1.100:3306/appstore -Djava.security.egd=file:/dev/./urandom"
```

** `java.security.egd`는 간혹 환경에 따라 `/dev/random`이 충분한 엔트로피를 받지 못할때 JVM이 행에 걸리는 현상을 방지하기 위함이다.

** MySQL 스키마는 다음SQL로 생성한다. 

https://github.com/TheOpenCloudEngine/beluga-appstore/blob/master/src/main/resources/beluga-appstore.sql

** MySQL서버는 따로 준비할 필요없이 클러스터생성시 만들어진 Management Node내의 MySQL을 사용한다. 계정은 기본적으로 `root` / `beluga123:)` 이다.

그리고 아래라인을 찾아서 tomcat의 메모리를 최대 768m으로 수정한다.
```
JAVA_OPTS="-Djava.awt.headless=true -Xmx768m -XX:+UseConcMarkSweepGC"
```

마지막으로 JAVA_HOME 을 설정한다.
```
JAVA_HOME=/usr/lib/jvm/java-8-oracle
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

3 . 간편 업데이트 구동

`script/update/init.sh` 파일을 서버에 복사한뒤 실행하면, 동일 디렉토리에 자동으로 `deploy.sh` 쉘이 생성되고, Tomcat의 `webapps`, `logs` 디렉토리가 심볼릭링크로 연결된다.

앱스토어를 특정버전으로 구동하기 위해서는 아래와 같이 실행한다.

```
$ ./deploy.sh 2.0.3
```
이를 통해 해당버전의 Appstore 파일이 다운로드되고, 톰캣의 ROOT 컨텍스트로 자동으로 복사되어, 재기동된다.
