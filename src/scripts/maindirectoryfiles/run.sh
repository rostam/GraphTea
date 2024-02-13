if [ "$JAVA_HOME" != "" ]; then
	$JAVA_HOME/bin/java -jar graphtea-main.jar;
else
	java -jar graphtea-main.jar;
fi
