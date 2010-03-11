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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;

import com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget;


public class ResourcesWidgetTest {
	
	Display display;
	Shell shell;
	ResourcesWidget resourcesWidget;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		display = new Display();
		shell = new Shell(display);
		resourcesWidget = new ResourcesWidget(shell, SWT.NONE);
	}
	

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		resourcesWidget = null;
		display.dispose();
	}

//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getShapesFile()}.
//	 */
//	@Test
//	public final void testGetShapesFile() {
// 		resourcesWidget.setShapesFiles(instanceStore.getShapesFiles());
//		String[] filenames = resourcesWidget.getShapesFiles();
//		
//		assertTrue("Shapes filenames should not be null", filenames != null);
//		assertTrue("Expected 2 filenames, got " + filenames.length, filenames.length == 2);
//		
//		String expected_fn = "./../SystemModelGenerator/resources/auxiliary/Shapes.xml";
//		String got_fn = filenames[0];
//		
//		assertTrue("First expected filename: [" + expected_fn + "], got: [" + got_fn + "]", got_fn.equalsIgnoreCase(expected_fn));
//		
//		expected_fn = "./../SystemModelGenerator/resources/auxiliary/Example-shapes.xml";
//		got_fn = filenames[1];
//		
//		assertTrue("First expected filename: [" + expected_fn + "], got: [" + got_fn + "]", got_fn.equalsIgnoreCase(expected_fn));
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getShapesFile()}.
//	 */
//	@Test
//	public final void testGetSelectedShapesFile() {
// 		resourcesWidget.setSelectedShapesFiles(instanceStore.getSelectedShapesFiles());
//
//		if (resourcesWidget.getSelectedShapesFiles() != null) {
//			fail("Selected shapes files should be null by default");
//		}
//	}
//	
//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getLevelsFile()}.
//	 */
//	@Test
//	public final void testGetLevelsFile() {
//		if (!resourcesWidget.getSelectedLevelsFiles()[0].equalsIgnoreCase("Auto")) {
//			fail("Did not return default levels file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getLocalisationFile()}.
//	 */
//	@Test
//	public final void testGetLocalisationFile() {
//		if (!resourcesWidget.getSelectedLocalisationFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default localisation file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getSystemInfoFile()}.
//	 */
//	@Test
//	public final void testGetSystemInfoFile() {
//		if (!resourcesWidget.getSelectedSystemInfoFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default system info file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getDependenciesFile()}.
//	 */
//	@Test
//	public final void testGetDependenciesFile() {
//		if (!resourcesWidget.getSelectedDependenciesFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default dependencies file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getColoursFile()}.
//	 */
//	@Test
//	public final void testGetColoursFile() {
//		if (!resourcesWidget.getSelectedColoursFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default colours file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getBorderStylesFile()}.
//	 */
//	@Test
//	public final void testGetBorderStylesFile() {
//		if (!resourcesWidget.getSelectedBorderStylesFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default border styles file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getBorderShapesFile()}.
//	 */
//	@Test
//	public final void testGetDefaultBorderShapesFile() {
//		if (!resourcesWidget.getSelectedBorderShapesFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default border shapes file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget#getPatternsFile()}.
//	 */
//	@Test
//	public final void testGetPatternsFile() {
//		if (!resourcesWidget.getSelectedPatternsFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default patterns file");
//		}
//	}
//	
//	@Test
//	public void testSetBorderShapesFile() {
//		resourcesWidget.setSelectedBorderShapesFiles(new String[] {""});
//		Assert.assertEquals(new String[] {""}, resourcesWidget.getSelectedBorderShapesFiles());
//	}
//	
//	@Test
//	public void testSetBorderStylesFile() {
//		resourcesWidget.setSelectedBorderStylesFiles(new String[] {""});
//		Assert.assertEquals(new String[] {""}, resourcesWidget.getSelectedBorderStylesFiles());
//	}
//
//	@Test
//	public void testSetColoursFile() {
//		resourcesWidget.setSelectedColoursFiles(new String[] {""});
//		Assert.assertEquals(new String[] {""}, resourcesWidget.getSelectedColoursFiles());
//	}
//	
//	@Test
//	public void testSetDependenciesFile() {
//		resourcesWidget.setSelectedDependenciesFiles(new String[] {""});
//		Assert.assertEquals(new String[] {""}, resourcesWidget.getSelectedDependenciesFiles());
//	}
//	
//	@Test
//	public void testSetLevelsFile() {
//		resourcesWidget.setSelectedLevelsFiles(new String[] {""});
//		Assert.assertEquals(new String[] {""}, resourcesWidget.getSelectedLevelsFiles());
//	}
//	
//	@Test
//	public void testSetLocalisationFile() {
//		resourcesWidget.setSelectedLocalisationFiles(new String[] {""});
//		Assert.assertEquals(new String[] {""}, resourcesWidget.getSelectedLocalisationFiles());
//	}
//	@Test
//	public void testSetPatternsFile() {
//		resourcesWidget.setSelectedPatternsFiles(new String[] {""});
//		Assert.assertEquals(new String[] {""}, resourcesWidget.getSelectedPatternsFiles());
//	}
//	
//	@Test
//	public void testSetShapesFile() {
//		resourcesWidget.setSelectedShapesFiles(new String[] {""});
//		Assert.assertEquals(new String[] {""}, resourcesWidget.getSelectedShapesFiles());
//	}
//	
//	@Test
//	public void testSetSystemInfoFile() {
//		resourcesWidget.setSelectedSystemInfoFiles(new String[] {""});
//		Assert.assertEquals(new String[] {""}, resourcesWidget.getSelectedSystemInfoFiles());
//	}
//
//	@Test
//	public void setAndGetBorderShapesFiles() {	
//		String items[] = {"file1", "file2"};
//
//		resourcesWidget.setBorderShapesFiles(items);
//	
//		String returned[] = resourcesWidget.getBorderShapesFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetBorderStylesFiles() {
//		String items[] = {"file1", "file2"};
//
//		resourcesWidget.setBorderStylesFiles(items);
//	
//		String returned[] = resourcesWidget.getBorderStylesFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetColoursFiles() {
//		String items[] = {"file1", "file2"};
//
//		resourcesWidget.setColoursFiles(items);
//	
//		String returned[] = resourcesWidget.getColoursFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetPatternsFiles() {
//		String items[] = {"file1", "file2"};
//
//		resourcesWidget.setPatternsFiles(items);
//	
//		String returned[] = resourcesWidget.getPatternsFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//	
//	@Test
//	public void setAndGetSystemInfoFiles() {
//		String items[] = {"file1", "file2"};
//
//		resourcesWidget.setSystemInfoFiles(items);
//	
//		String returned[] = resourcesWidget.getSystemInfoFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetLevelsFiles() {
//		String items[] = {"file1", "file2"};
//
//		resourcesWidget.setLevelsFiles(items);
//	
//		String returned[] = resourcesWidget.getLevelsFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetLocalisationFiles() {
//		String items[] = {"file1", "file2"};
//
//		resourcesWidget.setLocalisationFiles(items);
//	
//		String returned[] = resourcesWidget.getLocalisationFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	
//	@Test
//	public void setAndGetDependenciesFiles() {
//		String items[] = {"file1", "file2"};
//
//		resourcesWidget.setDependenciesFiles(items);
//	
//		String returned[] = resourcesWidget.getDependenciesFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetShapesFiles() {
//		String items[] = {"file1", "file2"};
//
//		resourcesWidget.setShapesFiles(items);
//	
//		String returned[] = resourcesWidget.getShapesFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
}
