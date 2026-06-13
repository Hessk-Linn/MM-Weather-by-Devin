package com.monsoonalert.myanmar.data.model

// Comprehensive Myanmar cities and townships data
// Organized by State/Region for professional weather coverage

data class MyanmarLocation(
    val id: Int,
    val nameEn: String,
    val nameMy: String,
    val stateRegion: String,
    val district: String,
    val latitude: Double,
    val longitude: Double,
    val isMajorCity: Boolean = false,
    val population: Int = 0
)

object MyanmarLocations {
    
    // Yangon Region - 57 townships/cities (complete coverage)
    private val yangonLocations = listOf(
        // Western District
        MyanmarLocation(1, "Kamayut", "ကမာရွတ်", "Yangon", "Western District", 16.8312, 96.1284),
        MyanmarLocation(2, "Bahan", "ဗဟန်း", "Yangon", "Western District", 16.8115, 96.1558),
        MyanmarLocation(3, "Sanchaung", "စမ်းချောင်း", "Yangon", "Western District", 16.8046, 96.1294),
        MyanmarLocation(4, "Hlaing", "လှိုင်", "Yangon", "Western District", 16.8450, 96.1260),
        MyanmarLocation(5, "Mayangone", "မရမ်းကုန်း", "Yangon", "Western District", 16.8687, 96.1432),
        MyanmarLocation(6, "Dagon", "ဒဂုံ", "Yangon", "Western District", 16.7937, 96.1481),
        MyanmarLocation(7, "Kyauktada", "ကျောက်တံတား", "Yangon", "Western District", 16.7725, 96.1598, isMajorCity = true, population = 150000),
        MyanmarLocation(8, "Pabedan", "ပန်းဘဲတန်း", "Yangon", "Western District", 16.7766, 96.1561),
        MyanmarLocation(9, "Latha", "လသာ", "Yangon", "Western District", 16.7778, 96.1497),
        MyanmarLocation(10, "Lanmadaw", "လမ်းမတော်", "Yangon", "Western District", 16.7797, 96.1428),
        MyanmarLocation(11, "Ahlone", "အလုံ", "Yangon", "Western District", 16.7865, 96.1278),
        MyanmarLocation(12, "Kyeemyindaing", "ကြည့်မြင်တိုင်", "Yangon", "Western District", 16.8016, 96.1152),
        
        // Eastern District
        MyanmarLocation(13, "Yankin", "ရန်ကင်း", "Yangon", "Eastern District", 16.8372, 96.1601),
        MyanmarLocation(14, "Pazundaung", "ပုဇွန်တောင်", "Yangon", "Eastern District", 16.7869, 96.1738),
        MyanmarLocation(15, "Botataung", "ဗိုလ်တထောင်", "Yangon", "Eastern District", 16.7816, 96.1702),
        MyanmarLocation(16, "Mingala Taungnyunt", "မင်္ဂလာတောင်ညွန့်", "Yangon", "Eastern District", 16.7909, 96.1672),
        MyanmarLocation(17, "Tamwe", "တာမွေ", "Yangon", "Eastern District", 16.8025, 96.1675),
        MyanmarLocation(18, "Thingangyun", "သင်္ဃန်းကျွန်း", "Yangon", "Eastern District", 16.8198, 96.1953),
        MyanmarLocation(19, "South Okkalapa", "တောင်ဥက္ကလာပ", "Yangon", "Eastern District", 16.8378, 96.1963),
        MyanmarLocation(20, "North Okkalapa", "မြောက်ဥက္ကလာပ", "Yangon", "Eastern District", 16.8924, 96.1843),
        MyanmarLocation(21, "Thaketa", "သာကေတ", "Yangon", "Eastern District", 16.7951, 96.2081),
        MyanmarLocation(22, "Dawbon", "ဒေါပုံ", "Yangon", "Eastern District", 16.7844, 96.1942),
        
        // Northern District
        MyanmarLocation(23, "Insein", "အင်းစိန်", "Yangon", "Northern District", 16.8833, 96.1100),
        MyanmarLocation(24, "Mingaladon", "မင်္ဂလာဒုံ", "Yangon", "Northern District", 16.9234, 96.1301),
        MyanmarLocation(25, "Shwepyitha", "ရွှေပြည်သာ", "Yangon", "Northern District", 16.9610, 96.0963),
        MyanmarLocation(26, "Hlaingthaya", "လှိုင်သာယာ", "Yangon", "Northern District", 16.8778, 96.0645, isMajorCity = true, population = 300000),
        MyanmarLocation(27, "North Dagon", "ဒဂုံမြောက်ပိုင်း", "Yangon", "Northern District", 16.8920, 96.1850),
        MyanmarLocation(28, "East Dagon", "ဒဂုံအရှေ့ပိုင်း", "Yangon", "Northern District", 16.9150, 96.2100),
        MyanmarLocation(29, "South Dagon", "ဒဂုံတောင်ပိုင်း", "Yangon", "Northern District", 16.8550, 96.1950),
        MyanmarLocation(30, "Dagon Seikkan", "ဒဂုံစီခုံ", "Yangon", "Northern District", 16.9050, 96.1750),
        
        // Southern District
        MyanmarLocation(31, "Thanlyin", "သန်လျင်", "Yangon", "Southern District", 16.7456, 96.2411, isMajorCity = true, population = 200000),
        MyanmarLocation(32, "Kyauktan", "ကျောက်တန်း", "Yangon", "Southern District", 16.6347, 96.3262),
        MyanmarLocation(33, "Kayan", "ကယန်း", "Yangon", "Southern District", 16.9011, 96.5512),
        MyanmarLocation(34, "Thongwa", "သုံးခွ", "Yangon", "Southern District", 16.6610, 96.5186),
        MyanmarLocation(35, "Twante", "တွံတေး", "Yangon", "Southern District", 16.7112, 95.9621),
        MyanmarLocation(36, "Kawhmu", "ကော့မှူး", "Yangon", "Southern District", 16.5103, 96.0514),
        MyanmarLocation(37, "Kungyangon", "ကွမ်းခြံကုန်း", "Yangon", "Southern District", 16.4354, 95.9984),
        MyanmarLocation(38, "Cocokyun", "ကိုကိုးကျွန်း", "Yangon", "Southern District", 14.1167, 93.3667),
        MyanmarLocation(39, "Dala", "ဒလ", "Yangon", "Southern District", 16.7562, 96.1492),
        MyanmarLocation(40, "Seikkyi Kanaungto", "ဆိပ်ကြီးခနောင်တို", "Yangon", "Southern District", 16.7521, 96.1189),
        MyanmarLocation(41, "Tada", "သဒင်", "Yangon", "Southern District", 16.6850, 96.2050),
        MyanmarLocation(42, "Khayan", "ဘုရင့်နောင်", "Yangon", "Southern District", 16.8700, 96.5350),
        MyanmarLocation(43, "Syriam", "မြန်မာ့ရေနံမြေဒေသ", "Yangon", "Southern District", 16.7800, 96.2450),
        MyanmarLocation(44, "Tontay", "သုံးတောင်", "Yangon", "Southern District", 16.6200, 96.3500),
        
        // West Dagon - New Townships (added to Northern District area)
        MyanmarLocation(45, "West Dagon", "ဒဂုံအနောက်ပိုင်း", "Yangon", "Northern District", 16.8650, 96.1550),
        
        // New Town Development Areas
        MyanmarLocation(46, "Myaungdagar", "မြောငတက်ခ", "Yangon", "Northern District", 16.9500, 96.1200),
        MyanmarLocation(47, "Shwepyithar Industrial", "ရွှေပြည်သာစက်မှု", "Yangon", "Northern District", 16.9750, 96.0800),
        
        // Southern District - Small townships
        MyanmarLocation(48, "Kyaik Khauk", "ကျိုက်ခေါက်", "Yangon", "Southern District", 16.8167, 96.2333),
        MyanmarLocation(49, "Payargon", "ပရဂုံ", "Yangon", "Southern District", 16.7350, 96.2200),
        MyanmarLocation(50, "Thit Taw Thit", "သစ်တော်သစ်", "Yangon", "Southern District", 16.6250, 96.4150),
        MyanmarLocation(51, "Thandwe", "သန်ထွေ", "Yangon", "Southern District", 16.6150, 96.3800),
        
        // Western District - Additional areas
        MyanmarLocation(52, "Myoma", "မြို့မ", "Yangon", "Western District", 16.7750, 96.1500),
        MyanmarLocation(53, "Sinthe", "စဉ့်ထဲ", "Yangon", "Western District", 16.7600, 96.1550),
        
        // Eastern District - Additional areas
        MyanmarLocation(54, "Ngahtatgyi", "ငါးထပ်ကြီး", "Yangon", "Eastern District", 16.8000, 96.1750),
        MyanmarLocation(55, "Shwedagon", "ရွှေသာလျောင်း", "Yangon", "Eastern District", 16.8050, 96.1600),
        
        // Special Economic Zones
        MyanmarLocation(56, "Thilawa SEZ", "သီလဝါစီးပွားရေးဇုန်", "Yangon", "Southern District", 16.6200, 96.2600),
        
        // Yangon Central - Heart of the city
        MyanmarLocation(57, "Yangon Central", "ရန်ကုန်မြို့", "Yangon", "Capital", 16.8661, 96.1951, isMajorCity = true, population = 5500000)
    )
    
