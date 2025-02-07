package org.kovalexey.edt.autoopenpropossal;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class EditorsListenerManager {
	
	private WindowListener windowListener = new WindowListener();
	private PartCloseOpenListener partListener = new PartCloseOpenListener();
	private PageChangeListener pageListener = new PageChangeListener();
	
	public EditorsListenerManager() {
		
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (IWorkbenchWindow iWorkbenchWindow : windows) {
			iWorkbenchWindow.getPartService().addPartListener(partListener);
		}
		PlatformUI.getWorkbench().addWindowListener(windowListener);
	}
	
	
	private class WindowListener implements IWindowListener {

		@Override
		public void windowActivated(IWorkbenchWindow window) {
		}

		@Override
		public void windowOpened(IWorkbenchWindow window) {
		}

		@Override
		public void windowDeactivated(IWorkbenchWindow window) {
		}

		@Override
		public void windowClosed(IWorkbenchWindow window) {
		}

	}
	
	private class PartCloseOpenListener implements IPartListener2 {
		
	}
	
	private class PageChangeListener implements IPageChangedListener {

		@Override
		public void pageChanged(PageChangedEvent event) {
			
		}
		
	}
	
	
}
