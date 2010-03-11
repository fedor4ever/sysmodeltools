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
// MoveResourceFileDownAction
//



package com.symbian.smt.gui.smtwidgets.resources;

import java.util.HashMap;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Button;

import com.symbian.smt.gui.ResourcesEnums;
import com.symbian.smt.gui.smtwidgets.AbstractMultipleEntriesWidgetAction;

/**
 * This is the action that moves a command line option down by one position in
 * the list of assigned options.
 * 
 * @author barbararosi-schwartz
 * 
 */
class MoveResourceFileDownAction extends AbstractMultipleEntriesWidgetAction {

	private CheckboxTableViewer resourceFilesViewer;
	private ListViewer resourceTypesViewer;
	private HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap;

	/**
	 * The option that has been moved by the user
	 */
	private CheckableResourceFilename movedCheckableFilename = null;

	MoveResourceFileDownAction(
			Button button,
			ISelectionProvider filesViewer,
			HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap,
			ListViewer resourceTypesViewer) {
		super(filesViewer, "Move Down", button);

		this.resourceFilesViewer = (CheckboxTableViewer) filesViewer;
		this.resourceFilesMap = resourceFilesMap;
		this.resourceTypesViewer = resourceTypesViewer;

		setEnabled(false);
	}

	/**
	 * Returns the CheckableResourceFilename object that was moved by the user.
	 * 
	 * @return the CheckableResourceFilename object that was moved by the user
	 */
	CheckableResourceFilename getMovedCheckableFilename() {
		return movedCheckableFilename;
	}

	/**
	 * Moves the selected CheckableResourceFilename object down by one position
	 * in the model.
	 */
	@Override
	public void run() {
		movedCheckableFilename = (CheckableResourceFilename) ((StructuredSelection) getSelection())
				.getFirstElement();

		// Rearrange the order of the files in <code>resourceFilesMap</code>
		java.util.List<CheckableResourceFilename> checkableFilenames = ResourcesWidgetHelper
				.getCheckableResourceFilenames(ResourcesWidgetHelper
						.getSelectedResourceType(resourceTypesViewer),
						resourceFilesMap);
		int oldIndex = checkableFilenames.indexOf(movedCheckableFilename);
		checkableFilenames.remove(oldIndex);
		int newIndex = oldIndex + 1;
		checkableFilenames.add(newIndex, movedCheckableFilename);
	}

	/**
	 * Enabled if the list has exactly one selection and if the selection is not
	 * the last element in the list.
	 */
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		if (selection.size() != 1) {
			setEnabled(false);
			return;
		}

		boolean enabled = true;
		CheckableResourceFilename selectedElement = (CheckableResourceFilename) selection
				.getFirstElement();
		CheckableResourceFilename lastElement = (CheckableResourceFilename) resourceFilesViewer
				.getElementAt(resourceFilesViewer.getTable().getItemCount() - 1);

		if (lastElement != null && selectedElement.equals(lastElement)) {
			enabled = false;
		}

		setEnabled(enabled);
	}

}
