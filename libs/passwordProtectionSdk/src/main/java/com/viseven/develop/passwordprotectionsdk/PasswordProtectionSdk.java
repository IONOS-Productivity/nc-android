package com.viseven.develop.passwordprotectionsdk;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.viseven.develop.passwordprotectionsdk.activity.PasswordProtectionActivityObserver;
import com.viseven.develop.passwordprotectionsdk.activity.PasswordProtectionActivityTracker;
import com.viseven.develop.passwordprotectionsdk.activity.PasswordProtectionOwner;
import com.viseven.develop.passwordprotectionsdk.activity.callback.ActivityLifecycleCallbacksAdapter;
import com.viseven.develop.passwordprotectionsdk.activity.callback.ExcludeActivityLifecycleCallbacks;
import com.viseven.develop.passwordprotectionsdk.activity.callback.NullActivityLifecycleCallbacks;
import com.viseven.develop.passwordprotectionsdk.configuration.Configuration;
import com.viseven.develop.passwordprotectionsdk.result_handler.access_granted.AccessGrantedListener;
import com.viseven.develop.passwordprotectionsdk.result_handler.access_granted.AccessGrantedPasswordProtectionResultHandlerDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;
import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

public final class PasswordProtectionSdk {
	private static final PasswordProtectionSdk INSTANCE = new PasswordProtectionSdk();

	private final Lifecycle processLifecycle;
	private final PasswordProtectionActivityTracker activityTracker;

	@Nullable
	private Application application;
	private Configuration configuration;
	@NonNull
	private ActivityLifecycleCallbacks activityLifecycleCallbacksDecorator;
	private Map<Integer, Intent> startActivityIntents = new HashMap<>();

	private boolean appWasInBackground;
	private boolean externalComponentStarted;
	private boolean isLocked;

	public static PasswordProtectionSdk getInstance() {
		return INSTANCE;
	}

	private PasswordProtectionSdk() {
		this.processLifecycle = ProcessLifecycleOwner.get().getLifecycle();
		this.activityTracker = PasswordProtectionActivityTracker.get();
		this.activityLifecycleCallbacksDecorator = new NullActivityLifecycleCallbacks();
		this.appWasInBackground = processLifecycle.getCurrentState() == Lifecycle.State.CREATED;
		this.externalComponentStarted = false;
		this.processLifecycle.addObserver(new LifecycleObserver() {
			@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
			public void onAppBackgrounded() {
				appWasInBackground = true;
			}
		});
	}

	public final void init(Application application, Configuration configuration) {
		this.application = application;
		this.configuration = createDecoratedConfiguration(configuration);
		this.activityTracker.init(application, createTrackerActivitiesToExclude(configuration));
		this.activityLifecycleCallbacksDecorator =
				new ExcludeActivityLifecycleCallbacks(
						configuration.activitiesToExclude,
						this.activityLifecycleCallback);

		application.unregisterActivityLifecycleCallbacks(observerActivityLifecycleCallback);
		application.registerActivityLifecycleCallbacks(observerActivityLifecycleCallback);
	}

	public final void enablePasswordProtection() {
		if (this.application == null) {
			throw new RuntimeException(getClass().getSimpleName() + " not initialized");
		}
		Activity topActivity = this.activityTracker.getTopActivity();
		if (topActivity != null) {
			topActivity.getWindow().addFlags(FLAG_SECURE);
		}
		this.externalComponentStarted = false;
		this.appWasInBackground = processLifecycle.getCurrentState() == Lifecycle.State.CREATED;
		this.application.unregisterActivityLifecycleCallbacks(this.activityLifecycleCallbacksDecorator);
		this.application.registerActivityLifecycleCallbacks(this.activityLifecycleCallbacksDecorator);
	}

	public final void disablePasswordProtection() {
		if (this.application == null) {
			throw new RuntimeException(getClass().getSimpleName() + " not initialized");
		}
		Activity topActivity = this.activityTracker.getTopActivity();
		if (topActivity != null) {
			topActivity.getWindow().clearFlags(FLAG_SECURE);
		}
		this.isLocked = false;
		this.externalComponentStarted = false;
		this.application.unregisterActivityLifecycleCallbacks(this.activityLifecycleCallbacksDecorator);
	}

