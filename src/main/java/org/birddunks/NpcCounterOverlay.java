package org.birddunks;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

public class NpcCounterOverlay extends Overlay
{
    private static final int PAD_X = 6;
    private static final int PAD_Y = 4;

    private final NpcCounterPlugin plugin;
    private final NpcCounterConfig config;

    @Inject
    public NpcCounterOverlay(NpcCounterPlugin plugin, NpcCounterConfig config)
    {
        this.plugin = plugin;
        this.config = config;

        setPosition(OverlayPosition.DYNAMIC);
        setMovable(true);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(OverlayPriority.MED);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final String text = String.valueOf(plugin.getCurrentCount());

        final Font font = config.font().toFont(config.fontSize());
        graphics.setFont(font);
        graphics.setColor(config.textColor());

        final FontMetrics fm = graphics.getFontMetrics();

        final int textW = fm.stringWidth(text);
        final int textH = fm.getHeight();

        final int boxW = textW + (PAD_X * 2);
        final int boxH = textH + (PAD_Y * 2);

        setPreferredSize(new Dimension(boxW, boxH));

        // IMPORTANT: draw relative to the overlay origin (0,0). RuneLite positions it for us.
        final int x = PAD_X;
        final int y = PAD_Y + fm.getAscent();

        graphics.drawString(text, x, y);

        return getPreferredSize();
    }
}
