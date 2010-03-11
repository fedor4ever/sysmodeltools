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
// SmmResourceChaneListener.java
// 
//

package com.symbian.smt.gui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.symbian.smt.gui.nature.Nature;

public class SmmResourceChangeListener implements IResourceChangeListener {

	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_BUILD) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceDescription description = workspace.getDescription();

			IWorkspaceRoot workspaceRoot = workspace.getRoot();
			IResourceDelta delta = event.getDelta();

			if (delta.getFullPath().equals(Path.ROOT)) {
				IResourceDelta[] children = delta
						.getAffectedChildren(IResourceDelta.CHANGED);

				if (children.length == 0) {
					return;
				}

				for (IResourceDelta child : children) {
					if (child.getKind() == IResourceDelta.CHANGED) {
						for (IProject aProject : workspaceRoot.getProjects()) {
							try {
								if (aProject.isOpen()
										&& aProject.hasNature(Nature.ID)
										&& aProject.getFullPath().equals(
												child.getFullPath())) {

									if (child.getFlags() == IResourceDelta.OPEN) {
										final IProject theProject = aProject;

										try {
											theProject.refreshLocal(
													IResource.DEPTH_INFINITE,
													null);

											IScopeContext projectScope = new ProjectScope(
													theProject);
											PersistentDataStore store = new PersistentDataStore(
													projectScope
															.getNode(Activator.PLUGIN_ID));

											Boolean needsShortcutsRefreshing = true;

											for (IResource resource : theProject
													.members()) {
												if (resource.isLinked()) {
													needsShortcutsRefreshing = false;
													break;
												}
											}

											if (needsShortcutsRefreshing == true) {
												// We also need to get the
												// shortcut to appear, as it
												// does not always happen
												// automatically
												ManageResources
														.updateSystemDefinitionFiles(
																theProject,
																store
																		.getSystemDefinitionFiles(),
																true);
											}
										} catch (CoreException e) {
											Logger.log(e.getMessage(), e);
										}
									}

									if (!description.isAutoBuilding()) {
										final IProject theProject = aProject;
										Job j = new Job("Building workspace") {
											@Override
											protected IStatus run(
													IProgressMonitor monitor) {
												try {
													if (event.getBuildKind() == IncrementalProjectBuilder.AUTO_BUILD) { // 9
														theProject
																.build(
																		IncrementalProjectBuilder.INCREMENTAL_BUILD,
																		monitor);
													}
												} catch (CoreException e) {
													Logger.log(e.getMessage(),
															e);
												}
												return new Status(
														IStatus.OK,
														Activator.PLUGIN_ID,
														IStatus.OK,
														"updating properties succeeded",
														null);
											}
										};
										j.schedule();
									}
								}
							} catch (CoreException e) {
								Logger.log(e.getMessage(), e);
							}
						}
					}
				}
			}
		} else if (event.getType() == IResourceChangeEvent.PRE_DELETE) {
			// If it is a delete event then the resource is an IProject
			IProject project = (IProject) event.getResource();

			IScopeContext projectScope = new ProjectScope(project);
			PersistentDataStore store = new PersistentDataStore(projectScope
					.getNode(Activator.PLUGIN_ID));

			final IFile oldProjectFile = project.getFile(store
					.getOutputFilename());

			ChangeManager manager = new ChangeManager();
			manager.remove(project);
			
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					IEditorPart oldEditor = page
							.findEditor(new FileEditorInput(oldProjectFile));
					page.closeEditor(oldEditor, false);
				}
			});
		} else {
			IResourceDelta delta = event.getDelta();

			if (event.getType() == IResourceChangeEvent.POST_CHANGE
					&& delta.getKind() == IResourceDelta.CHANGED) {
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot workspaceRoot = workspace.getRoot();
				IProject project = null;

				IPath fullPath = delta.getFullPath();

				if (delta.getFullPath().equals(Path.ROOT)) {
					IResourceDelta[] children = delta
							.getAffectedChildren(IResourceDelta.CHANGED);

					if (children.length == 0) {
						return;
					}

					for (IResourceDelta child : children) {
						if (child.getKind() == IResourceDelta.CHANGED) {
							fullPath = child.getFullPath();
						}
					}
				}
				for (IProject aProject : workspaceRoot.getProjects()) {
					try {
						if (aProject.isOpen() && aProject.hasNature(Nature.ID)
								&& aProject.getFullPath().equals(fullPath)) {
							project = aProject;
							break;
						}
					} catch (CoreException e) {
						Logger.log(e.getMessage(), e);
					}
				}

				if (project == null) {
					return;
				}

				ChangeManager manager = new ChangeManager();
				manager.handleDelta(delta, project);
			}
		}
	}
}
