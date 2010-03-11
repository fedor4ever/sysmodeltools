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

package com.symbian.smt.gui.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.smtwidgets.ModelLabelsWidget;
import com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent.Type;

public class LabelPreferences extends PreferencePage implements
		IWorkbenchPreferencePage, ValidModelDefinedListener {

	private ModelLabelsWidget labelsWidget;
	private PersistentDataStore instanceStore;
	private PersistentDataStore defaultStore;

	public LabelPreferences() {
	}

	public LabelPreferences(String title) {
		super(title);
	}

	public LabelPreferences(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		labelsWidget = new ModelLabelsWidget(composite, SWT.NONE);

		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);
		defaultStore = new PersistentDataStore(defaultNode);

		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);
		instanceStore = new PersistentDataStore(instanceNode, defaultNode);

		labelsWidget.initialiseModelVersionText(defaultStore);
		labelsWidget.initialiseDistributionText(defaultStore);
		labelsWidget.addModelListener(this);
		labelsWidget.setModelVersion(instanceStore.getModelVersion());
		labelsWidget.setSystemVersion(instanceStore.getSystemVersion());
		labelsWidget.setCopyrightText(instanceStore.getCopyrightText());
		labelsWidget.setModelName(instanceStore.getModelName());
		labelsWidget.setSystemName(instanceStore.getSystemName());

		composite.pack();
		parent.pack();

		return composite;
	}

	@Override
	public void dispose() {
		if (labelsWidget != null) {
			labelsWidget.removeModelListener(this);
		}
		
		super.dispose();
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("System Model Diagram default labels");
	}

	public void performDefaults() {
		restoreDefaults();
	}

	public boolean performOk() {
		storeValues();
		return super.performOk();
	}

	private void restoreDefaults() {
		labelsWidget.setDistributionTexts(defaultStore.getDistributionTexts());
		labelsWidget.setSelectedDistributionText(defaultStore
				.getSelectedDistributionText());
		labelsWidget.setModelVersion(defaultStore.getModelVersion());
		labelsWidget.setSystemVersion(defaultStore.getSystemVersion());
		labelsWidget.setCopyrightText(defaultStore.getCopyrightText());
		labelsWidget.setModelVersionTexts(defaultStore.getModelVersionTexts());
		labelsWidget.setSelectedModelVersionText(defaultStore
				.getSelectedModelVersionText());
		labelsWidget.setModelName(defaultStore.getModelName());
		labelsWidget.setSystemName(defaultStore.getSystemName());
	}

	private void storeValues() {
		instanceStore.setDistributionTexts(labelsWidget.getDistributionTexts());
		instanceStore.setSelectedDistributionText(labelsWidget
				.getSelectedDistributionText());
		instanceStore.setModelVersion(labelsWidget.getModelVersion());
		instanceStore.setSystemVersion(labelsWidget.getSystemVersion());
		instanceStore.setCopyrightText(labelsWidget.getCopyrightText());
		instanceStore.setModelVersionTexts(labelsWidget.getModelVersionTexts());
		instanceStore.setSelectedModelVersionText(labelsWidget
				.getSelectedModelVersionText());
		instanceStore.setModelName(labelsWidget.getModelName());
		instanceStore.setSystemName(labelsWidget.getSystemName());
	}

	/**
	 * This method is called by the observed object when a change occurs to
	 * ensure the information entered into the widget is acceptable. In this
	 * case we set the page in error mode if validation of the model version
	 * text field or of the distribution text field fails.
	 * 
	 * @return void
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener#validModelDefined(ValidModelEvent)
	 **/
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
