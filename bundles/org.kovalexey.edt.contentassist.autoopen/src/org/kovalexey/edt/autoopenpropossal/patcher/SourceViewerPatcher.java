package org.kovalexey.edt.autoopenpropossal.patcher;

import java.lang.reflect.Field;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;
import org.kovalexey.edt.autoopenpropossal.logger.PluginLogger;
import com.google.inject.Inject;

public class SourceViewerPatcher implements ISourceViewPatch {
	@Inject
	private PluginLogger logger;
	private static Field contentAssistantField;
	
	@Override
	public Boolean applyXTextSourceViewPatch(SourceViewer sourceView, int timeout, String charset) {
		ContentAssistant contentAssist = getContentAssistant(sourceView);
		if (contentAssist == null) {
			return false;
		}
		
		IContentAssistProcessor contentAssistProcessor = contentAssist.getContentAssistProcessor(IDocument.DEFAULT_CONTENT_TYPE);
		XtextContentAssistProcessor xtextContentAssistProcessor = null;
		if (contentAssistProcessor instanceof XtextContentAssistProcessor) {
			xtextContentAssistProcessor = (XtextContentAssistProcessor)contentAssistProcessor;
		}
		if (xtextContentAssistProcessor == null) {
			return false;
		}
		
		xtextContentAssistProcessor.setCompletionProposalAutoActivationCharacters(charset);
		contentAssist.setAutoActivationDelay(timeout);
		
		return true;
	}
	
	private ContentAssistant getContentAssistant(SourceViewer sourceViewer) {
		if (contentAssistantField == null) {
			if (!applyPatchClass()) {
				return null;
			}
		}
		
		try {
			return (ContentAssistant)contentAssistantField.get(sourceViewer);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(e);
		}
		
		return null;
	}
	
	private Boolean applyPatchClass() {
		Class<SourceViewer> sourceViewerClass = SourceViewer.class;
		
		try {
			logger.log("Включаем доступность fContentAssistant у SourveViewer");
			Field field = sourceViewerClass.getDeclaredField("fContentAssistant");
			field.setAccessible(true);
			contentAssistantField = field;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(e);
			return false;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(e);
			return false;
		}
		
		logger.log("Патч успешно применен");
		
		return true;
	}

}
