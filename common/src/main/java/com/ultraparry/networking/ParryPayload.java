package com.ultraparry.networking;

import com.ultraparry.ULTRAPARRY;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ParryPayload(boolean bl) implements CustomPayload {
public static final CustomPayload.Id<ParryPayload> ID = new CustomPayload.Id<>(ULTRAPARRY.PARRY_C2S_ID);
public static final PacketCodec<RegistryByteBuf, ParryPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOLEAN, ParryPayload::bl, ParryPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
