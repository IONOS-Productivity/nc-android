package com.nextcloud.client.di;

import android.content.Context;

import com.nextcloud.alk.PermissionQueryImpl;
import com.strato.hidrive.common_ui.image.loader.ImageLoader;
import com.strato.hidrive.domain.permission.PermissionQuery;
import com.strato.hidrive.domain.permission.PermissionsController;

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
