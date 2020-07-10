package de.sprax2013.advanced_dev_utils.spigot.utils.location;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class LocationUtils {
    /**
     * String to Location<br>
     * <br>
     * Possible Syntax:
     * <ul>
     * <li>X;Y;Z</li>
     * <li>X;Y;Z;Yaw;Pitch</li>
     * </ul>
     *
     * @return The Location or null
     *
     * @see #toString(Location)
     */
    public static Location toLocation(String locString) {
        try {
            String[] args = locString.split(";");

            if (args.length == 5) {
                return new Location(null, Double.parseDouble(args[0]), Double.parseDouble(args[1]),
                        Double.parseDouble(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4]));
            }

            return new Location(null, Double.parseDouble(args[0]), Double.parseDouble(args[1]),
                    Double.parseDouble(args[2]));
        } catch (@SuppressWarnings("unused") Exception ex) {
            return null;
        }
    }

    /**
     * Location to String<br>
     * <br>
     * Possible returned Syntax:
     * <ul>
     * <li>X;Y;Z</li>
     * <li>X;Y;Z;Yaw;Pitch</li>
     * </ul>
     *
     * @return The String or null
     *
     * @see #toLocation(String)
     */
    public static String toString(Location loc) {
        if (loc != null) {
            String result = loc.getX() + ";" + loc.getY() + ";" + loc.getZ();

            if (loc.getYaw() != 0 && loc.getPitch() != 0) {
                result += ";" + loc.getYaw() + ";" + loc.getPitch();
            }

            return result;
        }

        return null;
    }

    /**
     * Checks if the values of two location are the same.<br>
     * <br>
     * Equals:
     * <code>LocationUtils.isSameLocation(loc, loc2, false, false, false, false, false, false);</code>
     *
     * @param loc  Fist Location
     * @param loc2 Seconds Location
     *
     * @return true, if is same location
     *
     * @see #isSameLocation(Location, Location, boolean, boolean, boolean, boolean,
     * boolean, boolean)
     */
    public static boolean isSameLocation(Location loc, Location loc2) {
        return isSameLocation(loc, loc2, false, false, false, false, false, false);
    }

    /**
     * Checks if the values of two location are the same.<br>
     * You can choose with values should not be checked
     *
     * @param loc         Fist Location
     * @param loc2        Seconds Location
     * @param ignoreWorld Ignore World?
     * @param ignoreX     Ignore X?
     * @param ignoreY     Ignore Y?
     * @param ignoreZ     Ignore Z?
     * @param ignoreYaw   Ignore Yaw?
     * @param ignorePitch Ignore Pitch?
     *
     * @return true, if is same location
     */
    public static boolean isSameLocation(Location loc, Location loc2, boolean ignoreWorld, boolean ignoreX,
                                         boolean ignoreY, boolean ignoreZ, boolean ignoreYaw, boolean ignorePitch) {
        return (ignoreWorld || loc.getWorld().equals(loc2.getWorld())) && (ignoreX || loc.getX() == loc2.getX())
                && (ignoreY || loc.getY() == loc2.getY()) && (ignoreZ || loc.getZ() == loc2.getZ())
                && (ignoreYaw || loc.getYaw() == loc2.getYaw()) && (ignorePitch || loc.getPitch() == loc2.getPitch());
    }

    /**
     * @return CardinalDirectionEnum or null if failed (<code>null</code> is
     * impossible in 1.12.2 - Possible in other versions)
     *
     * @author DarkArc
     * (https://github.com/sk89q/CommandBook/blob/master/src/main/java/com/sk89q/commandbook/util/entity/EntityUtil.java)
     * Get the cardinal compass direction of an entity.
     */
    public static CardinalDirectionEnum getCardinalDirection(Entity entity) {
        double rot = (entity.getLocation().getYaw() - 90) % 360;

        if (rot < 0) {
            rot += 360.0;
        }

        if (0 <= rot && rot < 22.5) {
            return CardinalDirectionEnum.WEST;
        } else if (22.5 <= rot && rot < 67.5) {
            return CardinalDirectionEnum.NORTH_WEST;
        } else if (67.5 <= rot && rot < 112.5) {
            return CardinalDirectionEnum.NORTH;
        } else if (112.5 <= rot && rot < 157.5) {
            return CardinalDirectionEnum.NORTH_EAST;
        } else if (157.5 <= rot && rot < 202.5) {
            return CardinalDirectionEnum.EAST;
        } else if (202.5 <= rot && rot < 247.5) {
            return CardinalDirectionEnum.SOUTH_EAST;
        } else if (247.5 <= rot && rot < 292.5) {
            return CardinalDirectionEnum.SOUTH;
        } else if (292.5 <= rot && rot < 337.5) {
            return CardinalDirectionEnum.SOUTH_WEST;
        } else if (337.5 <= rot && rot < 360.0) {
            return CardinalDirectionEnum.WEST;
        } else {
            return null;
        }
    }

    /**
     * Returns the 2D-Centered location of <i>loc</i>.<br>
     * 2D because Y will be on top of a block.
     *
     * @param loc The location
     *
     * @return A new location (2D centered)
     *
     * @see #getCenter3D(Location)
     */
    public static Location getCenter2D(Location loc) {
        Location newLoc = loc.getBlock().getLocation().clone();
        newLoc.setYaw(loc.getYaw());
        newLoc.setPitch(loc.getPitch());

        if (newLoc.getX() <= 0 && newLoc.getZ() <= 0) {
            return newLoc.add(0.5, 0, 0.5);
        }

        return newLoc.add(0.5, 0, 0.5);
    }

    /**
     * Returns the 3D-Centered location of <i>loc</i>.<br>
     * 3D because Y will be inside of a block.
     *
     * @param loc The location
     *
     * @return A new location (3D centered)
     *
     * @see #getCenter2D(Location)
     */
    public static Location getCenter3D(Location loc) {
        Location newLoc = loc.getBlock().getLocation().clone();
        newLoc.setYaw(loc.getYaw());
        newLoc.setPitch(loc.getPitch());

        if (newLoc.getX() <= 0 && newLoc.getZ() <= 0) {
            return newLoc.add(0.5, -0.5, 0.5);
        }

        return newLoc.add(0.5, -0.5, 0.5);
    }

    public static boolean isOnGround(Entity ent) {
        return !isInAir(ent);
    }

    public static boolean isInAir(Entity ent) {
        return ent.getLocation().getBlock().getType() == Material.AIR && ent.getLocation().getBlock().getLocation()
                .clone().subtract(0, 1, 0).getBlock().getType() == Material.AIR;
    }

    public static Block getFirstBlockAtXZ(Location location, boolean ignoreNonSolidNotIncludingLiquids) {
        return getFirstBlockAtXZ(location, ignoreNonSolidNotIncludingLiquids, null);
    }

    public static Block getFirstBlockAtXZ(Location location, boolean ignoreNonSolidNotIncludingLiquids,
                                          boolean fromHeightZero) {
        return getFirstBlockAtXZ(location, ignoreNonSolidNotIncludingLiquids, null, fromHeightZero);
    }

    public static Block getFirstBlockAtXZ(Location location, boolean ignoreNonSolidNotIncludingLiquids,
                                          List<Material> blacklistedBlocks) {
        return getFirstBlockAtXZ(location, ignoreNonSolidNotIncludingLiquids, blacklistedBlocks, false);
    }

    public static Block getFirstBlockAtXZ(Location location, boolean ignoreNonSolidNotIncludingLiquids,
                                          List<Material> blacklistedBlocks, boolean fromHeightZero) {
        ArrayList<Material> blacklisted = new ArrayList<>();
        ArrayList<Material> ignored = new ArrayList<>();

        if (blacklistedBlocks != null) {
            blacklisted.addAll(blacklistedBlocks);
        }

        if (ignoreNonSolidNotIncludingLiquids) {
            ignored.add(Material.ACTIVATOR_RAIL);
            ignored.add(Material.BROWN_MUSHROOM);
            ignored.add(Material.DEAD_BUSH);
            ignored.add(Material.DETECTOR_RAIL);
            ignored.add(Material.LEVER);
            ignored.add(Material.POWERED_RAIL);
            ignored.add(Material.RED_MUSHROOM);
            ignored.add(Material.REDSTONE_WIRE);
            ignored.add(Material.STONE_BUTTON);
            ignored.add(Material.TORCH);
            ignored.add(Material.TRIPWIRE);
            ignored.add(Material.TRIPWIRE_HOOK);

            try { // Pre 1.13
                ignored.add(Material.valueOf("LONG_GRASS"));
                ignored.add(Material.valueOf("RAILS"));
                ignored.add(Material.valueOf("RED_ROSE"));
                ignored.add(Material.valueOf("REDSTONE_TORCH_OFF"));
                ignored.add(Material.valueOf("REDSTONE_TORCH_ON"));
                ignored.add(Material.valueOf("SAPLING"));
                ignored.add(Material.valueOf("DOUBLE_PLANT"));
                ignored.add(Material.valueOf("WOOD_BUTTON"));
                ignored.add(Material.valueOf("YELLOW_FLOWER"));
            } catch (@SuppressWarnings("unused") Throwable th) { // 1.13
//				ignored.add(Material.ACACIA_BUTTON);
//				ignored.add(Material.ACACIA_SAPLING);
//				ignored.add(Material.ALLIUM);
//				ignored.add(Material.AZURE_BLUET);
//				ignored.add(Material.BIRCH_BUTTON);
//				ignored.add(Material.BIRCH_SAPLING);
//				ignored.add(Material.BLUE_ORCHID);
//				ignored.add(Material.DANDELION);
//				ignored.add(Material.DANDELION_YELLOW);
//				ignored.add(Material.DARK_OAK_BUTTON);
//				ignored.add(Material.DARK_OAK_SAPLING);
//				ignored.add(Material.JUNGLE_BUTTON);
//				ignored.add(Material.JUNGLE_SAPLING);
//				ignored.add(Material.LILAC);
//				ignored.add(Material.OAK_BUTTON);
//				ignored.add(Material.OAK_SAPLING);
//				ignored.add(Material.ORANGE_TULIP);
//				ignored.add(Material.OXEYE_DAISY);
//				ignored.add(Material.PEONY);
//				ignored.add(Material.PINK_TULIP);
//				ignored.add(Material.RED_TULIP);
//				ignored.add(Material.REDSTONE_TORCH);
//				ignored.add(Material.REDSTONE_WALL_TORCH);
//				ignored.add(Material.ROSE_BUSH);
//				ignored.add(Material.ROSE_RED);
//				ignored.add(Material.SPRUCE_BUTTON);
//				ignored.add(Material.SPRUCE_SAPLING);
//				ignored.add(Material.SUNFLOWER);
//				ignored.add(Material.TALL_GRASS);
//				ignored.add(Material.WHITE_TULIP);
            }
        }

        ignored.add(Material.AIR);

        Location loc = location.getBlock().getLocation().clone();

        if (fromHeightZero) {
            loc.setY(0);

            while (loc.getY() <= loc.getWorld().getMaxHeight()) {
                loc.add(0, 1, 0);

                if (ignored.contains(loc.getBlock().getType())
                        && loc.clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
                    break;
                }
            }
        } else {
            loc.setY(loc.getWorld().getMaxHeight());

            while (loc.getY() >= 0) {
                loc.subtract(0, 1, 0);

                if (ignored.contains(loc.getBlock().getType())
                        && loc.clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
                    break;
                }
            }
        }

        return loc.clone().add(0, 1, 0).getBlock().getType() == Material.AIR && loc.getBlockY() >= 0 ? loc.getBlock()
                : null;
    }

    public static Block getFirstBlockAtXZ_NEW(Location location, boolean fromHeightZero) {
        return getFirstBlockAtXZ_NEW(location, fromHeightZero, false);
    }

    public static Block getFirstBlockAtXZ_NEW(Location location, boolean fromHeightZero, boolean fromCurrY) {
        Location loc = location.getBlock().getLocation().clone();

        ArrayList<Material> ignored = new ArrayList<>();

        ignored.add(Material.ACTIVATOR_RAIL);
        ignored.add(Material.BROWN_MUSHROOM);
        ignored.add(Material.AIR);
        ignored.add(Material.DEAD_BUSH);
        ignored.add(Material.DETECTOR_RAIL);
        ignored.add(Material.LEVER);
        ignored.add(Material.POWERED_RAIL);
        ignored.add(Material.RED_MUSHROOM);
        ignored.add(Material.REDSTONE_WIRE);
        ignored.add(Material.STONE_BUTTON);
        ignored.add(Material.TORCH);
        ignored.add(Material.TRIPWIRE);
        ignored.add(Material.TRIPWIRE_HOOK);

        try { // Pre 1.13
            ignored.add(Material.valueOf("DOUBLE_PLANT"));
            ignored.add(Material.valueOf("LONG_GRASS"));
            ignored.add(Material.valueOf("RAILS"));
            ignored.add(Material.valueOf("RED_ROSE"));
            ignored.add(Material.valueOf("REDSTONE_TORCH_OFF"));
            ignored.add(Material.valueOf("REDSTONE_TORCH_ON"));
            ignored.add(Material.valueOf("SAPLING"));
            ignored.add(Material.valueOf("WOOD_BUTTON"));
            ignored.add(Material.valueOf("YELLOW_FLOWER"));
        } catch (@SuppressWarnings("unused") Throwable th) { // 1.13
//			ignored.add(Material.ACACIA_BUTTON);
//			ignored.add(Material.ACACIA_SAPLING);
//			ignored.add(Material.ALLIUM);
//			ignored.add(Material.AZURE_BLUET);
//			ignored.add(Material.BIRCH_BUTTON);
//			ignored.add(Material.BIRCH_SAPLING);
//			ignored.add(Material.BLUE_ORCHID);
//			ignored.add(Material.DANDELION);
//			ignored.add(Material.DANDELION_YELLOW);
//			ignored.add(Material.DARK_OAK_BUTTON);
//			ignored.add(Material.DARK_OAK_SAPLING);
//			ignored.add(Material.JUNGLE_BUTTON);
//			ignored.add(Material.JUNGLE_SAPLING);
//			ignored.add(Material.LILAC);
//			ignored.add(Material.OAK_BUTTON);
//			ignored.add(Material.OAK_SAPLING);
//			ignored.add(Material.ORANGE_TULIP);
//			ignored.add(Material.OXEYE_DAISY);
//			ignored.add(Material.PEONY);
//			ignored.add(Material.PINK_TULIP);
//			ignored.add(Material.RED_TULIP);
//			ignored.add(Material.REDSTONE_TORCH);
//			ignored.add(Material.REDSTONE_WALL_TORCH);
//			ignored.add(Material.ROSE_BUSH);
//			ignored.add(Material.ROSE_RED);
//			ignored.add(Material.SPRUCE_BUTTON);
//			ignored.add(Material.SPRUCE_SAPLING);
//			ignored.add(Material.SUNFLOWER);
//			ignored.add(Material.TALL_GRASS);
//			ignored.add(Material.WHITE_TULIP);
        }

        if (fromHeightZero) {
            loc.setY(0);

            while (loc.getY() <= loc.getWorld().getMaxHeight()) {
                loc.add(0, 1, 0);

                if (!ignored.contains(loc.getBlock().getType())
                        && ignored.contains(loc.clone().add(0, 1, 0).getBlock().getType())) {
                    break;
                }
            }
        } else if (fromCurrY) {
            while (loc.getY() <= loc.getWorld().getMaxHeight()) {
                loc.add(0, 1, 0);

                if (!ignored.contains(loc.getBlock().getType())
                        && ignored.contains(loc.clone().add(0, 1, 0).getBlock().getType())) {
                    break;
                }
            }
        } else {
            loc.setY(loc.getWorld().getMaxHeight());

            while (loc.getY() >= 0) {
                loc.subtract(0, 1, 0);

                if (!ignored.contains(loc.getBlock().getType())
                        && ignored.contains(loc.clone().add(0, 1, 0).getBlock().getType())) {
                    break;
                }
            }
        }

        return loc.clone().add(0, 1, 0).getBlock().isEmpty() && loc.getBlockY() >= 0 ? loc.getBlock() : null;
    }

    /**
     * Returns a list of Locations that are on the circle.<br>
     * Imagine the circle as <i>loc</i> being the center and <i>radius</i> the
     * radius. <i>Amount</i> is the amount of locations on the circle
     *
     * @param loc    The circle's center
     * @param radius The circle's radius
     * @param amount Amount of locations
     *
     * @return A list of locations that are on the circle
     */
    public static List<Location> getCircle(Location loc, double radius, int amount) {
        List<Location> result = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            double angle = i * ((2 * Math.PI) / amount);

            double x = loc.getX() + (radius * Math.cos(angle));
            double z = loc.getZ() + (radius * Math.sin(angle));

            result.add(new Location(loc.getWorld(), x, loc.getY(), z));
        }

        return result;
    }
}