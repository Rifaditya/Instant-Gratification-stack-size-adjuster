# Concept: Stack Size Adjuster

## Philosophy Fit

**Collection**: Instant Gratification
**Reasoning**: Allows players and modpack creators to customize stack sizes for standard Minecraft item categories to speed up operations (e.g. stacking potions/ender pearls) or create challenge scenarios (non-stackable blocks).

## Core Mechanics

### 1. Dynamic Category Capping
Overrides default stack sizes for the three primary item categories based on settings:
- **64-Stack Items**: Blocks, crops, standard materials. Default: 64.
- **16-Stack Items**: Snowballs, ender pearls, eggs. Default: 16.
- **1-Stack Items**: Tools, weapons, armor, potions, stews. Default: 1.

### 2. Bypass Component Constraints
Avoids the strict component validation errors that normally prevent damageable items from being stackable by overriding the stack size logic at the method level while maintaining components integrity.

## Configuration

Standard alignment using three namespaced GameRules (configured to any raw integer limit up to `Integer.MAX_VALUE`):
- `stack-size-adjuster:items_64_limit`
- `stack-size-adjuster:items_16_limit`
- `stack-size-adjuster:items_1_limit`

These values are mirrored in the global `config/stack-size-adjuster.json` file on client and server.
