package com.snat.main.compoments;

import com.snat.main.compoments.LootItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static com.snat.main.Utils.*;

public enum CustomMob {

    Foul_Zomb("&eFoul Zomb", 25, 60, EntityType.ZOMBIE,
            createItem(Material.IRON_SWORD, 1, false ,true, true, null),
            makeArmorSet(new ItemStack(Material.IRON_HELMET), null, null, null),
            new LootItem(createItem(Material.ROTTEN_FLESH, 1, false, false,
                    false, "&fPreserved Flesh", "&7A useless flesh from a rotting corspe",
                    "not sure whatd you want this for tho", "&9Foodstuff"), 1, 3, 100)),

    Test_Skel("&9Testing Skelton", 4, 40, EntityType.SKELETON, null, null,
            new LootItem(createItem(Material.BONE, 1, false, false, false, "&9Special Bone",
                    "&fCoolest Bone ever", "But it is pretty dumb ngl", "poopyStuff"), 1, 2, 100))
            ;


    private String name;
    private double maxHealth, spawnChance;
    private EntityType type;
    private ItemStack mainItem;
    private ItemStack[] armor;
    private List<LootItem> lootTable;

    CustomMob(String name, double maxHealth, double spawnChance, EntityType type, ItemStack mainItem, ItemStack[] armor, LootItem... lootItems) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.type = type;
        this.mainItem = mainItem;
        this.armor = armor;
        lootTable = Arrays.asList(lootItems);
    }

    public LivingEntity spawn(Location location) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        entity.setCustomNameVisible(true);
        entity.setCustomName(color(name + " &r&c" + (int) maxHealth + "/" + (int) maxHealth + "❤"));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);
        EntityEquipment inv = entity.getEquipment();
        if (armor != null) inv.setArmorContents(armor);
        inv.setHelmetDropChance(0f);
        inv.setChestplateDropChance(0f);
        inv.setLeggingsDropChance(0f);
        inv.setBootsDropChance(0f);
        inv.setItemInMainHand(mainItem);
        inv.setItemInMainHandDropChance(0f);
        return entity;
    }

    public void tryDropLoot(Location location) {
        for (LootItem item : lootTable) {
            item.tryDropItem(location);
        }
    }

    public String getName() {
        return name;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getSpawnChance() {
        return spawnChance;
    }
}
