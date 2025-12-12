package org.birddunks;

import java.awt.Font;
import net.runelite.client.ui.FontManager;

public enum NpcCounterFont
{
    RUNESCAPE_BOLD("RuneScape Bold"),
    RUNESCAPE_PLAIN("RuneScape Plain"),
    ARIAL("Arial"),
    VERDANA("Verdana"),
    TAHOMA("Tahoma");

    private final String display;

    NpcCounterFont(String display)
    {
        this.display = display;
    }

    @Override
    public String toString()
    {
        return display;
    }

    public Font toFont(int size)
    {
        switch (this)
        {
            case RUNESCAPE_PLAIN:
                return FontManager.getRunescapeFont().deriveFont((float) size);
            case RUNESCAPE_BOLD:
                return FontManager.getRunescapeBoldFont().deriveFont((float) size);
            case VERDANA:
                return new Font("Verdana", Font.PLAIN, size);
            case TAHOMA:
                return new Font("Tahoma", Font.PLAIN, size);
            case ARIAL:
            default:
                return new Font("Arial", Font.PLAIN, size);
        }
    }
}
