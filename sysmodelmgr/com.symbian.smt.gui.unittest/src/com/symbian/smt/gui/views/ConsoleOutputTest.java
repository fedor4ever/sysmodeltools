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

package com.symbian.smt.gui.views;

import junit.framework.Assert;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;


public class ConsoleOutputTest {

	@Test
	public final void testConsoleOutput() {
		String textToTest = "Test text";
		
		Display display = new Display();
		Shell shell = new Shell(display);
		
		ConsoleOutput consoleOutput = new ConsoleOutput();
		
		consoleOutput.createPartControl(shell);
		
		consoleOutput.setFocus();  // This method doesn't actually do anything but keeps code coverage happy
		
		ConsoleOutput.addText(textToTest);
		
		Assert.assertEquals(ConsoleOutput.getText().substring(0, textToTest.length()), textToTest);
		
		ConsoleOutput.reset();
		
		Assert.assertEquals(ConsoleOutput.getText(), "");
		
		display.dispose();
	}
}