    // Mandalay Region - Major cities
    private val mandalayLocations = listOf(
        MyanmarLocation(101, "Mandalay", "မန္တလေးမြို့", "Mandalay", "Capital", 21.9813, 96.0823, isMajorCity = true, population = 1200000),
        MyanmarLocation(102, "Amarapura", "အမရပူရ", "Mandalay", "Southern", 21.9037, 96.0511, isMajorCity = true, population = 250000),
        MyanmarLocation(103, "Kyaukse", "ကျောက်ဆည်", "Mandalay", "Eastern", 21.6167, 96.1333, isMajorCity = true, population = 80000),
        MyanmarLocation(104, "Myitthar", "မြစ်သား", "Mandalay", "Northern", 21.4167, 95.9167),
        MyanmarLocation(105, "Sagaing", "စစ်ကိုင်း", "Mandalay", "Western", 21.8787, 95.9797, isMajorCity = true, population = 300000),
        MyanmarLocation(106, "Mogok", "မိုးကုတ်", "Mandalay", "Northern", 22.9176, 96.5096, isMajorCity = true, population = 170000),
        MyanmarLocation(107, "Pyin Oo Lwin", "ပြင်ဦးလွင်", "Mandalay", "Eastern", 22.0350, 96.4569, isMajorCity = true, population = 120000),
        MyanmarLocation(108, "Meiktila", "မိတ္ထီလာ", "Mandalay", "Southern", 20.8667, 95.8667, isMajorCity = true, population = 200000),
        MyanmarLocation(109, "Yamethin", "ရ်းသင်း", "Mandalay", "Southern", 20.4333, 96.1333),
        MyanmarLocation(110, "Thazi", "သာစည်", "Mandalay", "Southern", 20.8500, 96.0667),
        MyanmarLocation(111, "Madaya", "မဓပ်", "Mandalay", "Northern", 22.2000, 96.0833),
        MyanmarLocation(112, "Myingyan", "မြင်းခြံ", "Mandalay", "Western", 21.4667, 95.3833, isMajorCity = true, population = 150000),
        MyanmarLocation(113, "Nyaung-U", "ညောင်ဦး", "Mandalay", "Western", 21.2000, 94.9000, isMajorCity = true, population = 90000),
        MyanmarLocation(114, "Bagan", "ပုဂံ", "Mandalay", "Western", 21.1717, 94.8585, isMajorCity = true, population = 50000)
    )
    
