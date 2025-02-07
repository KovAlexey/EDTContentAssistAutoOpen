package org.kovalexey.edt.autoopenpropossal;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.osgi.framework.BundleContext;

import com._1c.g5.v8.dt.bsl.ui.editor.BslXtextEditor;

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
		init();
	}

	public void init() {
		
		Display display = Display.getDefault();
		if (display != null) {
			display.syncExec(new Runnable() {
				
				@Override
				public void run() {
					IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
					for (IWorkbenchWindow iWorkbenchWindow : windows) {
						for (IWorkbenchPage page : iWorkbenchWindow.getPages()) {
							for (IEditorReference editorReference : page.getEditorReferences()) {
								IWorkbenchPart editorPart = editorReference.getPart(false);
								if (editorPart instanceof BslXtextEditor) {
									SourceViewer viewer = (SourceViewer) ((XtextEditor) editorPart).getInternalSourceViewer();
									SourceViewerPatcher.ApplySourceViewPatch(viewer);
								}
							}
						}
						iWorkbenchWindow.getPartService().addPartListener(ListenersImpl.PartOpenedListener);
					}
					PlatformUI.getWorkbench().addWindowListener(ListenersImpl.windowsListener);
				}
			});
		}
	
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
