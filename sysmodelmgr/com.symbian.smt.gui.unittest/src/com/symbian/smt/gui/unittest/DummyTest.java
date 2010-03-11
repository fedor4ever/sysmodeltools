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

import org.eclipse.core.runtime.Plugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DummyTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testActivator() {
		Plugin plugin = com.symbian.smt.gui.Activator.getDefault();
		Assert.assertTrue(true);
	}

	@Test
	public void testStartBundleContext() {
		Assert.assertTrue(true);
	}

	@Test
	public void testStopBundleContext() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDefault() {
		Assert.assertTrue(true);
	}

}
