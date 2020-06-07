package de.sprax2013.advanced_dev_utils.spigot.particles;

import com.google.common.base.CaseFormat;

public enum Particle {
    EXPLODE(0), LARGEEXPLODE(1), HUGEEXPLOSION(2), FIREWORKS_SPARK(3), BUBBLE(4), SPLASH(5), WAKE(6), SUSPENDED(7),
    DEPTHSUSPEND(8), CRIT(9), MAGIC_CRIT(10), SMOKE(11), LARGESMOKE(12), SPELL(13), INSTANT_SPELL(14), MOB_SPELL(15),
    MOB_SPELL_AMBIENT(16), WITCH_MAGIC(17), DRIP_WATER(18), DRIP_LAVA(19), ANGRY_VILLAGER(20), HAPPY_VILLAGER(21),
    TOWNAURA(22), NOTE(23), PORTAL(24), ENCHANTMENTTABLE(25), FLAME(26), LAVA(27), FOOTSTEP(28), CLOUD(29), REDDUST(30),
    SNOWBALLPOOF(31), SNOWSHOVEL(32), SLIME(33), HEART(34), BARRIER(35),
    //	ICONCRACK_(ID)_(DATA)(36),
//	BLOCKCRACK_(ID+(DATA<<12))(37),
//	BLOCKDUST_(ID)(38),
    DROPLET(39), TAKE(40), MOBAPPEARANCE(41), DRAGONBREATH(42), ENDROD(43), DAMAGEINDICATOR(44), SWEEPATTACK(45),
    FALLINGDUST(46), TOTEM(47), SPIT(48);

    public final int id;

    private Particle(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        // Den Inhalt der Tabelle von: https://wiki.vg/Protocol#Particle
        final String s = "Particle Name 	Particle ID\r\n" + "explode 	0\r\n" + "largeexplode 	1\r\n"
                + "hugeexplosion 	2\r\n" + "fireworksSpark 	3\r\n" + "bubble 	4\r\n" + "splash 	5\r\n"
                + "wake 	6\r\n" + "suspended 	7\r\n" + "depthsuspend 	8\r\n" + "crit 	9\r\n"
                + "magicCrit 	10\r\n" + "smoke 	11\r\n" + "largesmoke 	12\r\n" + "spell 	13\r\n"
                + "instantSpell 	14\r\n" + "mobSpell 	15\r\n" + "mobSpellAmbient 	16\r\n" + "witchMagic 	17\r\n"
                + "dripWater 	18\r\n" + "dripLava 	19\r\n" + "angryVillager 	20\r\n" + "happyVillager 	21\r\n"
                + "townaura 	22\r\n" + "note 	23\r\n" + "portal 	24\r\n" + "enchantmenttable 	25\r\n"
                + "flame 	26\r\n" + "lava 	27\r\n" + "footstep 	28\r\n" + "cloud 	29\r\n" + "reddust 	30\r\n"
                + "snowballpoof 	31\r\n" + "snowshovel 	32\r\n" + "slime 	33\r\n" + "heart 	34\r\n"
                + "barrier 	35\r\n" + "iconcrack_(id)_(data) 	36\r\n" + "blockcrack_(id+(data<<12)) 	37\r\n"
                + "blockdust_(id) 	38\r\n" + "droplet 	39\r\n" + "take 	40\r\n" + "mobappearance 	41\r\n"
                + "dragonbreath 	42\r\n" + "endrod 	43\r\n" + "damageindicator 	44\r\n" + "sweepattack 	45\r\n"
                + "fallingdust 	46\r\n" + "totem 	47\r\n" + "spit 	48 ";

        String[] lines = s.split("\r\n");

        for (String line : lines) {
            String[] info = line.trim().split("	");

            String name = null;
            int id = 0;
            for (String i : info) {
                i = i.trim();

                if (!i.isEmpty()) {
                    if (i.matches("^[0-9]*$")) {
                        id = Integer.parseInt(i);
                    } else {
                        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, i);
                    }
                }
            }

            System.out.println(name + "(" + id + "),");
        }
    }
}