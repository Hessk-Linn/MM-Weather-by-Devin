# MonsoonAlert Myanmar

A professional weather application for Myanmar with comprehensive coverage of all states and regions, real-time weather alerts, and monsoon tracking.

## Features

- **Complete Myanmar Coverage**: Weather data for 15+ states and regions, 100+ cities and townships
- **Free Distribution**: No Play Store required - share APK directly or via Firebase/GitHub
- **Real-time Weather**: Current conditions, 5-day forecasts, and hourly updates
- **GPS Location**: Automatic weather detection based on your current location
- **Monsoon Alerts**: Severe weather warnings and advisory notifications
- **Offline Support**: Cached data for offline access
- **Background Sync**: Automatic weather updates via WorkManager
- **Beautiful UI**: Modern Material Design 3 with dark monsoon theme

## Supported Regions

- Yangon Region (57 townships - complete coverage)
- Mandalay Region (14 cities)
- Naypyidaw Union Territory
- Ayeyarwady Region
- Bago Region
- Mon State
- Tanintharyi Region
- Shan State
- Kachin State
- Kayin State
- Kayah State
- Rakhine State
- Chin State
- Sagaing Region
- Magway Region

## Prerequisites

- Android Studio Ladybug (2024.2.1) or newer
- JDK 21 or newer
- Android SDK API 36
- OpenWeatherMap API Key (free tier works)

## Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/monsoonalert-myanmar.git
   cd monsoonalert-myanmar
   ```

2. **Get OpenWeatherMap API Key**
   - Sign up at [OpenWeatherMap](https://openweathermap.org/api)
   - Navigate to API keys section
   - Copy your free API key

3. **Configure API Key**
   ```bash
   # Create .env file from example
   cp .env.example .env
   
   # Edit .env and add your API key
   OPENWEATHER_API_KEY=your_actual_api_key_here
   ```

4. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Choose the project directory
   - Let Gradle sync complete

5. **Build and Run**
   - Select a device or emulator (API 24+ required)
   - Click Run ▶️

## Architecture

- **MVVM Pattern**: ViewModel + Repository + Data layer
- **Jetpack Compose**: Modern declarative UI
- **Material Design 3**: Latest Material components
- **Coroutines + Flow**: Reactive programming
- **WorkManager**: Background weather sync
- **Retrofit**: Network API calls
- **DataStore**: User preferences
- **Location Services**: GPS-based weather detection

## Project Structure

```
app/src/main/java/com/monsoonalert/myanmar/
├── MainActivity.kt              # Entry point
├── data/
│   ├── location/               # GPS location services
│   ├── model/                  # Data models (MyanmarLocations)
│   ├── remote/                 # API service and DTOs
│   └── repository/             # Data repository
├── ui/
│   ├── screens/                # Composable screens
│   ├── theme/                  # Colors, typography, theme
│   └── WeatherViewModel.kt     # Main ViewModel
└── worker/                     # Background sync workers
```

## API Key Security

The app uses the Secrets Gradle Plugin to securely inject API keys at build time. Never commit your `.env` file to version control.

## Building & Distribution (No Play Store Required)

### Quick Build
```bash
# On Windows, just double-click:
build-apk.bat

# Or manually:
./gradlew assembleDebug    # Debug APK for testing
./gradlew assembleRelease  # Release APK for distribution
```

APK locations:
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

### Distribution Options (All Free)

**1. Direct APK Sharing** (Easiest)
- Share via WhatsApp, Email, Google Drive
- Users enable "Unknown Sources" and install

**2. Firebase App Distribution** (Recommended)
- Free hosting for up to 200 testers
- Automatic updates, invite links
- See [DISTRIBUTION.md](DISTRIBUTION.md) for setup

**3. GitHub Releases** (100% Free)
- Unlimited public downloads
- Perfect for open source distribution
- Automatic via GitHub Actions workflow

See [DISTRIBUTION.md](DISTRIBUTION.md) for detailed instructions.

### Building Signed Release

```bash
# Create keystore (one-time)
keytool -genkey -v -keystore monsoonalert.keystore -alias monsoonalert \
  -keyalg RSA -keysize 2048 -validity 10000

# Build signed APK
./gradlew assembleRelease

# Or use Android Studio
Build → Generate Signed Bundle/APK
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## License

MIT License - see LICENSE file for details

## Acknowledgments

- Weather data provided by [OpenWeatherMap](https://openweathermap.org)
- Icons by [Material Design](https://material.io/icons)
