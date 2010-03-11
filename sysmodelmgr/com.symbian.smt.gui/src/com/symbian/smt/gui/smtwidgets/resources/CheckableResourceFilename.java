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
// CheckableResourceFilename
//



package com.symbian.smt.gui.smtwidgets.resources;

/**
 * This is a wrapper class that represents the elements that appear in the
 * resource files table. It encompasses the String representing each and every
 * file path String representation and a boolean that represents its current
 * checked state.
 * 
 * @author barbararosi-schwartz
 * 
 */
public class CheckableResourceFilename {

	private String filename;
	private boolean isChecked;

	CheckableResourceFilename(String filename) {
		this(filename, false);
	}

	CheckableResourceFilename(String filename, boolean isChecked) {
		this.filename = filename;
		this.isChecked = isChecked;
	}

	String getFilename() {
		return filename;
	}

	boolean isChecked() {
		return isChecked;
	}

	void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	void setFilename(String filename) {
		this.filename = filename;
	}

}
