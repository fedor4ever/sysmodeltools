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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @author barbararosi-schwartz
 * 
 */
public class OpenFileAction extends Action {

	IWorkbenchPage page;
	ISelectionProvider selectionProvider;

	public OpenFileAction(IWorkbenchPage page,
			ISelectionProvider selectionProvider) {
		this.page = page;
		this.selectionProvider = selectionProvider;

		setText("Open");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		ISelection selection = selectionProvider.getSelection();

		if (!selection.isEmpty()) {
			IStructuredSelection ssel = (IStructuredSelection) selection;

			if (ssel.size() > 1) {
				return false;
			}

			IResource resource = (IResource) ssel.getFirstElement();

			if (resource instanceof IFile) {
				try {
					return !isResourceMarkedAsUrl((IFile) resource);
				} catch (CoreException e) {
					return false;
				}
			}
		}

		return false;
	}

	private boolean isResourceMarkedAsUrl(IFile file) throws CoreException {
		IMarker[] messageMarkers = file.findMarkers(IMarker.TASK, false,
				IResource.DEPTH_ZERO);

		for (int i = 0; i < messageMarkers.length; i++) {
			IMarker marker = messageMarkers[i];

			if (marker.getAttribute(ManageResources.IS_URL, false)) {
				return true;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		IStructuredSelection ssel = (IStructuredSelection) selectionProvider
				.getSelection();
		IFile file = (IFile) ssel.getFirstElement();

		try {
			page.openEditor(new FileEditorInput(file),
					"com.symbian.smt.gui.editors.xmleditor.XMLEditor", true);
		} catch (PartInitException e) {
			Logger.log(e.getMessage(), e);
		}
	}

}
