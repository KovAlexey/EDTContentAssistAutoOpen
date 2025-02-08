package org.kovalexey.edt.autoopenpropossal.listeners;

import java.util.HashMap;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;

import com._1c.g5.v8.dt.md.ui.editor.base.DtGranularEditor;

public class ListenerManager implements IListenersManager {
	
	private HashMap<IPartService, IPartListener2> partListenersMap = new HashMap<IPartService, IPartListener2>();
	private HashMap<DtGranularEditor<?>, IPageChangedListener> pageListenersMap = new HashMap<DtGranularEditor<?>, IPageChangedListener>();
	
	@Override
	public void init() {
		
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void addPartListener(IPartService partService, IPartListener2 listener) {
		if (!partListenersMap.containsKey(partService)) {
			partService.addPartListener(listener);
			partListenersMap.put(partService, listener);
		}
	}

	@Override
	public void removePartListener(IPartService partService) {
		if (partListenersMap.containsKey(partService)) {
			IPartListener2 listener = partListenersMap.get(partService);
			partService.removePartListener(listener);
			partListenersMap.remove(partService);
			listener = null;
		}
		
	}

	@Override
	public void addPageListener(DtGranularEditor<?> editor, IPageChangedListener listener) {
		if (!pageListenersMap.containsKey(editor)) {
			editor.addPageChangedListener(listener);
			pageListenersMap.put(editor, listener);
		}
		
	}

	@Override
	public void removePageListeners(DtGranularEditor<?> editor) {
		if (pageListenersMap.containsKey(editor)) {
			IPageChangedListener listener = pageListenersMap.get(editor);
			editor.removePageChangedListener(listener);
			pageListenersMap.remove(editor);
			listener = null;
		}
	}


}
