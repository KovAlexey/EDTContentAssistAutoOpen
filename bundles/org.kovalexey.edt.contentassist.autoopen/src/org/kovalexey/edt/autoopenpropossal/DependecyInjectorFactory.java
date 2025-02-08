package org.kovalexey.edt.autoopenpropossal;

import org.osgi.framework.Bundle;

import com._1c.g5.wiring.AbstractGuiceAwareExecutableExtensionFactory;
import com.google.inject.Injector;

public class DependecyInjectorFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		// TODO Auto-generated method stub
		return Activator.getDefault().getBundle();
	}

	@Override
	protected Injector getInjector() {
		// TODO Auto-generated method stub
		return Activator.getDefault().getInjector();
	}

}
