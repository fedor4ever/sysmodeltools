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
import junit.framework.Assert;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import com.symbian.smt.gui.smtwidgets.FilterWidget;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.eclipse.swt.SWT;

public class FilterWidgetTest {
	Display display;
	Shell shell;
	FilterWidget filterWidget;
	
	@Before
	public final void setUp() {
		display = new Display();
		shell = new Shell(display);
		
		filterWidget = new FilterWidget(shell, SWT.NONE);
	}

	@After
	public void tearDown() throws Exception {
		display.dispose();
	}
	
	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.FilterWidget#FilterWidget(org.eclipse.swt.widgets.Composite, int)}.
	 */
	@Test
	public final void testFilterWidget() {
		Assert.assertNotNull(filterWidget);
	}


	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.FilterWidget#setFilterItems(java.lang.String[])}.
	 */
	@Test
	public final void testSetEmpty() {
		String[] list = new String[0];

		filterWidget.setFilterItems(list);
		
		String[] results = filterWidget.getFilterItems();
		
		assertNotNull(results);
		
		if (results.length > 0) {
			fail("Should return an empty list");
		}
	}
	
	@Test
	public final void testFilterItems() {
		String[] list = new String[] {"1", "2"};

		filterWidget.setFilterItems(list);
		
		String[] results = filterWidget.getFilterItems();

		if (results.length != 2) {
			fail("The list returned should contain 2 elements");
		}
	}
	
	@Test
	public final void testSetTwice() {
		
		String[] list = new String[] {"1", "2"};
		
		String[] list2 = new String[] {"1", "2", "3", "4"};
		
		filterWidget.setFilterItems(list);
		
		filterWidget.setFilterItems(list2);
		
		String[] results = filterWidget.getFilterItems();
		
		Assert.assertEquals("Expected size: " + list2.length + ", got " + results.length, list2.length, results.length);
	}
	
/*	@Test
	public final void testSetString() {
		
		String filter = "java,gt,";
		
		filterWidget.setFilterItems(filter);
		
		String[] results = filterWidget.getFilterItems();
		
		Assert.assertEquals("Expected size: 2, got " + results.length, 2, results.length);
		Assert.assertEquals("Expected java as first item", "java", results[0]);
		Assert.assertEquals("Expected gt as first item", "gt", results[1]);
	
	}
	
	@Test
	public final void testSetString2() {
		
		String filter = "java,gt";
		
		filterWidget.setFilterItems(filter);
		
		String[] results = filterWidget.getFilterItems();
		
		Assert.assertEquals("Expected size: 2, got " + results.length, 2, results.length);
		Assert.assertEquals("Expected java as first item", "java", results[0]);
		Assert.assertEquals("Expected gt as first item", "gt", results[1]);
	
	}
	
	@Test
	public final void testGetString() {
		
		String[] list = new String[] {"1", "2"};
		
		filterWidget.setFilterItems(list);
		
	    String result = filterWidget.getFilterItemsString();
		
		Assert.assertEquals("1,2,", result);
	
	}
	*/
}



