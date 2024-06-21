package org.kovalexey.edt.autoopenpropossal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class ProposalSettingsInitializer extends AbstractPreferenceInitializer {
	
	public ProposalSettingsInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		ScopedPreferenceStore scopedPreferenceStore = Activator.getDefault().getPreferenceStore();
		scopedPreferenceStore.setDefault(Settings.SETTINGS_TIMEOUT, 500);
		scopedPreferenceStore.setDefault(Settings.SETTINGS_CHARSET, ".");
	}

}
