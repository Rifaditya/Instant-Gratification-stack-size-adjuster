# Troubleshooting & FAQ

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## ❓ Frequently Asked Questions

### Q1: Why are items in existing worlds not taking the stack limits set in `config/stack-size-adjuster.json`?
**A**: Global configuration files (`stack-size-adjuster.json`) only set baseline default values for **newly created worlds**. For existing worlds, game rules are saved inside the world's `level.dat`. Change settings in-game via `/gamerule` or the GameRules edit screen:
```text
/gamerule stack-size-adjuster:items_64_limit 256
```

### Q2: What happens if I set stack limits above 39,768,215?
**A**: Setting limits above $39,768,215$ risks **Large Chest integer overflow**. A 54-slot Large Chest holding 54 max stacks will exceed the signed 32-bit integer limit ($2,147,483,647$), causing total count wrapping and item deletion when moving items inside the chest. Keeping limits $\le 39,768,215$ is strongly recommended.

### Q3: Why does breaking a chest drop only 8 entity piles instead of thousands?
**A**: This is an intentional performance feature controlled by `stack-size-adjuster:max_drop_entities` (default: `8`). It prevents server crashes when breaking chests with large stack limits. Paired with **Item Clumps**, those 8 piles automatically merge into a single entity pile.

---

## 🛠️ Common Issue Diagnostics

| Symptom | Cause | Solution |
| :--- | :--- | :--- |
| **Client shows vanilla limits in inventory** | Network sync packet failed or server/client version mismatch | Ensure `stack-size-adjuster` is installed on **both** client and server. |
| **Give command fails with "too many items"** | `/give` exceeds $100 \times \text{maxStackSize}$ cap | Request fewer items or issue `/give` in multiple batches. |
| **YACL screen does not open in ModMenu** | ModMenu or YACL is missing | Install **ModMenu** and **YetAnotherConfigLib (YACL v3)** on client. |
