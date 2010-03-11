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

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

public class PersistentDataStore extends AbstractPersistentDataStore {

	private IEclipsePreferences node;
	private IEclipsePreferences defaultNode;

	public PersistentDataStore(IEclipsePreferences instanceNode) {
		this.node = instanceNode;
		this.defaultNode = null;
	}

	/**
	 * Creates the PersistentDataStore object, which is used for reading and
	 * writing to a IEclipsePreferences node
	 * 
	 * @param IEclipsePreferences
	 *            A node
	 */
	public PersistentDataStore(IEclipsePreferences instanceNode,
			IEclipsePreferences defaultNode) {
		this.node = instanceNode;
		this.defaultNode = defaultNode;
	}

	public String read(PersistentSettingsEnums key) {
		String result = node.get(key.toString(), null);

		if (result == null && defaultNode != null) {
			result = defaultNode.get(key.toString(), null);
		}

		return result;
	}

	public void write(PersistentSettingsEnums key, String value) {
		node.put(key.toString(), value);
		try {
			node.flush();
		} catch (BackingStoreException e) {
			Logger.log(e.getMessage(), e);
		}
	}
}
