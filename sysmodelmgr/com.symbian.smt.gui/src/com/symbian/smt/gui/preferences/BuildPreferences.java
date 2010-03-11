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

import com.symbian.smt.gui.AbstractPersistentDataStore;
import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.smtwidgets.BuildControlWidget;
import com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent;

public class BuildPreferences extends PreferencePage implements
		IWorkbenchPreferencePage, ValidModelDefinedListener {

	private BuildControlWidget buildWidget;

	private AbstractPersistentDataStore instanceStore;
	private AbstractPersistentDataStore defaultStore;

	public BuildPreferences() {
	}

	public BuildPreferences(String title) {
		super(title);
	}

	public BuildPreferences(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		buildWidget = new BuildControlWidget(composite, SWT.NONE, true);

		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);
		defaultStore = new PersistentDataStore(defaultNode);

		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);
		instanceStore = new PersistentDataStore(instanceNode, defaultNode);

		buildWidget.setOutputFilename(instanceStore.getOutputFilename());
		buildWidget.setWarningLevel(instanceStore.getWarningLevel());

		buildWidget.addModelListener(this);

		composite.pack();
		parent.pack();

		return composite;
	}

	@Override
	public void dispose() {
		if (buildWidget != null) {
			buildWidget.removeModelListener(this);
		}
		
		super.dispose();
	}

	/*
	 * Comma-separated list of filters to turn on when building the model. All
	 * filters on an item must be present in this list in order for that item to
	 * appear. Order does not matter
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("System Model Manager build options.");
	}

	public void performDefaults() {
		restoreDefaults();
	}

	public boolean performOk() {
		storeValues();
		return super.performOk();
	}

	private void restoreDefaults() {
		buildWidget.setOutputFilename(defaultStore.getOutputFilename());
		buildWidget.setWarningLevel(defaultStore.getWarningLevel());
	}

	private void storeValues() {
		instanceStore.setOutputFilename(buildWidget.getOutputFilename());
		instanceStore.setWarningLevel(buildWidget.getWarningLevel());
	}

	/*
	 * This is called by the observed object when a change is used to ensure the
	 * information entered into the widget is acceptable, in this case at least
	 * 1 system definition xml file
	 * 
	 * @return void
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