    // Naypyidaw Union Territory
    private val naypyidawLocations = listOf(
        MyanmarLocation(201, "Naypyidaw", "နေပြည်တော်", "Naypyidaw", "Capital", 19.7633, 96.0785, isMajorCity = true, population = 925000),
        MyanmarLocation(202, "Zabuthiri", "ဇမ္ဗူသီရိ", "Naypyidaw", "North", 19.8500, 96.1000),
        MyanmarLocation(203, "Ottarathiri", "ဥတ္တရသီရိ", "Naypyidaw", "North", 19.8000, 96.0500),
        MyanmarLocation(204, "Dekkhinathiri", "ဒက္ခိဏသီရိ", "Naypyidaw", "South", 19.7000, 96.0833),
        MyanmarLocation(205, "Pyinmana", "ပျဉ်းမနား", "Naypyidaw", "South", 19.7333, 96.2167, isMajorCity = true, population = 100000),
        MyanmarLocation(206, "Tatkon", "တပ်ကုန်း", "Naypyidaw", "North", 19.8667, 96.1167)
    )
    
    // Ayeyarwady Region
    private val ayeyarwadyLocations = listOf(
        MyanmarLocation(301, "Pathein", "ပုသိမ်", "Ayeyarwady", "Capital", 16.7833, 94.7333, isMajorCity = true, population = 287000),
        MyanmarLocation(302, "Hinthada", "ဟင်္သာတ", "Ayeyarwady", "Northern", 17.6500, 95.4667, isMajorCity = true, population = 180000),
        MyanmarLocation(303, "Maubin", "မြောင်း", "Ayeyarwady", "Central", 16.5167, 95.6500, isMajorCity = true, population = 100000),
        MyanmarLocation(304, "Myaungmya", "မြောင်းမြ", "Ayeyarwady", "Central", 16.6000, 94.9167, isMajorCity = true, population = 80000),
        MyanmarLocation(305, "Labutta", "လပွတ္တာ", "Ayeyarwady", "Southern", 16.1667, 94.7500, isMajorCity = true, population = 70000),
        MyanmarLocation(306, "Mawlamyinegyun", "မော်လမြိုင်ကျွန်း", "Ayeyarwady", "Southern", 16.3833, 95.2667),
        MyanmarLocation(307, "Ngwesaung", "ငွေဆောင်", "Ayeyarwady", "Western", 16.8583, 94.3806),
        MyanmarLocation(308, "Chaungtha", "ချောင်းသာ", "Ayeyarwady", "Western", 17.0667, 94.4833),
        MyanmarLocation(309, "Wakema", "ဝါးခhemas", "Ayeyarwady", "Central", 16.6000, 95.1833),
        MyanmarLocation(310, "Kyaunggon", "ကျောင်းကုန်း", "Ayeyarwady", "Central", 17.0833, 95.5333),
        MyanmarLocation(311, "Bogale", "ဘိုကြီး", "Ayeyarwady", "Southern", 16.2833, 95.4000),
        MyanmarLocation(312, "Yegyi", "ရေကျည်း", "Ayeyarwady", "Southern", 16.8333, 95.1833)
    )
    
