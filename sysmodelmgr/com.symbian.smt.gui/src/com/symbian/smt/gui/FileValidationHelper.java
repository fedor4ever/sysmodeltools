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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * @author barbararosi-schwartz
 * 
 */
public class FileValidationHelper {

	private static void createErrorMarker(IResource resource,
			String errorMessage) {
		IMarker marker;
		try {
			marker = resource.createMarker(IMarker.PROBLEM);

			if (marker.exists()) {
				marker.setAttribute(IMarker.MESSAGE, errorMessage);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			}
		} catch (CoreException e) {
			Logger.log("Cannot create error marker for resource ["
					+ resource.getName() + "].", e);
		}
	}

	private static void deleteErrorMarkers(IResource resource) {
		try {
			resource
					.deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			Logger.log("Cannot delete error marker for resource ["
					+ resource.getName() + "].", e);
		}
	}

	private static void showProblemsView() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();

		if (window == null) {
			return;
		}

		IWorkbenchPage page = window.getActivePage();

		if (page == null) {
			return;
		}

		try {
			page.showView("org.eclipse.ui.views.ProblemView");
		} catch (PartInitException e) {
			String message = "Could not open the Problems View. Reason: "
					+ e.getMessage();
			String title = "Error";

			MessageDialog.openError(window.getShell(), title, message);
		}
	}

	private static void communicateCoreException(CoreException e) {
		Logger.log(e.getMessage(), e);
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		if (window == null) {
			return;
		}

		String message = "Problem encountered in locating errors in the workspace. Reason: "
				+ e.getMessage();
		String title = "Error";
		
		MessageDialog.openError(window.getShell(), title, message);
	}
	
	public static void validateResourceFile(IFile file) {
		ResourcesEnums resourceType = ManageResources.getResourceType(file);

		if (resourceType != null) {
			ResourceFileValidator validator = new ResourceFileValidator(
					resourceType);
			String errorMessage;
			
			try {
				String url = ManageResources.getResourceUrl(file);
			
				if (url == null) {
					errorMessage = validator.validateXml(file.getLocation()
							.toOSString());
				}
				else {
					errorMessage = validator.validateXml(url);
				}
			
				deleteErrorMarkers(file);
	
				if (errorMessage != null) {
					createErrorMarker(file, errorMessage);
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						public void run() {
							showProblemsView();
						}
					});
					
				}
			} catch (CoreException e) {
				communicateCoreException(e);
			}
		}
	}

	public static void validateSysDefFile(IFile file) {
		XmlFileValidator validator = new XmlFileValidator();
		String errorMessage;
		
		try {
			String url = ManageResources.getResourceUrl(file);
			
			if (url == null) {
				errorMessage = validator.validateXml(file.getLocation()
					.toOSString());
			}
			else {
				errorMessage = validator.validateXml(url);
			}
			
			deleteErrorMarkers(file);
			
			if (errorMessage != null) {
				createErrorMarker(file, errorMessage);
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						showProblemsView();
					}
				});
			}
		} catch (CoreException e) {
			communicateCoreException(e);
		}
	}
}
