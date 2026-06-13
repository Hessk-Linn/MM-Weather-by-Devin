package com.example.data

object InitialData {
    val townships = listOf(
        Township(1, "Kamayut", "ကမာရွတ်", "Western District", 16.8312, 96.1284),
        Township(2, "Bahan", "ဗဟန်း", "Western District", 16.8115, 96.1558),
        Township(3, "Sanchaung", "စမ်းချောင်း", "Western District", 16.8046, 96.1294),
        Township(4, "Hlaing", "လှိုင်", "Western District", 16.8450, 96.1260),
        Township(5, "Mayangone", "မရမ်းကုန်း", "Western District", 16.8687, 96.1432),
        Township(6, "Yankin", "ရန်ကင်း", "Eastern District", 16.8372, 96.1601),
        Township(7, "Dagon", "ဒဂုံ", "Western District", 16.7937, 96.1481),
        Township(8, "Kyauktada", "ကျောက်တံတား", "Western District", 16.7725, 96.1598),
        Township(9, "Pabedan", "ပန်းဘဲတန်း", "Western District", 16.7766, 96.1561),
        Township(10, "Latha", "လသာ", "Western District", 16.7778, 96.1497),
        Township(11, "Lanmadaw", "လမ်းမတော်", "Western District", 16.7797, 96.1428),
        Township(12, "Ahlone", "အလုံ", "Western District", 16.7865, 96.1278),
        Township(13, "Kyeemyindaing", "ကြည့်မြင်တိုင်", "Western District", 16.8016, 96.1152),
        Township(14, "Pazundaung", "ပုဇွန်တောင်", "Eastern District", 16.7869, 96.1738),
        Township(15, "Botataung", "ဗိုလ်တထောင်", "Eastern District", 16.7816, 96.1702),
        Township(16, "Mingala Taungnyunt", "မင်္ဂလာတောင်ညွန့်", "Eastern District", 16.7909, 96.1672),
        Township(17, "Tamwe", "တာမွေ", "Eastern District", 16.8025, 96.1675),
        Township(18, "Thingangyun", "သင်္ဃန်းကျွန်း", "Eastern District", 16.8198, 96.1953),
        Township(19, "South Okkalapa", "တောင်ဥက္ကလာပ", "Eastern District", 16.8378, 96.1963),
        Township(20, "North Okkalapa", "မြောက်ဥက္ကလာပ", "Eastern District", 16.8924, 96.1843),
        Township(21, "Thaketa", "သာကေတ", "Eastern District", 16.7951, 96.2081),
        Township(22, "Dawbon", "ဒေါပုံ", "Eastern District", 16.7844, 96.1942),
        Township(23, "Insein", "အင်းစိန်", "Northern District", 16.8833, 96.1100),
        Township(24, "Mingaladon", "မင်္ဂလာဒုံ", "Northern District", 16.9234, 96.1301),
        Township(25, "Shwepyitha", "ရွှေပြည်သာ", "Northern District", 16.9610, 96.0963),
        Township(26, "Hlaingthaya", "လှိုင်သာယာ", "Northern District", 16.8778, 96.0645),
        Township(27, "Thanlyin", "သန်လျင်", "Southern District", 16.7456, 96.2411),
        Township(28, "Kyauktan", "ကျောက်တန်း", "Southern District", 16.6347, 96.3262),
        Township(29, "Kayan", "ကယန်း", "Southern District", 16.9011, 96.5512),
        Township(30, "Thongwa", "သုံးခွ", "Southern District", 16.6610, 96.5186),
        Township(31, "Twante", "တွံတေး", "Southern District", 16.7112, 95.9621),
        Township(32, "Kawhmu", "ကော့မှူး", "Southern District", 16.5103, 96.0514),
        Township(33, "Kungyangon", "ကွမ်းခြံကုန်း", "Southern District", 16.4354, 95.9984),
        Township(34, "Cocokyun", "ကိုကိုးကျွန်း", "Southern District", 14.1167, 93.3667),
        Township(35, "Dala", "ဒလ", "Southern District", 16.7562, 96.1492),
        Township(36, "Seikkyi Kanaungto", "ဆိပ်ကြီးခနောင်တို", "Southern District", 16.7521, 96.1189)
    )

