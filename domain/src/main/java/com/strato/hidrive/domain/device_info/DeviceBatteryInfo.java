package com.strato.hidrive.domain.device_info;

/**
 * Created by Sergey Shandyuk on 1/23/2017.
 */

public interface DeviceBatteryInfo {
	float getBatteryLevel();

	boolean isCharging();
}
