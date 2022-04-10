package com.snat.main;

import com.snat.main.compoments.CustomMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public final class Main extends JavaPlugin {

    private World world;
    private BukkitTask task;
    private Map<Entity, CustomMob> entities = new HashMap<>();

    @Override
    public void onEnable() {
        world = Bukkit.getWorld("test");

        spawnMobs(8, 10, 5 * 20);
    }

    public void spawnMobs(int size, int mobCap, int spawnTime) {
        CustomMob[] mobTypes = CustomMob.values();
        task = new BukkitRunnable() {
            Set<Entity> spawned = entities.keySet();
            List<Entity> removal = new ArrayList<>();
            @Override
            public void run() {
                    for (Entity entity : spawned) {
                        if (!entity.isValid() || entity.isDead()) removal.add(entity);
                    }
                    spawned.removeAll(removal);
                //spawning Algorithm
                int diff = mobCap - entities.size();
                if (diff <= 0) return;
                int spawnAmount = (int) (Math.random() * (mobCap + 1)), count = 0;
                while (count <= spawnAmount) {
                    count++;
                    int ranX = getRandomWithNeg(size), ranZ = getRandomWithNeg(size);
                    Block block = world.getHighestBlockAt(ranX, ranZ);
                    double xOffset = getRandomOffset(), zOffset = getRandomOffset();
                    Location loc = block.getLocation().clone().add(xOffset, 2, zOffset);
                    if (!isSpawnable(loc)) continue;
                    double random = Math.random() * 101, previous = 0;
                    CustomMob typeToSpawn = mobTypes[0];
                    for (CustomMob type : mobTypes) {
                        previous += type.getSpawnChance();
                        if (random <= previous) {
                            typeToSpawn = type;
                            break;
                        }
                    }
                    entities.put(typeToSpawn.spawn(loc), typeToSpawn);

                }
            }
        }.runTaskTimer(this, 0L, spawnTime);
    }

    private boolean isSpawnable(Location loc) {
        Block feetBlock = loc.getBlock(), headBlock = loc.clone().add(0, 1, 0).getBlock(), upperBlock
        = loc.clone().add(0, 2, 0).getBlock();
        return feetBlock.isPassable() && feetBlock.isLiquid() && headBlock.isPassable() && headBlock.isLiquid()
                && upperBlock.isPassable() && upperBlock.isLiquid();
    }

    private double getRandomOffset() {
        double random = Math.random();
        if (Math.random() > 0.5) random *= -1;
        return random;
    }
    private int getRandomWithNeg(int size) {
        int random = (int) (Math.random() * (size + 1));
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

}
