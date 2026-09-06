# Item Clumps 통합

## 개요

Stack Size Adjuster는 **Item Clumps**(`item_clumps >=1.0.18+26.2`)와 시너지를 이룹니다. Stack Size Adjuster가 인벤토리 내 스택 크기와 상자 파괴 시의 스폰을 제어하고, Item Clumps는 바닥에 떨어진 엔티티들을 하나로 병합합니다.

---

## 🤝 시너지 작동 순서도

```
[ 보관함 파괴 이벤트 ]
          |
          v
[ Stack Size Adjuster: InventoryDropHelper ]
아이템을 제한된 수의 엔티티로 분할 (예: 슬롯당 최대 8개)
          |
          v
[ 바닥에 드롭 엔티티 스폰 ]
          |
          v
[ Item Clumps Mod: 바닥 엔티티 병합 ]
반경 3.5블록을 스캔하여 단일 엔티티 묶음으로 합침
          |
          v
[ 대량 아이템이 든 단일 엔티티 ] (렉 없음!)
```

---

## 📊 기능 분담 매트릭스

| 레이어 | 담당 모듈 | 주요 이점 |
| :--- | :--- | :--- |
| **인벤토리 스택 한도** | **Stack Size Adjuster** | 64/16/1 한도를 동적으로 확장 |
| **보관함 파괴 시 스폰 제어** | **Stack Size Adjuster** | 파괴 시 수천 개의 엔티티가 한 번에 쏟아지는 문제 방지 |
| **바닥 아이템 병합** | **Item Clumps** | 바닥에 흩어진 아이템을 단일 묶음으로 통합 |
| **아이템 획득 처리** | **Minecraft & Mixins** | 병합된 대형 엔티티를 확장된 슬롯으로 즉시 흡수 |

---

## ⚙️ 권장 설정

**Item Clumps**와 함께 사용할 때는 `max_drop_entities`를 **8**(또는 극대화 성능 모드의 경우 **1**)로 설정하는 것을 권장합니다:
```text
/gamerule stack-size-adjuster:max_drop_entities 8
```
