# Nexus Repository에 jar 파일 배포하기

## jar 파일 업로드
### UI를 사용하여 업로드

### mvn 명령어로 업로드
```
mvn \
    deploy:deploy-file \
    -DgroupId=kr.leocat \
    -DartifactId=publish-sample \
    -Dversion=1.4 \
    -DgeneratePom=true \
    -Dpackaging=jar \
    -Durl=http://repo.my.host/content/repositories/releases/ \
    -Dfile=publish-sample-1.4.jar
	-DrepositoryId=mynexus
```