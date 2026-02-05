package github.ryanocerou5.girlfriendmod.init;

import github.ryanocerou5.girlfriendmod.GirlfriendMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundEventInit {
    public static final SoundEvent GIRLFRIEND_POPUP = registerSoundEvent("girlfriend_popup");
    public static final SoundEvent GIRLFRIEND_DISAPPEAR = registerSoundEvent("girlfriend_disappear");

    private static SoundEvent registerSoundEvent(String name)
    {
        Identifier id = Identifier.of(GirlfriendMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSoundEvents()
    {
        GirlfriendMod.LOGGER.info("Registering Sound Events for " + GirlfriendMod.MOD_ID);
    }
}
