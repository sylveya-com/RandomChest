# RandomChest

Randomized loot chest plugin for Spigot

## » About

Adds loot chests with configurable chest types, randomized contents, and automatic refills. Each chest type has its own loot table, item properties, respawn settings, and interaction rules.

## » Usage

* **Register a chest:** Run `/randomchest select <type>`, hold `GUNPOWDER`, and right-click a chest in creative mode.
* **Remove a chest:** Hold `SUGAR` and right-click a registered chest in creative mode.
* **Clear selection:** Run `/randomchest unselect`.

Registered chests generate random loot when opened and refill after their configured respawn delay.

## » Item Fields

| Field | Description |
| --- | --- |
| `material` | Material name on modern Minecraft versions |
| `id` | Material ID, used when `material` is not specified on legacy versions |
| `amount` | Item stack size |
| `data` | Material data value for legacy item metadata |
| `durability` | Durability range `[min, max]` as a percentage of maximum durability |
| `random-enchant` | Applies a random enchantment based on the item type |
| `enchantments` | List of configured `name` / `level` enchantments |
| `name` | Custom display name |
| `lore` | Custom lore lines |
| `skull` | Player name used as the skull owner |
| `potion-type` | Potion effect type |
| `potion-level` | Potion effect level |
| `potion-splash` | Makes the potion a splash potion |

## » Chest Type Fields

| Field | Description |
| --- | --- |
| `min` | Minimum number of items generated |
| `max` | Maximum number of items generated |
| `customname` | Reserved for future use |
| `powered` | Reserved for future redstone control |
| `break` | Allows players to destroy the chest |
| `respawn` | Respawn delay in seconds; a random value is selected from the configured list |

Enjoy RandomChest!
