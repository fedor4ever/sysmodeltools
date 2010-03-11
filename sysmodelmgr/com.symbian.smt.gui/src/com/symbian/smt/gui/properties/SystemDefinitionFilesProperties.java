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
// SystemDefinitionFiles.java
// 
//

package com.symbian.smt.gui.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.ManageResources;
import com.symbian.smt.gui.NodeListener;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.smtwidgets.SystemDefinitionFilesWidget;
import com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent;
import com.symbian.smt.gui.smtwidgets.ValidModelObservable;

public class SystemDefinitionFilesProperties extends PropertyPage implements
		IWorkbenchPropertyPage, ValidModelDefinedListener {
	private SystemDefinitionFilesWidget systemDefinitionFilesWidget;
	private PersistentDataStore projectStore;

	@Override
	protected Control createContents(Composite parent) {
		new NodeListener(getProject());

		// Disable the restore defaults button
		noDefaultAndApplyButton();

		// Create the project scope data store
		IScopeContext projectScope = new ProjectScope(this.getProject());
		projectStore = new PersistentDataStore(projectScope
				.getNode(Activator.PLUGIN_ID));

		// Create the widget
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		systemDefinitionFilesWidget = new SystemDefinitionFilesWidget(
				composite, SWT.NONE);

		if (systemDefinitionFilesWidget instanceof ValidModelObservable) {
			((ValidModelObservable) systemDefinitionFilesWidget)
					.addModelListener(this);
		}

		// Set required values
		populate(projectStore);

		return composite;
	}

	@Override
	public void dispose() {
		if (systemDefinitionFilesWidget != null) {
			((ValidModelObservable) systemDefinitionFilesWidget)
				.removeModelListener(this);
		}
		
		super.dispose();
	}

	private IProject getProject() {
		return (IProject) getElement();
	}

	@Override
	public boolean isValid() {
		// return requiredInformationPresent;
		return super.isValid();
	}

	@Override
	protected void performApply() {
		saveChanges();
	}

	@Override
	protected void performDefaults() {
	}

	@Override
	public boolean performOk() {
		saveChanges();
		return super.performOk();
	}

	private void populate(PersistentDataStore dataStore) {
		systemDefinitionFilesWidget.setSystemDefinitions(dataStore
				.getSystemDefinitionFiles());
	}

	private void saveChanges() {
		Runnable runnable = new Runnable() {
			public void run() {
			// Update the system definition files in the project
			ManageResources.updateSystemDefinitionFiles(getProject(),
					systemDefinitionFilesWidget.getSystemDefinitions(), false);
	
			// Write to the persistent data store
			projectStore.setSystemDefinitionFiles(systemDefinitionFilesWidget
					.getSystemDefinitions());
			}
		};
		
		BusyIndicator.showWhile(getShell().getDisplay(), runnable);
	}

	/**
	 * This is called by the observed object when a change is used to ensure the
	 * information entered into the widget is acceptable, in this case at least
	 * 1 system definition xml file
	 * 
	 * @return void
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener#validModelDefined(ValidModelEvent)
	 */
	public void validModelDefined(ValidModelEvent event) {
		Boolean isValid = event.isValid();

		setValid(isValid);

		if (isValid) {
			setErrorMessage(null);
		} else {
			setErrorMessage(event.getMessage());
		}
	}
	
	
}