    // Bago Region
    private val bagoLocations = listOf(
        MyanmarLocation(401, "Bago", "ပဲခူး", "Bago", "Capital", 17.3350, 96.4817, isMajorCity = true, population = 491000),
        MyanmarLocation(402, "Taungoo", "တောင်ငူ", "Bago", "Eastern", 18.9425, 96.4348, isMajorCity = true, population = 110000),
        MyanmarLocation(403, "Pyay", "ပြည်", "Bago", "Western", 18.8167, 95.2167, isMajorCity = true, population = 135000),
        MyanmarLocation(404, "Thayarwady", "သာယာဝတီ", "Bago", "Western", 17.6500, 95.2833, isMajorCity = true, population = 90000),
        MyanmarLocation(405, "Nyaunglebin", "ညောင်လေးပင်", "Bago", "Central", 17.9500, 96.7333, isMajorCity = true, population = 95000),
        MyanmarLocation(406, "Gyobingauk", "ကြိုးပင်းကောက်", "Bago", "Western", 18.2167, 95.6500),
        MyanmarLocation(407, "Waw", "ဝေါ", "Bago", "Eastern", 17.4500, 96.8000),
        MyanmarLocation(408, "Letpadan", "လက်ပတန်း", "Bago", "Western", 17.7833, 95.7333),
        MyanmarLocation(409, "Monyo", "မိုးညို", "Bago", "Central", 17.3667, 95.5500),
        MyanmarLocation(410, "Kawa", "ကဝ", "Bago", "Eastern", 17.0833, 96.8667),
        MyanmarLocation(411, "Thanatpin", "သနပ်ပင်", "Bago", "Eastern", 17.3000, 96.5833)
    )
    
