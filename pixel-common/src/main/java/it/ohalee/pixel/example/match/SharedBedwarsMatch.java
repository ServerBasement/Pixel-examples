package it.ohalee.pixel.example.match;

import it.ohalee.pixel.example.match.enumeration.BedwarsType;
import it.ohalee.pixel.example.match.enumeration.MatchStatus;
import it.ohalee.pixel.example.player.ParticipatorSection;
import it.ohalee.pixel.match.SharedMatch;
import it.ohalee.pixel.match.SharedMatchStatus;
import it.ohalee.pixel.util.Basement;
import lombok.Getter;
import lombok.Setter;
import org.redisson.api.RMapCache;

import java.util.Date;

@Getter
@Setter
public class SharedBedwarsMatch extends SharedMatch {

    private MatchStatus instanceStatus;
    private Date date = new Date();
    private String host;

    public SharedBedwarsMatch(String name, BedwarsType type) {
        super(name, type.toString());
    }

    public SharedBedwarsMatch() {
    }

    public void join(ParticipatorSection section) {
        setJoiningPlayers(this.getJoiningPlayers() + section.size());
        if (totalCount() == getRequired()) {
            this.setStatus(SharedMatchStatus.WAITING_LAST);
        }
        setChangedAt(System.currentTimeMillis());
        RMapCache<String, String> joining = Basement.rclient().getMapCache(this.getName() + "_joining");
        section.getParticipators().forEach(name -> joining.put(name, getName()));
    }

    @Override
    public boolean spectate(String player) {
        if (this.getStatus() != SharedMatchStatus.CLOSE || this.getInstanceStatus() != MatchStatus.PLAYING)
            return false;
        else {
            RMapCache<String, String> mapping = Basement.rclient().getMapCache(getName() + "_spectators");
            mapping.put(player, this.getName());
            return true;
        }
    }

}