    fun generateInitialWeather(timestamp: Long): List<WeatherData> {
        return listOf(
            WeatherData(1, 28.5, 32.4, 95, 28.0, 92, 45, "Good", "Monsoon Storm", timestamp),
            WeatherData(2, 29.0, 18.5, 80, 18.5, 88, 52, "Moderate", "Heavy Rain", timestamp),
            WeatherData(3, 28.0, 42.0, 99, 32.4, 95, 48, "Good", "Torrential Rain", timestamp),
            WeatherData(4, 29.5, 12.0, 75, 12.0, 85, 68, "Moderate", "Thunderstorm", timestamp),
            WeatherData(5, 27.5, 55.0, 100, 40.2, 98, 38, "Good", "Monsoon Storm", timestamp),
            WeatherData(6, 30.1, 8.5, 60, 10.5, 81, 75, "Moderate", "Cloudy", timestamp),
            WeatherData(7, 28.8, 22.0, 85, 20.0, 89, 58, "Moderate", "Heavy Rain", timestamp),
            WeatherData(8, 28.2, 38.0, 90, 26.5, 93, 85, "Moderate", "Torrential Rain", timestamp),
            WeatherData(9, 28.0, 40.5, 95, 30.0, 94, 98, "Moderate", "Torrential Rain", timestamp),
            WeatherData(10, 28.3, 35.2, 90, 24.5, 92, 88, "Moderate", "Torrential Rain", timestamp),
            WeatherData(11, 28.1, 38.8, 92, 27.0, 93, 82, "Moderate", "Torrential Rain", timestamp),
            WeatherData(12, 28.9, 15.4, 80, 16.0, 87, 44, "Good", "Heavy Rain", timestamp),
            WeatherData(13, 28.4, 29.2, 88, 22.4, 91, 52, "Moderate", "Heavy Rain", timestamp),
            WeatherData(14, 29.2, 10.2, 70, 11.5, 84, 78, "Moderate", "Thunderstorm", timestamp),
            WeatherData(15, 29.0, 14.8, 75, 14.0, 86, 80, "Moderate", "Thunderstorm", timestamp),
            WeatherData(16, 28.7, 26.5, 85, 21.0, 90, 92, "Moderate", "Heavy Rain", timestamp),
            WeatherData(17, 28.6, 28.0, 87, 23.0, 91, 86, "Moderate", "Heavy Rain", timestamp),
            WeatherData(18, 29.4, 11.5, 72, 13.0, 84, 64, "Moderate", "Cloudy", timestamp),
            WeatherData(19, 29.2, 14.2, 78, 15.0, 86, 58, "Moderate", "Heavy Rain", timestamp),
            WeatherData(20, 29.0, 24.5, 85, 22.0, 89, 48, "Good", "Heavy Rain", timestamp),
            WeatherData(21, 28.3, 36.4, 92, 28.5, 93, 40, "Good", "Torrential Rain", timestamp),
            WeatherData(22, 28.1, 35.0, 90, 26.0, 92, 45, "Good", "Torrential Rain", timestamp),
            WeatherData(23, 29.8, 6.4, 55, 9.5, 78, 55, "Moderate", "Cloudy", timestamp),
            WeatherData(24, 30.2, 4.2, 45, 8.0, 75, 52, "Moderate", "Fair", timestamp),
            WeatherData(25, 29.5, 12.5, 70, 11.0, 82, 60, "Moderate", "Cloudy", timestamp),
            WeatherData(26, 28.2, 48.0, 98, 36.0, 96, 115, "Unhealthy", "Monsoon Storm", timestamp),
            WeatherData(27, 28.0, 45.1, 95, 34.0, 94, 32, "Good", "Monsoon Storm", timestamp),
            WeatherData(28, 27.2, 62.0, 100, 42.5, 99, 25, "Good", "Monsoon Storm", timestamp),
            WeatherData(29, 27.8, 50.4, 97, 38.0, 96, 22, "Good", "Heavy Rain", timestamp),
            WeatherData(30, 27.5, 52.8, 98, 40.0, 97, 24, "Good", "Monsoon Storm", timestamp),
            WeatherData(31, 28.6, 21.4, 82, 19.5, 89, 28, "Good", "Heavy Rain", timestamp),
            WeatherData(32, 27.9, 39.5, 94, 29.0, 94, 18, "Good", "Torrential Rain", timestamp),
            WeatherData(33, 27.4, 58.0, 100, 41.0, 98, 15, "Good", "Monsoon Storm", timestamp),
            WeatherData(34, 26.8, 68.4, 100, 48.0, 99, 12, "Good", "Monsoon Storm", timestamp),
            WeatherData(35, 28.1, 41.2, 96, 31.0, 95, 56, "Moderate", "Torrential Rain", timestamp),
            WeatherData(36, 28.0, 43.0, 97, 32.5, 95, 50, "Good", "Torrential Rain", timestamp)
        )
    }

