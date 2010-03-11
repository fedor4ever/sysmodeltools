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
// ModelLabelsProperties.java
// 
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
import com.symbian.smt.gui.smtwidgets.ModelLabelsWidget;
import com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent.Type;

public class ModelLabelProperties extends PropertyPage implements
		IWorkbenchPropertyPage, ValidModelDefinedListener {

	private ModelLabelsWidget modelLabelsWidget;
	private PersistentDataStore projectStore;
	private PersistentDataStore instanceStore;

	@Override
	protected Control createContents(Composite parent) {
		new NodeListener(getProject());

		// Create the project scope data store
		IScopeContext projectScope = new ProjectScope(this.getProject());
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
		modelLabelsWidget = new ModelLabelsWidget(composite, SWT.NONE);

		modelLabelsWidget.initialiseModelVersionText(projectStore);
		modelLabelsWidget.initialiseDistributionText(projectStore);
		modelLabelsWidget.addModelListener(this);

		// Set required values
		populate(projectStore);

		return composite;
	}

	@Override
	public void dispose() {
		if (modelLabelsWidget != null) {
			modelLabelsWidget.removeModelListener(this);
		}
		
		super.dispose();
	}

	private IProject getProject() {
		return (IProject) getElement();
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
		// Model Version Text and Distribution Text fields have already been
		// initialised via the calls
		// to modelLabelsWidget.initialiseModelVersionText() and
		// modelLabelsWidget.initialiseDistributionText() respectively
		modelLabelsWidget.setCopyrightText(dataStore.getCopyrightText());

		modelLabelsWidget.setModelName(dataStore.getModelName());

		modelLabelsWidget.setModelVersion(dataStore.getModelVersion());

		modelLabelsWidget.setSystemName(dataStore.getSystemName());

		modelLabelsWidget.setSystemVersion(dataStore.getSystemVersion());
	}

	private void saveChanges() {
		projectStore.setCopyrightText(modelLabelsWidget.getCopyrightText());

		projectStore.setDistributionTexts(modelLabelsWidget
				.getDistributionTexts());
		projectStore.setSelectedDistributionText(modelLabelsWidget
				.getSelectedDistributionText());

		projectStore.setModelName(modelLabelsWidget.getModelName());

		projectStore.setModelVersion(modelLabelsWidget.getModelVersion());

		projectStore.setModelVersionTexts(modelLabelsWidget
				.getModelVersionTexts());
		projectStore.setSelectedModelVersionText(modelLabelsWidget
				.getSelectedModelVersionText());

		projectStore.setSystemName(modelLabelsWidget.getSystemName());

		projectStore.setSystemVersion(modelLabelsWidget.getSystemVersion());
	}

	/**
	 * This method is called by the observed object when a change occurs to
	 * ensure the information entered into the widget is acceptable. In this
	 * case we set the page in error mode if validation of the model version
	 * text field or of the distribution text field fails.
	 * 
	 * @return void
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener#validModelDefined(ValidModelEvent)
	 */
	public void validModelDefined(ValidModelEvent event) {
		Boolean isValid = event.isValid();
		Type eventType = event.getType();

		setValid(isValid);

		if (isValid) {
			setErrorMessage(null);

			if (eventType == Type.WARNING) {
				setMessage(event.getMessage(), WARNING);
			}
		} else {
			setErrorMessage(event.getMessage());
			setMessage(null);
		}
	}
}