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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * @author barbararosi-schwartz
 * 
 */
public class ColorManager {

	protected Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);

	public void dispose() {
		Iterator<Color> e = fColorTable.values().iterator();

		while (e.hasNext())
			((Color) e.next()).dispose();
	}

	public Color getColor(RGB rgb) {
		Color color = (Color) fColorTable.get(rgb);

		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}

		return color;
	}
}
