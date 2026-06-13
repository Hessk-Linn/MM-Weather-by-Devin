# Firebase App Distribution - Step-by-Step Guide

## What is Firebase App Distribution?
- **Free service** to host your APK
- **Invite links** sent via email
- **Up to 200 testers** (free tier)
- **Automatic updates** when you release new versions
- **No Play Store required**

---

## PART 1: Create Firebase Project

### Step 1: Go to Firebase Console
1. Open browser: https://console.firebase.google.com
2. Sign in with your Google account
3. Click **"Create a project"**

### Step 2: Project Setup
1. **Project name:** `MonsoonAlert` (or any name you like)
2. Click **Continue**
3. **Google Analytics:** Toggle OFF (not needed for this)
4. Click **Create project**
5. Wait for project creation, then click **Continue**

---

## PART 2: Add Your Android App

### Step 3: Register App
1. On Firebase dashboard, click **Android icon** (</>)
2. **Android package name:** Enter exactly this:
   ```
   com.aistudio.myanmarweather.wfptv
   ```
3. **App nickname:** `MonsoonAlert Myanmar`
4. **Debug signing certificate SHA-1:** (Optional - can skip)
5. Click **Register app**

### Step 4: Download Config File
1. Click **Download google-services.json**
2. Move this file to:
   ```
   g:/000 My Data/About Vibe Coding/monsoonalert/app/google-services.json
   ```
   
   **Simple rule:** Put `google-services.json` in the same folder as `build.gradle.kts` (the app-level one, not the project-level one).
   
   **Correct location:** `monsoonalert/app/google-services.json`
   
   **Wrong locations:**
   - ❌ `monsoonalert/google-services.json` (root)
   - ❌ `monsoonalert/app/src/google-services.json`
   - ❌ `monsoonalert/app/src/main/google-services.json`

3. Click **Next** → **Next** → **Continue to console**

---

## PART 3: Enable App Distribution

### Step 5: Open App Distribution
1. In left sidebar, click **Build** → **App Distribution**
2. If you see "Get started" button, click it
3. Accept terms if prompted

### Step 6: Create Testers Group
1. Click **Testers & Groups** tab
2. Click **Add group**
3. **Group name:** `Myanmar Testers`
4. Click **Create**

---

## PART 4: Build Your APK

### Step 7: Build in Android Studio
1. Open your project in Android Studio
2. In menu: **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
3. Wait for build to complete
4. You'll see notification: "APK(s) generated successfully"
5. Click notification to see location, or find at:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

**OR use the batch file:**
1. Open File Explorer to your project folder
2. Double-click `build-apk.bat`
3. Wait for build to complete

---

## PART 5: Upload APK to Firebase

### Step 8: Upload APK
1. Back in Firebase console (App Distribution)
2. Click **Releases** tab
3. Click **Upload your first release**
4. Drag your APK file or click **Browse**
5. Select: `app-debug.apk` or `app-release.apk`
6. Wait for upload (shows progress bar)

### Step 9: Add Release Notes
1. **Release notes:** Enter description
   ```
   MonsoonAlert Myanmar Weather App v1.0
   - Live weather for 57 Yangon townships
   - GPS location detection
   - 5-day forecast
   - Monsoon alerts
   ```
2. Click **Next**

### Step 10: Invite Testers
1. Select **Myanmar Testers** group (created in Step 6)
2. **OR** add individual testers:
   - Enter email addresses of friends/family
   - Separate multiple emails with commas
3. Click **Next** → **Distribute**

---

## PART 6: Testers Install the App

### Step 11: Testers Receive Email
Your testers will receive email like this:
```
Subject: MonsoonAlert has invited you to test MonsoonAlert Myanmar

You've been invited to test MonsoonAlert Myanmar app.

[Download App] button
```

### Step 12: Tester Actions
1. **Open email** on their Android phone
2. **Tap "Download App"** button
3. **Follow instructions:**
   - Download APK
   - Settings → Security → Enable "Unknown Sources"
   - Install app
4. **Open app** - it shows live weather!

---

## PART 7: Release Updates (When You Update App)

### Step 13: Upload New Version
1. Build new APK (repeat Step 7)
2. Go to Firebase → App Distribution
3. Click **Upload new release**
4. Select new APK file
5. Add release notes about what's new
6. Select same testers/group
7. Click **Distribute**

### Step 14: Automatic Notifications
- Testers get email about new version
- Existing users see in-app update prompt
- They tap to download and install

---

## Troubleshooting

### "Package name mismatch" error?
Make sure you enter exactly: `com.aistudio.myanmarweather.wfptv`

### Can't find google-services.json?
- Must place in `app/` folder (same level as `build.gradle.kts`)
- Not in root folder, not in `src/` folder

### Build fails after adding Firebase?
In Android Studio menu: **File** → **Sync Project with Gradle Files**

### Testers can't install?
Tell them to:
1. Settings → Security → Unknown Sources → ON
2. Or: Settings → Apps → Special Access → Install Unknown Apps → Browser → Allow

### "App not installed" error?
- Uninstall old version first
- Or: Different APK signature (debug vs release)

---

## Summary

| Step | What You Do | Time |
|------|-------------|------|
| 1-2 | Create Firebase project | 2 min |
| 3-4 | Add Android app | 3 min |
| 5-6 | Enable App Distribution | 2 min |
| 7 | Build APK | 5 min |
| 8-10 | Upload & invite testers | 3 min |
| 11-12 | Testers install | 2 min |

**Total setup time: ~15-20 minutes**

---

## Next Steps

Once working, you can:
- Add up to 200 testers (free)
- Enable in-app feedback
- View crash analytics
- Track download statistics

**Questions?** Check Firebase documentation: https://firebase.google.com/docs/app-distribution
