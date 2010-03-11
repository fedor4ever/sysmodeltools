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



package com.symbian.smt.gui.editors.xmleditor;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

/**
 * @author barbararosi-schwartz
 * 
 */
public class TagRule extends MultiLineRule {

	public TagRule(IToken token) {
		super("<", ">", token);
	}

	protected boolean sequenceDetected(ICharacterScanner scanner,
			char[] sequence, boolean eofAllowed) {
		int c = scanner.read();
		if (sequence[0] == '<') {
			if (c == '?') {
				// processing instruction - abort
				scanner.unread();
				return false;
			}
			if (c == '!') {
				scanner.unread();
				// comment - abort
				return false;
			}
		} else if (sequence[0] == '>') {
			scanner.unread();
		}
		return super.sequenceDetected(scanner, sequence, eofAllowed);
	}
}
