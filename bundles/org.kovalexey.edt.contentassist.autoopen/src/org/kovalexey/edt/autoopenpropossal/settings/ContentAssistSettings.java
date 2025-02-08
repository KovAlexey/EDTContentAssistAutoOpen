package org.kovalexey.edt.autoopenpropossal.settings;

import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.kovalexey.edt.autoopenpropossal.Activator;
import org.kovalexey.edt.autoopenpropossal.listeners.IEditorsListenerManager;

import com.google.inject.Inject;

public class ContentAssistSettings implements IContentAssistSettings {
	public static final String SETTINGS_ENABLED = "enabled";
	public static final String SETTINGS_CHARSET = "charset";
	public static final String SETTINGS_TIMEOUT = "timeout";
	public static final IScopeContext SCOPE_CONTEXT = InstanceScope.INSTANCE;

	private Boolean enabled = false;
	private int timeout = 500;
	private String charset = ".";
	private ScopedPreferenceStore preferenceStore;
	
	@Inject
	private IEditorsListenerManager listenerManager;
	
	public ContentAssistSettings() {
		this.preferenceStore = new ScopedPreferenceStore(SCOPE_CONTEXT, Activator.PLUGIN_ID);
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
				listenerManager.applyPatchToOpenedParts();
			}
		});
	}
	
	public ScopedPreferenceStore getPreferenceStore() {
		return this.preferenceStore;
	}
	
	public void loadSettings() {
		this.timeout = preferenceStore.getInt(SETTINGS_TIMEOUT);
		this.charset = preferenceStore.getString(SETTINGS_CHARSET);
		this.enabled = preferenceStore.getBoolean(SETTINGS_ENABLED);
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
	
	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
