@echo off
echo ==========================================
echo Building MonsoonAlert APK for Distribution
echo ==========================================
echo.

REM Clean previous builds
echo Cleaning previous builds...
call gradlew clean

REM Build debug APK
echo.
echo Building Debug APK...
call gradlew assembleDebug

REM Build release APK (unsigned, for local testing)
echo.
echo Building Release APK...
call gradlew assembleRelease

echo.
echo ==========================================
echo Build Complete!
echo ==========================================
echo.
echo APK Locations:
echo   Debug:   app\build\outputs\apk\debug\app-debug.apk
echo   Release: app\build\outputs\apk\release\app-release-unsigned.apk
echo.
echo To install on your device:
echo   adb install app\build\outputs\apk\debug\app-debug.apk
echo.
echo To share with others:
echo   - Upload to Firebase App Distribution
echo   - Upload to GitHub Releases
echo   - Share APK file directly
echo.
pause
