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

package com.symbian.smt.gui.print;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;

import com.symbian.smt.gui.editors.svgeditor.SVGEditor;

public class PrintAction extends Action {

	private IEditorPart editor;

	public PrintAction(String title) {
		super(title);
	}

	public void run() {
		if (editor instanceof SVGEditor) {
			((SVGEditor) editor).print();
		}
	}

	public void setActiveEditor(IEditorPart editor) {
		this.editor = editor;
	}
}
