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
package com.strato.hidrive.api.connection.gateway;

/**
 * Defines a result of remote method call containing a corresponding domain object ({@link DomainGatewayResult#getResult()}) or an gateway/api error ({@link DomainGatewayResult#getError()}).
 *
 * @param <T> type of requested domain object
 */

public class DomainGatewayResult<T> {

	private final T result;
	private final Throwable error;

	/**
	 * Constructs DomainGatewayResult object for success case
	 */
	public DomainGatewayResult(T result) {
		this.result = result;
		this.error = null;
	}

	/**
	 * Constructs DomainGatewayResult object for error case
	 */
	public DomainGatewayResult(Throwable error) {
		this.result = null;
		this.error = error;
	}

	/**
	 * Returns corresponding domain object
	 *
	 * @return requested domain object or null
	 */
	public T getResult() {
		return result;
	}

	/**
	 * Returns error
	 *
	 * @return {@link Throwable} or null
	 */
	public Throwable getError() {
		return error;
	}

}
