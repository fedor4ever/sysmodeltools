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

import static org.junit.Assert.fail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModelLabelsWidgetTest {
	Display display;
	Shell shell;
	ModelLabelsWidget modelLabelsWidget;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public final void setUp() {
		display = new Display();
		shell = new Shell(display);
		
		modelLabelsWidget = new ModelLabelsWidget(shell, SWT.NONE);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		display.dispose();
	}


	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.ModelLabelsWidget#setDistributionTexts(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetDistributionText() {
		String[] text = {"test"};
		
		modelLabelsWidget.setDistributionTexts(text);
		String[] result = modelLabelsWidget.getDistributionTexts();
		
		if (result.length != 1 || (!result[0].equals("test"))) {
			fail("Distribution Text set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.ModelLabelsWidget#setModelVersion(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetModelVersion() {
		modelLabelsWidget.setModelVersion("test");
		String result = modelLabelsWidget.getModelVersion();
		
		if (!result.equals("test")) {
			fail("Model Version set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.ModelLabelsWidget#setSystemVersion(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetSystemVersion() {
		modelLabelsWidget.setSystemVersion("test");
		String result = modelLabelsWidget.getSystemVersion();
		
		if (!result.equals("test")) {
			fail("System Version set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.ModelLabelsWidget#setCopyrightText(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetCopyrightText() {
		modelLabelsWidget.setCopyrightText("test");
		String result = modelLabelsWidget.getCopyrightText();
		
		if (!result.equals("test")) {
			fail("Copyright Text set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.ModelLabelsWidget#setModelVersionTexts(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetModelVersionText() {
		String[] text = {"test"};
		modelLabelsWidget.setModelVersionTexts(text);
		String[] result = modelLabelsWidget.getModelVersionTexts();
		
		if (result.length != 1 || (! result[0].equals("test"))) {
			fail("Model Version Text set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.ModelLabelsWidget#setModelName(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetModelName() {
		modelLabelsWidget.setModelName("test");
		String result = modelLabelsWidget.getModelName();
		
		if (!result.equals("test")) {
			fail("Model Name set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.ModelLabelsWidget#setSystemName(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetSystemName() {
		modelLabelsWidget.setSystemName("test");
		String result = modelLabelsWidget.getSystemName();
		
		if (!result.equals("test")) {
			fail("System Name set\\get failed");
		}
	}

}
