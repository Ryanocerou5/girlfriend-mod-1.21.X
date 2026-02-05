package github.ryanocerou5.girlfriendmod;

import github.ryanocerou5.girlfriendmod.init.KeybindInit;
import github.ryanocerou5.girlfriendmod.init.ScreenHandlerInit;
import github.ryanocerou5.girlfriendmod.init.SoundEventInit;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GirlfriendMod implements ModInitializer {
	public static final String MOD_ID = "girlfriendmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		KeybindInit.registerKeybinds();
		ScreenHandlerInit.registerScreenHandlers();
		SoundEventInit.registerSoundEvents();
	}
}