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
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.symbian.smt.gui.AbstractPersistentDataStore;
import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget;

public class ResourcesPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

	private ResourcesWidget resourcesWidget;
	private AbstractPersistentDataStore instanceStore;
	private AbstractPersistentDataStore defaultStore;

	public ResourcesPreferences() {
	}

	public ResourcesPreferences(String title) {
		super(title);
	}

	public ResourcesPreferences(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		resourcesWidget = new ResourcesWidget(composite, SWT.NONE);

		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);
		defaultStore = new PersistentDataStore(defaultNode);

		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);
		instanceStore = new PersistentDataStore(instanceNode, defaultNode);

		// All files
		resourcesWidget.setBorderShapesFiles(instanceStore
				.getBorderShapesFiles());
		resourcesWidget.setBorderStylesFiles(instanceStore
				.getBorderStylesFiles());
		resourcesWidget.setColoursFiles(instanceStore.getColoursFiles());
		resourcesWidget.setDependenciesFiles(instanceStore
				.getDependenciesFiles());
		resourcesWidget.setLevelsFiles(instanceStore.getLevelsFiles());
		resourcesWidget.setLocalisationFiles(instanceStore
				.getLocalisationFiles());
		resourcesWidget.setPatternsFiles(instanceStore.getPatternsFiles());
		resourcesWidget.setShapesFiles(instanceStore.getShapesFiles());
		resourcesWidget.setSystemInfoFiles(instanceStore.getSystemInfoFiles());
		resourcesWidget.setS12XmlFiles(instanceStore.getS12XmlFiles());

		// Default files
		resourcesWidget.setSelectedBorderShapesFiles(instanceStore
				.getSelectedBorderShapesFiles());
		resourcesWidget.setSelectedBorderStylesFiles(instanceStore
				.getSelectedBorderStylesFiles());
		resourcesWidget.setSelectedColoursFiles(instanceStore
				.getSelectedColoursFiles());
		resourcesWidget.setSelectedDependenciesFiles(instanceStore
				.getSelectedDependenciesFiles());
		resourcesWidget.setSelectedLevelsFiles(instanceStore
				.getSelectedLevelsFiles());
		resourcesWidget.setSelectedLocalisationFiles(instanceStore
				.getSelectedLocalisationFiles());
		resourcesWidget.setSelectedPatternsFiles(instanceStore
				.getSelectedPatternsFiles());
		resourcesWidget.setSelectedShapesFiles(instanceStore
				.getSelectedShapesFiles());
		resourcesWidget.setSelectedSystemInfoFiles(instanceStore
				.getSelectedSystemInfoFiles());
		resourcesWidget.setSelectedS12XmlFiles(instanceStore
				.getSelectedS12XmlFiles());
		composite.pack();
		parent.pack();

		return composite;
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Location of configuration files used when building System Model Diagram.");
	}

	public void performDefaults() {
		restoreDefaults();
	}

	public boolean performOk() {
		storeValues();
		return super.performOk();
	}

	private void restoreDefaults() {

		// All files
		resourcesWidget.setBorderShapesFiles(defaultStore
				.getBorderShapesFiles());
		resourcesWidget.setBorderStylesFiles(defaultStore
				.getBorderStylesFiles());
		resourcesWidget.setColoursFiles(defaultStore.getColoursFiles());
		resourcesWidget.setDependenciesFiles(defaultStore
				.getDependenciesFiles());
		resourcesWidget.setLevelsFiles(defaultStore.getLevelsFiles());
		resourcesWidget.setLocalisationFiles(defaultStore
				.getLocalisationFiles());
		resourcesWidget.setPatternsFiles(defaultStore.getPatternsFiles());
		resourcesWidget.setShapesFiles(defaultStore.getShapesFiles());
		resourcesWidget.setSystemInfoFiles(defaultStore.getSystemInfoFiles());
		resourcesWidget.setS12XmlFiles(defaultStore.getS12XmlFiles());

		// Default value
		resourcesWidget.setSelectedBorderShapesFiles(defaultStore
				.getSelectedBorderShapesFiles());
		resourcesWidget.setSelectedBorderStylesFiles(defaultStore
				.getSelectedBorderStylesFiles());
		resourcesWidget.setSelectedColoursFiles(defaultStore
				.getSelectedColoursFiles());
		resourcesWidget.setSelectedDependenciesFiles(defaultStore
				.getSelectedDependenciesFiles());
		resourcesWidget.setSelectedLevelsFiles(defaultStore
				.getSelectedLevelsFiles());
		resourcesWidget.setSelectedLocalisationFiles(defaultStore
				.getSelectedLocalisationFiles());
		resourcesWidget.setSelectedPatternsFiles(defaultStore
				.getSelectedPatternsFiles());
		resourcesWidget.setSelectedShapesFiles(defaultStore
				.getSelectedShapesFiles());
		resourcesWidget.setSelectedSystemInfoFiles(defaultStore
				.getSelectedSystemInfoFiles());
		resourcesWidget.setSelectedS12XmlFiles(defaultStore
				.getSelectedS12XmlFiles());
	}

	private void storeValues() {
		Runnable runnable = new Runnable() {
			public void run() {
			// All files
			instanceStore.setBorderShapesFiles(resourcesWidget
					.getBorderShapesFiles());
			instanceStore.setBorderStylesFiles(resourcesWidget
					.getBorderStylesFiles());
			instanceStore.setColoursFiles(resourcesWidget.getColoursFiles());
			instanceStore.setDependenciesFiles(resourcesWidget
					.getDependenciesFiles());
			instanceStore.setLevelsFiles(resourcesWidget.getLevelsFiles());
			instanceStore.setLocalisationFiles(resourcesWidget
					.getLocalisationFiles());
			instanceStore.setPatternsFiles(resourcesWidget.getPatternsFiles());
			instanceStore.setShapesFiles(resourcesWidget.getShapesFiles());
			instanceStore.setSystemInfoFiles(resourcesWidget.getSystemInfoFiles());
			instanceStore.setS12XmlFiles(resourcesWidget.getS12XmlFiles());
	
			// Default value
			instanceStore.setSelectedBorderShapesFiles(resourcesWidget
					.getSelectedBorderShapesFiles());
			instanceStore.setSelectedBorderStylesFiles(resourcesWidget
					.getSelectedBorderStylesFiles());
			instanceStore.setSelectedColoursFiles(resourcesWidget
					.getSelectedColoursFiles());
			instanceStore.setSelectedDependenciesFiles(resourcesWidget
					.getSelectedDependenciesFiles());
			instanceStore.setSelectedLevelsFiles(resourcesWidget
					.getSelectedLevelsFiles());
			instanceStore.setSelectedLocalisationFiles(resourcesWidget
					.getSelectedLocalisationFiles());
			instanceStore.setSelectedPatternsFiles(resourcesWidget
					.getSelectedPatternsFiles());
			instanceStore.setSelectedShapesFiles(resourcesWidget
					.getSelectedShapesFiles());
			instanceStore.setSelectedSystemInfoFiles(resourcesWidget
					.getSelectedSystemInfoFiles());
			instanceStore.setSelectedS12XmlFiles(resourcesWidget
					.getSelectedS12XmlFiles());
			}
		};
		
		BusyIndicator.showWhile(getShell().getDisplay(), runnable);
	}

}
