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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum PersistentSettingsEnums {
	// A class containing Enums to be used as the keys for the plug-in and
	// project persistent data store

	// System Definition Files
	SYSTEM_DEFINITION_FILES,

	// Model Labels
	COPYRIGHT_TEXT, DISTRIBUTION_TEXTS, DISTRIBUTION_TEXT_DEFAULT, DISTRIBUTION_TEXT_SELECTED, MODEL_NAME, MODEL_VERSION, MODEL_VERSION_TEXTS, MODEL_VERSION_TEXT_DEFAULT, MODEL_VERSION_TEXT_SELECTED, SYSTEM_NAME, SYSTEM_VERSION,

	// Model Control
	HIGHTLIGHT_CORE_OS, LEVEL_OF_DETAIL, PRINTED_DPIS, PRINTED_DPI_DEFAULT, PRINTED_DPI_SELECTED, SUPPRESS_MOUSE_OVER_EFFECT, FIX_ITEM_SIZE,

	// Resources
	// Lists of resource files
	SHAPES_FILES, LEVELS_FILES, LOCALISATION_FILES, DEPENDENCIES_FILES, SYSTEM_INFO_FILES, COLOURS_FILES, BORDER_STYLES_FILES, BORDER_SHAPES_FILES, PATTERNS_FILES, S12_XML_FILES,

	// The resource file selected by the user
	SHAPES_FILES_SELECTED, LEVELS_FILES_SELECTED, LOCALISATION_FILES_SELECTED, DEPENDENCIES_FILES_SELECTED, SYSTEM_INFO_FILES_SELECTED, COLOURS_FILES_SELECTED, BORDER_STYLES_FILES_SELECTED, BORDER_SHAPES_FILES_SELECTED, PATTERNS_FILES_SELECTED, S12_XML_FILES_SELECTED,

	// The hard coded default files for the resources
	SHAPES_FILES_DEFAULT, LEVELS_FILES_DEFAULT, LOCALISATION_FILES_DEFAULT, DEPENDENCIES_FILES_DEFAULT, SYSTEM_INFO_FILES_DEFAULT, COLOURS_FILES_DEFAULT, BORDER_STYLES_FILES_DEFAULT, BORDER_SHAPES_FILES_DEFAULT, PATTERNS_FILES_DEFAULT, S12_XML_FILES_DEFAULT,

	// Filter and Filter has Items
	FILTER_ITEMS, FILTER_HAS_ITEMS,

	// Ignore Items
	IGNORE_ITEMS,

	// Warning Level
	WARNING_LEVELS,

	// Advanced Options
	ADVANCED_OPTIONS,

	OUTPUT_NAME;

	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"defaults", Locale.getDefault(), getClass().getClassLoader());

	public String getDefault() {
		try {
			String value = resourceBundle.getString(this.name());
			return value.replaceAll("'", "\\\\\'");
		} catch (MissingResourceException e) {
			Logger.log(e.getMessage(), e);
			return this.name();
		} catch (NullPointerException e) {
			Logger.log(e.getMessage(), e);
			return this.name();
		}
	};
}
