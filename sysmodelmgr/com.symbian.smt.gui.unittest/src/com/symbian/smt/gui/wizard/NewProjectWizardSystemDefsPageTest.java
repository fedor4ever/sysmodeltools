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

import static org.junit.Assert.*;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.symbian.smt.gui.wizard.NewProjectWizardSystemDefsPage;

public class NewProjectWizardSystemDefsPageTest {
	IStructuredSelection selection;
	NewProjectWizardSystemDefsPage newProjectWizardSystemDefsPage;
	Display display;
	Shell shell;
	
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		display = new Display();
//		shell = new Shell(display);
//		
//		newProjectWizardSystemDefsPage = new NewProjectWizardSystemDefsPage(selection);
//		newProjectWizardSystemDefsPage.createControl(shell);
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//		display.dispose();
//	}
//
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardSystemDefsPage#validModelDefined(java.lang.Boolean)}.
//	 */
//	@Test
//	public final void testValidModelDefined() {
//		newProjectWizardSystemDefsPage.validModelDefined(true);
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardSystemDefsPage#SetSystemDefinitions(java.lang.String[])}.
//	 */
//	@Test
//	public final void testSetAndGetSystemDefinitions() {
//		String[] list = new String[] {"1", "2"};
//		
//		newProjectWizardSystemDefsPage.setSystemDefinitions(list);
//		
//		String[] results = newProjectWizardSystemDefsPage.getSystemDefinitions();
//
//		if (results.length != 2) {
//			fail("The list returned should contain 2 elements");
//		}
//	}
}
