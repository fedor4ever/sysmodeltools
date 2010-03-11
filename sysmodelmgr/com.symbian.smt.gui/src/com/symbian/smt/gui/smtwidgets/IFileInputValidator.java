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
// IFileInputValidator
//



package com.symbian.smt.gui.smtwidgets;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * This is an extension of the IInputValidator interface which caters for
 * validation of local files and URLs.
 * <p>
 * While isValid() is invoked dynamically as the user types his input (as per
 * the standard IInputValidator interface), the methods
 * <code>isFileReadable()</code> and <code>isUrlResourceReadable</code> defined
 * by this interface are invoked when the user presses the dialog's OK button,
 * as these operations may take longer to execute.
 * <p>
 * The third method, <code>isUrl()</code>, is a utility method that determines
 * whether a given String is a URL or a simple file path.
 * 
 * @author barbararosi-schwartz
 * 
 */
public interface IFileInputValidator extends IInputValidator {
	
	// The unconventional method signature follows the pattern of 
	// IInputValidator's isValid() method.
	public String isFileReadable(String filePath);

	// The unconventional method signature follows the pattern of 
	// IInputValidator's isValid() method.
	public boolean isUrl(String filePath) throws InvalidPathException;

	// The unconventional method signature follows the pattern of 
	// IInputValidator's isValid() method.
	public String isUrlResourceReadable(String urlPath);
}
