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

package com.symbian.smt.gui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SmmPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

	public static final String ID = "com.symbian.smt.gui.preferences";

	@Override
	protected Control createContents(Composite parent) {

		return null;
	}

	public void init(IWorkbench workbench) {
		noDefaultAndApplyButton();
		setDescription("Expand the tree to edit specific preferences for System Model Diagram.");
	}
}
