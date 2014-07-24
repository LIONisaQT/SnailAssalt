package com.missionbit.snailassalt.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.missionbit.snailassalt.SnailAssalt;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.hideStatusBar=true;
        config.useImmersiveMode=true;
        config.useWakelock=true;
		initialize(new SnailAssalt(), config);
	}
}
