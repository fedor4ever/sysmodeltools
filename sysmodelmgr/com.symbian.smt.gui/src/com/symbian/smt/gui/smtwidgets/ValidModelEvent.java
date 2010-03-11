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



package com.symbian.smt.gui.smtwidgets;

/**
 * This class encapsulates the outcome of the validation of a field which the
 * user has touched.
 * <p>
 * It contains information on the event result (whether validation was
 * successful or failed) and the message associated with the event.
 * </p>
 * <p>
 * It is created by objects of type ValidModelObservable when validation of a
 * field occurs and it is propagated to registered ValidModelDefinedListener
 * objects in order to allow them to set their state accordingly.
 * </p>
 * <p>
 * An example of usage of this class is provided by Properties pages which
 * require to disable their "Ok" and "Apply" buttons and to show a relevant
 * error message when validation of a specific field fails.
 * </p>
 * 
 * @author barbararosi-schwartz
 * 
 */
public class ValidModelEvent {
	public enum Type {
		ERROR, SUCCESS, WARNING;
	}

	private Boolean isValid;
	private String message;
	private Type type;

	/**
	 * The constructor.
	 * 
	 * @param result
	 *            a Boolean representing whether validation was successful or
	 *            not
	 * @param message
	 *            the message associated with the validation. If validation
	 *            succeeded, the message may be empty. If validation failed, the
	 *            message cannot be empty. If it is, a default error message is
	 *            enforced.
	 */
	public ValidModelEvent(Boolean isValid, String message, Type type) {
		if (isValid == null) {
			throw new IllegalArgumentException("Arg result cannot be null.");
		}

		if (message == null) {
			throw new IllegalArgumentException("Arg message cannot be null.");
		}

		// Enforcing the message not to be non empty
		if ((!isValid) && (message.length() == 0)) {
			message = "Error message unavailable.";
		}

		this.isValid = isValid;
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public Type getType() {
		return type;
	}

	public Boolean isValid() {
		return isValid;
	}

}
