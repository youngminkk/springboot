$JAR_NAME = "demo-0.0.1-SNAPSHOT.jar"
$REPOSITORY = "C:\spring"

Start-Process -NoNewWindow -FilePath "java" -ArgumentList "-jar", "$REPOSITORY\$JAR_NAME" -RedirectStandardOutput "null" -RedirectStandardError "null"