    // Mon State
    private val monLocations = listOf(
        MyanmarLocation(501, "Mawlamyine", "မော်လမြိုင်", "Mon", "Capital", 16.4905, 97.6282, isMajorCity = true, population = 289000),
        MyanmarLocation(502, "Thaton", "သထုံ", "Mon", "Northern", 16.9333, 97.3667, isMajorCity = true, population = 125000),
        MyanmarLocation(503, "Mudon", "မုဒုံ", "Mon", "Southern", 16.2500, 97.7167, isMajorCity = true, population = 85000),
        MyanmarLocation(504, "Ye", "ရေး", "Mon", "Southern", 15.2500, 97.8667, isMajorCity = true, population = 70000),
        MyanmarLocation(505, "Kyaikto", "ကျိုက်ထို", "Mon", "Northern", 17.3000, 97.0167),
        MyanmarLocation(506, "Thanbyuzayat", "သံဖြူဇရပ်", "Mon", "Southern", 15.9667, 97.7333),
        MyanmarLocation(507, "Kyaikmaraw", "ကျိုက်မရော", "Mon", "Central", 16.3667, 97.7333),
        MyanmarLocation(508, "Paung", "ပေါင်", "Mon", "Central", 16.6333, 97.6167)
    )
    
    // Tanintharyi Region
    private val tanintharyiLocations = listOf(
        MyanmarLocation(601, "Dawei", "ထားဝယ်", "Tanintharyi", "Capital", 14.0833, 98.2000, isMajorCity = true, population = 146000),
        MyanmarLocation(602, "Myeik", "မြိတ်", "Tanintharyi", "Southern", 12.4333, 98.6000, isMajorCity = true, population = 177000),
        MyanmarLocation(603, "Kawthoung", "ကော့သောင်း", "Tanintharyi", "Southern", 10.0000, 98.5500, isMajorCity = true, population = 65000),
        MyanmarLocation(604, "Thayetchaung", "သရက်ချောင်", "Tanintharyi", "Central", 13.6667, 98.2333),
        MyanmarLocation(605, "Bokpyin", "ဘုတ်ပြင်း", "Tanintharyi", "Southern", 11.2833, 98.7667),
        MyanmarLocation(606, "Palauk", "ပလောက်", "Tanintharyi", "Southern", 11.0833, 98.7833),
        MyanmarLocation(607, "Tanintharyi Town", "တနင်္သာရီမြို့", "Tanintharyi", "Central", 12.0833, 99.0333),
        MyanmarLocation(608, "Palauk", "ပုလော", "Tanintharyi", "Northern", 13.0667, 98.6667),
        MyanmarLocation(609, "Launglon", "လောင်းလုံ", "Tanintharyi", "Central", 14.0833, 98.0833)
    )
    
