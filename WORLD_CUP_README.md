# World Cup 2026 Android App

Ứng dụng Android hiển thị thông tin FIFA World Cup 2026 theo mô hình MVVM.

## Tính năng

### 🏠 Màn hình Home (Trang chủ)

- Hiển thị banner FIFA World Cup 2026
- Preview các trận đấu gần đây (4 trận)
- Preview bảng xếp hạng Group A

### ⚽ Màn hình Scores & Fixtures (Lịch thi đấu & Kết quả)

- Xem tất cả các trận đấu theo vòng (Round)
- Dropdown chọn vòng đấu (Group Stage Round 1, 2, 3...)
- Nút Last/Next để chuyển vòng đấu
- Hiển thị:
  - Trận đã kết thúc: Thời gian, tỉ số, trạng thái "FT"
  - Trận chưa diễn ra: Thời gian, dấu "-" thay cho tỉ số

### 🏆 Màn hình Group Standings (Bảng xếp hạng)

- Xem bảng xếp hạng theo từng bảng (Group A, B, C, D)
- Chip selection để chọn bảng
- Hiển thị thông tin:
  - Vị trí (với màu sắc: 1st=Cam đậm, 2nd=Cam nhạt, 3rd=Vàng, 4th=Xám)
  - Tên đội & cờ quốc gia
  - Thống kê: P (Played), W/D/L (Win/Draw/Loss), GF/GA (Goals), Pts (Points)

## Kiến trúc MVVM

### Data Layer

- **Entities**: `Team`, `Match`, `Standing` (Room database)
- **DAOs**: `TeamDao`, `MatchDao`, `StandingDao`
- **Database**: `AppDatabase` (version 2 với fallback migration)
- **Repository**: `WorldCupRepositoryImpl`

### Domain Layer

- **Repository Interface**: `WorldCupRepository`
- **Use Cases**:
  - `GetAllMatchesUseCase`
  - `GetMatchesByRoundUseCase`
  - `GetAllRoundsUseCase`
  - `GetStandingsByGroupUseCase`
  - `GetAllGroupsUseCase`
  - `GetTeamByIdUseCase`
  - `InitializeSampleDataUseCase`
- **Container**: `WorldCupUseCases`

### Presentation Layer (UI)

- **ViewModels**:
  - `WorldCupHomeViewModel`
  - `ScoresFixturesViewModel`
  - `StandingsViewModel`
- **Fragments**:
  - `FragmentWorldCupHome`
  - `FragmentScoresFixtures`
  - `FragmentStandings`
- **Adapters**:
  - `MatchAdapter` (RecyclerView cho danh sách trận đấu)
  - `StandingAdapter` (RecyclerView cho bảng xếp hạng)
- **Activity**: `ActivityWorldCup` (với ViewPager2 và BottomNavigationView)

## Dependency Injection (Hilt)

- `DatabaseModule`: Provide DAOs
- `DataModule`: Provide Repository
- `UseCaseModule`: Provide Use Cases container

## Dữ liệu mẫu

App tự động khởi tạo dữ liệu mẫu khi chạy lần đầu:

- 16 đội bóng (4 bảng: A, B, C, D)
- 12 trận đấu (6 trận đã kết thúc, 6 trận sắp diễn ra)
- Bảng xếp hạng cho 4 bảng

## Công nghệ sử dụng

- **Kotlin**: Ngôn ngữ lập trình
- **Room Database**: Local storage
- **Hilt**: Dependency injection
- **ViewBinding**: View access
- **Kotlin Coroutines & Flow**: Async programming
- **Glide**: Load ảnh cờ quốc gia
- **Material Components**: BottomNavigationView, ChipGroup
- **ViewPager2**: Swipe giữa các tab
- **RecyclerView**: Danh sách trận đấu và bảng xếp hạng

## Cách chạy

1. Open project trong Android Studio
2. Sync Gradle
3. Run app
4. Từ Home, tap vào button "World Cup 2026"
5. Navigate giữa các tab: Home, Fixtures, Standings

## Màu sắc chính

- Primary: `#EE4173` (Hồng)
- Position 1st: `#EC6613` (Cam đậm)
- Position 2nd: `#EE9862` (Cam nhạt)
- Position 3rd: `#F5C551` (Vàng)
- Text: `#000000` (Đen)
- Background: `#FFFFFF` (Trắng)

## API cờ quốc gia

Sử dụng flagcdn.com để load ảnh cờ:

```
https://flagcdn.com/w40/{country_code}.png
```

## Ghi chú

- UI được thiết kế đơn giản, dễ sử dụng
- Dữ liệu được lưu local với Room Database
- Support Dark Mode (có thể extend thêm)
- Responsive layout với ConstraintLayout và LinearLayout
