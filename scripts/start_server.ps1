$JAR_NAME = "demo-0.0.1-SNAPSHOT.jar"
$REPOSITORY = "C:\spring"

$process = Start-Process -NoNewWindow -FilePath "java" -ArgumentList "-jar", "$REPOSITORY\$JAR_NAME" -PassThru
