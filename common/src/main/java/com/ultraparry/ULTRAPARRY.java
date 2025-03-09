package com.ultraparry;

import com.google.common.base.Suppliers;
import com.ultraparry.networking.ParryPayload;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.utils.Env;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ULTRAPARRY {
    public static final String MOD_ID = "ultraparry";


    public static final Identifier PARRY_C2S_ID = Identifier.of("ultraparry", "parry_c2s");

    public static HashMap<UUID, Integer> parryTicksList = new HashMap<UUID, Integer>();

    public static final Supplier<RegistrarManager> REGISTRAR_MANAGER = Suppliers.memoize(()->{return RegistrarManager.get(MOD_ID);});

    public static void init() {
        // Write common init code here.
        Sounds.registerSounds();

        NetworkManager.registerReceiver(NetworkManager.Side.C2S,ParryPayload.ID, ParryPayload.CODEC, new ParryReceiver());

        if(Platform.getEnvironment()==Env.CLIENT){
            ULTRAPARRYClient.init();
        }

        PlayerEvent.PLAYER_JOIN.register(player -> {
            parryTicksList.put(player.getUuid(), 0);
        });
        PlayerEvent.PLAYER_QUIT.register(player -> {
            parryTicksList.remove(player.getUuid());
        });

        TickEvent.SERVER_LEVEL_PRE.register(world -> {
            for (ServerPlayerEntity player  : world.getPlayers()){
                if (parryTicksList.get(player.getUuid()) > 0){
                    Box box = player.getBoundingBox().expand(3);
                    List<Entity> projectiles = world.getOtherEntities(player, box, new ProjectilePredicate());

                    if(!projectiles.isEmpty()){
                        world.playSoundFromEntity(null, player, Sounds.PARRY_SOUND.get() , SoundCategory.PLAYERS, 2.0f,1.0f);
                        parryTicksList.put(player.getUuid(), 0);
                    } else {
                        parryTicksList.put(player.getUuid(), parryTicksList.get(player.getUuid())-1);
                    }

                    for(Entity entity : projectiles){
                        ProjectileEntity projectile = (ProjectileEntity) entity;
                        Vec3d playerLookVector = player.getRotationVector();
                        SmallFireballEntity fireball = new SmallFireballEntity(projectile.getWorld(), player.getX(), player.getY() + 1 , player.getZ(), playerLookVector);
                        SmallFireballEntity.spawn(fireball, player.getServerWorld(), ItemStack.EMPTY);
                        projectile.discard();
                    }

                }
            }
        });
    }



    public static class ParryReceiver implements NetworkManager.NetworkReceiver<ParryPayload> {
        @Override
        public void receive(ParryPayload value, NetworkManager.PacketContext context) {
            parryTicksList.put(context.getPlayer().getUuid(), 4);
        }
    }

    static class ProjectilePredicate implements Predicate<Entity> {

        @Override
        public boolean test(Entity entity) {
            return entity instanceof ProjectileEntity projectileEntity && !projectileEntity.getVelocity().equals(new Vec3d(0,0,0));
        }
    }
}
