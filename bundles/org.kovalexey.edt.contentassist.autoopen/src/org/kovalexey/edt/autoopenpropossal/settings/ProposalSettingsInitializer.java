package org.kovalexey.edt.autoopenpropossal.settings;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import com.google.inject.Inject;

public class ProposalSettingsInitializer extends AbstractPreferenceInitializer {
	
	@Inject
	IContentAssistSettings settings;
	
	public ProposalSettingsInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		ScopedPreferenceStore scopedPreferenceStore = settings.getPreferenceStore();
		scopedPreferenceStore.setDefault(ContentAssistSettings.SETTINGS_TIMEOUT, 500);
		scopedPreferenceStore.setDefault(ContentAssistSettings.SETTINGS_CHARSET, ".");
	}

}
