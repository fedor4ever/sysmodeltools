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

package com.symbian.smt.gui.exportwizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

public class ExportSystemModelDiagram extends Wizard implements IExportWizard {

	private ExportSelectionPage page;

	public void addPages(IStructuredSelection selection) {
		page = new ExportSelectionPage(selection);
		addPage(page);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("System Model Manager Export Wizard");
		addPages(selection);
	}

	@Override
	public boolean performFinish() {
		return page.copyFile();
	}
}