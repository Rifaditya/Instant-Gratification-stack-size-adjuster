# 동작 프로필 및 조건

## 개요

Stack Size Adjuster는 상태 머신 아키텍처를 바탕으로 월드 설정, 동적 게임 규칙 및 네트워크 패킷을 결합합니다.

---

## 🔄 상태 머신 라이프사이클 다이어그램

```text
               +----------------------------------+
               |      서버 시작 / 월드 생성       |
               +----------------------------------+
                                |
                                v
               [ 기본 구성 템플릿 로드            ]
               (`config/stack-size-adjuster.json`)
                                |
                                v
               [ 동적 GameRules 등록              ]
               (`items_64_limit`, `items_16_limit` 등)
                                |
                +---------------+---------------+
                |                               |
           새로 생성된 월드                 기존 로드된 월드
                |                               |
                v                               v
        템플릿 기본값 적용               `level.dat`에서
                                         규칙 값 로드
                |                               |
                +---------------+---------------+
                                |
                                v
               [ StackSizeManager 초기화          ]
                                |
                                v
               [ 플레이어 접속 / 규칙 변경        ]
                                |
                                v
               [ S2C 패킷 발송: sync_limit        ]
                                |
                                v
               [ 클라이언트 화면 상태 강제 갱신   ]
```

---

## ⚙️ 주요 트리거 및 핸들러

1. **서버 시작 완료 (`ServerLifecycleEvents.SERVER_STARTED`)**:
   - 설정 파일 재로드.
   - 신규 월드인 경우 기본값을 GameRules에 설정.
   - `StackSizeManager` 초기화.
2. **플레이어 접속 (`ServerPlayConnectionEvents.JOIN`)**:
   - 접속한 플레이어에게 `StackSizeLimitSyncPayload` 발송.
3. **게임 규칙 변경 (`MinecraftServerMixin.onGameRuleChanged`)**:
   - `stack-size-adjuster:*` 규칙의 변경 감지.
   - `StackSizeManager` 갱신.
   - 접속자 전체에 변경 사항 전송.
