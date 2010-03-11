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
// RemoveResourceFileAction
//



package com.symbian.smt.gui.smtwidgets.resources;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Button;

import com.symbian.smt.gui.ResourcesEnums;
import com.symbian.smt.gui.smtwidgets.AbstractMultipleEntriesWidgetAction;

/**
 * This is the action that removes a command line option from the list of
 * assigned options.
 * 
 * @author barbararosi-schwartz
 * 
 */
class RemoveResourceFileAction extends AbstractMultipleEntriesWidgetAction {

	private ListViewer resourceTypesViewer;
	private HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap;

	RemoveResourceFileAction(
			Button button,
			ISelectionProvider filesViewer,
			HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap,
			ListViewer resourceTypesViewer) {
		super(filesViewer, "Remove", button);

		this.resourceFilesMap = resourceFilesMap;
		this.resourceTypesViewer = resourceTypesViewer;

		setEnabled(false);
	}

	/**
	 * Removes the selected filename from the model.
	 */
	@Override
	public void run() {
		StructuredSelection ssel = (StructuredSelection) getSelection();
		java.util.List<CheckableResourceFilename> checkableFilenames = ResourcesWidgetHelper
				.getCheckableResourceFilenames(ResourcesWidgetHelper
						.getSelectedResourceType(resourceTypesViewer),
						resourceFilesMap);

		@SuppressWarnings("unchecked")
		Iterator<CheckableResourceFilename> iter = ssel.iterator();

		while (iter.hasNext()) {
			CheckableResourceFilename to_be_removed = (CheckableResourceFilename) iter
					.next();
			checkableFilenames.remove(to_be_removed);
		}
	}

	/**
	 * Enabled if we have at least one selection in the list.
	 */
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			setEnabled(false);
			return;
		}

		setEnabled(true);
	}

}
