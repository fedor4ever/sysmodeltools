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
import com.symbian.smt.gui.smtwidgets.FilterWidget;

public class FilterPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

	private FilterWidget filterWidget;
	private AbstractPersistentDataStore instanceStore;
	private AbstractPersistentDataStore defaultStore;

	public FilterPreferences() {
	}

	public FilterPreferences(String title) {
		super(title);
	}

	public FilterPreferences(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		filterWidget = new FilterWidget(composite, SWT.NONE);

		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);
		defaultStore = new PersistentDataStore(defaultNode);

		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);
		instanceStore = new PersistentDataStore(instanceNode, defaultNode);

		filterWidget.setFilterItems(instanceStore.getFilterHasItems());

		composite.pack();
		parent.pack();

		return composite;
	}

	/*
	 * Comma-separated list of filters to turn on when building the model. All
	 * filters on an item must be present in this list in order for that item to
	 * appear. Order does not matter
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("List of filters to turn on when building the model. \n"
				+ "All filters on an item must be present in this "
				+ "list in order for that item to appear. \n"
				+ "Order does not matter.");
	}

	public void performDefaults() {
		restoreDefaults();
	}

	public boolean performOk() {
		storeValues();
		return super.performOk();
	}

	private void restoreDefaults() {
		filterWidget.setFilterItems(defaultStore.getFilterHasItems());
	}

	private void storeValues() {
		instanceStore.setFilterHasItems(filterWidget.getFilterItems());
	}
}
