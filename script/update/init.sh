#!/bin/bash

ln -s /var/lib/tomcat7/logs logs
ln -s /var/lib/tomcat7/webapps webapps

cat <<'EOD' > ./deploy.sh
#!/bin/bash

if [ $# -lt 1 ] ; then
	echo "Usage: $0 <Version>"
	echo "Sample: $0 2.0.3"
	exit 1
fi

VERSION="$1"
URL="https://github.com/TheOpenCloudEngine/beluga-appstore/releases/download/v$VERSION/beluga-appstore-$VERSION.war"
STATUS=$(curl -s --head $URL | head -n 1 | grep "HTTP/1.[01] [23]..")

echo Downloading war from $URL...
echo $STATUS

if [ -z "$STATUS" ] ; then
	echo Caanot find resource!
	exit 1
fi

rm beluga-appstore*.war

curl -L -O $URL

sudo service tomcat7 stop

sudo rm -rf webapps/ROOT.war webapps/ROOT

sudo cp beluga-appstore*.war webapps/ROOT.war

sudo service tomcat7 start
EOD

chmod +x ./deploy.sh