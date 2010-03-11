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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * This specialisation of the InputDialog class is equipped to handle warnings
 * as well as errors in user input. Warnings are messages that need to be
 * displayed in the dialog but do not require disablement of buttons.
 * <p>
 * This dialog makes use of the specialised validator interface
 * IInputValidatorWithWarning.
 * </p>
 * 
 * @author barbararosi-schwartz
 * @see com.symbian.smt.gui.smtwidgets.IInputValidatorWithWarning
 */
public class InputDialogWithWarning extends InputDialog {

	/**
	 * The object that handles validation
	 */
	private IInputValidatorWithWarning validator;

	public InputDialogWithWarning(Shell parentShell, String dialogTitle,
			String dialogMessage, String initialValue,
			IInputValidatorWithWarning validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);

		this.validator = validator;
	}

	/**
	 * After invoking the superclass <code>setErrorMessage()</code> method, this
	 * method ensures that the dialog's OK button, if present, is enabled.
	 * <p>
	 * The warning message will be automatically presented by the superclass.
	 * </p>
	 * 
	 * @param warningMessage
	 *            the warning message, or <code>null</code> if no warning is
	 *            required
	 */
	public void setWarningMessage(String warningMessage) {
		super.setErrorMessage(warningMessage);

		Control button = getButton(IDialogConstants.OK_ID);

		if (button != null) {
			button.setEnabled(true);
		}
	}

	/**
	 * Validates the input.
	 * <p>
	 * Delegates the request to the supplied input validator object.
	 * </p>
	 * <p>
	 * If it finds the input invalid, the error message is displayed in the
	 * dialog's message line and the dialog's OK button is disabled.
	 * </p>
	 * <p>
	 * If it finds the input valid (i.e. no error), it checks with the validator
	 * if the input requires a warning message and, if so, the warning message
	 * is displayed in the dialog's message line but no disabling of the button
	 * occurs.
	 * </p>
	 * <p>
	 * This hook method is called whenever the text changes in the input field.
	 * </p>
	 */
	@Override
	protected void validateInput() {
		String errorMessage = null;
		String warningMessage = null;

		if (validator != null) {
			String newText = getText().getText();
			errorMessage = validator.isValid(newText);

			if (errorMessage == null) {
				warningMessage = validator.isWarning(newText);

				setWarningMessage(warningMessage);
			} else {
				setErrorMessage(errorMessage);
			}
		}
	}

}
