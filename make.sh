ant clean
ant
java -jar binary/graphtea-main.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
