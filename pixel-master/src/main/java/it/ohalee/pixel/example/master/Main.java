package it.ohalee.pixel.example.master;

import it.ohalee.pixel.PixelProxy;
import it.ohalee.pixel.api.MapSupplier;
import it.ohalee.pixel.example.master.listeners.PlayerListener;
import it.ohalee.pixel.example.master.map.MapLoader;
import it.ohalee.pixel.example.master.player.BedwarsParticipator;
import it.ohalee.pixel.example.master.queue.BedwarsQueue;
import it.ohalee.pixel.example.master.queue.CustomQueue;
import it.ohalee.pixel.example.match.SharedBedwarsMatch;
import it.ohalee.pixel.example.match.enumeration.BedwarsType;
import it.ohalee.pixel.server.ServerRancherConfiguration;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static final boolean MAIN_HUB = true;

    @Getter
    private static PixelProxy<BedwarsType, SharedBedwarsMatch, BedwarsParticipator, BedwarsQueue> pixelProxy;

    @Override
    public void onEnable() {

        PixelBedwars pixelBedwars = new PixelBedwars(this);
        pixelBedwars.registerLogger(getLogger()).metadata(MAIN_HUB);
        pixelBedwars.registerRancher(this, new ServerRancherConfiguration<BedwarsType, SharedBedwarsMatch>() {

            @Override
            public String modeName() {
                return "bedwars";
            }

            @Override
            public int maxMatchesPerServer() {
                return 5;
            }

            @Override
            public double warningPercentage() {
                return 60;
            }

            @Override
            public Class<SharedBedwarsMatch> sharedMatchClass() {
                return SharedBedwarsMatch.class;
            }

            @Override
            public Class<BedwarsType> typeClass() {
                return BedwarsType.class;
            }

            @Override
            public ServerManagerConfiguration<BedwarsType, SharedBedwarsMatch> serverManager() {
                return new ServerManagerConfiguration<BedwarsType, SharedBedwarsMatch>() {
                    @Override
                    public boolean dynamicallyStartServers() {
                        return true;
                    }

                    @Override
                    public int maxAmountOfServers() {
                        return 100;
                    }
                };
            }

            @Override
            public LobbyConfiguration lobbyConfiguration() {
                return new LobbyConfiguration() {
                    @Override
                    public String genericModePrefix() {
                        return "bw";
                    }

                    @Override
                    public boolean enablePlaceholderAPI() {
                        return true;
                    }
                };
            }
        });

        MapSupplier supplier = new MapLoader(this);

        for (BedwarsType value : BedwarsType.defaults()) {
            pixelBedwars.registerQueue(new BedwarsQueue("bedwars", value, supplier));
        }

        pixelBedwars.registerQueue(new CustomQueue("bedwars", BedwarsType.PRIVATE, supplier));

        pixelProxy = pixelBedwars.process();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

    }

    @Override
    public void onDisable() {
        PixelProxy.getRawProxy().shutdown();
    }

}
