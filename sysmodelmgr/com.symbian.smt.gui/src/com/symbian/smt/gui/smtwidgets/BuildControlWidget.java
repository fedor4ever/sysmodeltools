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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.symbian.smt.gui.smtwidgets.ValidModelEvent.Type;

public class BuildControlWidget extends Composite implements
		ValidModelObservable {
	private Text outputFilenameText;
	private List<ValidModelDefinedListener> listeners = new ArrayList<ValidModelDefinedListener>();

	private Button warningLevel1, warningLevel2, warningLevel3, warningLevel4;

	// private String message;

	private static final Pattern validationPattern = Pattern
			.compile("[^:*?<>\"/\\\\|]*");

	/**
	 * Creates a BuildControlWidget composite object
	 * 
	 * @return void
	 */
	public BuildControlWidget(final Composite parent, int style,
			boolean allOptions) {
		super(parent, style);

		setLayout(new FillLayout());

		final Composite gridLayoutComposite = new Composite(this, SWT.NONE);
		gridLayoutComposite.setLayout(new GridLayout(2, false));

		final Label outputFilenameLabel = new Label(gridLayoutComposite,
				SWT.NONE);
		outputFilenameLabel.setText("Output filename");

		outputFilenameText = new Text(gridLayoutComposite, SWT.BORDER);
		outputFilenameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		outputFilenameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkIfValid();
			}
		});

		if (allOptions) {
			Group group = new Group(gridLayoutComposite, SWT.SHADOW_ETCHED_IN);
			group.setText("Warning level");
			group.setLayout(new RowLayout(SWT.VERTICAL));
			group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
					2, 1));

			warningLevel1 = new Button(group, SWT.RADIO);
			warningLevel1.setText("Errors only");
			warningLevel2 = new Button(group, SWT.RADIO);
			warningLevel2.setText("Errors and warnings");
			warningLevel3 = new Button(group, SWT.RADIO);
			warningLevel3.setText("Verbose");
			warningLevel4 = new Button(group, SWT.RADIO);
			warningLevel4.setText("Debug (note: will increase build times)");
		}
	}

	/**
	 * Registers a listener object, which will be notified if the conditions for
	 * proceeding have been met.
	 * 
	 * @param listener
	 *            A ValidModelDefinedListener object
	 * @return void
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelObservable#addModelListener(ValidModelDefinedListener)
	 */
	public void addModelListener(ValidModelDefinedListener listener) {
		listeners.add(listener);
	}

	// Check that at least one system definition file has been specified. Any
	// registered
	// model listeners are notified with a ValidModelEvent.
	private void checkIfValid() {
		if (listeners.size() > 0) {
			String text = outputFilenameText.getText().trim();
			String message = "";
			boolean valid = true;
			Type type = Type.SUCCESS;

			if (text.length() == 0) {
				valid = false;
				message = "Filename must be specified.";
				type = Type.ERROR;
			}

			Matcher matcher = validationPattern.matcher(text);
			matcher.reset();

			if (!matcher.matches()) {
				valid = false;
				message = "Forbidden characters in output filename.";
				type = Type.ERROR;
			}

			ValidModelEvent event = new ValidModelEvent(valid, message, type);

			for (ValidModelDefinedListener listener : listeners) {
				listener.validModelDefined(event);
			}
		}
	}

	/**
	 * This method is for testing purposes only.
	 * 
	 * @return
	 */
	public List<ValidModelDefinedListener> getModelListeners() {
		List<ValidModelDefinedListener> l;

		synchronized (listeners) {
			l = new ArrayList<ValidModelDefinedListener>(listeners);
		}

		return l;
	}

	public String getOutputFilename() {

		String entry = outputFilenameText.getText().trim();

		if (entry.length() > 0) {
			if (!entry.endsWith(".svg")) {
				return entry + ".svg";
			}
		}

		return entry;
	}

	public String getWarningLevel() {
		if (warningLevel1.getSelection()) {
			return "1";
		} else if (warningLevel2.getSelection()) {
			return "2";
		} else if (warningLevel3.getSelection()) {
			return "3";
		} else if (warningLevel4.getSelection()) {
			return "4";
		} else {
			return null;
		}
	}

	/**
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelObservable#removeModelListener(ValidModelDefinedListener)
	 */
	public void removeModelListener(ValidModelDefinedListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public void setOutputFilename(String arg) {
		outputFilenameText.setText(arg);
	}

	public void setWarningLevel(String arg) {
		if ("1".equals(arg)) {
			warningLevel1.setSelection(true);
		} else if ("2".equals(arg)) {
			warningLevel2.setSelection(true);
		} else if ("3".equals(arg)) {
			warningLevel3.setSelection(true);
		} else if ("4".equals(arg)) {
			warningLevel4.setSelection(true);
		}
	}

	// public String getMessage() {
	// return message;
	// }
}
