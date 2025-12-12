package org.birddunks;

import java.awt.Color;
import net.runelite.client.config.*;

@ConfigGroup("npccounter")
public interface NpcCounterConfig extends Config
{
    @ConfigItem(
            keyName = "npcNames",
            name = "NPC names",
            description = "Comma-separated NPC names to count (case-insensitive)"
    )
    default String npcNames()
    {
        return "chicken";
    }

    @ConfigItem(
            keyName = "font",
            name = "Font",
            description = "Font used for the overlay text"
    )
    default NpcCounterFont font()
    {
        return NpcCounterFont.RUNESCAPE_BOLD;
    }

    @Range(min = 8, max = 64)
    @ConfigItem(
            keyName = "fontSize",
            name = "Font size",
            description = "Overlay font size"
    )
    default int fontSize()
    {
        return 22;
    }

    @Alpha
    @ConfigItem(
            keyName = "textColor",
            name = "Color",
            description = "Overlay text color"
    )
    default Color textColor()
    {
        return new Color(255, 0, 110, 255);
    }
}
