package org.kovalexey.edt.autoopenpropossal;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.kovalexey.edt.autoopenpropossal"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	private ScopedPreferenceStore preferenceStore;
	private Settings settings;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	public ScopedPreferenceStore getPreferenceStore() {
		if (this.preferenceStore == null) {
			this.preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.PLUGIN_ID);
		}
		return this.preferenceStore;
	}
	
	public Settings getSettings() {
		if (this.settings == null) {
			this.settings = new Settings();
			this.settings.loadSettings();
		}
		return this.settings;
	}

}
