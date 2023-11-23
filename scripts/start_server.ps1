$JAR_NAME = "demo-0.0.1-SNAPSHOT.jar"
$REPOSITORY = "C:\spring"
$LOG_FILE = "C:\spring\springboot\log.txt"

Start-Process -NoNewWindow -FilePath "java" -ArgumentList "-jar", "$REPOSITORY\$JAR_NAME" -RedirectStandardOutput $LOG_FILE -RedirectStandardError $LOG_FILE
