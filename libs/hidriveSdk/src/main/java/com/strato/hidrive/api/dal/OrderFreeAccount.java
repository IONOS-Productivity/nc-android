/**
 * Copyright 2014 STRATO AG
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.strato.hidrive.api.dal;

import com.google.gson.JsonObject;

public class OrderFreeAccount {
	private Boolean isTermsAccepted;
	private String language;
	private String country;
	private String email;
	private String password;
	private String username;
	private String securityCode;
	private String partnerId;
	private String partnerRefId;
	private String partnerSubId;
	private String product;
	private String productName;

	public OrderFreeAccount(Boolean _isTermsAccepted,
							String _language,
							String _country,
							String _email,
							String _password,
							String _username,
							String _securityCode,
							String _partnerId,
							String _partnerRefId,
							String _partnerSubId,
							String _product,
							String _productName) {
		this.isTermsAccepted = _isTermsAccepted;
		this.language = _language;
		this.country = _country;
		this.email = _email;
		this.password = _password;
		this.username = _username;
		this.securityCode = _securityCode;
		this.partnerId = _partnerId;
		this.partnerRefId = _partnerRefId;
		this.partnerSubId = _partnerSubId;
		this.product = _product;
		this.productName = _productName;
	}

	public JsonObject getJson() {
		JsonObject dataJson = new JsonObject();
		dataJson.addProperty("agb_check", this.isTermsAccepted);
		dataJson.addProperty("language", this.language);
		dataJson.addProperty("country", this.country);
		dataJson.addProperty("product", this.product);
		dataJson.addProperty("product_name", this.productName);
		dataJson.addProperty("email", this.email);
		dataJson.addProperty("password", this.password);
		dataJson.addProperty("captcha_number", this.securityCode);
		dataJson.addProperty("username", this.username);
		dataJson.addProperty("partner_id", this.partnerId);
		dataJson.addProperty("partner_ref_id", this.partnerRefId);
		dataJson.addProperty("partner_sub_id", this.partnerSubId);
		return dataJson;
	}

	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}
}
