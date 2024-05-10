package com.strato.hidrive.domain.utils;


import io.reactivex.Single;

public interface RxConnectionInfo {

	enum ConnectionState {
		ENABLED,
		DISABLED
	}

	class ConnectionInfo {
		public final ConnectionState wifiState;
		public final ConnectionState mobileInternetState;

		public ConnectionInfo(boolean wifiEnabled, boolean mobileInternetAvailable) {
			this.wifiState = wifiEnabled ? ConnectionState.ENABLED : ConnectionState.DISABLED;
			this.mobileInternetState = mobileInternetAvailable ? ConnectionState.ENABLED : ConnectionState.DISABLED;
		}

		public boolean wifiEnabled() {
			return wifiState == ConnectionState.ENABLED;
		}

		public boolean mobileInternetEnabled() {
			return mobileInternetState == ConnectionState.ENABLED;
		}

		public boolean anyConnectionAvailable() {
			return mobileInternetEnabled() || wifiEnabled();
		}
	}

	Single<ConnectionInfo> getConnectionInfo();
}

