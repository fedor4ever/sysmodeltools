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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NewProjectWizardTabbedPropertiesPageTest {
	IStructuredSelection selection;
	NewProjectWizardTabbedPropertiesPage newProjectWizardTabbedPropertiesPage;
	Display display;
	Shell shell;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		display = new Display();
		shell = new Shell(display);
		
		newProjectWizardTabbedPropertiesPage = new NewProjectWizardTabbedPropertiesPage(selection);
		newProjectWizardTabbedPropertiesPage.createControl(shell);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		display.dispose();
	}	
	

	
	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setDistributionText(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetDistributionText() {
		String[] text = {"test"};
		
		newProjectWizardTabbedPropertiesPage.setDistributionTexts(text);
		String[] result = newProjectWizardTabbedPropertiesPage.getDistributionTexts();

		if (result.length != 1 || (!result[0].equals("test"))) {
			fail("Distribution Text set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setModelVersion(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetModelVersion() {
		newProjectWizardTabbedPropertiesPage.setModelVersion("test");
		String result = newProjectWizardTabbedPropertiesPage.getModelVersion();
		
		if (!result.equals("test")) {
			fail("Model Version set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setSystemVersion(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetSystemVersion() {
		newProjectWizardTabbedPropertiesPage.setSystemVersion("test");
		String result = newProjectWizardTabbedPropertiesPage.getSystemVersion();
		
		if (!result.equals("test")) {
			fail("System Version set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setCopyrightText(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetCopyrightText() {
		newProjectWizardTabbedPropertiesPage.setCopyrightText("test");
		String result = newProjectWizardTabbedPropertiesPage.getCopyrightText();
		
		if (!result.equals("test")) {
			fail("Copyright Text set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setModelVersionTexts(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetModelVersionText() {
		String[] text = {"test"};
		
		newProjectWizardTabbedPropertiesPage.setModelVersionTexts(text);
		String[] result = newProjectWizardTabbedPropertiesPage.getModelVersionTexts();
		
		if (result.length != 1 || (!result[0].equals("test"))) {
			fail("Model Version Text set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setModelName(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetModelName() {
		newProjectWizardTabbedPropertiesPage.setModelName("test");
		String result = newProjectWizardTabbedPropertiesPage.getModelName();
		
		if (!result.equals("test")) {
			fail("Model Name set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setSystemName(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetSystemName() {
		newProjectWizardTabbedPropertiesPage.setSystemName("test");
		String result = newProjectWizardTabbedPropertiesPage.getSystemName();
		
		if (!result.equals("test")) {
			fail("System Name set\\get failed");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setHighlightCoreOS(java.lang.Boolean)}.
	 */
	@Test
	public final void testSetAndGetHighlightCoreOS() {
		newProjectWizardTabbedPropertiesPage.setHighlightCoreOS(true);
		assertTrue("Highlight Core OS set\\get failed", newProjectWizardTabbedPropertiesPage.getHighlightCoreOS());
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setLevelOfDetail(java.lang.String)}.
	 */
	@Test
	public final void testSetAndGetLevelOfDetail() {
		newProjectWizardTabbedPropertiesPage.setLevelOfDetail("component");
		String result = newProjectWizardTabbedPropertiesPage.getLevelOfDetail();
		
		if (!result.equals("component")) {
			fail("Level of Detail set\\get failed");
		}
	}

//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#getShapesFile()}.
//	 */
//	@Test
//	public final void testSetAndGetDefaultShapesFile() {
//		newProjectWizardTabbedPropertiesPage.setDefaultShapesFiles(new String[] {"./../SystemModelGenerator/resources/auxiliary/Shapes.xml"});
//		
//		if (!newProjectWizardTabbedPropertiesPage.getDefaultShapesFiles()[0].equalsIgnoreCase("./../SystemModelGenerator/resources/auxiliary/Shapes.xml")) {
//			fail("Did not return default shapes file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#getLevelsFile()}.
//	 */
//	@Test
//	public final void testSetAndGetDefaultLevelsFile() {
//		newProjectWizardTabbedPropertiesPage.setDefaultLevelsFiles(new String[]{"Auto"});
//		
//		if (!newProjectWizardTabbedPropertiesPage.getDefaultLevelsFiles()[0].equalsIgnoreCase("Auto")) {
//			fail("Did not return default levels file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#getLocalisationFile()}.
//	 */
//	@Test
//	public final void testSetAndGetDefaultLocalisationFile() {
//		newProjectWizardTabbedPropertiesPage.setDefaultLocalisationFiles(new String[] {"./../SystemModelGenerator/resources/auxiliary/display-names.xml"});
//		
//		if (!newProjectWizardTabbedPropertiesPage.getDefaultLocalisationFiles()[0].equalsIgnoreCase("./../SystemModelGenerator/resources/auxiliary/display-names.xml")) {
//			fail("Did not return default localisation file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#getSystemInfoFile()}.
//	 */
//	@Test
//	public final void testSetAndGetDefaultSystemInfoFile() {
//		newProjectWizardTabbedPropertiesPage.setDefaultSystemInfoFiles(new String[] {""});
//		
//		if (!newProjectWizardTabbedPropertiesPage.getDefaultSystemInfoFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default system info file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#getDependenciesFile()}.
//	 */
//	@Test
//	public final void testSetAndGetDefaultDependenciesFile() {
//		newProjectWizardTabbedPropertiesPage.setDefaultDependenciesFiles(new String[] {""});
//		
//		if (!newProjectWizardTabbedPropertiesPage.getDefaultDependenciesFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default dependencies file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#getColoursFile()}.
//	 */
//	@Test
//	public final void testSetAndGetDefaultColoursFile() {
//		newProjectWizardTabbedPropertiesPage.setDefaultColoursFiles(new String[] {""});
//		
//		if (!newProjectWizardTabbedPropertiesPage.getDefaultColoursFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default colours file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#getBorderStylesFile()}.
//	 */
//	@Test
//	public final void testSetAndGetDefaultBorderStylesFile() {
//		newProjectWizardTabbedPropertiesPage.setDefaultBorderStylesFiles(new String[] {""});
//		
//		if (!newProjectWizardTabbedPropertiesPage.getDefaultBorderStylesFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default border styles file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#getBorderShapesFile()}.
//	 */
//	@Test
//	public final void testSetAndGetDefaultBorderShapesFile() {
//		newProjectWizardTabbedPropertiesPage.setDefaultBorderShapesFiles(new String[] {""});
//		
//		if (!newProjectWizardTabbedPropertiesPage.getDefaultBorderShapesFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default border shapes file");
//		}
//	}
//
//	/**
//	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#getPatternsFile()}.
//	 */
//	@Test
//	public final void testSetAndGetDefaultPatternsFile() {
//		newProjectWizardTabbedPropertiesPage.setDefaultPatternsFiles(new String[] {""});
//		
//		if (!newProjectWizardTabbedPropertiesPage.getDefaultPatternsFiles()[0].equalsIgnoreCase("")) {
//			fail("Did not return default patterns file");
//		}
//	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setFilterItems(java.lang.String[])}.
	 */
	@Test
	public final void testSetAndGetFilterItems() {
		String[] list = new String[] {"1", "2"};

		newProjectWizardTabbedPropertiesPage.setFilterItems(list);
		
		String[] results = newProjectWizardTabbedPropertiesPage.getFilterItems();

		if (results.length != 2) {
			fail("The list returned should contain 2 elements");
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.wizard.NewProjectWizardTabbedPropertiesPage#setIgnoreItems(java.util.List)}.
	 */
	@Test
	public final void testSetAndGetIgnoreItems() {
		ArrayList<String[]> ignoreItems = new ArrayList<String[]>();
		
		String[] itemData = {"layer", "2"} ;
		ignoreItems.add(itemData);
	
		newProjectWizardTabbedPropertiesPage.setIgnoreItems(ignoreItems);
		
		List<String[]> returned = newProjectWizardTabbedPropertiesPage.getIgnoreItems();
		
		Assert.assertEquals(1, returned.size());
		
		if (returned.get(0)[0] != "layer" || returned.get(0)[1] != "2") {
			fail("The list returned is incorrect");
		}
	}

	
	
	
	
	
	

//	@Test
//	public void setAndGetBorderShapesFiles() {	
//		String items[] = {"file1", "file2"};
//
//		newProjectWizardTabbedPropertiesPage.setBorderShapesFiles(items);
//	
//		String returned[] = newProjectWizardTabbedPropertiesPage.getBorderShapesFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetBorderStylesFiles() {
//		String items[] = {"file1", "file2"};
//
//		newProjectWizardTabbedPropertiesPage.setBorderStylesFiles(items);
//	
//		String returned[] = newProjectWizardTabbedPropertiesPage.getBorderStylesFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetColoursFiles() {
//		String items[] = {"file1", "file2"};
//
//		newProjectWizardTabbedPropertiesPage.setColoursFiles(items);
//	
//		String returned[] = newProjectWizardTabbedPropertiesPage.getColoursFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetPatternsFiles() {
//		String items[] = {"file1", "file2"};
//
//		newProjectWizardTabbedPropertiesPage.setPatternsFiles(items);
//	
//		String returned[] = newProjectWizardTabbedPropertiesPage.getPatternsFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//	
//	@Test
//	public void setAndGetSystemInfoFiles() {
//		String items[] = {"file1", "file2"};
//
//		newProjectWizardTabbedPropertiesPage.setSystemInfoFiles(items);
//	
//		String returned[] = newProjectWizardTabbedPropertiesPage.getSystemInfoFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetLevelsFiles() {
//		String items[] = {"file1", "file2"};
//
//		newProjectWizardTabbedPropertiesPage.setLevelsFiles(items);
//	
//		String returned[] = newProjectWizardTabbedPropertiesPage.getLevelsFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetLocalisationFiles() {
//		String items[] = {"file1", "file2"};
//
//		newProjectWizardTabbedPropertiesPage.setLocalisationFiles(items);
//	
//		String returned[] = newProjectWizardTabbedPropertiesPage.getLocalisationFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	
//	@Test
//	public void setAndGetDependenciesFiles() {
//		String items[] = {"file1", "file2"};
//
//		newProjectWizardTabbedPropertiesPage.setDependenciesFiles(items);
//	
//		String returned[] = newProjectWizardTabbedPropertiesPage.getDependenciesFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
//
//	@Test
//	public void setAndGetShapesFiles() {
//		String items[] = {"file1", "file2"};
//
//		newProjectWizardTabbedPropertiesPage.setShapesFiles(items);
//	
//		String returned[] = newProjectWizardTabbedPropertiesPage.getShapesFiles();
//		
//		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
//	}
	
	
	@Test
	public final void setMakeModelStatic() {
		newProjectWizardTabbedPropertiesPage.setSuppressMouseOverEffect(true);
		
		assertTrue(newProjectWizardTabbedPropertiesPage.getSuppressMouseOverEffect());
	}	
	
}
