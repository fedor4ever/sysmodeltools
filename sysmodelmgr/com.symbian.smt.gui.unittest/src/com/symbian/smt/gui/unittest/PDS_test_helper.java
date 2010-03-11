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

import java.util.HashMap;
import java.util.Map;

import com.symbian.smt.gui.AbstractPersistentDataStore;
import com.symbian.smt.gui.PersistentSettingsEnums;


public class PDS_test_helper  extends AbstractPersistentDataStore {

	Map<String, String> data = new HashMap<String, String>();
	
	@Override
	public String read(PersistentSettingsEnums key) {
		String name = key.name();

		return data.get(name).toString();
	}

	@Override
	public void write(PersistentSettingsEnums key, String value) {
		data.put(key.toString(), value);
	}
}
