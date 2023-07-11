package it.ohalee.pixel.example.instance;

import it.ohalee.pixel.SubPixel;
import it.ohalee.pixel.example.instance.arena.Arena;
import it.ohalee.pixel.example.instance.listener.ConnectionListener;
import it.ohalee.pixel.example.instance.match.BedwarsMatchManager;
import it.ohalee.pixel.example.instance.match.BedwarsSharedListener;
import it.ohalee.pixel.example.match.SharedBedwarsMatch;
import it.ohalee.pixel.example.match.enumeration.BedwarsType;
import it.ohalee.pixel.match.PixelMatchManager;
import it.ohalee.pixel.player.PlayerReceiver;

public class PixelBedwarsInstance extends SubPixel<BedwarsType, SharedBedwarsMatch, Arena> {

    public PixelBedwarsInstance(Main plugin) {
        super(plugin, null);
    }

    @Override
    public PixelMatchManager<BedwarsType, SharedBedwarsMatch, Arena> summonMatchManager() {
        return new BedwarsMatchManager(new BedwarsSharedListener());
    }

    @Override
    public PlayerReceiver<BedwarsType, SharedBedwarsMatch, Arena> summonPlayerReceiver() {
        return new ConnectionListener();
    }

}