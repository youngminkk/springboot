$JAR_NAME = "demo-0.0.1-SNAPSHOT.jar"
$REPOSITORY = "C:\spring"

Start-Process -FilePath "java" -ArgumentList "-jar", "$REPOSITORY\$JAR_NAME" -WindowStyle Hidden
