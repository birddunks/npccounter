package org.birddunks;

import com.google.inject.Provides;
import com.google.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
        name = "NPC Counter",
        description = "Counts matching NPCs by name and shows the total",
        tags = {"npc", "count", "counter"}
)
public class NpcCounterPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private ClientThread clientThread;

    @Inject private NpcCounterConfig config;
    @Inject private OverlayManager overlayManager;
    @Inject private NpcCounterOverlay overlay;

    @Getter
    private int currentCount = 0;

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);

        // Keep any client interactions on the client thread.
        clientThread.invokeLater(() -> log.debug("NPC Counter started"));
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        currentCount = 0;
        log.debug("NPC Counter stopped");
    }

    @Subscribe
    public void onGameTick(GameTick tick)
    {
        final List<String> targets = Arrays.stream(config.npcNames().split(","))
                .map(s -> s.trim().toLowerCase(Locale.ROOT))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (targets.isEmpty())
        {
            currentCount = 0;
            return;
        }

        currentCount = (int) client.getNpcs().stream()
                .map(NPC::getName)
                .filter(name -> name != null)
                .map(name -> name.toLowerCase(Locale.ROOT))
                .filter(targets::contains)
                .count();
    }

    @Provides
    NpcCounterConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(NpcCounterConfig.class);
    }
}