    // Shan State (East, North, South)
    private val shanLocations = listOf(
        MyanmarLocation(701, "Taunggyi", "တောင်ကြီး", "Shan", "Capital", 20.7836, 97.0354, isMajorCity = true, population = 264000),
        MyanmarLocation(702, "Kyaing Tong", "ကျိုင်းတုံ", "Shan", "Eastern", 21.2833, 99.6167, isMajorCity = true, population = 172000),
        MyanmarLocation(703, "Mong La", "မိုင်းလား", "Shan", "Eastern", 21.6833, 100.0833, isMajorCity = true, population = 50000),
        MyanmarLocation(704, "Tachileik", "တာချီလိတ်", "Shan", "Eastern", 20.4500, 99.8833, isMajorCity = true, population = 60000),
        MyanmarLocation(705, "Lashio", "လားရှိုး", "Shan", "Northern", 22.9667, 97.7500, isMajorCity = true, population = 130000),
        MyanmarLocation(706, "Mu Se", "မူဆယ်", "Shan", "Northern", 23.9667, 97.8500, isMajorCity = true, population = 55000),
        MyanmarLocation(707, "Kunlong", "ကွမ်းလုံ", "Shan", "Northern", 23.4167, 98.0833),
        MyanmarLocation(708, "Laukkaing", "လောက်ကိုင်", "Shan", "Northern", 23.6500, 98.0833, isMajorCity = true, population = 45000),
        MyanmarLocation(709, "Hsenwi", "သိန္နိဝီ", "Shan", "Northern", 23.6167, 97.2667),
        MyanmarLocation(710, "Nawnghkio", "နောင်ခို", "Shan", "Northern", 21.9667, 96.8167),
        MyanmarLocation(711, "Kalaw", "ကလော့", "Shan", "Southern", 20.6333, 96.5667, isMajorCity = true, population = 70000),
        MyanmarLocation(712, "Pindaya", "ပင်းတယား", "Shan", "Southern", 20.9333, 96.6500),
        MyanmarLocation(713, "Yatsauk", "ရပ်စောက်", "Shan", "Southern", 20.6167, 96.6167),
        MyanmarLocation(714, "Hsi Hseng", "ဆီဆီး", "Shan", "Southern", 21.2667, 97.2500),
        MyanmarLocation(715, "Loikaw", "လွိုင်ကော်", "Kayah", "Capital", 19.6833, 97.2167, isMajorCity = true, population = 50000),
        MyanmarLocation(716, "Demoso", "ဒီမော့ဆို", "Kayah", "Central", 19.8667, 97.0333)
    )
    
    // Kachin State
    private val kachinLocations = listOf(
        MyanmarLocation(801, "Myitkyina", "မြစ်ကြီးနား", "Kachin", "Capital", 25.3833, 97.4000, isMajorCity = true, population = 243000),
        MyanmarLocation(802, "Bhamo", "ဘားမော်", "Kachin", "Central", 24.2667, 97.2333, isMajorCity = true, population = 90000),
        MyanmarLocation(803, "Putao", "ပူတာအို", "Kachin", "Northern", 27.3333, 97.4167, isMajorCity = true, population = 25000),
        MyanmarLocation(804, "Hpakant", "ဖားကန့်", "Kachin", "Central", 25.6167, 96.3000, isMajorCity = true, population = 300000),
        MyanmarLocation(805, "Mogaung", "မိုးကောင်", "Kachin", "Central", 25.3000, 96.9333),
        MyanmarLocation(806, "Chipwi", "ချီဖွီ", "Kachin", "Northern", 25.9000, 98.1333),
        MyanmarLocation(807, "Tanai", "တနိုင်းမြို့", "Kachin", "Central", 26.3500, 96.7333)
    )
    
    // Kayin State
    private val kayinLocations = listOf(
        MyanmarLocation(901, "Hpa-an", "ဘားအံ", "Kayin", "Capital", 16.8833, 97.6333, isMajorCity = true, population = 75000),
        MyanmarLocation(902, "Myawaddy", "မြဝတီ", "Kayin", "Eastern", 16.6833, 98.5167, isMajorCity = true, population = 65000),
        MyanmarLocation(903, "Kawkareik", "ကော့ကရိတ်", "Kayin", "Central", 16.5667, 98.2333, isMajorCity = true, population = 50000),
        MyanmarLocation(904, "Thandaunggyi", "သံတောင်ကြီး", "Kayin", "Northern", 19.0667, 96.5333),
        MyanmarLocation(905, "Hlaingbwe", "လှိုင်းဘွဲ", "Kayin", "Central", 17.3500, 97.1667),
        MyanmarLocation(906, "Papun", "ဖာပွန်", "Kayin", "Northern", 18.0667, 97.4500)
    )
    
