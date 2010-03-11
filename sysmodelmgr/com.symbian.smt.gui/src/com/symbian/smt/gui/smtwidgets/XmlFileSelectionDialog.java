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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

/**
 * This dialog is adapted from Eclipse's InputDialog in order to accept the name
 * (local or URL) of an XML file. Validation of the XML also occurs.
 * 
 * @author barbararosi-schwartz
 * 
 */
public class XmlFileSelectionDialog extends Dialog {

	private static final String BROWSE_BUTTON = "Browse..."; //$NON-NLS-1$
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * Browse button widget.
	 */
	private Button browseButton;

	/**
	 * Cancel button widget.
	 */
	private Button cancelButton;

	/**
	 * Error message string.
	 */
	private String errorMessage;

	/**
	 * Error message label widget.
	 */
	private Text errorMessageText;

	/**
	 * The file extensions to filter on.
	 */
	private String[] extensionFilter;

	/**
	 * The message to display, or <code>null</code> if none.
	 */
	private String message;

	/**
	 * Ok button widget.
	 */
	private Button okButton;

	/**
	 * Input text widget.
	 */
	private Text inputText;

	/**
	 * The title of the dialog.
	 */
	private String title;

	/**
	 * The input m_validator, or <code>null</code> if none.
	 */
	private IXmlFileInputValidator validator;

	/**
	 * The input value; the empty string by default.
	 */
	private String inputValue = EMPTY_STRING;

	/**
	 * Creates an input dialog with OK and Cancel buttons. Note that the dialog
	 * will have no visual representation (no widgets) until it is told to open.
	 * <p>
	 * Note that the <code>open</code> method blocks for input dialogs.
	 * </p>
	 * 
	 * @param parentShell
	 *            the parent shell, or <code>null</code> to create a top-level
	 *            shell
	 * @param dialogTitle
	 *            the dialog title, or <code>null</code> if none
	 * @param dialogMessage
	 *            the dialog message, or <code>null</code> if none
	 * @param initialValue
	 *            the initial input value, or <code>null</code> if none
	 *            (equivalent to the empty string)
	 * @param extensionFilter
	 *            the file extensions to filter on.
	 * @param validator
	 *            an input validator, or <code>null</code> if none
	 */
	public XmlFileSelectionDialog(Shell parentShell, String dialogTitle,
			String dialogMessage, String initialValue,
			String[] extensionFilter, IXmlFileInputValidator validator) {
		super(parentShell);

		this.title = dialogTitle;
		message = dialogMessage;
		this.extensionFilter = extensionFilter;

		if (initialValue == null) {
			inputValue = EMPTY_STRING;
		} else {
			inputValue = initialValue;
		}

		this.validator = validator;
	}

