/**
 * 
 */
package myz.mobs;

import java.util.ArrayList;
import java.util.List;

import myz.mobs.pathing.PathfinderGoalLookAtTarget;
import myz.mobs.pathing.PathfinderGoalNearestAttackableZombieTarget;
import myz.mobs.pathing.PathfinderGoalWalkTo;
import myz.mobs.pathing.PathfinderGoalZombieAttack;
import myz.mobs.pathing.PathingSupport;
import myz.support.PlayerZombieDropEvent;
import myz.support.interfacing.Configuration;
import net.minecraft.server.v1_7_R1.DamageSource;
import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.EntitySkeleton;
import net.minecraft.server.v1_7_R1.EntityVillager;
import net.minecraft.server.v1_7_R1.EntityZombie;
import net.minecraft.server.v1_7_R1.EnumDifficulty;
import net.minecraft.server.v1_7_R1.GenericAttributes;
import net.minecraft.server.v1_7_R1.ItemStack;
import net.minecraft.server.v1_7_R1.Items;
import net.minecraft.server.v1_7_R1.MathHelper;
import net.minecraft.server.v1_7_R1.PathfinderGoal;
import net.minecraft.server.v1_7_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R1.PathfinderGoalMoveIndoors;
import net.minecraft.server.v1_7_R1.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_7_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_7_R1.PathfinderGoalOpenDoor;
import net.minecraft.server.v1_7_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_7_R1.PathfinderGoalRestrictOpenDoor;
import net.minecraft.server.v1_7_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R1.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.util.UnsafeList;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

/**
 * @author Jordan
 * 
 */
public class CustomEntityZombie extends EntityZombie {

    protected List<org.bukkit.inventory.ItemStack> inventory = new ArrayList<org.bukkit.inventory.ItemStack>();
    private int priority = 0;

    public CustomEntityZombie(World world) {
        super(world);

        populateGoals();
    }

