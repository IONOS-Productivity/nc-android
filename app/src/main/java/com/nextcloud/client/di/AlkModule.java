package com.nextcloud.client.di;

import android.content.Context;

import com.nextcloud.alk.PermissionQueryImpl;
import com.ionos.domain.permission.PermissionQuery;
import com.ionos.domain.permission.PermissionsController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AlkModule {

    @Singleton
    @Provides
    PermissionQuery providePermissionQuery(PermissionsController permissionsController) {
        return new PermissionQueryImpl(permissionsController);
    }

//    @Singleton
//    @Provides
//    ImageLoader provideImageLoader(Context context) {
//        return null; //new GlideImageLoader(new GlideCache(context));
//    }
}
