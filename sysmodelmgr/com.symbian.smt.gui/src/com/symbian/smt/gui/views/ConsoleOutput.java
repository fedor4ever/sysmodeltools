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

package com.symbian.smt.gui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ConsoleOutput extends ViewPart {

	public static final String ID = "com.symbian.smt.gui.views.consoleoutput"; //$NON-NLS-1$
	private static Text text;
	private static StringBuilder theText = new StringBuilder();

	/**
	 * Adds text to the console output
	 * 
	 * @param String
	 *            Text to add to the console
	 * @return void
	 */
	public static void addText(String someText) {

		theText.append(someText);
		theText.append(System.getProperty("line.separator"));

		if (text != null) {
			text.setText(theText.toString());
			text.append("");
		}
	}

	/**
	 * Returns the text contained in the console output
	 * 
	 * @return String
	 */
	public static String getText() {
		if (theText.toString().length() > 0) {
			return theText.toString();
		}

		return "";
	}

	/**
	 * Resets the console output, removes all text
	 * 
	 * @return void
	 */
	public static void reset() {
		theText = new StringBuilder();

		if (text != null) {
			text.setText("");
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		text = new Text(parent, SWT.WRAP | SWT.V_SCROLL | SWT.MULTI
				| SWT.BORDER);
		text.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		text.setEditable(false);
	}

	@Override
	public void setFocus() {
		if (theText != null) {
			text.setText(theText.toString());
			text.append("");
		}
	}
}