package com.gkoliver.panoramic.panorama;

import com.gkoliver.panoramic.Panoramic;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;

public class PanoramaType {
    public ResourceLocation location = null;
    public ResourceLocation hypothetical = null;
    public String modId = null;
    public int weight = 0;
    public float trueWeight = 0.0F;
    public boolean act = false;
    public PanoramaType(String id, int weight) {
        this.location = new ResourceLocation(Panoramic.MOD_ID, "textures/gui/title/background/"+id+"/panorama");
        this.hypothetical = new ResourceLocation(Panoramic.MOD_ID, "textures/gui/title/background/"+id+"/panorama_overlay.png");
        this.weight = weight;
        PanoramaRandom.ALL_PANORAMAS.add(this);
    }
    public PanoramaType(String id, int weight, String modId) {
        this(id, weight);
        this.modId = modId;
        this.location = new ResourceLocation(Panoramic.MOD_ID, "textures/gui/title/background/mod_compat/"+modId+"/"+id+"/panorama");
    }
    public PanoramaType(String id, int weight, boolean shouldBeTrue) {
        this(id, weight);
        this.act = true;
    }
    public static class PanoramaRandom {
        public static int TOTAL_WEIGHT = 0;
        public static ArrayList<PanoramaType> ACTIONABLE_PANORAMAS = Lists.newArrayList();
        public static ArrayList<PanoramaType> ALL_PANORAMAS = Lists.newArrayList();
        public static boolean action() {
            for (PanoramaType type : ALL_PANORAMAS) {
                if (type.modId==null) {
                    ACTIONABLE_PANORAMAS.add(type);
                } else {
                    if (ModList.get().isLoaded(type.modId)) {
                        ACTIONABLE_PANORAMAS.add(type);
                    }
                }
            }
            for (PanoramaType type : ACTIONABLE_PANORAMAS) {
                TOTAL_WEIGHT+=type.weight;
            }
            for (PanoramaType type : ACTIONABLE_PANORAMAS) {
                type.trueWeight = type.weight/TOTAL_WEIGHT;
            }
            return false;
        }
        public static PanoramaType getRandom() {
            float weightIndex = Panoramic.rand.nextFloat();
            float creativeI = 0.0F;
            float bb = 0.0F;
            for (int i = 0; i < ACTIONABLE_PANORAMAS.size(); i++) {
                int index = i;

                PanoramaType typeIn = ACTIONABLE_PANORAMAS.get(i);
                //bb+=typeIn.getAdjustedWeight();
                //System.out.println("!!!: "+String.valueOf(bb));
                if (index!=0) {

                    PanoramaType before = ACTIONABLE_PANORAMAS.get(index-1);
                    float beforeWeight = before.getAdjustedWeight();
                    float adjustedWeight = typeIn.getAdjustedWeight();
                    creativeI+=adjustedWeight;
                    System.out.println("NEW ONE");
                    System.out.println(beforeWeight);
                    System.out.println(creativeI);
                    System.out.println(weightIndex);
                    if ((beforeWeight < weightIndex) && (weightIndex <= creativeI)) {
                        return typeIn;
                    }

                } else {
                    if ((weightIndex <= typeIn.getAdjustedWeight())) {
                        creativeI+=typeIn.getAdjustedWeight();
                        return typeIn;
                    }
                }
            }
            return null;
        }
    }
    public float getAdjustedWeight() {
        return ((float)weight)/((float) PanoramaRandom.TOTAL_WEIGHT);
    }
    public static boolean between(int num, int bound1, int bound2) {
        return (bound1<=num)&&(num>=bound2);
    }
    public static class Panoramas {
        public static final PanoramaType BUZZY_BEES = new PanoramaType("buzzy_bees", 25, true);
        public static final PanoramaType VILLAGE_AND_PILLAGE = new PanoramaType("village_and_pillage", 20, true);
        public static final PanoramaType UPDATE_AQUATIC = new PanoramaType("update_aquatic", 22, true);
        public static final PanoramaType NETHER_UPDATE = new PanoramaType("nether_update", 10);

        public static final PanoramaType WOODLAND_MANSION = new PanoramaType("woodland_mansion", 7);
        public static final PanoramaType DESERT = new PanoramaType("desert", 19);
        public static final PanoramaType ICE_SPIKES = new PanoramaType("ice_spikes", 5);
        public static final PanoramaType SNOWY = new PanoramaType("snowy", 15);
        public static final PanoramaType CAVE = new PanoramaType("cave", 15);
        public static final PanoramaType CHORUS = new PanoramaType("chorus", 3);
        public static final PanoramaType END_CITY = new PanoramaType("end_city", 2);
        public static final PanoramaType ICEBERG = new PanoramaType("iceberg", 8);
        public static final PanoramaType JUNGLE = new PanoramaType("jungle", 10);
        public static final PanoramaType MUSHROOM = new PanoramaType("mushroom", 3);
        public static final PanoramaType LEGACY = new PanoramaType("legacy", 6);

        public static final PanoramaType DUNES = new PanoramaType("dunes", 18, "atmospheric");
        public static final PanoramaType RAINFOREST = new PanoramaType("rainforest", 15, "atmospheric");
        public static final PanoramaType RAINFOREST_BASIN = new PanoramaType("rainforest_basin", 15, "atmospheric");

        public static final PanoramaType POISE = new PanoramaType("poise", 3, "endergetic");
        public static final PanoramaType CORROCK_PLAINS = new PanoramaType("corrock_plains", 3, "endergetic");

        public static final PanoramaType BLOSSOM = new PanoramaType("blossom", 15, "environmental");
        public static final PanoramaType FLOWER_FOREST = new PanoramaType("flower_forest", 15, "environmental");
        public static final PanoramaType MARSH = new PanoramaType("marsh", 20, "environmental");

        public static final PanoramaType GLOWSHROOM = new PanoramaType("glowshroom_caves", 4, "quark");
        public static final PanoramaType MEGA_CAVE = new PanoramaType("mega_cave", 13, "quark");
        public static int m = 0;
        public static void rref() {
            m = 1;
        }

    }




}
