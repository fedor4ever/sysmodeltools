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
// ${file_name}
// 
//

package com.symbian.smt.gui.wizard;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.symbian.smt.gui.smtwidgets.SystemDefinitionFilesWidget;
import com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent;
import com.symbian.smt.gui.smtwidgets.ValidModelObservable;

public class NewProjectWizardSystemDefsPage extends WizardPage implements
		ValidModelDefinedListener {
	private SystemDefinitionFilesWidget sysdef;

	/**
	 * Creates a wizard page for entering information about System Definitions
	 * 
	 * @return void
	 */
	protected NewProjectWizardSystemDefsPage(ISelection selection) {
		super("wizardPage");
		setTitle("System Model Manager Wizard");
		setDescription("Enter one or more system definition files to use for the System Model Diagram");
		setPageComplete(false);
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FillLayout(SWT.VERTICAL));
		initialize();

		setControl(container);

		sysdef = new SystemDefinitionFilesWidget(container, SWT.NONE);

		if (sysdef instanceof ValidModelObservable) {
			((ValidModelObservable) sysdef).addModelListener(this);
		}
	}

	@Override
	public void dispose() {
		if (sysdef != null) {
			if (sysdef instanceof ValidModelObservable) {
				((ValidModelObservable) sysdef).removeModelListener(this);
			}
		}
		
		super.dispose();
	}

	/**
	 * Returns a list of the system definition files
	 * 
	 * @return String[]
	 */
	public String[] getSystemDefinitions() {
		return sysdef.getSystemDefinitions();
	}

	private void initialize() {
	}

	/**
	 * Sets the system definition files
	 * 
	 * @param sysDefs
	 *            A list containing system definition files
	 * @return void
	 */
	public void setSystemDefinitions(String[] sysDefs) {
		sysdef.setSystemDefinitions(sysDefs);
	}

	/**
	 * This is called by the observed object when a change is made and controls
	 * wizard flow
	 * 
	 * @param event
	 *            the ValidModelEvent object created by the observer object and
	 *            indicating if the wizard page is complete
	 * @return void
	 */
	public void validModelDefined(ValidModelEvent event) {
		Boolean isValid = event.isValid();

		setPageComplete(isValid);
	}
}
