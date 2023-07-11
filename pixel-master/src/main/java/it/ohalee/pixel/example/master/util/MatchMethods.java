package it.ohalee.pixel.example.master.util;

import it.ohalee.pixel.example.master.Main;
import it.ohalee.pixel.example.master.queue.BedwarsQueue;
import it.ohalee.pixel.example.match.SharedBedwarsMatch;
import it.ohalee.pixel.example.match.enumeration.BedwarsType;
import it.ohalee.pixel.example.match.enumeration.MatchStatus;

public class MatchMethods {

    public static SharedBedwarsMatch[] availableMatches(BedwarsType type) {
        BedwarsQueue queue = Main.getPixelProxy().getQueue(type);

        return queue.getMatches()
                .parallelStream()
                .filter(match -> match.getInstanceStatus() == MatchStatus.WAITING || match.getInstanceStatus() == MatchStatus.STARTING)
                .toArray(SharedBedwarsMatch[]::new);
    }

}
