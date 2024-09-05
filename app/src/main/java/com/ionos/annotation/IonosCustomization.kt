/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Your Name <your@email.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.annotation

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.EXPRESSION,
)
@Retention(AnnotationRetention.SOURCE)
/* Used to highlight changes in core code
* Alternatives:
*   - for layouts use - 'app:ionosCustomization=""'
*   - comment with text '<IONOS Customization>' where other options is not applicable
* */
annotation class IonosCustomization(val value: String = "")