// Copyright (c) 2008-2009 Nokia Corporation and/or its subsidiary(-ies).
// All rights reserved.
// This component and the accompanying materials are made available
// under the terms of "Eclipse Public License v1.0"
// which accompanies this distribution, and is available
// at the URL "http://www.eclipse.org/legal/epl-v10.html".
//
// Initial Contributors:
// Nokia Corporation - initial contribution.
//
// Contributors:
//
// Description:
//

package com.symbian.smt.gui;


@SuppressWarnings("serial")
public class SystemDefinitionValidationException extends Exception {

	/**
	 * Constructs a new exception that indicates that System Definition validation has failed but a build may still be attempted.
	 * @param message - the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 */
	public SystemDefinitionValidationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception that indicates that System Definition validation has failed but a build may still be attempted.
	 * @param cause - the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public SystemDefinitionValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception that indicates that System Definition validation has failed but a build may still be attempted.
	 * @param message - the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 * @param cause - the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public SystemDefinitionValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
