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

import org.eclipse.core.runtime.Status;

public class Logger {
	static Activator theActivator = new Activator();

	/**
	 * Logs an entry in the Eclipse error log
	 * 
	 * @param String
	 *            Text to appear in the Message field of the Error Log
	 * @return void
	 */
	public static void log(String msg) {
		log(msg, null);
	}

	/**
	 * Logs an entry in the Eclipse error log
	 * 
	 * @param String
	 *            Text to appear in the Message field of the Error Log
	 * @param Exception
	 *            The exception to log
	 * @return void
	 */
	public static void log(String msg, Exception e) {
		theActivator.getLog()
				.log(
						new Status(Status.ERROR, Activator.PLUGIN_ID,
								Status.OK, msg, e));
	}

	private Logger() {
	}
}