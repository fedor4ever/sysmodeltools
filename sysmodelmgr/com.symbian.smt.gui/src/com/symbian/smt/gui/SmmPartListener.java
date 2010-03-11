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

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.symbian.smt.gui.nature.Nature;

final class SmmPartListener implements IPartListener {

	private final class PropertyChangeListener implements IPropertyListener {

		public void propertyChanged(Object source, int propId) {
			if (propId == IEditorPart.PROP_DIRTY) {
				if (source instanceof IEditorPart) {
					IEditorPart editorPart = (IEditorPart) source;
					IEditorInput input = editorPart.getEditorInput();

					if (input instanceof IFileEditorInput) {
						boolean isEditorDirty = editorPart.isDirty();
						String dirty = String.valueOf(isEditorDirty);
						IFile file = ((IFileEditorInput) input).getFile();

						// If user has saved and if it is a resource file or a
						// sys def file,
						// need to validate the file and mark it in error
						// if it is invalid.
						if (!isEditorDirty) {
							// save but do not build if validation fails.
							if (ManageResources.isResourceFile(file)) {
								// TODO:BRS:Remove the if test below when
								// Shapes.xsd is available.
								if (!file.getParent().getName()
										.equals("Shapes")) {
									FileValidationHelper
											.validateResourceFile(file);
								}
							} else if (ManageResources
									.isSystemDefinitionFile(file)) {
								FileValidationHelper.validateSysDefFile(file);
							}
						}

						if (!items.containsKey(file.getProject().getName())) {
							String entry[] = { file.getName(), dirty };
							ArrayList<String[]> entries = new ArrayList<String[]>();
							entries.add(entry);
							items.put(file.getProject().getName(), entries);
						} else {
							ArrayList<String[]> projectFiles = items.get(file
									.getProject().getName());

							Boolean alreadyExists = false;

							for (String[] anEntry : projectFiles) {
								if (anEntry[0].equalsIgnoreCase(file.getName())) {
									anEntry[1] = dirty;
									alreadyExists = true;
								}
							}

							if (!alreadyExists) {
								String entry[] = { file.getName(), dirty };
								items.get(file.getProject().getName()).add(
										entry);
							}
						}
					}
				}

				// Tell it to do any decorating
				PlatformUI.getWorkbench().getDecoratorManager().update(
						"com.symbian.smt.gui.outofsyncdecorator");
			}
		}
	}

	private PropertyChangeListener listener;

	private static HashMap<String, ArrayList<String[]>> items = new HashMap<String, ArrayList<String[]>>();

	public SmmPartListener() {
		listener = new PropertyChangeListener();
	}

	private void addListener(IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			try {
				IEditorInput input = ((IEditorPart) part).getEditorInput();
				if (input instanceof IFileEditorInput) {
					IFile file = ((IFileEditorInput) input).getFile();

					if (file.getProject().isOpen()) {
						boolean ourProject = file.getProject().hasNature(
								Nature.ID);
						if (ourProject && !file.isDerived()) {
							part.addPropertyListener(listener);
						}
					}
				}
			} catch (CoreException e) {
				Logger.log(e.getMessage(), e);
			}
		}
	}

	public boolean isInSync(String project) {
		// Get the files for this project
		ArrayList<String[]> projectFiles = items.get(project);

		// Iterate over the resources
		if (projectFiles != null) {
			for (String[] anEntry : projectFiles) {
				if (Boolean.valueOf(anEntry[1])) {
					// A file is out of sync, so the project it dirty
					return false;
				}
			}
		}

		// No sync information exists for the project or all are in sync -
		// return true
		return true;
	}

	public void partActivated(IWorkbenchPart part) {
		addListener(part);

		// if (part instanceof IViewPart) {
		// String partId = ((IViewPart) part).getViewSite().getId();
		//
		// if (partId.equals("org.eclipse.ui.navigator.ProjectExplorer")) {
		// FileValidationHelper.showProblemsViewIfNeeded();
		// }
		// }
	}

	public void partBroughtToTop(IWorkbenchPart part) {
		addListener(part);
	}

	public void partClosed(IWorkbenchPart part) {
		part.removePropertyListener(listener);

		if (part instanceof IEditorPart) {
			IEditorInput input = ((IEditorPart) part).getEditorInput();
			if (input instanceof IFileEditorInput) {
				IFile file = ((IFileEditorInput) input).getFile();

				if (items.containsKey(file.getProject().getName())) {
					ArrayList<String[]> projectFiles = items.get(file
							.getProject().getName());

					for (String[] anEntry : projectFiles) {
						if (anEntry[0].equalsIgnoreCase(file.getName())) {
							projectFiles.remove(anEntry);
							break;
						}
					}
				}
			}

			PlatformUI.getWorkbench().getDecoratorManager().update(
					"com.symbian.smt.gui.outofsyncdecorator");
		}
	}

	public void partDeactivated(IWorkbenchPart part) {
	}

	public void partOpened(IWorkbenchPart part) {
		addListener(part);
	}
}
