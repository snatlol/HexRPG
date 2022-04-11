package com.snat.main.manager;

import com.snat.main.Main;
import com.snat.main.Ranks.Rank;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RankManager {

    private File file;
    private YamlConfiguration config;

    public RankManager(Main main) {
        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }

        file = new File(main.getDataFolder(), "ranks.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }
    public void setRank(UUID uuid, Rank rank) {
        config.set(uuid.toString(), rank.name());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Rank getRank(UUID uuid) {
        return Rank.valueOf(config.getString(uuid.toString()));

    }

}
