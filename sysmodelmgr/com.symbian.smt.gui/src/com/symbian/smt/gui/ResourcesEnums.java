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

public enum ResourcesEnums {
	BORDER_SHAPES("Border Shapes"), BORDER_STYLES("Border Styles"), COLOURS(
			"Colours"), DEPENDENCIES("Dependencies"), LEVELS("Levels"), LOCALISATION(
			"Localisation"), PATTERNS("Patterns"), SHAPES("Shapes"), SYSTEM_INFO(
			"System Info"), S12_XML("S12 XML");

	public static ResourcesEnums getResourcesEnums(String arg) {
		ResourcesEnums[] enums = values();

		for (int i = 0; i < enums.length; i++) {
			if (enums[i].arg().equals(arg)) {
				return enums[i];
			}
		}

		throw new IllegalArgumentException("Unknown ResourcesEnum with arg = ["
				+ arg + "]");
	}

	private final String value;

	ResourcesEnums(String value) {
		this.value = value;
	}

	public String arg() {
		return value;
	}
}