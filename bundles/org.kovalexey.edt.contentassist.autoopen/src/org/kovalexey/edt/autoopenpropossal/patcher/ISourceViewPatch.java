package org.kovalexey.edt.autoopenpropossal.patcher;

import org.eclipse.jface.text.source.SourceViewer;

public interface ISourceViewPatch {
	public Boolean applyXTextSourceViewPatch(SourceViewer sourceView, int timeout, String charset);
}
