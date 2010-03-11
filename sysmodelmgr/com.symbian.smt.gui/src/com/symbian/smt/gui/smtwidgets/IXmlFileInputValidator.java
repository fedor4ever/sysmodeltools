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
// IXmlFileInputValidator
//



package com.symbian.smt.gui.smtwidgets;

/**
 * This is an extension of the IFileInputValidator interface which caters for
 * validation of XML files
 * 
 * @author barbararosi-schwartz
 * 
 */
public interface IXmlFileInputValidator extends IFileInputValidator {
	
	/**
	 * Determines whether or not the file identified by the provided path
	 * is a valid XML resource. Returns a message to display if validation 
	 * fails.
	 * <p>
	 * Returns <code>null</code> if validation succeeds.
	 * <p>
	 * The unconventional method signature follows the pattern of 
	 * IInputValidator's isValid() method.
	 * 
	 * @param filePath
	 *            the String representing the path to the file to
	 *            be validated.
	 * 
	 * @return a message or <code>null</code> if no validation error
	 */
	public String isXmlValid(String filePath);
}
