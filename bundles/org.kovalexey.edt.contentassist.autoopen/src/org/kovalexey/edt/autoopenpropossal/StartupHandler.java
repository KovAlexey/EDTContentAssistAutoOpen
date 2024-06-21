package org.kovalexey.edt.autoopenpropossal;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorReference;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;

import com._1c.g5.v8.dt.md.ui.editor.base.DtGranularEditor;
import com._1c.g5.v8.dt.md.ui.editor.base.DtGranularEditorXtextEditorPage;

public class StartupHandler implements IStartup {
	
	
	private static HashMap<SourceViewer, IDocumentListener> listeners = new HashMap<SourceViewer, IDocumentListener>();

	public static IPageChangedListener pageChangeListener = new IPageChangedListener() {
		
		@Override
		public void pageChanged(PageChangedEvent event) {
			var page = event.getSelectedPage();
			if (page instanceof DtGranularEditorXtextEditorPage<?>)
			{
				var xtextPage = (DtGranularEditorXtextEditorPage<?>)page;
				SourceViewer editor = (SourceViewer) ((XtextEditor)xtextPage.getEmbeddedEditor()).getInternalSourceViewer();

				
				ContentAssistant contentAssist = SourceViewerPatcher.getContentAssistant(editor);

				Settings scopePreferenceStore = Activator.getDefault().getSettings();
				int timeout = scopePreferenceStore.getTimeout();
				String charset = scopePreferenceStore.getCharset();
				
				contentAssist.setAutoActivationDelay(timeout);
				var contentAssistProcessor = (XtextContentAssistProcessor)contentAssist.getContentAssistProcessor("__dftl_partition_content_type");
				contentAssistProcessor.setCompletionProposalAutoActivationCharacters(charset);
				
			} else {
				for (SourceViewer viewer : listeners.keySet()) {
					IDocument document = viewer.getDocument();
					if (document != null) {
						viewer.getDocument().removeDocumentListener(listeners.get(viewer));
					}
				}
				listeners.clear();
			}
			
		}
	};

	@Override
	public void earlyStartup() {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(new IPartListener2() {

					@Override
					public void partOpened(IWorkbenchPartReference partRef) {
						// TODO Auto-generated method stub
						if (partRef instanceof EditorReference) {
							
							
							IEditorReference editorReference = (IEditorReference)partRef;
							var part = partRef.getPart(false);
							if (part instanceof DtGranularEditor<?>) {
								var editorPart = (DtGranularEditor<?>)part;
								
								editorPart.removePageChangedListener(pageChangeListener);
								editorPart.addPageChangedListener(pageChangeListener);
								
								
							}
						}

					}
					
				});
			}
		});

	}

}
