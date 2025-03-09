package com.ultraparry;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Supplier;

public class Sounds {

    private static final Registrar<SoundEvent> soundEvents = ULTRAPARRY.REGISTRAR_MANAGER.get().get(RegistryKeys.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> PARRY_SOUND =soundEvents.register(Identifier.of(ULTRAPARRY.MOD_ID, "parry"),()->{return SoundEvent.of(Identifier.of(ULTRAPARRY.MOD_ID, "parry"));});

    public static void registerSounds(){

    }



}
