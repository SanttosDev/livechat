Get-ChildItem -Recurse -Include *.js,*.java | ForEach-Object {
    $filePath = $_.FullName
    if (Test-Path $filePath) {
        # Adiciona o nome do arquivo no início
        "Arquivo: $filePath" | Out-File -FilePath .\projeto.txt -Append
        $content = Get-Content $filePath
        foreach ($line in $content) {
            $line | Out-File -FilePath .\projeto.txt -Append
        }
        # Adiciona uma linha separadora entre os conteúdos dos arquivos
        "`n" | Out-File -FilePath .\projeto.txt -Append
    } else {
        Write-Host "Arquivo não encontrado: $filePath"
    }
}
