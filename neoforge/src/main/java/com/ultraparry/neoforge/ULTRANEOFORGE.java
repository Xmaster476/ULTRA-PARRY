package com.ultraparry.neoforge;

import com.ultraparry.ULTRAPARRYClient;
import net.minecraft.client.gui.DrawContext;
import net.neoforged.fml.common.Mod;

import com.ultraparry.ULTRAPARRY;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;

import javax.swing.*;

@Mod(ULTRAPARRY.MOD_ID)
public final class ULTRANEOFORGE {
    public ULTRANEOFORGE() {
        // Run our common setup.
        ULTRAPARRY.init();
        NeoForge.EVENT_BUS.addListener(ULTRANEOFORGE::preRenderHud);
    }


    private static void preRenderHud(RenderGuiEvent.Pre event){
        DrawContext drawContext = event.getGuiGraphics();
        ULTRAPARRYClient.onHudRender(drawContext);
    }
}
