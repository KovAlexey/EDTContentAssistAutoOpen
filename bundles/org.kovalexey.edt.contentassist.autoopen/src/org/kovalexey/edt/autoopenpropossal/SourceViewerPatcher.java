package org.kovalexey.edt.autoopenpropossal;

import java.lang.reflect.Field;

import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.source.SourceViewer;

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
	
	public static ContentAssistant getContentAssistant(SourceViewer sourceViewer) {
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

}
