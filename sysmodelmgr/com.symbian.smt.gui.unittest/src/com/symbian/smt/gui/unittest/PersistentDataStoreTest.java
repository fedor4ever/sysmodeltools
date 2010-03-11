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

package com.symbian.smt.gui.unittest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PersistentDataStoreTest {
	
	PDS_test_helper dataStore;
	
	@Before
	public final void setUp() {
		dataStore = new PDS_test_helper();
	}
	
	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getCopyrightText()}.
	 */
	@Test
	public final void testSetAndGetCopyrightText() {
		dataStore.setCopyrightText("CopyrightText");
		
		assertTrue(dataStore.getCopyrightText().equals("CopyrightText"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getDependenciesFile()}.
	 */
	@Test
	public final void testSetAndGetDefaultDependenciesFile() {
		dataStore.setSelectedDependenciesFiles(new String[]{"DependenciesFile"});
		
		assertTrue(dataStore.getSelectedDependenciesFiles()[0].equals("DependenciesFile"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getDistributionTexts()}.
	 */
	@Test
	public final void testSetAndGetDistributionText() {
		String[] text = {"DistributionText"};
		dataStore.setDistributionTexts(text);
		
		String[] result = dataStore.getDistributionTexts();
		
		assertTrue(result.length == 1);
		assertTrue(result[0].equals("DistributionText"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getFilterItems()}.
	 */
	@Test
	public final void testSetAndGetFilterItems() {
		String items[] = {"number1", "number2"};

		dataStore.setFilterHasItems(items);
	
		String returned[] = dataStore.getFilterHasItems();
		
		assertTrue(returned.length == 2 && returned[0].equals("number1") && returned[1].equals("number2"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getHighlightCoreOS()}.
	 */
	@Test
	public final void testSetAndGetHighlightCoreOS() {
		dataStore.setHighlightCoreOS(true);
		
		assertTrue(dataStore.getHighlightCoreOS());
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getIgnoreItems()}.
	 */
	@Test
	public final void testSetAndGetIgnoreItems() {
		List<String[]> ignoreItems = new ArrayList<String[]>();
	
		String items[] = {"number1", "number2"};
	
		ignoreItems.add(items);
		ignoreItems.add(items);
		
		dataStore.setIgnoreItems(ignoreItems);
		
		List<String[]> returnedItems = dataStore.getIgnoreItems();
		
		for (String[] item : returnedItems) {
			if(!item[0].equals("number1") && !item[0].equals("number1")) {
				fail();
			}
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getLevelOfDetail()}.
	 */
	@Test
	public final void testSetAndGetLevelOfDetail() {
		dataStore.setLevelOfDetail("LevelOfDetail");
		
		assertTrue(dataStore.getLevelOfDetail().equals("LevelOfDetail"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getLevelsFile()}.
	 */
	@Test
	public final void testSetAndGetDefaultLevelsFile() {
		dataStore.setSelectedLevelsFiles(new String[]{"LevelsFile"});
		
		assertTrue(dataStore.getSelectedLevelsFiles()[0].equals("LevelsFile"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getLocalisationFile()}.
	 */
	@Test
	public final void testSetAndGetDefaultLocalisationFile() {
		dataStore.setSelectedLocalisationFiles(new String[]{"LocalisationFile"});
		
		assertTrue(dataStore.getSelectedLocalisationFiles()[0].equals("LocalisationFile"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getModelName()}.
	 */
	@Test
	public final void testSetAndGetModelName() {
		dataStore.setModelName("ModelName");
		
		assertTrue(dataStore.getModelName().equals("ModelName"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getModelVersion()}.
	 */
	@Test
	public final void testSetAndGetModelVersion() {
		dataStore.setModelVersion("ModelVersion");
		
		assertTrue(dataStore.getModelVersion().equals("ModelVersion"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getModelVersionTexts()}.
	 */
	@Test
	public final void testSetAndGetModelVersionText() {
		String[] text = {"ModelVersionText"};
		
		dataStore.setModelVersionTexts(text);
		
		assertTrue(dataStore.getModelVersionTexts().length == 1);
		assertTrue(dataStore.getModelVersionTexts()[0].equals("ModelVersionText"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getShapesFile()}.
	 */
	@Test
	public final void testSetAndGetDefaultShapesFile() {
		dataStore.setSelectedShapesFiles(new String[]{"ShapesFile"});
		
		assertTrue(dataStore.getSelectedShapesFiles()[0].equals("ShapesFile"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getSystemDefinitionFiles()}.
	 */
	@Test
	public final void testSetAndGetSystemDefinitionFiles() {
		String items[] = {"file1", "file2"};

		dataStore.setSystemDefinitionFiles(items);
	
		String returned[] = dataStore.getSystemDefinitionFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getSystemInfoFile()}.
	 */
	@Test
	public final void testSetAndGetDefaultSystemInfoFile() {
		dataStore.setSelectedSystemInfoFiles(new String[]{"SystemInfoFile"});
		
		assertTrue(dataStore.getSelectedSystemInfoFiles()[0].equals("SystemInfoFile"));
	}

	
	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#setSystemName(java.lang.String)}.
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getSystemName()}.	 
	 */
	@Test
	public final void testSetAndGetSystemName() {	
		dataStore.setSystemName("sysname");
		
		assertTrue(dataStore.getSystemName().equals("sysname"));
	}
	
	
	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getSystemVersion()}.
	 */
	@Test
	public final void testSetAndGetSystemVersion() {
		dataStore.setSystemVersion("SystemVersion");
		
		assertTrue(dataStore.getSystemVersion().equals("SystemVersion"));
	}


	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getBorderShapesFile()}.
	 */
	@Test
	public final void testSetAndGetDefaultBorderShapesFile() {
		dataStore.setSelectedBorderShapesFiles(new String[]{"BorderShapesFile"});
		
		assertTrue(dataStore.getSelectedBorderShapesFiles()[0].equals("BorderShapesFile"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getBorderStylesFile()}.
	 */
	@Test
	public final void testSetAndGetDefaultBorderStylesFile() {
		dataStore.setSelectedBorderStylesFiles(new String[]{"BorderStylesFile"});
		
		assertTrue(dataStore.getSelectedBorderStylesFiles()[0].equals("BorderStylesFile"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getColoursFile()}.
	 */
	@Test
	public final void testSetAndGetDefaultColoursFile() {
		dataStore.setSelectedColoursFiles(new String[]{"ColoursFile"});
		
		assertTrue(dataStore.getSelectedColoursFiles()[0].equals("ColoursFile"));
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.AbstractPersistentDataStore#getPatternsFile()}.
	 */
	@Test
	public final void testSetAndGetDefaultPatternsFile() {
		dataStore.setSelectedPatternsFiles(new String[]{"PatternsFile"});
		
		assertTrue(dataStore.getSelectedPatternsFiles()[0].equals("PatternsFile"));
	}


	@Test
	public final void setWarningLevel() {
		dataStore.setWarningLevel("1");
		
		assertTrue(dataStore.getWarningLevel().equals("1"));
	}
	
	@Test
	public final void setMakeModelStatic() {
		dataStore.setSuppressMouseOverEffect(true);
		
		assertTrue(dataStore.getSuppressMouseOverEffect());
	}	
	


	@Test
	public void setAndGetBorderShapesFiles() {	
		String items[] = {"file1", "file2"};

		dataStore.setBorderShapesFiles(items);
	
		String returned[] = dataStore.getBorderShapesFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}

	@Test
	public void setAndGetBorderStylesFiles() {
		String items[] = {"file1", "file2"};

		dataStore.setBorderStylesFiles(items);
	
		String returned[] = dataStore.getBorderStylesFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}

	@Test
	public void setAndGetColoursFiles() {
		String items[] = {"file1", "file2"};

		dataStore.setColoursFiles(items);
	
		String returned[] = dataStore.getColoursFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}

	@Test
	public void setAndGetPatternsFiles() {
		String items[] = {"file1", "file2"};

		dataStore.setPatternsFiles(items);
	
		String returned[] = dataStore.getPatternsFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}
	
	@Test
	public void setAndGetSystemInfoFiles() {
		String items[] = {"file1", "file2"};

		dataStore.setSystemInfoFiles(items);
	
		String returned[] = dataStore.getSystemInfoFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}

	@Test
	public void setAndGetLevelsFiles() {
		String items[] = {"file1", "file2"};

		dataStore.setLevelsFiles(items);
	
		String returned[] = dataStore.getLevelsFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}

	@Test
	public void setAndGetLocalisationFiles() {
		String items[] = {"file1", "file2"};

		dataStore.setLocalisationFiles(items);
	
		String returned[] = dataStore.getLocalisationFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}

	
	@Test
	public void setAndGetDependenciesFiles() {
		String items[] = {"file1", "file2"};

		dataStore.setDependenciesFiles(items);
	
		String returned[] = dataStore.getDependenciesFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}

	@Test
	public void setAndGetShapesFiles() {
		String items[] = {"file1", "file2"};

		dataStore.setShapesFiles(items);
	
		String returned[] = dataStore.getShapesFiles();
		
		assertTrue(returned.length == 2 && returned[0].equals("file1") && returned[1].equals("file2"));
	}
}