	/*
	 * Method declared on Dialog.
	 */
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			inputValue = inputText.getText();
		} else {
			inputValue = null;
		}

		super.buttonPressed(buttonId);
	}

	/*
	 * 
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
	}

	/*
	 * 
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);

		cancelButton = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);

		// do this here because setting the m_text will set enablement on the ok
		// button
		inputText.setFocus();
		if (inputValue != null) {
			inputText.setText(inputValue);
			inputText.selectAll();
		}
	}

	/*
	 * Method declared on Dialog.
	 */
	protected Control createDialogArea(Composite parent) {
		// create composite
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout layout = (GridLayout) composite.getLayout();

		layout.numColumns = 2;

		// create message
		if (message != null) {
			Label label = new Label(composite, SWT.WRAP);
			label.setText(message);

			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL,
					GridData.VERTICAL_ALIGN_CENTER, true, true, 2, 1);

			data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);

			label.setLayoutData(data);
			label.setFont(parent.getFont());
		}

		inputText = new Text(composite, getInputTextStyle());

		inputText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));
		inputText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});

		browseButton = new Button(composite, SWT.PUSH);

		browseButton.setText(BROWSE_BUTTON);
		browseButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
			}

			public void widgetSelected(SelectionEvent event) {
				FileDialog fd = new FileDialog(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						SWT.OPEN | SWT.APPLICATION_MODAL);
				fd.setText("Select the desired resource file name");

				if (extensionFilter != null) {
					fd.setFilterExtensions(extensionFilter);
				}

				String selected = fd.open();

				if (selected != null) {
					inputText.setText(selected);
				}
			}
		});

		GridData gd = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_VERTICAL
				| GridData.VERTICAL_ALIGN_FILL);

		gd.horizontalSpan = 2;
		gd.minimumHeight = 40;
		errorMessageText = new Text(composite, SWT.MULTI | SWT.READ_ONLY
				| SWT.WRAP);

		errorMessageText.setLayoutData(gd);
		errorMessageText.setBackground(errorMessageText.getDisplay()
				.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

		// Set the error message text
		setErrorMessage(errorMessage);

		applyDialogFont(composite);
		return composite;
	}

	/**
	 * @return the cancelButton
	 */
	public Button getCancelButton() {
		return cancelButton;
	}

	/**
	 * Returns the style bits that should be used for the input text field.
	 * Defaults to a single line entry. Subclasses may override.
	 * 
	 * @return the integer style bits that should be used when creating the
	 *         input text
	 */
	protected int getInputTextStyle() {
		return SWT.SINGLE | SWT.BORDER;
	}

	/**
	 * Returns the ok button.
	 * 
	 * @return the ok button
	 */
	public Button getOkButton() {
		return okButton;
	}

	/**
	 * Returns the text area.
	 * 
	 * @return the text area
	 */
	public Text getText() {
		return inputText;
	}

	/**
	 * Returns the validator.
	 * 
	 * @return the validator
	 */
	protected IInputValidator getValidator() {
		return validator;
	}

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the input string
	 */
	public String getValue() {
		return inputValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		IRunnableWithProgress op = new IRunnableWithProgress() {
			String input = getValue();
			String result;
			
			// Validation of the XML content does not happen here as an invalid XML
			// file
			// is deemed acceptable. It occurs as part of the update that takes
			// place
			// in the ManageResources class, which is also responsible for arranging
			// for error markers to be placed on any invalid resource file or sys
			// def file.
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Reading " + input, 2);
				
				try {
					input = getValue();
					
					if (validator.isUrl(input)) {
						result = validator.isUrlResourceReadable(input);
					} else { // If file path is local
						result = validator.isFileReadable(input);
					}
				} catch (InvalidPathException e) {
					result = e.getMessage();
				}

				monitor.worked(1);
				monitor.setTaskName("Checking validation");
				setValidationError(result);
				monitor.worked(1);
				monitor.done();
			}
		};
		
		try {
			new ProgressMonitorDialog(getShell()).run(true, false, op);
		} catch (InterruptedException e) {
			return;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return;
		}
		finally {
			if (validationError != null) {
				setErrorMessage(validationError);
				setValidationError(null);
				
				return;
			}
		}
		
		super.okPressed();
	}

	private String validationError;
	
	private void setValidationError(String error) {
		validationError = error;
	}
	
	/**
	 * Sets or clears the error message. If not <code>null</code>, the OK button
	 * is disabled.
	 * 
	 * @param errorMessage
	 *            the error message, or <code>null</code> to clear
	 * @since 3.0
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		if (errorMessageText != null && !errorMessageText.isDisposed()) {

			errorMessageText
					.setText(errorMessage == null ? "\n" : errorMessage); //$NON-NLS-1$

			// Disable the error message text control if there is no error, or
			// no error text (empty or whitespace only). Hide it also to avoid
			// color change.
			boolean hasError = errorMessage != null
					&& (StringConverter.removeWhiteSpaces(errorMessage))
							.length() > 0;
			errorMessageText.setEnabled(hasError);
			errorMessageText.setVisible(hasError);
			errorMessageText.getParent().update();

			// Access the ok button by id, in case clients have overridden
			// button creation.
			Control button = getButton(IDialogConstants.OK_ID);

			if (button != null) {
				button.setEnabled(errorMessage == null);
			}
		}
	}

	/**
	 * Validates the input.
	 * <p>
	 * The default implementation of this framework method delegates the request
	 * to the supplied input validator object; if it finds the input invalid,
	 * the error message is displayed in the dialog's message line. This
	 * hook method is called whenever the text changes in the input field.
	 * </p>
	 */
	protected void validateInput() {
		String errorMessage = null;
		if (validator != null) {
			errorMessage = validator.isValid(inputText.getText());
		}
		// It is important not to treat "" (blank error) the same as null
		// (no error)
		setErrorMessage(errorMessage);
	}
}
