# Set environment variables for Ant
$env:ANT_OPTS = "-Xss8m -Xmx1024m"

# Current directory for the build file
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path

# Run ant with the specified build file
Write-Host "Building JAR file with Ant..." -ForegroundColor Cyan
ant -f "$scriptPath\build.xml" jar

# Check if build was successful
if ($LASTEXITCODE -eq 0) {
        Write-Host "Build successful! Starting application..." -ForegroundColor Green
            
                # Change directory to dist
                    Set-Location -Path "$scriptPath\dist"
                        
                            # Run the Java application with memory settings
                                java -jar -Xss2m -Xms32m -Xmx1024m SIMRSKhanza.jar
} else {
        Write-Host "Build failed with exit code $LASTEXITCODE" -ForegroundColor Red
            Read-Host "Press Enter to exit"
}
