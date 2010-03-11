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

package com.symbian.smt.gui;

import java.io.IOException;

import junit.framework.TestCase;

import org.xml.sax.SAXParseException;

/**
 * @author jamesclark
 *
 */
public class SystemDefinitionTest extends TestCase {

	String validSysDef = "src/com/symbian/smt/gui/validSysDef.xml";
	String noDTDSysDef = "src/com/symbian/smt/gui/noDTDSysDef.xml";
	String invalidDocTypeSysDef = "src/com/symbian/smt/gui/invalidDocTypeSysDef.xml";
	String nonExistantSysDef = "iDontExist.xml";
	String malformedSysDef = "src/com/symbian/smt/gui/malformedSysDef.xml";

	/**
	 * Test method for {@link com.symbian.smt.gui.SystemDefinition#checkValidSystemDefinitionFile(java.lang.String)}.
	 */
	public void testCheckValidSystemDefinitionFile_ValidSysDef() {
		try {
			SystemDefinition.checkValidSystemDefinitionFile(validSysDef);
		} catch (SystemDefinitionValidationException e) {
			e.printStackTrace();
			fail("Valid sys def failed validation due to: "+e.getLocalizedMessage());
		}
	}
	
	/**
	 * Test method for {@link com.symbian.smt.gui.SystemDefinition#checkValidSystemDefinitionFile(java.lang.String)}.
	 */
	public void testCheckValidSystemDefinitionFile_noDTDSysDef() {
		try {
			SystemDefinition.checkValidSystemDefinitionFile(noDTDSysDef);
		} catch (SystemDefinitionValidationException e) {
			fail("SysDef with no DTD should pass validation");
		}
	}
	
	/**
	 * Test method for {@link com.symbian.smt.gui.SystemDefinition#checkValidSystemDefinitionFile(java.lang.String)}.
	 */
	public void testCheckValidSystemDefinitionFile_invalidDocTypeSysDef() {
		try {
			SystemDefinition.checkValidSystemDefinitionFile(invalidDocTypeSysDef);
			fail("Invalid sys def passed validation: "+invalidDocTypeSysDef);
		} catch (SystemDefinitionValidationFatalException e) {
			//pass
		} catch (SystemDefinitionValidationException e) {
			e.printStackTrace();
			fail("Incase of an invalid doc type SystemDefinitionValidationFatalException but got "+e.getLocalizedMessage());
		}
	}
	
	/**
	 * Test method for {@link com.symbian.smt.gui.SystemDefinition#checkValidSystemDefinitionFile(java.lang.String)}.
	 */
	public void testCheckValidSystemDefinitionFile_nonExistantSysDef() {
		try {
			SystemDefinition.checkValidSystemDefinitionFile(nonExistantSysDef);
			fail("Invalid sys def passed validation: "+nonExistantSysDef);
		} catch (SystemDefinitionValidationException e) {
			assertTrue("Expected exception cause to be IOException but was "+e.getClass(), e.getCause() instanceof IOException);
		}
	}
	
	/**
	 * Test method for {@link com.symbian.smt.gui.SystemDefinition#checkValidSystemDefinitionFile(java.lang.String)}.
	 */
	public void testCheckValidSystemDefinitionFile_malformedSysDef() {
		try {
			SystemDefinition.checkValidSystemDefinitionFile(malformedSysDef);
			fail("Invalid sys def passed validation: "+malformedSysDef);
		} catch (SystemDefinitionValidationException e) {
		}
	}	

}
