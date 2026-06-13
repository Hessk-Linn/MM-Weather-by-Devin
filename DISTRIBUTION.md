# Free APK Distribution Guide

Build and distribute your MonsoonAlert app without Google Play Store.

## Quick Start - 3 Options

### Option 1: Direct APK Sharing (Easiest)
Just build and share the APK file directly.

```bash
# Build debug APK
./gradlew assembleDebug

# APK location: app/build/outputs/apk/debug/app-debug.apk
```

Share via:
- Email attachment
- WhatsApp/Telegram
- Google Drive link
- Bluetooth file transfer

**User installs by:**
1. Enable "Unknown Sources" in Settings → Security
2. Tap APK file to install

---

### Option 2: Firebase App Distribution (Recommended)
Free hosting with invite-only access and automatic updates.

**Setup:**
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create new project "MonsoonAlert"
3. Add Android app with package: `com.aistudio.myanmarweather.wfptv`
4. Download `google-services.json` and place in `app/` folder
5. Enable App Distribution in Firebase console

**Build and Upload:**
```bash
# Build release APK
./gradlew assembleRelease

# Upload via Firebase CLI (install if needed: npm install -g firebase-tools)
firebase appdistribution:distribute app/build/outputs/apk/release/app-release.apk \
  --app YOUR_APP_ID \
  --groups testers \
  --release-notes "MonsoonAlert Myanmar Weather App"
```

**Benefits:**
- ✅ Free hosting (up to 200 testers)
- ✅ Automatic update notifications
- ✅ Download links via email
- ✅ Version tracking

---

### Option 3: GitHub Releases (100% Free)
Host APK on GitHub for unlimited public downloads.

**Steps:**
1. Push code to GitHub repository
2. Go to Repository → Releases → "Create a new release"
3. Tag version: `v1.0.0`
4. Title: "MonsoonAlert Myanmar 1.0.0"
5. Attach APK file
6. Publish release

**Benefits:**
- ✅ Completely free
- ✅ Unlimited downloads
- ✅ Version history
- ✅ Direct download links

---

## Building Signed Release APK

For production distribution, sign your APK:

### Create Keystore (One-time)
```bash
keytool -genkey -v -keystore monsoonalert.keystore -alias monsoonalert \
  -keyalg RSA -keysize 2048 -validity 10000
```

### Configure Signing
Create `keystore.properties`:
```properties
storeFile=monsoonalert.keystore
storePassword=YOUR_PASSWORD
keyAlias=monsoonalert
keyPassword=YOUR_PASSWORD
```

### Build Signed APK
```bash
./gradlew assembleRelease
```

Signed APK: `app/build/outputs/apk/release/app-release.apk`

---

## User Installation Instructions

### For Android Users:

1. **Download APK** from your shared link
2. **Enable Unknown Sources:**
   - Settings → Security → "Unknown Sources" → Enable
   - Or: Settings → Apps → Special Access → Install Unknown Apps
3. **Install:** Tap downloaded APK file
4. **Open:** Find "MonsoonAlert" in app drawer

### Troubleshooting:

**"Install blocked" error:**
- Go to Settings → Allow installation from this source

**"App not installed" error:**
- Uninstall existing version first
- Check if APK is corrupted (re-download)

**"Parse error":**
- APK was built for newer Android version
- Minimum supported: Android 7.0 (API 24)

---

## Automated Distribution (CI/CD)

Use GitHub Actions to build and distribute automatically:

See `.github/workflows/distribute.yml` for automated workflow.

---

## Cost Comparison

| Method | Cost | Tester Limit | Updates | Best For |
|--------|------|--------------|---------|----------|
| Direct APK | Free | Unlimited | Manual | Friends & family |
| Firebase App Distribution | Free | 200 testers | Automatic | Beta testing |
| GitHub Releases | Free | Unlimited | Manual | Open source/public |
| Play Store | $25 one-time | Unlimited | Automatic | Mass distribution |

---

## Recommended Workflow

**Development:**
1. Build debug APK for testing
2. Share with close testers

**Beta Testing:**
1. Use Firebase App Distribution
2. Invite 10-50 testers
3. Collect feedback

**Public Release:**
1. Create signed release APK
2. Upload to GitHub Releases
3. Share download link on social media
4. Consider Play Store for wider reach
