package it.ohalee.pixel.example.instance.arena;

import it.ohalee.pixel.SubPixel;
import it.ohalee.pixel.api.Match;
import it.ohalee.pixel.example.instance.Main;
import it.ohalee.pixel.example.match.SharedBedwarsMatch;
import it.ohalee.pixel.example.match.enumeration.BedwarsType;
import it.ohalee.pixel.example.match.enumeration.MatchStatus;
import it.ohalee.pixel.util.StaticTask;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Arena extends Match<BedwarsType, SharedBedwarsMatch> {

    private final Main plugin;

    private final List<Player> players = new ArrayList<>();
    private boolean removeTask = false;
    private final String worldName;

    public Arena(Main plugin, SharedBedwarsMatch shared, String configInRedis, String mapName) {
        super(shared);
        this.plugin = plugin;
        this.worldName = mapName;

        // loadWorldArena(this);
    }

    @Override
    public String getWorldName() {
        return worldName;
    }

    @Override
    public void letJoin(Player player) {
        switch (getStatus()) {
            case WAITING:
            case STARTING:
                addPlayer(player);

                this.joining.remove(player.getName());
                this.shared.setEffectivePlayers(this.players.size());
                this.shared.setJoiningPlayers(this.shared.getJoiningPlayers() - 1);
                break;
            default:
                // Technically spectator should be in shared_spectators list, you can do it with redisson messages
                if (shared_spectators.containsKey(player.getName())) {
                    addSpectator(player);
                    shared_spectators.remove(player.getName());
                } else {
                    SubPixel.getRaw().getCrossServerManager().sendToGameLobby(player.getName(), "bedwars_lobby");
                    return;
                }
                break;
        }

    }

    @Override
    public void letQuit(Player player) {

        removePlayer(player);

        if (getStatus() == MatchStatus.WAITING && players.isEmpty() && !removeTask) {
            removeTask = true;
            StaticTask.runTaskLater(() -> {
                removeTask = false;
                if (players.isEmpty()) {
                    SubPixel.<BedwarsType, SharedBedwarsMatch, Arena>getRaw().getMatchManager().removeMatch(this);
                }
            }, 20L * 20, true);
        }

        this.shared.setEffectivePlayers(this.players.size());
    }

    public void addSpectator(Player player) {

    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    @Override
    public int getMatchWeight() {
        return players.size();
    }

    public MatchStatus getStatus() {
        return this.shared.getInstanceStatus() != null ? this.shared.getInstanceStatus() : MatchStatus.WAITING;
    }

    @Override
    public boolean equals(Object arena) {
        return arena instanceof Arena && ((Arena) arena).getWorldName().equals(this.getWorldName());
    }

}