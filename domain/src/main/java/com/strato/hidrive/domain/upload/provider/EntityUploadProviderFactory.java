package com.strato.hidrive.domain.upload.provider;

public interface EntityUploadProviderFactory<T> {

	EntityUploadProvider create(T input);
}
