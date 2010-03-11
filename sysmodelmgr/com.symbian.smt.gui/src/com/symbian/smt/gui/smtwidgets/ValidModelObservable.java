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
// ${file_name}
// 
//

package com.symbian.smt.gui.smtwidgets;

public interface ValidModelObservable {

	/**
	 * Registers the provided listener as an interested party in
	 * ValidModelEvents.
	 * 
	 * @param listener
	 *            the ValidModelDefinedListener object to be registered with
	 *            this class.
	 */
	void addModelListener(ValidModelDefinedListener listener);

	/**
	 * Removes the provided listener from the list of registered listeners of
	 * ValidModelEvents.
	 * 
	 * @param listener
	 *            the ValidModelDefinedListener object to be unregistered.
	 */
	void removeModelListener(ValidModelDefinedListener listener);
}
