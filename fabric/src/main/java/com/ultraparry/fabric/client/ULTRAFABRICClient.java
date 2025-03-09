package com.ultraparry.fabric.client;


import com.ultraparry.ULTRAPARRY;
import com.ultraparry.ULTRAPARRYClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.joml.Matrix4f;

public final class ULTRAFABRICClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        HudRenderCallback.EVENT.register(((drawContext, tickDeltaManager) -> {
            ULTRAPARRYClient.onHudRender(drawContext);
        }));
    }
}
