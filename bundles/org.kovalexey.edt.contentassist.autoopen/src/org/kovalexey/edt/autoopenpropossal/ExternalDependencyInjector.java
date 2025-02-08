package org.kovalexey.edt.autoopenpropossal;

import org.eclipse.core.runtime.Plugin;
import org.kovalexey.edt.autoopenpropossal.listeners.EditorsListenerManager;
import org.kovalexey.edt.autoopenpropossal.listeners.IEditorsListenerManager;
import org.kovalexey.edt.autoopenpropossal.listeners.IListenersManager;
import org.kovalexey.edt.autoopenpropossal.listeners.ListenerManager;
import org.kovalexey.edt.autoopenpropossal.logger.IPluginLogger;
import org.kovalexey.edt.autoopenpropossal.logger.PluginLogger;
import org.kovalexey.edt.autoopenpropossal.patcher.ISourceViewPatch;
import org.kovalexey.edt.autoopenpropossal.patcher.SourceViewerPatcher;
import org.kovalexey.edt.autoopenpropossal.settings.IContentAssistSettings;
import org.kovalexey.edt.autoopenpropossal.settings.ContentAssistSettings;
import com._1c.g5.wiring.AbstractServiceAwareModule;
import com.google.inject.Singleton;

public class ExternalDependencyInjector extends AbstractServiceAwareModule  {

	public ExternalDependencyInjector(Plugin context) {
		super(context);
	}

	@Override
	protected void doConfigure() {
		bind(IPluginLogger.class).to(PluginLogger.class).in(Singleton.class);
		bind(IListenersManager.class).to(ListenerManager.class).in(Singleton.class);
		bind(IEditorsListenerManager.class).to(EditorsListenerManager.class).in(Singleton.class);
		bind(IContentAssistSettings.class).to(ContentAssistSettings.class).in(Singleton.class);
		bind(ISourceViewPatch.class).to(SourceViewerPatcher.class).in(Singleton.class);
	}

}
