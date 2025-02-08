package org.kovalexey.edt.autoopenpropossal.listeners;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;

import com._1c.g5.v8.dt.md.ui.editor.base.DtGranularEditor;

public interface IListenersManager {
		
	public void init();
	public void stop();
	public void addPartListener(IPartService partService, IPartListener2 listener);
	public void removePartListener(IPartService partService);
	public void addPageListener(DtGranularEditor<?> editor, IPageChangedListener listener);
	public void removePageListeners(DtGranularEditor<?> editor);

}
