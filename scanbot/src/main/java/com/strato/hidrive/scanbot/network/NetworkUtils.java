package com.strato.hidrive.scanbot.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.strato.hidrive.domain.utils.availability.Availability;

public class NetworkUtils {

	private volatile static boolean wasOfflineBefore;

	private NetworkUtils() {
		throw new AssertionError();
	}

	@Deprecated
	/**
	 * Deprecated method. Use Interface
	 * {@link Availability}
	 * and it's implementation
	 *@see com.strato.hidrive.utils.availability.MobileInternetAvailability
	 */
	public static boolean isMobileInternetAvailable(Context context) {
		try {
			return getNetworkInfo(context).getType() == ConnectivityManager.TYPE_MOBILE;
		} catch (NetworkInfoIsAbsentException e) {
			return false;
		}
	}

	@Deprecated
	/**
	 * Deprecated method. Use Interface
	 * {@link Availability}
	 * and it's implementation
	 *@see com.strato.hidrive.utils.availability.WifiAvailability
	 */
	public static boolean isWiFiInternetAvailable(Context context) {
		try {
			return getNetworkInfo(context).getType() == ConnectivityManager.TYPE_WIFI;
		} catch (NetworkInfoIsAbsentException e) {
			return false;
		}
	}

	@Deprecated
	/**
	 * Deprecated method. Use Interface
	 * {@link Availability}
	 * and it's implementation
	 *@see com.strato.hidrive.utils.availability.NetworkAvailability
	 */
	public static boolean isOnline(Context context) {
		try {
			return getNetworkInfo(context).isConnectedOrConnecting();
		} catch (NetworkInfoIsAbsentException e) {
			return false;
		}
	}

	public static boolean isConnected(Context context) {
		try {
			return getNetworkInfo(context).isConnected();
		} catch (NetworkInfoIsAbsentException e) {
			return false;
		}
	}

	private static NetworkInfo getNetworkInfo(Context context) throws NetworkInfoIsAbsentException {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null) {
				return networkInfo;
			}
		}
		throw new NetworkInfoIsAbsentException();

	}

	public static NetworkStateBundle getNetworkStateBundle(Context context) {
		boolean isOnline = isOnline(context);
		boolean isWiFiInternetAvailable = isWiFiInternetAvailable(context);
		boolean isMobileInternetAvailable = isMobileInternetAvailable(context);

		NetworkStateBundle networkStateBundle = new NetworkStateBundle(
			isOnline,
			isWiFiInternetAvailable,
			isMobileInternetAvailable,
			wasOfflineBefore
		);

		wasOfflineBefore = !isOnline;
		return networkStateBundle;
	}
}
