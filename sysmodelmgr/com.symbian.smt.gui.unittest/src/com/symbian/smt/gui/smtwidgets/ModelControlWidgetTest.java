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

package com.symbian.smt.gui.smtwidgets;

import static org.junit.Assert.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.symbian.smt.gui.smtwidgets.ModelControlWidget;

public class ModelControlWidgetTest {
	Display display;
	Shell shell;
	ModelControlWidget modelControlWidget;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		display = new Display();
		shell = new Shell(display);
		
		modelControlWidget = new ModelControlWidget(shell, SWT.NONE);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		display.dispose();
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.ModelControlWidgetTest#getHighlightCoreOS()}.
	 */
	@Test
	public final void testSetAndGetHighlightCoreOS() {
		modelControlWidget.setHighlightCoreOS(true);
		assertTrue("Highlight Core OS set\\get failed", modelControlWidget.getHighlightCoreOS());

	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.ModelControlWidgetTest#getLevelOfDetail()}.
	 */
	@Test
	public final void testSetAndGetLevelOfDetail() {
		modelControlWidget.setLevelOfDetail("component");
		String result = modelControlWidget.getLevelOfDetail();
		
		if (!result.equals("component")) {
			fail("Level of Detail set\\get failed");
		}
	}
}
