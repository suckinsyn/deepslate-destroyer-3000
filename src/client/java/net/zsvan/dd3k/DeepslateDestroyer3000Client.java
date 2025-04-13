package net.zsvan.dd3k;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class DeepslateDestroyer3000Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex == 0 && DeepslateDestroyer3000.DEEPSLATE_DESTROYER.isBuffActive(stack)) {
                // Pulsing red effect
                double time = System.currentTimeMillis() % 2000 / 1000.0;
                float pulse = (float) (Math.sin(time * Math.PI) * 0.5 + 0.5);
                // Blend between white (0xFFFFFFFF) and red (0xFFFF0000)
                int red = 255;
                int green = (int) ((1 - pulse) * 255);
                int blue = green;
                return 0xFF000000 | (red << 16) | (green << 8) | blue;
            }
            return 0xFFFFFFFF; // White (no tint)
        }, DeepslateDestroyer3000.DEEPSLATE_DESTROYER);
    }
}