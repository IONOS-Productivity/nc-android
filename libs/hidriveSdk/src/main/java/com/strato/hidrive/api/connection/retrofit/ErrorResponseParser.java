package com.strato.hidrive.api.connection.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.strato.hidrive.api.connection.gateway.exceptions.ApiException;
import com.strato.hidrive.api.connection.gateway.exceptions.ErrorCode;
import com.strato.hidrive.domain.logger.LoggerUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by: Alex Kucherenko
 * Date: 06.06.2019.
 */
public class ErrorResponseParser {
	private static final Charset UTF8 = StandardCharsets.UTF_8;
	private static final String ERROR_FIELD = "error";
	private static final String ERROR_DESCRIPTION_FIELD = "error_description";
	private static final String DEFAULT_FIELD_VALUE = "";

	public ApiException parse(Response response) {
		String error = "";
		String errorDescription = "";
		try {
			ResponseBody responseBody = response.body();
			if (responseBody != null) {
				long contentLength = responseBody.contentLength();
				if (contentLength != 0 && HttpHeaders.promisesBody(response)) {
					if (!bodyEncoded(response.headers())) {
						BufferedSource source = responseBody.source();
						source.request(Long.MAX_VALUE); // Buffer the entire body.
						Buffer buffer = source.getBuffer();
						String readString = buffer.clone().readString(UTF8);
						error = parse(readString, ERROR_FIELD);
						errorDescription = parse(readString, ERROR_DESCRIPTION_FIELD);
					}
				}
			}
		} catch (IOException | NullPointerException e) {
			LoggerUtil.logE(getClass().getSimpleName(), e);
		}
		return new ApiException(
				ErrorCode.Companion.byCode(response.code()),
				response.message(),
				error,
				errorDescription);
	}

	private String parse(String source, String field) {
		try {
			JsonElement jelement = JsonParser.parseString(source);
			JsonObject jobject = jelement.getAsJsonObject();
			return jobject.has(field)
					? jobject.get(field).getAsString()
					: DEFAULT_FIELD_VALUE;
		} catch (Exception e) {
			LoggerUtil.logE(getClass().getSimpleName(), e);
			return DEFAULT_FIELD_VALUE;
		}
	}

	private boolean bodyEncoded(Headers headers) {
		String contentEncoding = headers.get("Content-Encoding");
		return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
	}
}
