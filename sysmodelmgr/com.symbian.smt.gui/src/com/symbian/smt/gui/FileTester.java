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

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.symbian.smt.gui.nature.Nature;

/**
 * @author barbararosi-schwartz
 *
 */
public class FileTester extends PropertyTester {

	/**
	 * 
	 */
	public FileTester() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object element, String property, Object[] args, Object expectedValue) {
		IFile file = (IFile) element;
		
		if (property.equals("belongsToSMMProject")) {
			try {
				boolean belongsToSMMProject = file.getProject().hasNature(Nature.ID);
				return expectedValue == null ?  belongsToSMMProject : (belongsToSMMProject == ((Boolean) expectedValue).booleanValue());
			}
			catch (CoreException e) {
				Logger.log(e.getMessage(), e);
				return false;
			}
			
		}
		
		return false;
	}

}
