package org.kovalexey.edt.autoopenpropossal.listeners;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPage;
import org.kovalexey.edt.autoopenpropossal.patcher.ISourceViewPatch;
import org.kovalexey.edt.autoopenpropossal.settings.IContentAssistSettings;

import com._1c.g5.v8.dt.bsl.ui.editor.BslXtextEditor;
import com._1c.g5.v8.dt.md.ui.editor.base.DtGranularEditor;
import com._1c.g5.v8.dt.md.ui.editor.base.DtGranularEditorXtextEditorPage;
import com.google.inject.Inject;

public class EditorsListenerManager implements IEditorsListenerManager {
	
	private WindowListener windowListener = new WindowListener();

	@Inject
	private IListenersManager listenersManager;
	@Inject
	private ISourceViewPatch patcher;
	@Inject
	private IContentAssistSettings proposalPatchSettings;
	
	public EditorsListenerManager() {
		

	}
	
	public void init() {
		proposalPatchSettings.loadSettings();
		Display display = Display.getDefault();
		if (display != null) {
			
			display.syncExec(new Runnable() {
							
				@Override
				public void run() {
					applyPatchToOpenedParts();
					PlatformUI.getWorkbench().addWindowListener(windowListener);
				}
			
			});
		}
		
		
		
	}
	
	public void stop() {
		PlatformUI.getWorkbench().removeWindowListener(windowListener);
	}
	
	public void applyPatchToOpenedParts() {
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (IWorkbenchWindow iWorkbenchWindow : windows) {
			applyPatchToOpenedParts(iWorkbenchWindow);
			listenersManager.addPartListener(iWorkbenchWindow.getPartService(), new PartCloseOpenListener());
		}
	}
	
	public void applyPatchToOpenedParts(IWorkbenchWindow window) {
		for (IWorkbenchPage page : window.getPages()) {
			IWorkbenchPartReference partReference = page.getActivePartReference();
			IWorkbenchPart part = partReference.getPart(false);
			if (part instanceof DtGranularEditor<?>) {
				DtGranularEditor<?> editorPart = (DtGranularEditor<?>)part;
				applyPatchToXtextEditor(editorPart);
				
			} 
		}
	}
	
	private class PartCloseOpenListener implements IPartListener2 {

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			
			IWorkbenchPart part = partRef.getPart(false);
			if (part instanceof DtGranularEditor<?>) {
				DtGranularEditor<?> editorPart = (DtGranularEditor<?>)part;
				listenersManager.removePageListeners(editorPart);
			}
			
			IPartListener2.super.partClosed(partRef);
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			IPartListener2.super.partOpened(partRef);
			
			IWorkbenchPart part = partRef.getPart(false);
			
			if (part instanceof DtGranularEditor<?>) {
				DtGranularEditor<?> editorPart = (DtGranularEditor<?>)part;
				applyPatchToXtextEditor(editorPart);
				
			} else if (part instanceof BslXtextEditor) {
				applyPatchToXtextEditor((BslXtextEditor)part);
			}
		}
		
	}
	
	private void applyPatchToXtextEditor(DtGranularEditor<?> editor) {
		IEditorPart activeEditor = editor.getActiveEditor();
		
		// Только редактор BSL 
		if (activeEditor instanceof BslXtextEditor) {
			applyPatchToXtextEditor((BslXtextEditor)activeEditor);
		}
		
		listenersManager.addPageListener(editor, new  PageChangeListener());
	}
	
	private void applyPatchToXtextEditor(BslXtextEditor editor) {
		ISourceViewer viewer = editor.getInternalSourceViewer();
		if (viewer instanceof SourceViewer) {
			SourceViewer sourceViewer = (SourceViewer)viewer;
			patcher.applyXTextSourceViewPatch(sourceViewer, proposalPatchSettings.getTimeout(), proposalPatchSettings.getCharset());
		}
		
	}
	
	private class PageChangeListener implements IPageChangedListener {
			
		@Override
		public void pageChanged(PageChangedEvent event) {
			Object page = event.getSelectedPage();
			
			if (page instanceof DtGranularEditorXtextEditorPage<?>)
			{
				DtGranularEditorXtextEditorPage<?> xtextPage = (DtGranularEditorXtextEditorPage<?>)page;
				IEditorPart editorPart = xtextPage.getEmbeddedEditor();
				if (editorPart instanceof BslXtextEditor) {
					applyPatchToXtextEditor((BslXtextEditor)editorPart);
				}
			}
		}
		
	}

	
	private class WindowListener implements IWindowListener {
		
		@Override
		public void windowActivated(IWorkbenchWindow window) {
		}

		@Override
		public void windowOpened(IWorkbenchWindow window) {
			listenersManager.addPartListener(window.getPartService(), new PartCloseOpenListener());
		}

		@Override
		public void windowDeactivated(IWorkbenchWindow window) {
		}

		@Override
		public void windowClosed(IWorkbenchWindow window) {
			listenersManager.removePartListener(window.getPartService());
		}

	}
	
}