    // Rakhine State
    private val rakhineLocations = listOf(
        MyanmarLocation(1001, "Sittwe", "စစ်တွေ", "Rakhine", "Capital", 20.1500, 92.9000, isMajorCity = true, population = 148000),
        MyanmarLocation(1002, "Kyaukpyu", "ကျောက်ဖြူ", "Rakhine", "Southern", 19.4333, 93.5500, isMajorCity = true, population = 45000),
        MyanmarLocation(1003, "Mrauk-U", "မြောက်ဦး", "Rakhine", "Northern", 20.6000, 93.2000, isMajorCity = true, population = 36000),
        MyanmarLocation(1004, "Maungdaw", "မောင်တော", "Rakhine", "Northern", 20.4333, 92.3333, isMajorCity = true, population = 55000),
        MyanmarLocation(1005, "Buthidaung", "ဘသီးတောင်", "Rakhine", "Northern", 20.8667, 92.5333),
        MyanmarLocation(1006, "Thandwe", "သံတွဲ", "Rakhine", "Southern", 18.4667, 94.3667, isMajorCity = true, population = 40000),
        MyanmarLocation(1007, "Ngapali", "ငွေလီပင်", "Rakhine", "Southern", 18.4500, 94.0667),
        MyanmarLocation(1008, "Ramree", "ရမ်းရယ်", "Rakhine", "Southern", 19.0833, 93.8667),
        MyanmarLocation(1009, "Ann", "အမ်း", "Rakhine", "Central", 19.7833, 94.0333),
        MyanmarLocation(1010, "Pauktaw", "ပေါက်တော", "Rakhine", "Central", 20.1333, 93.0667)
    )
    
    // Chin State
    private val chinLocations = listOf(
        MyanmarLocation(1101, "Hakha", "ဟားခါး", "Chin", "Capital", 22.6500, 93.6167, isMajorCity = true, population = 20000),
        MyanmarLocation(1102, "Falam", "ဖလမ်း", "Chin", "Northern", 22.9167, 93.6833, isMajorCity = true, population = 25000),
        MyanmarLocation(1103, "Thantlang", "ထန်တလန်", "Chin", "Northern", 22.7333, 93.2167),
        MyanmarLocation(1104, "Tedim", "တီးတိန်", "Chin", "Northern", 23.3667, 93.6833, isMajorCity = true, population = 35000),
        MyanmarLocation(1105, "Mindat", "မင်းတပ်", "Chin", "Southern", 21.3667, 94.0500),
        MyanmarLocation(1106, "Kanpetlet", "ကံပိုင်လက်", "Chin", "Southern", 21.2000, 94.4667),
        MyanmarLocation(1107, "Matupi", "မတူပီ", "Chin", "Southern", 21.6000, 93.4500, isMajorCity = true, population = 30000),
        MyanmarLocation(1108, "Paletwa", "ပလက်ဝ", "Chin", "Southern", 21.3000, 92.8833)
    )
    
    // Sagaing Region
    private val sagaingLocations = listOf(
        MyanmarLocation(1201, "Sagaing City", "စစ်ကိုင်းမြို့", "Sagaing", "Capital", 21.8833, 95.9833, isMajorCity = true, population = 307000),
        MyanmarLocation(1202, "Monywa", "မုံရွာ", "Sagaing", "Central", 22.1167, 95.1333, isMajorCity = true, population = 207000),
        MyanmarLocation(1203, "Shwebo", "ရွှေဘို", "Sagaing", "Central", 22.5833, 95.7000, isMajorCity = true, population = 88000),
        MyanmarLocation(1204, "Kale", "ကလေး", "Sagaing", "Western", 23.0833, 94.0667, isMajorCity = true, population = 90000),
        MyanmarLocation(1205, "Kalaymyo", "ကလေးမြို", "Sagaing", "Western", 22.7500, 94.0500),
        MyanmarLocation(1206, "Tamu", "တမူး", "Sagaing", "Western", 23.9333, 94.3667),
        MyanmarLocation(1207, "Mawlaik", "မော်လိုက်", "Sagaing", "Northern", 27.0333, 95.5833),
        MyanmarLocation(1208, "Hkamti", "ခန္တီး", "Sagaing", "Northern", 26.0000, 95.7000),
        MyanmarLocation(1209, "Indaw", "အင်းတော်", "Sagaing", "Central", 24.2000, 96.1333),
        MyanmarLocation(1210, "Katha", "ကသာ", "Sagaing", "Northern", 24.1833, 96.3500, isMajorCity = true, population = 40000),
        MyanmarLocation(1211, "Yinmabin", "ရေနံချင်း", "Sagaing", "Southern", 22.1000, 94.8833),
        MyanmarLocation(1212, "Wuntho", "ဝန်းသို", "Sagaing", "Northern", 23.8833, 95.6833)
    )
    
