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

package com.symbian.smt.gui.builder;

public enum SystemModelGeneratorEnumsForCLI {

	// System Definition Files
	SYSTEM_DEFINITION_FILES("--sysdef"),

	// Model Labels
	COPYRIGHT_TEXT("--copyright"), DISTRIBUTION_TEXT("--distribution"), MODEL_NAME(
			"--model_name"), MODEL_VERSION("--model_version"), MODEL_VERSION_TEXT(
			"--model_version_type"), SYSTEM_NAME("--system_name"), SYSTEM_VERSION(
			"--system_version"),

	// Model Control
	HIGHTLIGHT_CORE_OS("--coreos"), LEVEL_OF_DETAIL("--detail"), PRINTED_DPI(
			"--dpi"), SUPPRESS_MOUSE_OVER_EFFECT("--static"), FIX_ITEM_SIZE(
			"--detail-type"),

	// Resources
	SHAPES_FILES("--shapes"), LEVELS_FILES("--levels"), LOCALISATION_FILES(
			"--localize"), DEPENDENCIES_FILES("--deps"), SYSTEM_INFO_FILES(
			"--sysinfo"), COLOURS_FILES("--color"), BORDER_STYLES_FILES(
			"--border-style"), BORDER_SHAPES_FILES("--border-shape"), PATTERNS_FILES(
			"--pattern"), S12_XML_FILES("--s12"),

	// Filter Items
	FILTER_ITEMS("--filter"),

	// Filter Items
	FILTER_HAS_ITEMS("--filter-has"),

	// Ignore Items
	IGNORE_ITEMS("--ignore"),

	// Warning Level
	WARNING_LEVEL("-w"),

	// Output filename
	OUTPUT_FILE("-output"),

	// Temp Dir
	TEMPDIR("--tempdir");

	private final String commandlineArg;

	SystemModelGeneratorEnumsForCLI(String commandlineArg) {
		this.commandlineArg = commandlineArg;
	}

	public String arg() {
		return commandlineArg;
	}
}
