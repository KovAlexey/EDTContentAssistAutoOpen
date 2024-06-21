package org.kovalexey.edt.autoopenpropossal;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.EditorReference;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com._1c.g5.v8.dt.bsl.ui.editor.BslXtextEditor;
import com._1c.g5.v8.dt.md.ui.editor.base.DtGranularEditor;
import com._1c.g5.v8.dt.md.ui.editor.base.DtGranularEditorXtextEditorPage;

public class ListenersImp {
	
	public static IPageChangedListener PageChangedListener = new IPageChangedListener() {
		@Override
		public void pageChanged(PageChangedEvent event) {
			var page = event.getSelectedPage();
			if (page instanceof DtGranularEditorXtextEditorPage<?>)
			{
				DtGranularEditorXtextEditorPage<?> xtextPage = (DtGranularEditorXtextEditorPage<?>)page;
				SourceViewer editor = (SourceViewer) ((XtextEditor)xtextPage.getEmbeddedEditor()).getInternalSourceViewer();
				SourceViewerPatcher.ApplySourceViewPatch(editor);
			}
		}
	};
	
	public static IPartListener2 PartOpenedListener = new IPartListener2() {
		@Override
		public void partOpened(IWorkbenchPartReference partRef) {

			if (partRef instanceof EditorReference) {
				IEditorReference editorReference = (IEditorReference)partRef;
				IWorkbenchPart part = partRef.getPart(false);
				if (part instanceof DtGranularEditor<?>) {
					DtGranularEditor<?> editorPart = (DtGranularEditor<?>)part;
					
					editorPart.removePageChangedListener(PageChangedListener);
					editorPart.addPageChangedListener(PageChangedListener);
				} else if (part instanceof BslXtextEditor) {
					BslXtextEditor editorPart = (BslXtextEditor)part;
					SourceViewerPatcher.ApplySourceViewPatch((SourceViewer)editorPart.getInternalSourceViewer());
				}
			}

		}
	};
	
	public static IWindowListener windowsListener = new IWindowListener() {
			@Override
			public void windowOpened(IWorkbenchWindow window) {
				window.getPartService().addPartListener(PartOpenedListener);
			}

			@Override
			public void windowActivated(IWorkbenchWindow window) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(IWorkbenchWindow window) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(IWorkbenchWindow window) {
				window.getPartService().removePartListener(PartOpenedListener);
				
			}
	};

}
