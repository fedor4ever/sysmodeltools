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

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.symbian.smt.gui.smtwidgets.IgnoreWidget;


public class IgnoreWidgetTest {
	Display display;
	Shell shell;
	IgnoreWidget ignoreWidget;
	
	@Before
	public final void setUp() {
		display = new Display();
		shell = new Shell(display);
		
		ignoreWidget = new IgnoreWidget(shell, SWT.NONE);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		display.dispose();
	}
	
	@Test
	public final void testSetAndGetIgnoreItems() {
		
		ArrayList<String[]> ignoreItems = new ArrayList<String[]>();
		
		String[] itemData = {"layer", "2"} ;
		ignoreItems.add(itemData);
	
		ignoreWidget.setIgnoreItems(ignoreItems);
		
		List<String[]> returned = ignoreWidget.getIgnoreItems();
		
		Assert.assertEquals(1, returned.size());
		
		if (returned.get(0)[0] != "layer" || returned.get(0)[1] != "2") {
			fail("The list returned is incorrect");
		}
	}
	
/*	@Test 
	public final void testSetString() {
		String ignore = "layer:Tools;layer:Utils;layer:SDKENG;layer:MISC;";
			
		ignoreWidget.setIgnoreItems(ignore);
		
		List<String[]> returned = ignoreWidget.getIgnoreItems();
		
		Assert.assertEquals("The list returned is incorrect", 4, returned.size());
		
		Assert.assertEquals("layer", returned.get(0)[0]);
		Assert.assertEquals("layer", returned.get(1)[0]);
		Assert.assertEquals("layer", returned.get(2)[0]);
		Assert.assertEquals("layer", returned.get(3)[0]);
		
		Assert.assertEquals("Tools", returned.get(0)[1]);
		Assert.assertEquals("Utils", returned.get(1)[1]);
		Assert.assertEquals("SDKENG", returned.get(2)[1]);
		Assert.assertEquals("MISC", returned.get(3)[1]);
	}
	
	@Test 
	public final void testSetString2() {
		String ignore = "layer:Tools;layer:Utils;layer:SDKENG;layer:MISC";
			
		ignoreWidget.setIgnoreItems(ignore);
		
		List<String[]> returned = ignoreWidget.getIgnoreItems();
		
		Assert.assertEquals("The list returned is incorrect", 4, returned.size());
		
		Assert.assertEquals("layer", returned.get(0)[0]);
		Assert.assertEquals("layer", returned.get(1)[0]);
		Assert.assertEquals("layer", returned.get(2)[0]);
		Assert.assertEquals("layer", returned.get(3)[0]);
		
		Assert.assertEquals("Tools", returned.get(0)[1]);
		Assert.assertEquals("Utils", returned.get(1)[1]);
		Assert.assertEquals("SDKENG", returned.get(2)[1]);
		Assert.assertEquals("MISC", returned.get(3)[1]);
	}
	
	@Test 
	public final void testGetString() {
		String ignore = "layer:1;layer:2;layer:3;";
		
		ArrayList<String[]> ignoreItems = new ArrayList<String[]>();
		
		String[] itemData = {"layer", "1"} ;
		ignoreItems.add(itemData);
		
		String[] itemData2 = {"layer", "2"} ;
		ignoreItems.add(itemData2);
		
		String[] itemData3 = {"layer", "3"} ;
		ignoreItems.add(itemData3);
		
		ignoreWidget.setIgnoreItems(ignoreItems);
		
		String result = ignoreWidget.getIgnoreItemsString();
		
		Assert.assertEquals("The list returned is incorrect", ignore, result);
	}
	
	@Test 
	public final void testAddDouble() {
		String ignore = "layer:1;layer:2;layer:3;";
		String ignore2 = "layer:5;layer:6;layer:7;layer:8;";
		
		ignoreWidget.setIgnoreItems(ignore);
		ignoreWidget.setIgnoreItems(ignore2);
		
		List<String[]> returned = ignoreWidget.getIgnoreItems();
		
		Assert.assertEquals("The list returned is incorrect", 4, returned.size());
		Assert.assertEquals("5", returned.get(0)[1]);
		Assert.assertEquals("6", returned.get(1)[1]);
		Assert.assertEquals("7", returned.get(2)[1]);
		Assert.assertEquals("8", returned.get(3)[1]);
	}*/
}





