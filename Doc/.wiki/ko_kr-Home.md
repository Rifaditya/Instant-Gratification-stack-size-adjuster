# Instant Gratification: Stack Size Adjuster Wiki

![Minecraft 26.2](https://img.shields.io/badge/Minecraft-26.2-brightgreen.svg) ![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.1+-blue.svg) ![License GPLv3](https://img.shields.io/badge/License-GPLv3-orange.svg) ![DasikLibrary](https://img.shields.io/badge/DasikLibrary-1.8.3-purple.svg)

**Instant Gratification: Stack Size Adjuster** 공식 백과사전 문서에 오신 것을 환영합니다. 이 Minecraft Fabric 모드는 서버 관리자와 플레이어가 세 가지 자연 범주(64개 스택, 16개 스택 및 스택 불가능 아이템)에 걸쳐 아이템 스택 크기를 NBT 팽창 없이 극단적인 수치까지 동적으로 구성할 수 있도록 지원하며, 보관함 드롭 최적화 및 오버플로 방지 기능을 제공합니다.

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 최신 미출시 커밋 또는 개발 중인 기능을 포함할 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 📦 Minecraft 버전 디렉터리

* [[Minecraft 26.2 가이드|ko_kr-Minecraft-26.2-Guide]] — Minecraft 26.2 공식 설치, 필수 종속성 및 설정 가이드.
* [[버전 호환성 매트릭스|ko_kr-Version-Compatibility]] — 지원 버전, `ModVersionGuard` 런타임 검사 및 종속성 범위.

---

## 🎮 플레이어 및 관리자 가이드

* [[동적 게임 규칙 참조|ko_kr-Dynamic-GameRules-Reference]] — 인게임 GameRules (`items_64_limit`, `items_16_limit`, `items_1_limit`, `max_drop_entities`).
* [[카테고리 기반 스택 제한|ko_kr-Category-Based-Stack-Limits]] — 64개, 16개 및 단일 아이템 스케일링 세부 사항.
* [[컨테이너 드롭 최적화|ko_kr-Container-Drop-Optimization]] — 엔티티 스폰 제한, 슬롯 분할 연산 및 보관함 파괴 시 렉 방지.
* [[대형 상자 오버플로 방지|ko_kr-Large-Chest-Overflow-Protection]] — 정수 오버플로 연산($39,768,215$ 안전 한계) 및 배정밀도 빠른 제작 로직.
* [[ModMenu 및 YACL 구성|ko_kr-ModMenu-and-YACL-Configuration]] — 메인 메뉴 GUI 설정(`stack-size-adjuster.json`) 및 YACL 3.9.5 화면 통합.
* [[아이템 수량 GUI 렌더링|ko_kr-Item-Count-GUI-Rendering]] — 다자리 스택 수량에 대한 동적 폰트 축소 알고리즘.
* [[Item Clumps 통합|ko_kr-Item-Clumps-Integration]] — Item Clumps 모드와의 바닥 아이템 병합 시너지.
* [[문제 해결 및 자주 묻는 질문|ko_kr-Troubleshooting-and-FAQ]] — 자주 묻는 질문, 오버플로 방지 및 서버-클라이언트 동기화.

---

## 💻 개발자 및 기술 참조

* [[개발자 설정 및 빌드 가이드|ko_kr-Developer-Setup-and-Building]] — JDK 25 환경, Gradle 9.3+, Loom 1.15+ 및 컴파일 워크플로.
* [[아키텍처 및 패키지 레이아웃|ko_kr-Architecture-and-Package-Layout]] — 시스템 아키텍처 트리, 패키지 구성 및 스레드 안전성 모델.
* [[Mixin 참조 및 훅|ko_kr-Mixin-Reference-and-Hooks]] — `Item`, `ItemStack`, `Container`, `GiveCommand`, `DataComponents` 주입 지점.
* [[애드온 재정의 API|ko_kr-Addon-Override-API]] — `StackSizeManager.registerOverride`를 통한 커스텀 스택 크기 등록.
* [[네트워크 동기화 프로토콜|ko_kr-Network-Sync-Protocol]] — S2C 패킷 `stack-size-adjuster:sync_limit` 및 클라이언트 메뉴 실시간 갱신.
* [[Give 명령어 처리|ko_kr-Give-Command-Handling]] — 대량 스택 아이템을 튕김 없이 처리하는 커스텀 `GiveCommandHelper`.
* [[동작 프로필 및 조건|ko_kr-Behavior-Profiles-and-Conditions]] — 동적 GameRule 동기화 상태 머신.
* [[소비자 모드 통합 가이드|ko_kr-Consumer-Mods-Integration-Guide]] — 서드파티 애드온 개발자를 위한 통합 가이드.
* [[성능 및 메모리 영향|ko_kr-Performance-and-Memory-Impact]] — NBT 팽창 제로 및 메모리 벤치마크.
* [[발전 과제 및 배지|ko_kr-Advancements-and-Badges]] — 발전 과제 매트릭스 및 바닐라 동등성 가이드.

---

## 📜 저작권 및 귀속

**Dasik (Rifaditya)** 개발 | **GNU General Public License v3.0 (GPLv3)** 라이선스.
