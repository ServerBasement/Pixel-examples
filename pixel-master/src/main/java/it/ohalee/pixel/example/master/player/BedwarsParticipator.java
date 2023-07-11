package it.ohalee.pixel.example.master.player;

import it.ohalee.pixel.example.master.queue.BedwarsQueue;
import it.ohalee.pixel.player.PixelParticipator;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BedwarsParticipator extends PixelParticipator {

    protected static final Map<String, BedwarsParticipator> participators = new HashMap<>();

    public BedwarsParticipator(Player player) {
        super(player);
    }

    public static void prepare(Player player) {
        participators.put(player.getName().toLowerCase(), new BedwarsParticipator(player));
    }

    public static void unload(Player player) {
        BedwarsParticipator participator = participators.remove(player.getName().toLowerCase());
        if (participator != null)
            participator.removeFromQueue();
    }

    public static BedwarsParticipator get(String name) {
        return participators.get(name.toLowerCase());
    }

    @Override
    public BedwarsQueue queue() {
        return (BedwarsQueue) queue;
    }

}