    // Magway Region
    private val magwayLocations = listOf(
        MyanmarLocation(1301, "Magway", "မကွေး", "Magway", "Capital", 20.1500, 95.2333, isMajorCity = true, population = 90000),
        MyanmarLocation(1302, "Pakokku", "ပခုက္ကူ", "Magway", "Central", 21.3333, 95.1000, isMajorCity = true, population = 107000),
        MyanmarLocation(1303, "Minbu", "မင်းဘူး", "Magway", "Western", 20.1833, 94.8667, isMajorCity = true, population = 65000),
        MyanmarLocation(1304, "Thayet", "သရက်", "Magway", "Southern", 19.3167, 95.1833, isMajorCity = true, population = 45000),
        MyanmarLocation(1305, "Gangaw", "ဂန့်ဂါ", "Magway", "Western", 22.1667, 94.1167, isMajorCity = true, population = 40000),
        MyanmarLocation(1306, "Yenangyaung", "ရေနံချောင်း", "Magway", "Southern", 20.4667, 94.8667),
        MyanmarLocation(1307, "Chauk", "ချောက်", "Magway", "Southern", 20.8833, 94.8167),
        MyanmarLocation(1308, "Aunglan", "အောင်လံ", "Magway", "Central", 19.3667, 95.2333),
        MyanmarLocation(1309, "Natmauk", "နတ်မောက်", "Magway", "Southern", 20.3500, 95.4167),
        MyanmarLocation(1310, "Myothit", "မြို့သစ်", "Magway", "Central", 20.1333, 95.3500)
    )
    
    // Combine all locations
    val allLocations: List<MyanmarLocation> by lazy {
        yangonLocations + mandalayLocations + naypyidawLocations + ayeyarwadyLocations +
        bagoLocations + monLocations + tanintharyiLocations + shanLocations +
        kachinLocations + kayinLocations + rakhineLocations + chinLocations +
        sagaingLocations + magwayLocations
    }
    
    // Helper functions
    fun getAllLocations(): List<MyanmarLocation> = allLocations
    
    fun getLocationsByState(state: String): List<MyanmarLocation> {
        return allLocations.filter { it.stateRegion == state }
    }
    
    fun getMajorCities(): List<MyanmarLocation> {
        return allLocations.filter { it.isMajorCity }
    }
    
    fun getLocationById(id: Int): MyanmarLocation? {
        return allLocations.find { it.id == id }
    }
    
    fun searchLocations(query: String): List<MyanmarLocation> {
        val normalizedQuery = query.lowercase()
        return allLocations.filter { 
            it.nameEn.lowercase().contains(normalizedQuery) ||
            it.nameMy.contains(query) ||
            it.stateRegion.lowercase().contains(normalizedQuery)
        }
    }
    
    fun getStatesAndRegions(): List<String> {
        return allLocations.map { it.stateRegion }.distinct().sorted()
    }
    
    // Default location (Yangon Central)
    fun getDefaultLocation(): MyanmarLocation {
        return allLocations.find { it.nameEn == "Yangon Central" } ?: allLocations.first()
    }
}
 
