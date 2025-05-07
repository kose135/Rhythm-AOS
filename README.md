# Music Player Application (Android)

Rhythm is an Android music player app built on Jetpack Compose and Clean Architecture.</br>
It supports stable music playback and background services using Media3, and is structurally structured by applying the MVVM pattern and modern Android technology stacks such as Hilt, Room, and Retrofit.

# Preview

### Splash

### Main

#### Tracks, Home, Storage Page

#### Player, Track, Lyrics Page (Bottom)

#### Track Detail Page (Side)

When music is playing, a small playback bar appears.


# Page Flow

<img src="/doc/view_flow.jpg"/>

# Package Structure

```
rhythm
├── data                 # 데이터 계층 - Room(local), Retrofit(remote), RepositoryImpl
├── di                   # Hilt를 통한 의존성 주입 설정 - App, Network, Database, UseCase, Repository
├── domain               # 도메인 계층 - UseCase, Repository Interface
├── presentation         # UI 및 State 계층 - Compose 기반 화면 및 컴포넌트, ViewModel
│   ├── navigation       # 화면 간 네비게이션 그래프 및 라우트 정의
│   ├── screen           # 화면 구성 요소 - Screen, Page, Section
│   ├── theme            # 앱 전체에 적용되는 테마, 색상, 타이포그래피 정의
│   ├── viewmodel        # 각 화면에 대응되는 ViewModel 클래스 - 상태 관리 및 유즈케이스 호출
│   └── MainActivity.kt  # 앱의 엔트리포인트 - NavigationHost 및 전체 UI 컴포지션 루트
├── service              # 백그라운드 재생을 위한 서비스 - RhythmService, Notification
│   └── player           # 음악 재생 로직 - MusicPlayer, MusicPlayerManager, MediaSource 설정 등
├── util                 # 유틸리티 클래스 및 확장 함수
└── RhythmApplication.kt # 애플리케이션 클래스 - Hilt 초기화 및 전체 앱 설정
```

# Architecture

<img src="/doc/architecture.jpg"/>

# API

The backend of this project is built as a simple server based on the Koa framework, and communicates with clients via a RESTful API.

### APIs used in this project

#### Track APIs
```
GET (Track List)
/tracks?page={page}&offset={offset}

GET (Recommended Track List)
/track/recommended?limit={limit}

GET (Track's Music List)
/track/{trackId}/music

GET (Track List by IDs)
/track?ids={id1,id2,...}
```

#### Music APIs
```
GET (Music Info)
/music/{musicId}

GET (Best Music List)
/music/best?limit={limit}

GET (Music List by IDs)
/music?ids={id1,id2,...}
```


# License

```xml
Designed and developed by 2025 kose135 (JongGeun Lim)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```