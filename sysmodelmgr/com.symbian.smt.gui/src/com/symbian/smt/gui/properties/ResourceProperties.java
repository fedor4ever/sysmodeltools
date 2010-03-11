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
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

import com.symbian.smt.gui.AbstractPersistentDataStore;
import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.ManageResources;
import com.symbian.smt.gui.NodeListener;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.preferences.SmmPreferencesInitializer;
import com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget;

public class ResourceProperties extends PropertyPage implements
		IWorkbenchPropertyPage {

	private ResourcesWidget resourcesWidget;
	private AbstractPersistentDataStore projectStore;
	private AbstractPersistentDataStore instanceStore;

	@Override
	protected Control createContents(Composite parent) {
		new NodeListener(getProject());

		// Initialise the default values
		SmmPreferencesInitializer initialiser = new SmmPreferencesInitializer();
		initialiser.initializeDefaultPreferences();

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
		resourcesWidget = new ResourcesWidget(composite, SWT.NONE);

		// Set required values
		populate(projectStore);

		return composite;
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

	private void populate(AbstractPersistentDataStore dataStore) {

		// All files
		resourcesWidget.setBorderShapesFiles(dataStore.getBorderShapesFiles());
		resourcesWidget.setBorderStylesFiles(dataStore.getBorderStylesFiles());
		resourcesWidget.setColoursFiles(dataStore.getColoursFiles());
		resourcesWidget.setDependenciesFiles(dataStore.getDependenciesFiles());
		resourcesWidget.setLevelsFiles(dataStore.getLevelsFiles());
		resourcesWidget.setLocalisationFiles(dataStore.getLocalisationFiles());
		resourcesWidget.setPatternsFiles(dataStore.getPatternsFiles());
		resourcesWidget.setShapesFiles(dataStore.getShapesFiles());
		resourcesWidget.setSystemInfoFiles(dataStore.getSystemInfoFiles());
		resourcesWidget.setS12XmlFiles(dataStore.getS12XmlFiles());

		// Selected files
		resourcesWidget.setSelectedBorderShapesFiles(dataStore
				.getSelectedBorderShapesFiles());
		resourcesWidget.setSelectedBorderStylesFiles(dataStore
				.getSelectedBorderStylesFiles());
		resourcesWidget.setSelectedColoursFiles(dataStore
				.getSelectedColoursFiles());
		resourcesWidget.setSelectedDependenciesFiles(dataStore
				.getSelectedDependenciesFiles());
		resourcesWidget.setSelectedLevelsFiles(dataStore
				.getSelectedLevelsFiles());
		resourcesWidget.setSelectedLocalisationFiles(dataStore
				.getSelectedLocalisationFiles());
		resourcesWidget.setSelectedPatternsFiles(dataStore
				.getSelectedPatternsFiles());
		resourcesWidget.setSelectedShapesFiles(dataStore
				.getSelectedShapesFiles());
		resourcesWidget.setSelectedSystemInfoFiles(dataStore
				.getSelectedSystemInfoFiles());
		resourcesWidget.setSelectedS12XmlFiles(dataStore
				.getSelectedS12XmlFiles());
	}

	private void saveChanges() {
		Runnable runnable = new Runnable() {
			public void run() {
				// All files
				projectStore.setBorderShapesFiles(resourcesWidget
						.getBorderShapesFiles());
				projectStore.setBorderStylesFiles(resourcesWidget
						.getBorderStylesFiles());
				projectStore.setColoursFiles(resourcesWidget.getColoursFiles());
				projectStore.setDependenciesFiles(resourcesWidget
						.getDependenciesFiles());
				projectStore.setLevelsFiles(resourcesWidget.getLevelsFiles());
				projectStore.setLocalisationFiles(resourcesWidget
						.getLocalisationFiles());
				projectStore.setPatternsFiles(resourcesWidget.getPatternsFiles());
				projectStore.setShapesFiles(resourcesWidget.getShapesFiles());
				projectStore.setSystemInfoFiles(resourcesWidget.getSystemInfoFiles());
				projectStore.setS12XmlFiles(resourcesWidget.getS12XmlFiles());
		
				// Add the folders and files to the project
				ManageResources.updateShapesFiles(getProject(), resourcesWidget
						.getSelectedShapesFiles());
				ManageResources.updateLevelsFiles(getProject(), resourcesWidget
						.getSelectedLevelsFiles());
				ManageResources.updateLocalisationFiles(getProject(), resourcesWidget
						.getSelectedLocalisationFiles());
				ManageResources.updateDependenciesFiles(getProject(), resourcesWidget
						.getSelectedDependenciesFiles());
				ManageResources.updateSystemInfoFiles(getProject(), resourcesWidget
						.getSelectedSystemInfoFiles());
				ManageResources.updateColoursFiles(getProject(), resourcesWidget
						.getSelectedColoursFiles());
				ManageResources.updateBorderStylesFiles(getProject(), resourcesWidget
						.getSelectedBorderStylesFiles());
				ManageResources.updateBorderShapesFiles(getProject(), resourcesWidget
						.getSelectedBorderShapesFiles());
				ManageResources.updatePatternsFiles(getProject(), resourcesWidget
						.getSelectedPatternsFiles());
				ManageResources.updateS12XmlFiles(getProject(), resourcesWidget
						.getSelectedS12XmlFiles());
			}
		};
		
		BusyIndicator.showWhile(getShell().getDisplay(), runnable);
	}

}