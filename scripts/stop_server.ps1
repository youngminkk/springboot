$PROJECT_NAME = "demo-0.0.1-SNAPSHOT"

$CURRENT_PID = Get-WmiObject Win32_Process -Filter "name = 'java.exe'" | Where-Object { $_.CommandLine -like "*$PROJECT_NAME*" } | Select-Object -ExpandProperty ProcessId

if ($null -ne $CURRENT_PID) {
    Stop-Process -ID $CURRENT_PID
    Start-Sleep -Seconds 5
}