# 🏆 Hướng dẫn sử dụng World Cup 2026 App

## Cách chạy app

1. **Mở Android Studio** và load project `world_cup`
2. **Sync Gradle** (Build > Sync Project with Gradle Files)
3. **Chạy app** trên thiết bị/emulator
4. Tap vào nút **"World Cup 2026"** màu hồng ở màn hình Home

## 3 Màn hình chính

### 🏠 **Home (Trang chủ)**

- Banner FIFA World Cup 2026
- 4 trận đấu gần đây nhất
- Bảng xếp hạng Group A (preview)

### ⚽ **Scores & Fixtures (Lịch thi đấu)**

- Dropdown chọn vòng đấu (Group Stage Round 1, 2...)
- Nút **Last** / **Next** để chuyển vòng
- **Trận đã đá**: Hiển thị tỉ số + "FT"
- **Trận chưa đá**: Chỉ hiển thị thời gian + dấu "-"

### 🏆 **Group Standings (Bảng xếp hạng)**

- Chip để chọn bảng (Group A, B, C, D)
- Thông tin chi tiết:
  - **P**: Số trận đã đá
  - **W/D/L**: Thắng/Hòa/Thua
  - **GF/GA**: Bàn thắng ghi/bàn thắng thủng lưới
  - **Pts**: Điểm số
- Màu sắc theo vị trí:
  - 🥇 Vị trí 1: Cam đậm
  - 🥈 Vị trí 2: Cam nhạt
  - 🥉 Vị trí 3: Vàng
  - Vị trí 4: Xám

## Dữ liệu mẫu

App tự động tạo dữ liệu mẫu lần đầu chạy:

- **16 đội** từ 4 bảng
- **12 trận đấu** (6 đã kết thúc, 6 sắp diễn ra)
- **Bảng xếp hạng** đầy đủ cho 4 bảng

## Kiến trúc MVVM

```
📁 Data Layer
   ├── Entity (Team, Match, Standing)
   ├── DAO (TeamDao, MatchDao, StandingDao)
   └── Repository (WorldCupRepositoryImpl)

📁 Domain Layer
   ├── Repository Interface
   └── Use Cases (8 use cases)

📁 Presentation Layer
   ├── ViewModel (3 ViewModels với StateFlow)
   ├── Fragment (3 Fragments)
   └── Adapter (2 RecyclerView Adapters)
```

## Tech Stack

- ✅ Kotlin
- ✅ Room Database (local storage)
- ✅ Hilt (Dependency Injection)
- ✅ ViewBinding
- ✅ Coroutines + Flow
- ✅ Glide (load ảnh cờ)
- ✅ Material Components
- ✅ ViewPager2 + BottomNavigationView

## Lưu ý

- Coin API cũ để tham khảo pattern MVVM, không ảnh hưởng World Cup
- World Cup dùng database local, không cần internet (chỉ cần để load ảnh cờ)
- Build thành công ✅
- Sẵn sàng chạy! 🚀

## Nếu có lỗi

### Lỗi build

```bash
./gradlew clean
./gradlew assembleDebug
```

### Lỗi database

- Uninstall app và chạy lại (để khởi tạo lại database)

### Lỗi Hilt

- Rebuild project (Build > Rebuild Project)

## Tùy chỉnh

### Thêm đội bóng mới

Chỉnh `WorldCupRepositoryImpl.kt` > `initializeSampleData()` > thêm teams

### Thêm trận đấu

Chỉnh `WorldCupRepositoryImpl.kt` > `initializeSampleData()` > thêm matches

### Đổi màu sắc

Chỉnh `res/values/colors.xml` và `res/color/bottom_nav_color.xml`

---

**Chúc bạn code vui! ⚽🏆**
