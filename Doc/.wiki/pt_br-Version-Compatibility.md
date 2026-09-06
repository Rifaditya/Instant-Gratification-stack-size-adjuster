# Matriz de compatibilidade de versões

> 📌 **Isenção de responsabilidade sobre o código-fonte**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes dos lançamentos públicos no CurseForge e Modrinth.

---

## 📊 Matriz de compatibilidade

| Alvo do Minecraft | Versão do mod | Estado de compilação | DasikLibrary alvo | Limites de dependência |
| :--- | :--- | :--- | :--- | :--- |
| **MC 26.2** | `1.4.16+26.2` | **Ativo / Atual** | `1.8.3` | `minecraft >=26.2-` |

---

## 🛡️ Limites de dependência e regras

Conforme o princípio **1 Jar 1 Version**, o arquivo `fabric.mod.json` estabelece limites inferiores abertos:

```json
"depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": "${minecraft_dependency}",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": "*",
    "item_clumps": ">=1.0.18+26.2"
}
```

### Regras de versionamento:
1. **Sem esquemas legados**: Não são mantidas numerações `1.21.x` para as versões 26.x.
2. **Limites inferiores abertos**: `minecraft >=26.2-` oferece suporte nativo a atualizações menores de correção.
3. **Verificação de classpath**: Execução do `ModVersionGuard.checkClass` ao inicializar.

---

## 📦 Arquivos históricos verificados

Todos os arquivos binários compilados das versões anteriores estão guardados em `Archive Jar of all versions/`:

- `stack-size-adjuster-1.4.16+26.2.jar` (Versão atual)
- `stack-size-adjuster-1.4.15+26.2.jar`
- `stack-size-adjuster-1.4.14+26.2.jar`
- `stack-size-adjuster-1.0.0+26.2.jar` (Primeira versão 26.2)
