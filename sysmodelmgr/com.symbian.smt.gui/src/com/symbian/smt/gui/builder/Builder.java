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

package com.symbian.smt.gui.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.ChangeManager;
import com.symbian.smt.gui.Logger;
import com.symbian.smt.gui.ManageResources;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.editors.svgeditor.SVGEditor;
import com.symbian.smt.gui.views.ConsoleOutput;

public class Builder extends IncrementalProjectBuilder {
	final static String TEMP_FOLDER = ".svg_temp"; // Folder names stating with
	// a . will not be displayed
	// in the project navigator

	private IFolder svgTempFolder;
	private IFile projectFile;

	private List<IResource> markedResources;

	private void addFileToProject() {
		// Adds the file to the root of the project folder
		try {
			// Refresh the project so that it picks up the generated SVG file
			getProject().refreshLocal(IResource.DEPTH_INFINITE, null);

			// Set the file as a derived resource (indicates was built from
			// source and not an original file)
			projectFile.setDerived(true);
		} catch (CoreException e) {
			Logger.log(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		
		IProject project = getProject();

		if (!checkSMGinstalled()) {
			writeToConsoleOutput("ERROR: Unable to locate the System Model Generator");
			return null;
		}
		
		project.touch(null);

		// First we check that project is in sync with the file system
		findResourcesMarkedAsUrl(project);

		if (markedResources.size() == 0) {
			if (!project.isSynchronized(IResource.DEPTH_INFINITE)) {
				writeToConsoleOutput("ERROR: The project is out of sync with the file system");
				return null;
			}
		} else {
			boolean isSynchronised = checkSyncrhonised(project);

			if (!isSynchronised) {
				writeToConsoleOutput("ERROR: The project is out of sync with the file system");
				return null;
			}
		}

		IResourceDelta delta = getDelta(project);
		ChangeManager manager = new ChangeManager();

		manager.handleDelta(delta, project);

		if (!manager.needsBuilding(project)) {
			return null;
		}

		manager.reset(project);

		// We are not interested in different build types are we are only able
		// to perform one type of build using the Perl SMG

		svgTempFolder = project.getFolder(TEMP_FOLDER);

		// Delete generated temp files
		if (svgTempFolder.exists()) {
			svgTempFolder.delete(true, null);
		}

		// Build
		performBuild();

		return null;
	}

	private boolean checkSyncrhonised(IResource resource) {
		boolean result = true;

		if (resource instanceof IFile) {
			if (markedResources.contains(resource)) {
				result = true;
			} else {
				result = resource.isSynchronized(IResource.DEPTH_ZERO);
			}
		} else if (resource instanceof IContainer) { // It is either IProject or
														// IFolder
			IContainer container = (IContainer) resource;

			try {
				IResource[] children = container.members();

				for (IResource child : children) {
					result = checkSyncrhonised(child);

					if (!result) {
						break;
					}
				}
			} catch (CoreException e) {
				Logger.log(e.getMessage(), e);
				result = false;
			}
		}

		return result;
	}

	private void findResourcesMarkedAsUrl(IProject project)
			throws CoreException {
		IMarker[] messageMarkers = project.findMarkers(IMarker.TASK, false,
				IResource.DEPTH_INFINITE);
		markedResources = new ArrayList<IResource>();

		for (int i = 0; i < messageMarkers.length; i++) {
			IMarker marker = messageMarkers[i];

			if (marker.getAttribute(ManageResources.IS_URL, false)) {
				markedResources.add(marker.getResource());
			}
		}
	}

	private void performBuild() {
		// Reset the command output window
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				ConsoleOutput.reset();
			}
		});

		// Create a processBuilder. This will run the SMT in another thread.
		SMTCommand command = new SMTCommand(getProject());
		List<String> generatedCommand = command.generateCommand();

		if (generatedCommand == null) {
			return;
		}

		SMTProcess process = new SMTProcess();

		// Run the process
		int exitCode = process.run(generatedCommand);
		
		if (exitCode == 0) {
			IScopeContext projectScope = new ProjectScope(getProject());
			IEclipsePreferences projectNode = projectScope
					.getNode(Activator.PLUGIN_ID);
			PersistentDataStore projectStore = new PersistentDataStore(
					projectNode);

			projectFile = getProject()
					.getFile(projectStore.getOutputFilename());

			// Add the SVG file to the project
			addFileToProject();

			// Inform the user that the model has been created
			writeToConsoleOutput("Finished");

			// Open the file in the editor
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					try {
						IWorkbenchPage page = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage();

						IEditorPart part = page.findEditor(new FileEditorInput(
								projectFile));
						if (part != null && part instanceof SVGEditor) {
							page.openEditor(new FileEditorInput(projectFile),
									SVGEditor.ID);
							((SVGEditor) part).refresh();
						} else {
							page.openEditor(new FileEditorInput(projectFile),
									SVGEditor.ID);
						}
					} catch (PartInitException e) {
						Logger.log(e.getMessage(), e);
					}
				}
			});
		} else {
			writeToConsoleOutput("Exit Code: " + exitCode);

			// Add the output to the Eclipse log
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					Exception e = new Exception(ConsoleOutput.getText());
					Logger
							.log(
									"Failed to successfully generate System Model diagram",
									e);
				}
			});

			writeToConsoleOutput("ERROR: Failed to successfully generate System Model diagram");
		}
	}
	
	private boolean checkSMGinstalled() {
		
		final ResourceBundle resourceBundle = ResourceBundle.getBundle(
				"location", Locale.getDefault(), this.getClass()
						.getClassLoader());

		String smgFolder = resourceBundle.getString("location");

		File smgCommand = new File(smgFolder + File.separator + "SysModGen.pl");

		return smgCommand.exists();
	}

	private void writeToConsoleOutput(final String string) {
		// Writes a string to the console output view

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				ConsoleOutput.addText(string);
			}
		});
	}
	
}
