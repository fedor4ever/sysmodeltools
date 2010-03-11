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
// ${file_name}
// 
//

package com.symbian.smt.gui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.symbian.smt.gui.AbstractPersistentDataStore;
import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.Logger;
import com.symbian.smt.gui.ManageResources;
import com.symbian.smt.gui.PersistentDataStore;

public class NewSMTProjectWizard extends Wizard implements INewWizard,
		IExecutableExtension {
	private NewProjectCreationPageCaseInsensitive projectCreationWizardPage;

	private NewProjectWizardSystemDefsPage systemDefsWizardPage;
	private NewProjectWizardTabbedPropertiesPage tabbedPropertiesWizardPage;

	private ISelection selection;
	private IWorkbench workbench;
	private IConfigurationElement config;
	private IProject projectHandle;

	/**
	 * This is the entry point for creating and managing the wizard
	 * 
	 * @return void
	 */
	public NewSMTProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adds pages to the wizard
	 * 
	 * @return void
	 */
	public void addPages() {
		projectCreationWizardPage = new NewProjectCreationPageCaseInsensitive(
				"page1");

		projectCreationWizardPage.setTitle("System Model Manager Wizard");
		projectCreationWizardPage
				.setDescription("Enter a name for the new project...");
		addPage(projectCreationWizardPage);

		systemDefsWizardPage = new NewProjectWizardSystemDefsPage(selection);
		addPage(systemDefsWizardPage);

		tabbedPropertiesWizardPage = new NewProjectWizardTabbedPropertiesPage(
				selection);
		addPage(tabbedPropertiesWizardPage);
	}

	private void copyFilesIntoProject() {
		// Add the folders and files to the project
		ManageResources.updateShapesFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultShapesFiles());
		ManageResources.updateLevelsFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultLevelsFiles());
		ManageResources.updateLocalisationFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultLocalisationFiles());
		ManageResources.updateDependenciesFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultDependenciesFiles());
		ManageResources.updateSystemInfoFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultSystemInfoFiles());
		ManageResources.updateColoursFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultColoursFiles());
		ManageResources.updateBorderStylesFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultBorderStylesFiles());
		ManageResources.updateBorderShapesFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultBorderShapesFiles());
		ManageResources.updatePatternsFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultPatternsFiles());
		ManageResources.updateS12XmlFiles(projectHandle,
				tabbedPropertiesWizardPage.getDefaultS12XmlFiles());

		// Add the system definition files to the project
		ManageResources.updateSystemDefinitionFiles(projectHandle,
				systemDefsWizardPage.getSystemDefinitions(), false);
	}

	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);

		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);

		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);
		PersistentDataStore instanceStore = new PersistentDataStore(
				instanceNode, defaultNode);

		tabbedPropertiesWizardPage.initialize(instanceStore);
	}

	/**
	 * Sets up the project - project folder, nature, files, folders etc
	 * 
	 * @return void
	 */
	void createProject(IProjectDescription description, IProject proj,
			IProgressMonitor monitor) throws CoreException,
			OperationCanceledException {
		try {

			monitor.beginTask("", 2000);

			proj.create(description, new SubProgressMonitor(monitor, 1000));

			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			proj.open(IResource.BACKGROUND_REFRESH, new SubProgressMonitor(
					monitor, 1000));

			try {
				String[] natures = description.getNatureIds();
				String[] newNatures = new String[natures.length + 1];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				newNatures[natures.length] = "com.symbian.smt.gui.nature";
				description.setNatureIds(newNatures);
				proj.setDescription(description, null);
			} catch (CoreException e) {
				Logger.log(e.getMessage(), e);
			}

		} finally {
			monitor.done();
		}
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialise
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		this.workbench = workbench;
	}

	/**
	 * This method is called when the finish button is pressed in the wizard
	 * 
	 * @return boolean
	 */
	public boolean performFinish() {
		projectHandle = projectCreationWizardPage.getProjectHandle();

		if (projectHandle == null) {
			return false;
		}

		URI projectURI = (!projectCreationWizardPage.useDefaults()) ? projectCreationWizardPage
				.getLocationURI()
				: null;

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		final IProjectDescription desc = workspace
				.newProjectDescription(projectHandle.getName());

		desc.setLocationURI(projectURI);

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			protected void execute(IProgressMonitor monitor)
					throws CoreException {
				createProject(desc, projectHandle, monitor);
			}
		};

		try {
			getContainer().run(false, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException
					.getMessage());
			return false;
		}
		
		completePerformFinish();

		// If auto building has been disabled then we force the build

	    IWorkspaceDescription description = workspace.getDescription();
	    // If auto building has been disabled then we force the build
	    if (!description.isAutoBuilding()) {
			Job j= new Job("Building workspace") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						projectHandle.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
					} catch (CoreException e) {
						Logger.log(e.getMessage(), e);
					}
					
					return new Status(IStatus.OK, Activator.PLUGIN_ID, IStatus.OK, "updating properties succeeded", null);
				}
			};
			j.schedule();
	    }
		
		return true;
	}

	private void completePerformFinish() {
		IWorkspaceRunnable  op = new IWorkspaceRunnable () {
			public void run(IProgressMonitor monitor) throws CoreException  {
				monitor.beginTask("Creating project", 0);
				
				// Persist the project information
				// This needs to be done first as the builder will try to read from the
				// persistent store
				persistInformation();

				// Copy the files into the project / Create file shortcut icons
				copyFilesIntoProject();

				BasicNewProjectResourceWizard.updatePerspective(config);
				BasicNewProjectResourceWizard.selectAndReveal(projectHandle, workbench
						.getActiveWorkbenchWindow());

				monitor.worked(1);
				monitor.done();
			}
		};
		
		try {
			ResourcesPlugin.getWorkspace().run(op, projectHandle, IWorkspace.AVOID_UPDATE, null);
		} catch (CoreException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
			return;
		}
	}
	
	private void persistInformation() {
		IScopeContext projectScope = new ProjectScope(projectHandle);
		IEclipsePreferences node = projectScope.getNode(Activator.PLUGIN_ID);

		AbstractPersistentDataStore dataStore = new PersistentDataStore(node);

		// Persist the Output Filename
		dataStore.setOutputFilename(tabbedPropertiesWizardPage
				.getOutputFilename());
		
		// Persist the Model Labels
		dataStore.setCopyrightText(tabbedPropertiesWizardPage
				.getCopyrightText());
		dataStore.setDistributionTexts(tabbedPropertiesWizardPage
				.getDistributionTexts());
		dataStore.setSelectedDistributionText(tabbedPropertiesWizardPage
				.getSelectedDistributionText());
		dataStore.setModelName(tabbedPropertiesWizardPage.getModelName());
		dataStore.setModelVersion(tabbedPropertiesWizardPage.getModelVersion());
		dataStore.setModelVersionTexts(tabbedPropertiesWizardPage
				.getModelVersionTexts());
		dataStore.setSelectedModelVersionText(tabbedPropertiesWizardPage
				.getSelectedModelVersionText());
		dataStore.setSystemName(tabbedPropertiesWizardPage.getSystemName());
		dataStore.setSystemVersion(tabbedPropertiesWizardPage
				.getSystemVersion());

		// Persist the Model Control
		dataStore.setHighlightCoreOS(tabbedPropertiesWizardPage
				.getHighlightCoreOS());
		dataStore.setLevelOfDetail(tabbedPropertiesWizardPage
				.getLevelOfDetail());
		dataStore.setPrintedDpis(tabbedPropertiesWizardPage.getPrintedDpis());
		dataStore.setSelectedPrintedDpi(tabbedPropertiesWizardPage
				.getSelectedPrintedDpi());
		dataStore.setSuppressMouseOverEffect(tabbedPropertiesWizardPage
				.getSuppressMouseOverEffect());
		dataStore.setFixItemSize(tabbedPropertiesWizardPage.getFixItemSize());

		// Persist the Resources, the selected file is persisted by the
		// ManageResources widget
		// All files (Selection is persisted as part of method
		// copyFilesIntoProject())
		dataStore.setBorderShapesFiles(tabbedPropertiesWizardPage
				.getBorderShapesFiles());
		dataStore.setBorderStylesFiles(tabbedPropertiesWizardPage
				.getBorderStylesFiles());
		dataStore.setColoursFiles(tabbedPropertiesWizardPage.getColoursFiles());
		dataStore.setDependenciesFiles(tabbedPropertiesWizardPage
				.getDependenciesFiles());
		dataStore.setLevelsFiles(tabbedPropertiesWizardPage.getLevelsFiles());
		dataStore.setLocalisationFiles(tabbedPropertiesWizardPage
				.getLocalisationFiles());
		dataStore.setPatternsFiles(tabbedPropertiesWizardPage
				.getPatternsFiles());
		dataStore.setShapesFiles(tabbedPropertiesWizardPage.getShapesFiles());
		dataStore.setSystemInfoFiles(tabbedPropertiesWizardPage
				.getSystemInfoFiles());
		dataStore.setS12XmlFiles(tabbedPropertiesWizardPage.getS12XmlFiles());

		// Persist the Ignore Items
		dataStore.setIgnoreItems(tabbedPropertiesWizardPage.getIgnoreItems());

		// Persist the Filter Items
		dataStore
				.setFilterHasItems(tabbedPropertiesWizardPage.getFilterItems());

		// Persist the Advanced Options
		dataStore.setAdvancedOptions(tabbedPropertiesWizardPage
				.getAdvancedOptions());


	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		this.config = config;
	}

}
