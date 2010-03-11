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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;

public class NodeListener {

	public NodeListener(final IProject project) {
		IScopeContext projectScope = new ProjectScope(project);

		// if name changed in properties
		projectScope.getNode(Activator.PLUGIN_ID).addPreferenceChangeListener(
				new IPreferenceChangeListener() {

					public void preferenceChange(PreferenceChangeEvent event) {
						String key = event.getKey();
						try {
							if (key.equals(PersistentSettingsEnums.OUTPUT_NAME
									.toString())
									&& event.getNewValue() != null) {
								final IFile oldfile = project
										.getFile((String) event.getOldValue());
								final IFile newfile = project
										.getFile((String) event.getNewValue());

								project.getWorkspace().run(
										new IWorkspaceRunnable() {
											public void run(
													IProgressMonitor monitor) {
												if (oldfile.exists()) {
													try {
														newfile
																.create(
																		oldfile
																				.getContents(true),
																		false,
																		null);
														newfile
																.setDerived(true);
														oldfile.delete(true,
																null);
													} catch (CoreException e) {
														Logger.log(e
																.getMessage(),
																e);
													}
												}
											}
										}, null);

							} else {
								Boolean alreadyHasMarker = false;
								IMarker[] mMarkers = project.findMarkers(
										IMarker.MESSAGE, false, 0);

								for (IMarker aMarker : mMarkers) {
									if (aMarker.getAttribute(IMarker.MESSAGE) != null) {
										if (aMarker.getAttribute(
												IMarker.MESSAGE).equals(
												"properties changed")) {
											alreadyHasMarker = true;
										}
									}
								}

								if (!alreadyHasMarker) {
									IMarker marker = project
											.createMarker(IMarker.MESSAGE);
									marker.setAttribute(IMarker.MESSAGE,
											"properties changed");
								}
							}
						} catch (CoreException e) {
							Logger.log(e.getMessage(), e);
						}
					}
				});
	}
}
