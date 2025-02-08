package org.kovalexey.edt.autoopenpropossal;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.kovalexey.edt.autoopenpropossal.listeners.IEditorsListenerManager;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.kovalexey.edt.autoopenpropossal"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	private Injector injector;
	private IEditorsListenerManager listenerManager;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		init();
	}
	
    public synchronized Injector getInjector()
    {
        if (injector == null)
            return injector = createInjector();
        return injector;
    }

    private Injector createInjector()
    {
        try
        {
            return Guice.createInjector(new ExternalDependencyInjector(this));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to create injector for " + getBundle().getSymbolicName(), e);
        }
    }

	public void init() {
		
		Display display = Display.getDefault();
		if (display != null) {
			display.syncExec(new Runnable() {
				
				@Override
				public void run() {
					listenerManager = getInjector().getInstance(IEditorsListenerManager.class);
					listenerManager.init();
				}
			});
		}
	
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	

	public void log(String log) {
		plugin.getLog().info(log);
	}
	
	public void log(Throwable exception) {
		plugin.getLog().error(PLUGIN_ID, exception);
	}

}
