package com.Latedozer.dontsleepmod;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod("dontsleep")
public class DontSleep {
    public int tries = 0;
    public DontSleep() {
        MinecraftForge.EVENT_BUS.register(this);

    }
    @SubscribeEvent
    public void event(WorldEvent.Load event){

    }
    @SubscribeEvent
    public void event(PlayerSleepInBedEvent event) {
        ServerPlayerEntity player2 = (ServerPlayerEntity) event.getPlayer();
        if (player2.world.getDimensionKey() == World.OVERWORLD) {
            PlayerEntity player = event.getPlayer();
            if (tries >= 2) {
                player.sendStatusMessage(new StringTextComponent("Your sleepiness consumes you. You know you won't wake up"), true);
            }
            if (tries < 2) {
                event.setResult(PlayerEntity.SleepResult.NOT_POSSIBLE_HERE);
                ++tries;
                player.sendStatusMessage(new StringTextComponent("You know you shouldn't lay down, they always come back"), true);
            }
            Minecraft.getInstance().player.sendChatMessage("/spawnpoint");
        }
    }

   @SubscribeEvent
    public void event(SleepFinishedTimeEvent event) {
        event.setTimeAddition(event.getWorld().getWorldInfo().getDayTime());
    }
    @SubscribeEvent
    public  void event(PlayerWakeUpEvent event){
        PlayerEntity player = event.getPlayer();
        player.setHealth(0);
        player.sendStatusMessage(new StringTextComponent("You slept and your night terrors caught up to you"), true);
    }
}


