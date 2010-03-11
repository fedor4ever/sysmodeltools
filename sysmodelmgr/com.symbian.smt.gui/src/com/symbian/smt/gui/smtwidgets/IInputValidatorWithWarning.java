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

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * An extension of the IInputValidator interface that is equipped to also handle
 * warnings. While the superinterface method <code>isValid()</code> determines
 * whether user input is ok or in error, this interface's <code>isWarning</code>
 * method determines whether the user input is produces a non disabling warning.
 * <p>
 * For an example of usage, see class InputDialogWithWarning.
 * </p>
 * 
 * @author barbararosi-schwartz
 * @see com.symbian.smt.gui.smtwidgets.InputDialogWithWarning
 */
public interface IInputValidatorWithWarning extends IInputValidator {
	/**
	 * Determines whether or not the given string should produce a non-disabling
	 * warning. Returns a warning message to display if the new text is invalid.
	 * Returns <code>null</code> if there is no warning.
	 * 
	 * The unconventional method signature follows the pattern of 
	 * IInputValidator's isValid() method.
	 * 
	 * @param newText
	 *            the text to check for validity
	 * 
	 * @return a warning message or <code>null</code> if no warning
	 */
	public String isWarning(String newText);
}
