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
// InvalidPathException
//



package com.symbian.smt.gui.smtwidgets;

/**
 * @author barbararosi-schwartz
 * 
 */
public class InvalidPathException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public InvalidPathException() {
	}

	/**
	 * This constructor takes a message as the exception message.
	 * 
	 * @param message
	 */
	public InvalidPathException(String message) {
		super(message);
	}

	/**
	 * This constructor takes a message as the exception message
	 * and wraps the provided Throwable.
	 * 
	 * @param message	the String to be set as the exception message
	 * @param t			the Throwable that is wrapped by this exception
	 */
	public InvalidPathException(String message, Throwable t) {
		super(message, t);
	}

	/**
	 * This constructor takes wraps the provided Throwable.
	 * 
	 * @param t			the Throwable that is wrapped by this exception
	 */
	public InvalidPathException(Throwable t) {
		super(t);
	}

}
