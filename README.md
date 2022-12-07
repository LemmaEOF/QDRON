<img src="icon.png" align="right" width="180px"/>

# QDRON


[>> Downloads <<](https://github.com/LemmaEOF/QDRON/releases)

*Quick & Dirty Recipe Output NBT*

**This mod is open source and under a permissive license.** As such, it can be included in any modpack on any platform without prior permission. We appreciate hearing about people using our mods, but you do not need to ask to use them. See the [LICENSE file](LICENSE) for more details.

QDRON lets you use the usually-disallowed `data` object in recipe output objects used for crafting and smithing recipes, as well as most well-formed recipe specs for other mods' crafting recipes. This will **not** work for stonecutting or any form of cooking recipes by default, but other mods may add support for this.

The contents of the `data` object are standard JSON which will be converted to an NBT object using the built-in `NbtOps`.

***THIS IS NOT MEANT TO BE A FULL REPLACEMENT FOR NBTCRAFTING***. It does not allow you to specify NBT for inputs, recipe remainders, potion recipe JSONs, or the dollar system. This is quick-and-dirty recipe output NBT. **Nothing else**.