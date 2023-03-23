# Change progress icons

Freeplane has a progress feature based on icons 0%, 25%, 50%, 75%, 100%, and checkmark icon.

I like it, but I prefer to change a little bit its behavior.

Basically the 3 scripts `ProgressUp2`, `ProgressDown2`, and `RemoveProgress2` do the same thing that the builtin:
- Insert > Icon > Progress icon (%) > Progress up
- Insert > Icon > Progress icon (%) > Progress down
- Insert > Icon > Progress icon (%) > Remove progress

The main change is that when 100% is reached, only the checkmark icon stay (Freeplane keep the 100% icon next to the checkmark icon).
