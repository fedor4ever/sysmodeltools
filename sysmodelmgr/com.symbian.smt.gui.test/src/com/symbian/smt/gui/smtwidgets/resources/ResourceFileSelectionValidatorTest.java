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
package com.symbian.smt.gui.smtwidgets.resources;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.symbian.smt.gui.ResourcesEnums;
import com.symbian.smt.gui.preferences.SmmPreferencesInitializer;
import com.symbian.smt.gui.smtwidgets.InvalidPathException;
import com.symbian.smt.gui.smtwidgets.XmlFileSelectionDialog;

/**
 * @author barbararosi-schwartz
 *
 */
public class ResourceFileSelectionValidatorTest extends TestCase {

	private XmlFileSelectionDialog dialog;
	private String smgFolder;
	private ResourceFileSelectionValidator validator;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		// Initialise the default values
		SmmPreferencesInitializer initialiser = new SmmPreferencesInitializer();

		initialiser.initializeDefaultPreferences();

		smgFolder = initialiser.getSmgFolder();
		IWorkbench workbench = PlatformUI.getWorkbench();
		Shell shell = workbench.getActiveWorkbenchWindow().getShell();
		String dialogTitle = "New System Definition File";
		String dialogMessage = "Enter the path or URL to the system definition file";
		String initialPath = "";
		String[] filterNames = { "*.xml" };
		List<CheckableResourceFilename> filenames = new ArrayList<CheckableResourceFilename>();
		validator = new ResourceFileSelectionValidator(ResourcesEnums.COLOURS, filenames);
		dialog = new XmlFileSelectionDialog(shell, dialogTitle, dialogMessage, initialPath, filterNames, validator); 

		dialog.setBlockOnOpen(false);
		dialog.open();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourceFileSelectionValidator#ResourceFileSelectionValidator(com.symbian.smt.gui.ResourcesEnums, java.util.List)}.
	 */
	public final void testResourceFileSelectionValidator() {
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourceFileSelectionValidator#isFileReadable(java.lang.String)}.
	 */
	public final void testIsFileReadable() {
		String localPath = smgFolder + "/foobar.xml";
		
		String actual = validator.isFileReadable(localPath);
		
		assertEquals("Selected file cannot be read.", actual);
		
		localPath = smgFolder + "/resources/auxiliary/system_model_colors.xml";
		
		actual = validator.isFileReadable(localPath);
		
		assertNull(actual);
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourceFileSelectionValidator#isUrl(java.lang.String)}.
	 */
	public final void testIsUrl() {
		try {
			String path = "foo.bar";
			boolean result = validator.isUrl(path);
			
			assertFalse(result);
			
			path = ":foo.bar:baz";
			result = validator.isUrl(path);
			
			assertFalse(result);
			
			path = "http://bar.baz";
			result = validator.isUrl(path);
			
			assertTrue(result);
		} catch (InvalidPathException e) {
			throw new AssertionError(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourceFileSelectionValidator#isUrlResourceReadable(java.lang.String)}.
	 */
	public final void testIsUrlResourceReadable() {
		// Not yet implemented
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourceFileSelectionValidator#isValid(java.lang.String)}.
	 */
	public final void testIsValid() {
		// Not yet implemented
	}

	/**
	 * Test method for {@link com.symbian.smt.gui.smtwidgets.resources.ResourceFileSelectionValidator#isXmlValid(java.lang.String)}.
	 */
	public final void testIsXmlValid() {
		// Not yet implemented
	}

}
