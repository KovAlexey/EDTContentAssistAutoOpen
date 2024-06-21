package org.kovalexey.edt.autoopenpropossal;

import java.lang.reflect.Field;

import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;

public class SourceViewerPatcher {
	private static Field contentAssistantField;
	
	public static Boolean ApplyPatchClass() {
		Class<SourceViewer> sourceViewerClass = SourceViewer.class;
		
		try {
			Field field = sourceViewerClass.getDeclaredField("fContentAssistant");
			field.setAccessible(true);
			contentAssistantField = field;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static ContentAssistant GetContentAssistant(SourceViewer sourceViewer) {
		if (contentAssistantField == null) {
			if (!ApplyPatchClass()) {
				return null;
			}
		}
		
		try {
			return (ContentAssistant)contentAssistantField.get(sourceViewer);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Boolean ApplySourceViewPatch(SourceViewer sourceView) {
		Settings settings = Activator.getDefault().getSettings();
		if (!settings.getEnabled()) {
			return false;
		}
		ContentAssistant contentAssist = SourceViewerPatcher.GetContentAssistant(sourceView);
		if (contentAssist == null) {
			return false;
		}


		int timeout = settings.getTimeout();
		String charset = settings.getCharset();
		
		contentAssist.setAutoActivationDelay(timeout);
		var contentAssistProcessor = (XtextContentAssistProcessor)contentAssist.getContentAssistProcessor("__dftl_partition_content_type");
		contentAssistProcessor.setCompletionProposalAutoActivationCharacters(charset);
		
		return true;
	}

}
