package com.ultraparry;

import com.ultraparry.networking.ParryPayload;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public final class ULTRAPARRYClient {

    public static final KeyBinding PARRY_KEYBIND = new KeyBinding("key.ultraparry.parry", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_R, "category.ultraparry.ultraparry");

    public static int parryTicks = 0;
    private static int screenFlashTicks =0;

    public static void init(){
        KeyMappingRegistry.register(PARRY_KEYBIND);

        ClientTickEvent.CLIENT_POST.register((minecraft) -> {
            boolean bl = false;
            while (PARRY_KEYBIND.wasPressed()){
                NetworkManager.sendToServer(new ParryPayload(true));
                parryTicks = 4;
                bl = true;
            }
            if (!bl && parryTicks>0){
                --parryTicks;
            }
        });

        ClientTickEvent.CLIENT_PRE.register((client)->{
            if(client.player != null && parryTicks > 0 && client.world != null){
                Box box = client.player.getBoundingBox().expand(3);
                List<Entity> projectiles = client.world.getOtherEntities(client.player, box, entity -> {
                    return entity instanceof ProjectileEntity projectileEntity && !projectileEntity.getVelocity().equals(new Vec3d(0, 0, 0));
                });
                if (!projectiles.isEmpty()) {
                    screenFlashTicks = 7;
                }
            }
        });

    }

    public static void onHudRender(DrawContext context){
        if (screenFlashTicks>0) context.fill(0,0, context.getScaledWindowWidth(), context.getScaledWindowHeight(), 0x88ffffff);
        screenFlashTicks--;
    }
}
