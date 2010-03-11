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

public interface ValidModelDefinedListener {

	/**
	 * This is the call back method invoked by a ValidObservable object on all
	 * ValidModelDefinedListener objects registered with it to inform the
	 * listeners of the outcome of validation of a given field in a widget. The
	 * listeners are then responsible to set their state accordingly.
	 * 
	 * @param event
	 *            the ValidModelEvent that has been created by the
	 *            ValidObservable object and that contains all of the necessary
	 *            validation related information.
	 */
	void validModelDefined(ValidModelEvent event);
}
