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

package com.symbian.smt.gui.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.NodeListener;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.smtwidgets.BuildControlWidget;
import com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent;

public class BuildControlProperties extends PropertyPage implements
		IWorkbenchPropertyPage, ValidModelDefinedListener {

	private BuildControlWidget buildWidget;
	private PersistentDataStore projectStore;
	private PersistentDataStore instanceStore;

	@Override
	protected Control createContents(Composite parent) {
		new NodeListener(getProject());

		// Create the project scope data store
		IScopeContext projectScope = new ProjectScope(getProject());
		projectStore = new PersistentDataStore(projectScope
				.getNode(Activator.PLUGIN_ID));

		// Create the default scope data store
		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);

		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);

		instanceStore = new PersistentDataStore(instanceNode, defaultNode);

		// Create the widget
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		buildWidget = new BuildControlWidget(composite, SWT.NONE, false);
		buildWidget.addModelListener(this);

		// Set required values
		populate(projectStore);

		return composite;
	}

	@Override
	public void dispose() {
		if (buildWidget != null) {
			buildWidget.removeModelListener(this);
		}
		
		super.dispose();
	}

	private IProject getProject() {
		return (IProject) getElement();
	}

	@Override
	public boolean isValid() {
		return super.isValid();
	}

	@Override
	protected void performApply() {
		saveChanges();
	}

	@Override
	protected void performDefaults() {
		populate(instanceStore);
	}

	@Override
	public boolean performOk() {
		saveChanges();
		return super.performOk();
	}

	private void populate(PersistentDataStore dataStore) {
		buildWidget.setOutputFilename(dataStore.getOutputFilename());
	}

	private void saveChanges() {
		projectStore.setOutputFilename(buildWidget.getOutputFilename());
	}

	/**
	 * This is called by the observed object when a change is used to ensure the
	 * information entered into the widget is acceptable, in this case a non
	 * empty output file name with no illegal characters.
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
