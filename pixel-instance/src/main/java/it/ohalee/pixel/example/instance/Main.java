package it.ohalee.pixel.example.instance;

import it.ohalee.pixel.example.instance.arena.StandardArenaManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private PixelBedwarsInstance pixelInstance;

    @Override
    public void onEnable() {
        instance = this;

        StandardArenaManager.init();

        this.pixelInstance = new PixelBedwarsInstance(this);
    }

    @Override
    public void onDisable() {
        this.pixelInstance.shutdown();
    }

}
