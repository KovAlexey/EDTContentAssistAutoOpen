package org.kovalexey.edt.autoopenpropossal;

import java.io.IOException;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class Settings {
	public static final String SETTINGS_ENABLED = "enabled";
	public static final String SETTINGS_CHARSET = "charset";
	public static final String SETTINGS_TIMEOUT = "timeout";
	public static final IScopeContext SCOPE_CONTEXT = ConfigurationScope.INSTANCE;

	private Boolean enabled = false;
	private int timeout = 500;
	private String charset = ".";
	private ScopedPreferenceStore preferenceStore;
	
	public Settings() {
		this.preferenceStore = Activator.getDefault().getPreferenceStore();
		this.preferenceStore.addPropertyChangeListener(new IPropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				switch (event.getProperty()) {
				case SETTINGS_CHARSET:
					setCharset((String)event.getNewValue());
					break;
				
				case SETTINGS_ENABLED:
					setEnabled((Boolean)event.getNewValue());
					break;
					
				case SETTINGS_TIMEOUT:
					setTimeout((Integer)event.getNewValue());
					break;
					
				default:
					break;
				}
			}
		});
	}
	
	
	
	public ScopedPreferenceStore getPreferenceStore() {
		return this.preferenceStore;
	}
	
	public void loadSettings() {
		this.timeout = preferenceStore.getInt(Settings.SETTINGS_TIMEOUT);
		this.charset = preferenceStore.getString(Settings.SETTINGS_CHARSET);
		this.enabled = preferenceStore.getBoolean(Settings.SETTINGS_ENABLED);
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getTimeout() {
		return timeout;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
