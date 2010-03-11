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

package com.symbian.smt.gui.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class NewProjectCreationPageCaseInsensitive extends
		WizardNewProjectCreationPage {

	public NewProjectCreationPageCaseInsensitive(String pageName) {
		super(pageName);
	}

	@Override
	public boolean isPageComplete() {
		String projectName = getProjectName().trim();

		if (projectName.length() > 0) {
			for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
					.getProjects()) {
				if (project.getName().equalsIgnoreCase(projectName)) {
					setErrorMessage("A project with that name already exists in the workspace.");
					return false;
				}
			}
		}

		if (getLocationPath().isUNC()) {
			setErrorMessage("UNC paths are not compatible with the System Model Manager");
			return false;
		}

		return super.isPageComplete();
	}
}