# 아키텍처 및 패키지 레이아웃

## 1 파일 1 목적 원칙

Stack Size Adjuster는 단일 책임 원칙을 엄격히 준수합니다. 각 클래스는 네트워크 동기화, 설정 관리, 드롭 분할, 렌더링 등 하나의 고유한 기능에 집중합니다.

---

## 🌳 ASCII 패키지 계층 구조

```text
src/main/java/net/instantgratification/stacksizeadjuster/
├── StackSizeAdjusterFabric.java       # Fabric 엔트리포인트 & GameRules 등록
├── StackSizeAdjusterFabricClient.java # 클라이언트 엔트리포인트 & S2C 패킷 수신기
├── config/
│   ├── ModMenuIntegration.java        # ModMenu API 통합 엔트리포인트
│   ├── StackSizeConfig.java           # JSON 구성 컨테이너 (config/stack-size-adjuster.json)
│   └── YaclScreenHelper.java          # YACL 3.9.5 리플렉션 화면 빌더
├── mixin/
│   ├── AbstractContainerMenuMixin.java# 배정밀도 빠른 제작 오버플로 방지 연산
│   ├── ContainerMixin.java            # 컨테이너 슬롯 스택 상한 오버라이드 (Integer.MAX_VALUE)
│   ├── ContainersMixin.java           # Containers.dropItemStack 가로채기
│   ├── DataComponentsMixin.java       # DataComponents MAX_STACK_SIZE 코덱 확장
│   ├── GiveCommandMixin.java          # /give 명령어 가로채기
│   ├── ItemInstanceMixin.java         # ItemInstance.getMaxStackSize 훅
│   ├── ItemMixin.java                 # Item.getDefaultMaxStackSize 훅
│   ├── ItemStackMixin.java            # ItemStack.getMaxStackSize 범위 리디렉션
│   ├── ItemStackTemplateMixin.java    # ItemStackTemplate 코덱 범위 리디렉션
│   ├── MinecraftServerMixin.java      # 서버 GameRule 변경 이벤트 리스너
│   └── client/
│       └── GuiGraphicsExtractorMixin.java # 슬롯 아이템 수량 축소 렌더링 훅
├── network/
│   └── StackSizeLimitSyncPayload.java # S2C 패킷 레코드 (stack-size-adjuster:sync_limit)
└── util/
    ├── GiveCommandHelper.java         # 100배 안전 승수를 적용한 /give 아이템 분배
    ├── InventoryDropHelper.java       # 보관함 파괴 시 제어된 드롭 분할 알고리즘
    ├── ItemCountRenderer.java         # 동적 폰트 행렬 축소 렌더러
    ├── ModVersionGuard.java           # 런타임 클래스 호환성 검증
    └── StackSizeManager.java          # 스레드 안전 한도 관리자 및 재정의 레지스트리
```

---

## 🔒 스레딩 및 동시성 모델

* **읽기 작업**: `StackSizeManager.getModifiedStackSize`는 `volatile int` 필드와 `CopyOnWriteArrayList`를 통해 락 없이 $O(1)$의 높은 속도로 조회됩니다.
* **서버-클라이언트 동기화**: 패킷은 서버 메인 스레드에서 발송되고, 클라이언트 메인 스레드에서 `context.client().execute(...)`를 통해 안전하게 처리됩니다.