	private List<Class<? extends Activity>> createTrackerActivitiesToExclude(Configuration configuration) {
		List<Class<? extends Activity>> activitiesToExclude = new ArrayList<>(configuration.activitiesToExclude);
		activitiesToExclude.add(PasswordProtectionActivity.class);
		return activitiesToExclude;
	}

	Configuration getConfiguration() {
		return this.configuration;
	}

	private Configuration createDecoratedConfiguration(Configuration configuration) {
		AccessGrantedPasswordProtectionResultHandlerDecorator decoratedResultHandler =
				new AccessGrantedPasswordProtectionResultHandlerDecorator(
						configuration.passwordProtectionResultHandler,
						new AccessGrantedListener() {
							@Override
							public void accessGranted() {
								isLocked = false;
							}
						});
		return new Configuration.Builder()
				.setFragmentFactory(configuration.fragmentFactory)
				.setActivitiesToExclude(configuration.activitiesToExclude)
				.setPasswordProtectionResultHandler(decoratedResultHandler)
				.build();
	}

	private void startPasswordProtectionActivity(@NonNull Activity activity) {
		if (!(activity instanceof PasswordProtectionActivity)) {
			activity.startActivityForResult(
					new Intent(activity, PasswordProtectionActivity.class)
							.addFlags(FLAG_ACTIVITY_NO_ANIMATION)
							.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT),
					PasswordProtectionActivity.REQUEST_CODE);
		}
	}

	private void closePasswordProtectionScreenWhenNotLocked(@NonNull Activity activity) {
		if (!this.isLocked && activity instanceof PasswordProtectionActivity) {
			activity.finish();
		}
	}

	private final ActivityLifecycleCallbacks activityLifecycleCallback = new ActivityLifecycleCallbacksAdapter() {
		@Override
		public void onActivityStarted(@NonNull Activity activity) {
			super.onActivityStarted(activity);
			View activitiesRootView = activity.getWindow().findViewById(android.R.id.content).getRootView();
			activitiesRootView.setVisibility(View.VISIBLE);
			if (appWasInBackground && !externalComponentStarted) {
				activitiesRootView.setVisibility(View.GONE);
			} else if (isLocked) {
				activitiesRootView.setVisibility(View.GONE);
			}
		}

		@Override
		public void onActivityResumed(@NonNull Activity activity) {
			super.onActivityResumed(activity);
			if (appWasInBackground) {
				if (!externalComponentStarted) {
					startPasswordProtectionScreenAndSetScreenLocked(activity);
				}
				appWasInBackground = false;
				externalComponentStarted = false;
			} else if (isLocked) {
				startPasswordProtectionScreenAndSetScreenLocked(activity);
			}
			closePasswordProtectionScreenWhenNotLocked(activity);
		}

		@Override
		public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
			activity.getWindow().addFlags(FLAG_SECURE);
			super.onActivityCreated(activity, savedInstanceState);
		}
	};

	private final ActivityLifecycleCallbacks observerActivityLifecycleCallback = new ActivityLifecycleCallbacksAdapter() {
		@Override
		public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
			super.onActivityCreated(activity, savedInstanceState);
			if (activity instanceof PasswordProtectionOwner) {
				((PasswordProtectionOwner) activity).registerActivityObserver(activityObserver);
			}
		}

		@Override
		public void onActivityDestroyed(@NonNull Activity activity) {
			super.onActivityDestroyed(activity);
			if (activity instanceof PasswordProtectionOwner) {
				((PasswordProtectionOwner) activity).unregisterActivityObserver(activityObserver);
			}
		}
	};

	private void startPasswordProtectionScreenAndSetScreenLocked(@NonNull Activity activity) {
		startPasswordProtectionActivity(activity);
		isLocked = true;
	}

	private final PasswordProtectionActivityObserver activityObserver = new PasswordProtectionActivityObserver() {
		@Override
		public void startActivityForResult(Intent intent, int requestCode, boolean forceSecurityModeAfterFinish) {
			if (forceSecurityModeAfterFinish) {
				externalComponentStarted = false;
			} else {
				startActivityIntents.put(requestCode, intent);
				externalComponentStarted = isImplicitIntent(intent);
			}
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode != PasswordProtectionActivity.REQUEST_CODE) {
				Intent startActivityIntent = startActivityIntents.remove(requestCode);
				externalComponentStarted = isImplicitIntent(startActivityIntent);
			}
		}

		private boolean isImplicitIntent(@Nullable Intent intent) {
			return intent != null && intent.getAction() != null;
		}
	};
}
