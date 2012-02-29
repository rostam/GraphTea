if [ "$JAVA_HOME" != "" ]; then
	$JAVA_HOME/bin/java -jar graphlab-main.jar;
else
	java -jar graphlab-main.jar;
fi
