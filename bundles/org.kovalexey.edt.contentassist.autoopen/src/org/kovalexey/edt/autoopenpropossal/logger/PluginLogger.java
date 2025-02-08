package org.kovalexey.edt.autoopenpropossal.logger;

import org.kovalexey.edt.autoopenpropossal.Activator;

public class PluginLogger implements IPluginLogger {

	@Override
	public void log(String log) {
		Activator.getDefault().log(log);
	}

	@Override
	public void log(Throwable exception) {
		Activator.getDefault().log(exception);
	}

}
