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
package com.symbian.smt.gui.editors.svgeditor;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.LabelRetargetAction;
import org.eclipse.ui.part.EditorActionBarContributor;

import com.symbian.smt.gui.print.PrintAction;

public class SVGEditorContributor extends EditorActionBarContributor {

	private LabelRetargetAction printRetargetAction;

	private PrintAction printAction;

	public SVGEditorContributor() {
		printAction = new PrintAction("Print SVG");
		printRetargetAction = new LabelRetargetAction(ActionFactory.PRINT
				.getId(), "Print SVG");
	}

	public void dispose() {
		// Remove retarget actions as page listeners
		getPage().removePartListener(printRetargetAction);
	}

	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		bars.setGlobalActionHandler(ActionFactory.PRINT.getId(), printAction);

		// Hook retarget actions as page listeners
		page.addPartListener(printRetargetAction);
		IWorkbenchPart activePart = page.getActivePart();
		if (activePart != null) {
			printRetargetAction.partActivated(activePart);

		}
	}

	public void setActiveEditor(IEditorPart editor) {
		printAction.setActiveEditor(editor);
	}
}