    fun generateInitialAlerts(timestamp: Long): List<MonsoonAlert> {
        return listOf(
            MonsoonAlert(
                id = 1,
                title = "Red Alert: Severe Monsoon Low-Pressure Area",
                townshipId = 0, // regional
                description = "Deep depression located over South Bay of Bengal is heading Northwest. Expect extreme torrential rain (exceeding 100mm/day), strong surface winds up to 55 km/h, and high storm surges. Immediate preparedness and evacuation of low-lying areas advised.",
                severity = "Extreme",
                isPushSimulated = false,
                timestamp = timestamp
            ),
            MonsoonAlert(
                id = 2,
                title = "Flash Flood & Waterlogging Warning",
                townshipId = 9, // Pabedan
                description = "Extreme water congestion depth estimated at 1.5 feet on major downtown arteries. Commuters should bypass Pabedan routes during high tide cycles between 15:00 and 18:00.",
                severity = "Severe",
                isPushSimulated = false,
                timestamp = timestamp - 3600000
            ),
            MonsoonAlert(
                id = 3,
                title = "Agricultural Downpour Notice (Kamayut & Bahan)",
                townshipId = 1, // Kamayut
                description = "High probability of consecutive 6-hour rain bands. Farmers are advised to secure irrigation bounds, postpose pesticide application, and ensure proper channel drainage to protect transplantings.",
                severity = "Moderate",
                isPushSimulated = false,
                timestamp = timestamp - 7200000
            ),
            MonsoonAlert(
                id = 4,
                title = "Severe Monsoon Alert for Coastal Suburbs",
                townshipId = 28, // Kyauktan
                description = "Extremely high tides paired with heavy inland runoffs. Kyauktan jetty operators must halt commercial small boat crossings. Coastal populations should monitor wind speeds closely.",
                severity = "Severe",
                isPushSimulated = false,
                timestamp = timestamp - 10800000
            )
        )
    }

    fun generateInitialTraffic(timestamp: Long): List<TrafficAlert> {
        return listOf(
            TrafficAlert(
                id = 1,
                townshipId = 1,
                title = "Severe Flooding at Hledan Junction",
                roadName = "Pyay Road (Hledan Junction intersection)",
                congestionLevel = "Blocked",
                floodingDepth = "Knee-deep",
                alternateRoute = "Use Baho Road or Insein Road via secondary bypass links.",
                timestamp = timestamp
            ),
            TrafficAlert(
                id = 2,
                townshipId = 9,
                title = "Downtown Waterlogging on Anawrahta Road",
                roadName = "Anawrahta Road (Pabedan block)",
                congestionLevel = "Heavy",
                floodingDepth = "Ankle-deep",
                alternateRoute = "Divert to Strand Road which has improved tidal drainage gates.",
                timestamp = timestamp - 1800000
            ),
            TrafficAlert(
                id = 3,
                townshipId = 2,
                title = "Congestion near Shwegondine Junction",
                roadName = "Kabar Aye Pagoda Road (underpass entry)",
                congestionLevel = "Heavy",
                floodingDepth = "None",
                alternateRoute = "Use Shwegondine Flyover upper lanes or West Bahan link roads.",
                timestamp = timestamp - 3600000
            ),
            TrafficAlert(
                id = 4,
                townshipId = 26,
                title = "Major Waterlogging near Hlaingthaya Bridge approach",
                roadName = "Yangon-Pathein Highway (bridge slipway)",
                congestionLevel = "Blocked",
                floodingDepth = "Severe Flooding",
                alternateRoute = "Delay transits or use FMI City inner avenues to access Pan Hlaing links.",
                timestamp = timestamp - 5400000
            ),
            TrafficAlert(
                id = 5,
                townshipId = 19,
                title = "Moderate Flooding on Waizayandar Road",
                roadName = "Waizayandar Road (near South Okkalapa Pagoda circular)",
                congestionLevel = "Moderate",
                floodingDepth = "Ankle-deep",
                alternateRoute = "Detour via Yadanar Road or Lay Daung Kan Road.",
                timestamp = timestamp - 7200000
            )
        )
    }

    fun generateHistoricalRainfall(): List<HistoricalRainfall> {
        val list = mutableListOf<HistoricalRainfall>()
        val months = listOf("May", "June", "July", "August", "September", "October")
        
        // 0 indicates representative average for General Yangon Region
        val yangonAvgRain = listOf(220.5, 540.2, 595.6, 610.1, 410.8, 185.0)
        val yangonAvgTemp = listOf(32.5, 28.7, 27.5, 27.4, 28.2, 30.1)
        
        for (i in months.indices) {
            list.add(HistoricalRainfall(townshipId = 0, monthName = months[i], averageRainfall = yangonAvgRain[i], averageTemp = yangonAvgTemp[i]))
        }

        // We can add simulated customized seasonal curves for other townships to give localized charts!
        for (townshipId in 1..36) {
            val townshipScale = 0.8 + (townshipId % 10) * 0.05 // slightly vary rainfall per township
            for (i in months.indices) {
                list.add(
                    HistoricalRainfall(
                        townshipId = townshipId,
                        monthName = months[i],
                        averageRainfall = (yangonAvgRain[i] * townshipScale).coerceAtLeast(50.0),
                        averageTemp = yangonAvgTemp[i] + (townshipId % 3 - 1) * 0.5
                    )
                )
            }
        }
        return list
    }
}
