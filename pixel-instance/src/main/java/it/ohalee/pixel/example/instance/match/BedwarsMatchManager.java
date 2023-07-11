package it.ohalee.pixel.example.instance.match;

import it.ohalee.pixel.example.instance.arena.Arena;
import it.ohalee.pixel.example.match.SharedBedwarsMatch;
import it.ohalee.pixel.example.match.enumeration.BedwarsType;
import it.ohalee.pixel.match.IncomingSharedListener;
import it.ohalee.pixel.match.PixelMatchManager;

public class BedwarsMatchManager extends PixelMatchManager<BedwarsType, SharedBedwarsMatch, Arena> {

    public BedwarsMatchManager(IncomingSharedListener<BedwarsType, SharedBedwarsMatch, Arena> listener) {
        super(listener);
    }

    @Override
    public Class<BedwarsType> typeClass() {
        return BedwarsType.class;
    }

    @Override
    public String getLobby() {
        return "bedwars_lobby_";
    }

    @Override
    public void removeMatch(Arena match) {
        System.out.println("Removing match in progress... " + match.getWorldName());
        super.removeMatch(match);
        System.out.println("Match removed: " + match.getWorldName());
    }
}