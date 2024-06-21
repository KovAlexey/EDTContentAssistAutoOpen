package org.kovalexey.edt.autoopenpropossal;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class SettingsPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public SettingsPage() {
		super();
	}

	public SettingsPage(int style) {
		super(style);

	}

	public SettingsPage(String title, int style) {
		super(title, style);
	}

	public SettingsPage(String title, ImageDescriptor image, int style) {
		super(title, image, style);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(Settings.SETTINGS_ENABLED, "Включено", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Settings.SETTINGS_TIMEOUT, "Задержка", getFieldEditorParent()));
		addField(new StringFieldEditor(Settings.SETTINGS_CHARSET, "Символы", getFieldEditorParent()));
	}

}
