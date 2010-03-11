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

package com.symbian.smt.gui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public static final String ID = "com.symbian.smt.gui.perspective"; //$NON-NLS-1$

	// Add fast views to the perspective
	private void addFastViews(IPageLayout layout) {
	}

	// Add perspective shortcuts to the perspective
	private void addPerspectiveShortcuts(IPageLayout layout) {
	}

	// Add view shortcuts to the perspective
	private void addViewShortcuts(IPageLayout layout) {
	}

	// Creates the initial layout for a page
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		addFastViews(layout);
		addViewShortcuts(layout);
		addPerspectiveShortcuts(layout);

		IFolderLayout topLeft = layout.createFolder("topLeft",
				IPageLayout.LEFT, 0.25f, editorArea); // $NON-NLS$
		topLeft.addView("org.eclipse.ui.navigator.ProjectExplorer");
		layout.addView(com.symbian.smt.gui.views.ConsoleOutput.ID,
				IPageLayout.BOTTOM, 0.80f, editorArea);
	}
}
