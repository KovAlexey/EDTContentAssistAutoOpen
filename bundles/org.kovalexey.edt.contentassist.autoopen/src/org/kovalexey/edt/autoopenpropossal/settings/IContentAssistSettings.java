package org.kovalexey.edt.autoopenpropossal.settings;

import org.eclipse.ui.preferences.ScopedPreferenceStore;

public interface IContentAssistSettings {
	public void loadSettings();
	public ScopedPreferenceStore getPreferenceStore();
	public void setTimeout(int timeout);
	public String getCharset();
	public void setCharset(String charset);
	public int getTimeout();
	public Boolean isEnabled();
	public void setEnabled(Boolean enabled);
}