    private void populateGoals() {
        try {
            PathingSupport.getField().set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            PathingSupport.getField().set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
            PathingSupport.getSecondField().set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            PathingSupport.getSecondField().set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        getNavigation().b(true);
        goalSelector.a(0, new PathfinderGoalFloat(this));
        goalSelector.a(2, new PathfinderGoalZombieAttack(this, EntityHuman.class, (Double) Configuration.getConfig("mobs.zombie.speed")
                * (isBaby() ? 0.4 : 1), false));
        goalSelector.a(3, new PathfinderGoalZombieAttack(this, EntityVillager.class, (Double) Configuration.getConfig("mobs.zombie.speed")
                * (isBaby() ? 0.4 : 1), true));
        goalSelector.a(3, new PathfinderGoalZombieAttack(this, EntitySkeleton.class, (Double) Configuration.getConfig("mobs.zombie.speed")
                * (isBaby() ? 0.4 : 1), true));
        goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        goalSelector.a(5, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        goalSelector.a(2, new PathfinderGoalMoveIndoors(this));
        goalSelector.a(3, new PathfinderGoalRestrictOpenDoor(this));
        goalSelector.a(4, new PathfinderGoalOpenDoor(this, true));
        goalSelector.a(7, new PathfinderGoalLookAtTarget(this, EntityHuman.class, 8.0F));
        goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        targetSelector.a(2, new PathfinderGoalNearestAttackableZombieTarget(this, EntityHuman.class, 0, true));
        targetSelector.a(2, new PathfinderGoalNearestAttackableZombieTarget(this, EntityVillager.class, 0, false));
        targetSelector.a(2, new PathfinderGoalNearestAttackableZombieTarget(this, EntitySkeleton.class, 0, false));
    }

    @Override
    protected void aD() {
        super.aD();
        getAttributeInstance(GenericAttributes.e).setValue((Double) Configuration.getConfig("mobs.zombie.damage") * (isBaby() ? 0.75 : 1));
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (!super.damageEntity(damagesource, f))
            return false;
        else {
            EntityLiving entityliving = getGoalTarget();

            if (entityliving == null && bR() instanceof EntityLiving)
                entityliving = (EntityLiving) bR();

            if (entityliving == null && damagesource.getEntity() instanceof EntityLiving)
                entityliving = (EntityLiving) damagesource.getEntity();

            if (entityliving != null && world.difficulty == EnumDifficulty.NORMAL
                    && random.nextFloat() < getAttributeInstance(bp).getValue()) {
                int i = MathHelper.floor(locX);
                int j = MathHelper.floor(locY);
                int k = MathHelper.floor(locZ);
                EntityZombie entityzombie = new EntityZombie(world);

                for (int l = 0; l < 50; ++l) {
                    int i1 = i + MathHelper.nextInt(random, 7, 40) * MathHelper.nextInt(random, -1, 1);
                    int j1 = j + MathHelper.nextInt(random, 7, 40) * MathHelper.nextInt(random, -1, 1);
                    int k1 = k + MathHelper.nextInt(random, 7, 40) * MathHelper.nextInt(random, -1, 1);

                    if (World.a(world, i1, j1 - 1, k1) && world.getLightLevel(i1, j1, k1) < 10) {
                        entityzombie.setPosition(i1, j1, k1);
                        if (world.b(entityzombie.boundingBox) && world.getCubes(entityzombie, entityzombie.boundingBox).isEmpty()
                                && !world.containsLiquid(entityzombie.boundingBox)) {
                            world.addEntity(entityzombie);
                            // Prevent crazy zombie mobs.
                            entityzombie.setGoalTarget(entityliving);
                            break;
                        }
                    }
                }
            }

            return true;
        }
    }

    @Override
    protected ItemStack getRareDrop(int i) {
        switch (random.nextInt(3)) {
            case 0:
                return new ItemStack(Items.POTION);

            case 1:
                return new ItemStack(Items.BOWL);

            default:
                return new ItemStack(Items.LEASH);
        }
    }

    @Override
    protected void bA() {
        if (random.nextFloat() < (world.difficulty == EnumDifficulty.NORMAL ? 0.05F : 0.01F)) {
            int i = random.nextInt(3);

            if (i == 0)
                setEquipment(0, new ItemStack(Items.STONE_SWORD));
            else
                setEquipment(0, new ItemStack(Items.STONE_SPADE));
        }
    }

    /*@Override
	public boolean m(Entity entity) {
		return entity.damageEntity(DamageSource.mobAttack(this), (float) Configuration.getZombieDamage() * (isBaby() ? 0.5f : 1f));
	}*/

    @Override
    protected Entity findTarget() {
        EntityHuman entityhuman = PathingSupport.findNearbyVulnerablePlayer(this);

        return entityhuman != null && this.o(entityhuman) ? entityhuman : null;
    }

    /**
     * Set this zombie's inventory contents. Generally contains a player's
     * inventory plus their helmet and subtract their item in hand.
     * 
     * @param inventory
     *            The list of items to set.
     */
    public void setInventory(List<org.bukkit.inventory.ItemStack> inventory) {
        this.inventory = inventory;
    }

    private void emptyInventory() {
        if (inventory != null)
            for (org.bukkit.inventory.ItemStack item : inventory)
                if (item != null)
                    getBukkitEntity().getWorld().dropItemNaturally(getBukkitEntity().getLocation(), item);
        inventory = null;

        Bukkit.getPluginManager().callEvent(new PlayerZombieDropEvent(this));
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        emptyInventory();
    }

    @Override
    public void die() {
        super.die();
        emptyInventory();
    }

    @Override
    public boolean canSpawn() {
        return world.difficulty != EnumDifficulty.PEACEFUL && world.b(boundingBox) && world.getCubes(this, boundingBox).isEmpty()
                && !world.containsLiquid(boundingBox);
    }

    public void see(Location location, int priority) {
        if (priority < this.priority)
            return;
        if (random.nextInt(priority + 1) >= 1 && getGoalTarget() == null || priority > 1) {
            setGoalTarget(null);
            target = null;
            double dub = (Double) Configuration.getConfig("mobs.zombie.speed");
            addPather(location, (float) dub * (isBaby() ? 0.4f : 1f));
        }
    }

    public void addPather(Location to, float speed) {
        goalSelector.a(4, new PathfinderGoalWalkTo(this, to, speed));
    }

    public void cleanPather(PathfinderGoal goal) {
        populateGoals();
        priority = 0;
    }

    public static CustomEntityZombie newInstance(Player player) {
        World world = ((CraftWorld) player.getWorld()).getHandle();
        CustomEntityZombie zombie = new CustomEntityZombie(world);

        zombie.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        world.addEntity(zombie, SpawnReason.CUSTOM);
        return zombie;
    }
}
