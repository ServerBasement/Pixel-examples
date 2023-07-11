package it.ohalee.pixel.example.master;

import it.ohalee.pixel.Pixel;
import it.ohalee.pixel.example.master.player.BedwarsParticipator;
import it.ohalee.pixel.example.master.queue.BedwarsQueue;
import it.ohalee.pixel.example.match.SharedBedwarsMatch;
import it.ohalee.pixel.example.match.enumeration.BedwarsType;
import org.bukkit.plugin.java.JavaPlugin;

public class PixelBedwars extends Pixel<BedwarsType, SharedBedwarsMatch, BedwarsParticipator, BedwarsQueue> {

    public PixelBedwars(JavaPlugin plugin) {
        super(plugin);
    }

}