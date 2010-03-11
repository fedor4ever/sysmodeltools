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

package com.symbian.smt.gui;

import java.io.File;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.symbian.smt.gui.editors.svgeditor.SVGEditor;

public class ChangeManager {
	static HashMap<String, Boolean> dirtyProjects = new HashMap<String, Boolean>();

	private String checkModelNameExtension(final IProject project,
			final IResource addedFile, String newOutputName) {
		if (!newOutputName.endsWith(".svg")) {
			final StringBuilder fullName = new StringBuilder();
			fullName.append(newOutputName);
			fullName.append(".svg");

			newOutputName = fullName.toString();
			final String outputName = fullName.toString();

			// The file has the extension added and is moved to the new filename
			WorkspaceJob wj = new WorkspaceJob("auto-rename") {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor)
						throws CoreException {
					IPath newLocation = addedFile.getFullPath()
							.addFileExtension("svg");
					addedFile
							.move(newLocation, true, new NullProgressMonitor());

					project.getFile(outputName).setDerived(true);

					return new Status(IStatus.OK, Activator.PLUGIN_ID,
							IStatus.OK, "auto-rename succeeded", null);
				}
			};
			wj.schedule();
		}

		return newOutputName;
	}

	private void checkPropertyChangeMarkers(final IProject project) {
		try {
			IMarker[] mMarkers = project.findMarkers(IMarker.MESSAGE, false, 0);

			for (final IMarker aMarker : mMarkers) {
				if (aMarker.getAttribute(IMarker.MESSAGE) != null) {
					if (aMarker.getAttribute(IMarker.MESSAGE).equals(
							"properties changed")) {
						dirtyProjects.put(project.getName(), true);

						try {
							aMarker.delete();
						} catch (CoreException e) {
							// Do nothing, the markers are a bit unstable, an
							// exception will only occur if the marker has
							// already been deleted.
						}
					}
				}
			}
		} catch (CoreException e) {
			Logger.log(e.getMessage(), e);
		}
	}

	public void closeEditor(final IFile oldProjectFile) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				IEditorPart oldEditor = page.findEditor(new FileEditorInput(
						oldProjectFile));
				page.closeEditor(oldEditor, false);
			}
		});
	}

	private IResourceDelta[] getAddedItems(IResourceDelta delta,
			IProject project) {
		IResourceDelta[] added = null;

		if (delta.getFullPath().equals(Path.ROOT)) {
			IResourceDelta[] children = delta
					.getAffectedChildren(IResourceDelta.CHANGED);
			for (IResourceDelta child : children) {
				added = child.getAffectedChildren(IResourceDelta.ADDED);
				break;
			}
		} else if (project.getFullPath().equals(delta.getFullPath())) {
			added = delta.getAffectedChildren(IResourceDelta.ADDED);
		}

		return added;
	}

	private IResourceDelta[] getDeletedItems(IResourceDelta delta,
			IProject project) {
		IResourceDelta[] deleted = null;

		if (delta.getFullPath().equals(Path.ROOT)) {
			IResourceDelta[] children = delta
					.getAffectedChildren(IResourceDelta.CHANGED);
			for (IResourceDelta child : children) {
				deleted = child.getAffectedChildren(IResourceDelta.REMOVED);
				break;
			}
		} else if (project.getFullPath().equals(delta.getFullPath())) {
			deleted = delta.getAffectedChildren(IResourceDelta.REMOVED);
		}

		return deleted;
	}

	private IPath getDeltaPath(IResourceDelta delta, IProject project) {
		IPath deltaPath = null;

		if (delta.getFullPath().equals(Path.ROOT)) {
			IResourceDelta[] children = delta
					.getAffectedChildren(IResourceDelta.CHANGED);
			for (IResourceDelta child : children) {
				deltaPath = child.getFullPath();
				break;
			}
		} else if (project.getFullPath().equals(delta.getFullPath())) {
			deltaPath = delta.getFullPath();
		}

		return deltaPath;
	}

	public void handleDelta(IResourceDelta delta, final IProject project) {
		checkPropertyChangeMarkers(project);

		if (delta != null) {
			IScopeContext projectScope = new ProjectScope(project);
			PersistentDataStore store = new PersistentDataStore(projectScope
					.getNode(Activator.PLUGIN_ID));

			// Check to see if the System Model diagram name has changed
			checkPropertyChangeMarkers(project); // True means that name has
													// changed

			// Get the added and deleted items, we use these to work out what
			// has happened
			IResourceDelta[] deletedItems = getDeletedItems(delta, project);
			IResourceDelta[] addedItems = getAddedItems(delta, project);

			// If the diagram has been deleted but a new one hasn't been added
			// then close the editor
			if (addedItems != null & deletedItems != null
					&& deletedItems.length == 1 && addedItems.length == 0) {
				final String deletedfile = deletedItems[0].getFullPath()
						.toString();
				String outputName = store.getOutputFilename();
				String resourceName = getDeltaPath(delta, project).append(
						outputName).toString();

				// Check that it was the system model diagram what was deleted,
				// we are not interested in any other files
				if (deletedfile.equals(resourceName)) {
					IFile oldProjectFile = project.getFile(store
							.getOutputFilename());
					closeEditor(oldProjectFile);
					dirtyProjects.put(project.getName(), true);
				}
			}

			// Otherwise if the diagram has been deleted and a new one added
			// then the diagram has been renamed
			else if (addedItems != null & deletedItems != null
					&& deletedItems.length == 1 && addedItems.length == 1) {
				String outputName = store.getOutputFilename();

				if (outputName == null) {
					return;
				}

				final String deletedfile = deletedItems[0].getFullPath()
						.toString();
				final String addedfile = addedItems[0].getFullPath().toString();

				String resourceName = getDeltaPath(delta, project).append(
						outputName).toString();

				if (addedfile.equals(resourceName)
						|| deletedfile.equals(resourceName)) {
					final String oldOutputName = new File(deletedfile)
							.getName();

					// Will add the .svg extension if required and also rename
					// the file
					final String newOutputName = checkModelNameExtension(
							project, addedItems[0].getResource(), new File(
									addedfile).getName());

					// Update the project properties with the new model name
					updatePropertiesWithModelName(store, resourceName,
							deletedfile, newOutputName);

					final IFile newProjectFile = project.getFile(newOutputName);

					Boolean isDirty = false;

					if (dirtyProjects.containsKey(project.getName())) {
						isDirty = dirtyProjects.get(project.getName());
					}

					if (delta.getAffectedChildren(IResourceDelta.CHANGED).length <= 1
							&& !isDirty) {
						Display.getDefault().asyncExec(new Runnable() {

							public void run() {
								if (newProjectFile.exists()
										&& !newProjectFile.isPhantom()) {
									try {
										IWorkbenchPage page = PlatformUI
												.getWorkbench()
												.getActiveWorkbenchWindow()
												.getActivePage();
										FileEditorInput editorInput = new FileEditorInput(
												newProjectFile);

										IEditorPart oldEditor = page
												.findEditor(new FileEditorInput(
														project
																.getFile(oldOutputName)));
										page.closeEditor(oldEditor, false);

										page.openEditor(editorInput,
												SVGEditor.ID);

									} catch (PartInitException e) {
										Logger.log(e.getMessage(), e);
									}
								}
							}
						});
					}
				}
			} else { // Nothing has been deleted and nothing has been added - A
						// file has been amended.
				IResourceDelta[] children = delta
						.getAffectedChildren(IResourceDelta.CHANGED);

				for (IResourceDelta child : children) {
					if (child.getResource() instanceof IFile) {
						if (child.getResource().getFileExtension()
								.equalsIgnoreCase("xml")) {
							dirtyProjects.put(project.getName(), true);
						}
					} else if (child.getResource() instanceof IProject) {
						if (child.getFlags() == IResourceDelta.OPEN) {
							dirtyProjects.put(child.getResource().getName(),
									true);
						}
					} else if (child.getResource() instanceof IFolder) {
						IResourceDelta[] moreChildren = child
								.getAffectedChildren(IResourceDelta.CHANGED);
						for (IResourceDelta littlerChild : moreChildren) {
							if (littlerChild.getResource().getFileExtension()
									.equalsIgnoreCase("xml")) {
								dirtyProjects.put(project.getName(), true);
							}
						}
					}
				}
			}
		}
	}

	public void remove(IProject project) {
		if (dirtyProjects.containsKey(project.getName())) {
			dirtyProjects.remove(project.getName());
		}
	}
	
	public boolean needsBuilding(IProject project) {
		if (dirtyProjects.containsKey(project.getName())) {
			return dirtyProjects.get(project.getName());
		}

		IScopeContext projectScope = new ProjectScope(project);
		PersistentDataStore store = new PersistentDataStore(projectScope
				.getNode(Activator.PLUGIN_ID));

		if (!project.getFile(store.getOutputFilename()).exists()) {
			return true;
		}

		return false;
	}

	public void reset(IProject project) {
		dirtyProjects.put(project.getName(), false);
	}

	private void updatePropertiesWithModelName(final PersistentDataStore store,
			final String resourceName, final String deletedfile,
			final String outName) {
		WorkspaceJob wj = new WorkspaceJob("updating properties") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				if (deletedfile.equals(resourceName)) {
					store.setOutputFilename(outName);
				}
				return new Status(IStatus.OK, Activator.PLUGIN_ID, IStatus.OK,
						"updating properties succeeded", null);
			}
		};
		wj.schedule();
	}
}
