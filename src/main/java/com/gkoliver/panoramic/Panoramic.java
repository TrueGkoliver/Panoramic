package com.gkoliver.panoramic;

import com.gkoliver.panoramic.panorama.PanoramaType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Panoramic.MOD_ID)
@Mod.EventBusSubscriber(modid=Panoramic.MOD_ID)
public class Panoramic
{
    public static final String MOD_ID = "panoramic";
    private static final Logger LOGGER = LogManager.getLogger();
    public static Random rand = new Random();
    public Panoramic() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        PanoramaType.Panoramas.rref();
        PanoramaType.PanoramaRandom.action();
        setSkybox();
        MinecraftForge.EVENT_BUS.register(this);
    }
    public static void setSkybox() {
        PanoramaType type = PanoramaType.PanoramaRandom.getRandom();
        MainMenuScreen.PANORAMA_RESOURCES = new RenderSkyboxCube(type.location);
        if (type.act) {
            MainMenuScreen.PANORAMA_OVERLAY_TEXTURES  = type.hypothetical;
        }
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onLeaveWorld(EntityLeaveWorldEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity ent = (PlayerEntity) event.getEntity();
            if (ent.getName() == Minecraft.getInstance().player.getName()) {
                setSkybox();
            }
        }
    }
}
