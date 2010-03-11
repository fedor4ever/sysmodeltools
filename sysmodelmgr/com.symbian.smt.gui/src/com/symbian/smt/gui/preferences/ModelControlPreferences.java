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
import com.symbian.smt.gui.smtwidgets.ModelControlWidget;
import com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent.Type;

public class ModelControlPreferences extends PreferencePage implements
		IWorkbenchPreferencePage, ValidModelDefinedListener {

	private ModelControlWidget modelControlWidget;
	private PersistentDataStore instanceStore;
	private PersistentDataStore defaultStore;

	public ModelControlPreferences() {
	}

	public ModelControlPreferences(String title) {
		super(title);
	}

	public ModelControlPreferences(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		modelControlWidget = new ModelControlWidget(composite, SWT.NONE);

		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);
		defaultStore = new PersistentDataStore(defaultNode);

		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);
		instanceStore = new PersistentDataStore(instanceNode, defaultNode);

		modelControlWidget.initialisePrintedDpi(defaultStore);
		modelControlWidget.addModelListener(this);
		populate(instanceStore);
		composite.pack();
		parent.pack();

		return composite;
	}

	@Override
	public void dispose() {
		if (modelControlWidget != null) {
			modelControlWidget.removeModelListener(this);
		}
		
		super.dispose();
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("System Model Diagram model control.");
	}

	public void performDefaults() {
		restoreDefaults();
	}

	public boolean performOk() {
		storeValues();
		return super.performOk();
	}

	private void populate(PersistentDataStore dataStore) {
		// printedDpi field has already been initialised via the call to
		// modelControlWidget.initialisePrintedDpi()
		modelControlWidget.setHighlightCoreOS(dataStore.getHighlightCoreOS());
		modelControlWidget.setLevelOfDetail(dataStore.getLevelOfDetail());
		modelControlWidget.setSuppressMouseOverEffect(dataStore
				.getSuppressMouseOverEffect());
		modelControlWidget.setFixItemSize(dataStore.getFixItemSize());
	}

	private void restoreDefaults() {
		modelControlWidget.setLevelOfDetail(defaultStore.getLevelOfDetail());
		modelControlWidget.setPrintedDpis(defaultStore.getPrintedDpis());
		modelControlWidget.setSelectedPrintedDpi(defaultStore
				.getSelectedPrintedDpi());
		modelControlWidget
				.setHighlightCoreOS(defaultStore.getHighlightCoreOS());
		modelControlWidget.setSuppressMouseOverEffect(defaultStore
				.getSuppressMouseOverEffect());
		modelControlWidget.setFixItemSize(defaultStore.getFixItemSize());
	}

	private void storeValues() {
		instanceStore.setHighlightCoreOS(modelControlWidget
				.getHighlightCoreOS());
		instanceStore.setLevelOfDetail(modelControlWidget.getLevelOfDetail());
		instanceStore.setPrintedDpis(modelControlWidget.getPrintedDpis());
		instanceStore.setSelectedPrintedDpi(modelControlWidget
				.getSelectedPrintedDpi());
		instanceStore.setSuppressMouseOverEffect(modelControlWidget
				.getSuppressMouseOverEffect());
		instanceStore.setFixItemSize(modelControlWidget.getFixItemSize());
	}

	/**
	 * This method is called by the observed object when a change occurs to
	 * ensure the information entered into the widget is acceptable. In this
	 * case we set the page in error mode if validation of the printed DPI field
	 * fails.
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
