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
import com.symbian.smt.gui.smtwidgets.AdvancedOptionsWidget;

/**
 * This is the Preference page for the Advanced Options. It utilises the
 * <code>AdvancedOptionsWidget</code>.
 * <p>
 * By default, there are no advanced options defined.
 * </p>
 * 
 * @author barbararosi-schwartz
 */
public class AdvancedOptionsPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

	private AdvancedOptionsWidget advancedOptionsWidget;
	private AbstractPersistentDataStore instanceStore;
	private AbstractPersistentDataStore defaultStore;

	public AdvancedOptionsPreferences() {
	}

	public AdvancedOptionsPreferences(String title) {
		super(title);
	}

	public AdvancedOptionsPreferences(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		advancedOptionsWidget = new AdvancedOptionsWidget(composite, SWT.NONE);

		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);
		defaultStore = new PersistentDataStore(defaultNode);

		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);
		instanceStore = new PersistentDataStore(instanceNode, defaultNode);

		advancedOptionsWidget.setAdvancedOptions(instanceStore
				.getAdvancedOptions());

		composite.pack();
		parent.pack();

		return composite;
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("System Model Diagram advanced options.\n"
				+ "These must be defined in their entirety. "
				+ "Their order is also user defined.");
	}

	public void performDefaults() {
		restoreDefaults();
	}

	public boolean performOk() {
		storeValues();
		return super.performOk();
	}

	private void restoreDefaults() {
		advancedOptionsWidget.setAdvancedOptions(defaultStore
				.getAdvancedOptions());
	}

	private void storeValues() {
		instanceStore.setAdvancedOptions(advancedOptionsWidget
				.getAdvancedOptions());
	}
}
