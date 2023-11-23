$PROJECT_NAME = "springboot"

$CURRENT_PID = Get-Process | Where-Object { $_.Path -like "*$PROJECT_NAME*" -and $_.Path -like "*java*" } | Select-Object -ExpandProperty ID

if ($null -ne $CURRENT_PID) {
    Stop-Process -ID $CURRENT_PID
    Start-Sleep -Seconds 5
}