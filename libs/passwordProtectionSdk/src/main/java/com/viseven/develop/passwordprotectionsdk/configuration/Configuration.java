package com.viseven.develop.passwordprotectionsdk.configuration;

import android.app.Activity;

import com.viseven.develop.passwordprotectionsdk.fragment.PasswordProtectionFragmentFactory;
import com.viseven.develop.passwordprotectionsdk.result_handler.PasswordProtectionResultHandler;

import java.util.List;

public class Configuration {
	public final List<Class<? extends Activity>> activitiesToExclude;
	public final PasswordProtectionFragmentFactory fragmentFactory;
	public final PasswordProtectionResultHandler passwordProtectionResultHandler;

	public Configuration(List<Class<? extends Activity>> activitiesToExclude, PasswordProtectionFragmentFactory fragmentFactory, PasswordProtectionResultHandler passwordProtectionResultHandler) {
		this.activitiesToExclude = activitiesToExclude;
		this.fragmentFactory = fragmentFactory;
		this.passwordProtectionResultHandler = passwordProtectionResultHandler;
	}

	public static class Builder {
		private PasswordProtectionFragmentFactory fragmentFactory;
		private PasswordProtectionResultHandler passwordProtectionResultHandler;
		private List<Class<? extends Activity>> activitiesToExclude;

		public Configuration.Builder setFragmentFactory(PasswordProtectionFragmentFactory fragmentFactory) {
			this.fragmentFactory = fragmentFactory;
			return this;
		}

		public Configuration.Builder setPasswordProtectionResultHandler(PasswordProtectionResultHandler passwordProtectionResultHandler) {
			this.passwordProtectionResultHandler = passwordProtectionResultHandler;
			return this;
		}

		public Configuration.Builder setActivitiesToExclude(List<Class<? extends Activity>> activitiesToExclude) {
			this.activitiesToExclude = activitiesToExclude;
			return this;
		}

		public Configuration build() {
			return new Configuration(
					this.activitiesToExclude,
					this.fragmentFactory,
					this.passwordProtectionResultHandler);
		}
	}
}
