package com.nextcloud.alk;

import android.util.Pair;

import com.ionos.domain.permission.CheckPermissionsResult;
import com.ionos.domain.permission.Permission;
import com.ionos.domain.permission.PermissionQuery;
import com.ionos.domain.permission.PermissionsController;
import com.ionos.domain.permission.PermissionsListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Stack;

/**
 * User: Dima Muravyov
 * Date: 04.07.2017
 */

public class PermissionQueryImpl implements PermissionQuery {
	private final PermissionsController permissionsController;
	private final Stack<Pair<List<Permission>, PermissionsListener>> permissionsQuery = new Stack<>();
	private boolean processingQuery = false;

	public PermissionQueryImpl(PermissionsController permissionsController) {
		this.permissionsController = permissionsController;
	}

	@Override
	public void checkPermissions(Permission[] permissions, PermissionsListener listener) {
		if (permissionsController.isPermissionsGranted(permissions)) {
			CheckPermissionsResult result = new CheckPermissionsResult(
					new LinkedHashSet<>(Arrays.asList(permissions)),
					Collections.emptySet()
			);
			listener.onResult(result);
		} else {
			permissionsQuery.push(new Pair<>(Arrays.asList(permissions), listener));
			processQuery();
		}
	}

	private void processQuery() {
		if (!processingQuery && !permissionsQuery.isEmpty()) {
			processingQuery = true;
			final Pair<List<Permission>, PermissionsListener> permissionQuery = permissionsQuery.pop();

			permissionsController.checkPermissions(
					permissionQuery.first.toArray(new Permission[0]),
					getPermissionsListener(permissionQuery.first, permissionQuery.second));
		}
	}

	private PermissionsListener getPermissionsListener(final List<Permission> permissions, final PermissionsListener permissionsListener) {
		return new PermissionsListener() {

			@Override
			public void onResult(CheckPermissionsResult result) {
				for (PermissionsListener listener : getSameRequests()) {
					listener.onResult(result);
				}
				processingQuery = false;
				processQuery();
			}

			private List<PermissionsListener> getSameRequests() {
				List<PermissionsListener> sameRequests = new ArrayList<>();
				sameRequests.add(permissionsListener);
				sameRequests.addAll(getSamePermissionQueries(permissions));
				return sameRequests;
			}
		};
	}


	private List<PermissionsListener> getSamePermissionQueries(List<Permission> permission) {
		List<PermissionsListener> sameRequests = new ArrayList<>();

		for (int i = permissionsQuery.size() - 1; i >= 0; i--) {
			if (permissionsQuery.get(i).first.equals(permission)) {
				sameRequests.add(permissionsQuery.remove(i).second);
			}
		}

		return sameRequests;
	}
}
