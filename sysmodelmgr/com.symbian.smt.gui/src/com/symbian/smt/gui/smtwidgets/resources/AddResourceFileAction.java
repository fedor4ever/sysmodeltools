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

package com.symbian.smt.gui.smtwidgets.resources;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.symbian.smt.gui.ResourcesEnums;
import com.symbian.smt.gui.smtwidgets.AbstractMultipleEntriesWidgetAction;
import com.symbian.smt.gui.smtwidgets.XmlFileSelectionDialog;

/**
 * This is the action that adds a new command line option to the list of
 * assigned options.
 * 
 * @author barbararosi-schwartz
 * 
 */
class AddResourceFileAction extends AbstractMultipleEntriesWidgetAction {

	private HashMap<ResourcesEnums, List<CheckableResourceFilename>> resourceFilesMap;

	/**
	 * The option that has been entered by the user or null if the user
	 * cancelled the operation.
	 */
	private String newFileLocation = null;

	AddResourceFileAction(
			Button button,
			ISelectionProvider filesViewer,
			HashMap<ResourcesEnums, List<CheckableResourceFilename>> resourceFilesMap,
			ListViewer resourceTypesViewer) {
		super(resourceTypesViewer, "Add...", button);

		this.resourceFilesMap = resourceFilesMap;

		setEnabled(false);
	}

	/**
	 * Returns the option that was entered by the user.
	 * 
	 * @return the option that was entered by the user (or null if the user
	 *         cancelled the operation)
	 */
	String getNewFileLocation() {
		return newFileLocation;
	}

	/**
	 * Creates and displays an InputDialogWithWarning that collects the new
	 * option entered by the user. The dialog is equipped with a
	 * DialogInputValidator object that automatically performs validation on the
	 * user's input.
	 * <p>
	 * When the dialog is dismissed, the action changes the model to reflect the
	 * new addition.
	 * </p>
	 */
	@Override
	public void run() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		String dialogTitle = "New Resource File";
		String dialogMessage = "Enter the path or URL to the resource file";
		String initialPath = "";
		String[] filterNames = { "*.xml" };
		ResourcesEnums selectedResourceType = ResourcesWidgetHelper
				.getSelectedResourceType((ListViewer) selectionProvider);
		List<CheckableResourceFilename> checkableFilenames = ResourcesWidgetHelper
				.getCheckableResourceFilenames(selectedResourceType,
						resourceFilesMap);

		ResourceFileSelectionValidator validator = new ResourceFileSelectionValidator(
				selectedResourceType, checkableFilenames);
		XmlFileSelectionDialog dialog = new XmlFileSelectionDialog(shell,
				dialogTitle, dialogMessage, initialPath, filterNames, validator);

		dialog.open();

		if (dialog.getReturnCode() == Dialog.CANCEL) {
			newFileLocation = null;
			return;
		}

		newFileLocation = dialog.getValue();

		// Adding the newly added file to the resource files
		// map.
		// Automatic checking of the related checkbox and
		// addition
		// to the selected resource files map is handled by the
		// <code>handleMultipleCheckRules()</code> method in the ResourcesWidget
		// class,
		// which is also used for user checks/unchecks.
		if (newFileLocation.length() != 0) {
			checkableFilenames.add(new CheckableResourceFilename(
					newFileLocation));
		}
	}

	/**
	 * This action is enabled if a resource type has been selected by the user.
	 */
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		ResourcesEnums selectedResourceType = ResourcesWidgetHelper
				.getSelectedResourceType((ListViewer) selectionProvider);

		if (selectedResourceType == null) {
			setEnabled(false);
		} else {
			setEnabled(true);
		}
	}

}
