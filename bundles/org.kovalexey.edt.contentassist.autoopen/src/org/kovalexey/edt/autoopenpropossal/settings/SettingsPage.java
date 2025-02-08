package org.kovalexey.edt.autoopenpropossal.settings;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import com.google.inject.Inject;

public class SettingsPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	@Inject
	IContentAssistSettings settings;

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
		setPreferenceStore(settings.getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(ContentAssistSettings.SETTINGS_ENABLED, "Включено", getFieldEditorParent()));
		addField(new IntegerFieldEditor(ContentAssistSettings.SETTINGS_TIMEOUT, "Задержка", getFieldEditorParent()));
		addField(new StringFieldEditor(ContentAssistSettings.SETTINGS_CHARSET, "Символы", getFieldEditorParent()));
	}

}
