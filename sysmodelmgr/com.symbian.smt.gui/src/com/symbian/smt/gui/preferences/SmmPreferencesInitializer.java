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

package com.symbian.smt.gui.preferences;

import static com.symbian.smt.gui.PersistentSettingsEnums.ADVANCED_OPTIONS;
import static com.symbian.smt.gui.PersistentSettingsEnums.BORDER_SHAPES_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.BORDER_SHAPES_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.BORDER_SHAPES_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.BORDER_STYLES_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.BORDER_STYLES_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.BORDER_STYLES_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.COLOURS_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.COLOURS_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.COLOURS_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.COPYRIGHT_TEXT;
import static com.symbian.smt.gui.PersistentSettingsEnums.DEPENDENCIES_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.DEPENDENCIES_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.DEPENDENCIES_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.DISTRIBUTION_TEXTS;
import static com.symbian.smt.gui.PersistentSettingsEnums.DISTRIBUTION_TEXT_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.DISTRIBUTION_TEXT_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.FILTER_HAS_ITEMS;
import static com.symbian.smt.gui.PersistentSettingsEnums.FILTER_ITEMS;
import static com.symbian.smt.gui.PersistentSettingsEnums.FIX_ITEM_SIZE;
import static com.symbian.smt.gui.PersistentSettingsEnums.HIGHTLIGHT_CORE_OS;
import static com.symbian.smt.gui.PersistentSettingsEnums.IGNORE_ITEMS;
import static com.symbian.smt.gui.PersistentSettingsEnums.LEVELS_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.LEVELS_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.LEVELS_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.LEVEL_OF_DETAIL;
import static com.symbian.smt.gui.PersistentSettingsEnums.LOCALISATION_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.LOCALISATION_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.LOCALISATION_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.MODEL_NAME;
import static com.symbian.smt.gui.PersistentSettingsEnums.MODEL_VERSION;
import static com.symbian.smt.gui.PersistentSettingsEnums.MODEL_VERSION_TEXTS;
import static com.symbian.smt.gui.PersistentSettingsEnums.MODEL_VERSION_TEXT_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.MODEL_VERSION_TEXT_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.OUTPUT_NAME;
import static com.symbian.smt.gui.PersistentSettingsEnums.PATTERNS_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.PATTERNS_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.PATTERNS_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.PRINTED_DPIS;
import static com.symbian.smt.gui.PersistentSettingsEnums.PRINTED_DPI_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.PRINTED_DPI_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.S12_XML_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.S12_XML_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.S12_XML_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.SHAPES_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.SHAPES_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.SHAPES_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.SUPPRESS_MOUSE_OVER_EFFECT;
import static com.symbian.smt.gui.PersistentSettingsEnums.SYSTEM_INFO_FILES;
import static com.symbian.smt.gui.PersistentSettingsEnums.SYSTEM_INFO_FILES_DEFAULT;
import static com.symbian.smt.gui.PersistentSettingsEnums.SYSTEM_INFO_FILES_SELECTED;
import static com.symbian.smt.gui.PersistentSettingsEnums.SYSTEM_NAME;
import static com.symbian.smt.gui.PersistentSettingsEnums.SYSTEM_VERSION;
import static com.symbian.smt.gui.PersistentSettingsEnums.WARNING_LEVELS;

import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.Helper;

public class SmmPreferencesInitializer extends AbstractPreferenceInitializer {

	private static String smgFolder = ""; // The location of the System Model
											// Generator
	private final static String SEPARATOR = "|";

	public SmmPreferencesInitializer() {
		super();

		final ResourceBundle resourceBundle = ResourceBundle.getBundle(
				"location", Locale.getDefault(), this.getClass()
						.getClassLoader());

		smgFolder = resourceBundle.getString("location");
	}

	/**
	 * Returns the location of the System Model Generator. This method has been
	 * introduced for the benefit of the PDE unit tests.
	 * 
	 * @return location of the System Model Generator
	 */
	public String getSmgFolder() {
		return smgFolder;
	}

	@Override
	public void initializeDefaultPreferences() {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		store
				.setDefault(WARNING_LEVELS.toString(), WARNING_LEVELS
						.getDefault());

		store.setDefault(OUTPUT_NAME.toString(), OUTPUT_NAME.getDefault());

		store
				.setDefault(COPYRIGHT_TEXT.toString(), COPYRIGHT_TEXT
						.getDefault());

		store.setDefault(DISTRIBUTION_TEXT_SELECTED.toString(),
				DISTRIBUTION_TEXT_SELECTED.getDefault());

		store.setDefault(DISTRIBUTION_TEXTS.toString(), DISTRIBUTION_TEXTS
				.getDefault());

		store.setDefault(DISTRIBUTION_TEXT_DEFAULT.toString(),
				DISTRIBUTION_TEXT_DEFAULT.getDefault());

		store.setDefault(MODEL_NAME.toString(), MODEL_NAME.getDefault());

		store.setDefault(MODEL_VERSION.toString(), MODEL_VERSION.getDefault());

		store.setDefault(MODEL_VERSION_TEXT_SELECTED.toString(),
				MODEL_VERSION_TEXT_SELECTED.getDefault());

		store.setDefault(MODEL_VERSION_TEXTS.toString(), MODEL_VERSION_TEXTS
				.getDefault());

		store.setDefault(MODEL_VERSION_TEXT_DEFAULT.toString(),
				MODEL_VERSION_TEXT_DEFAULT.getDefault());

		store.setDefault(SYSTEM_NAME.toString(), SYSTEM_NAME.getDefault());

		store
				.setDefault(SYSTEM_VERSION.toString(), SYSTEM_VERSION
						.getDefault());

		String highlight = HIGHTLIGHT_CORE_OS.getDefault();

		if (highlight.equals("on")) {
			highlight = "true";
		}

		store.setDefault(HIGHTLIGHT_CORE_OS.toString(), new Boolean(highlight));

		String makeModelStatic = SUPPRESS_MOUSE_OVER_EFFECT.getDefault();

		if (makeModelStatic.equals("on")) {
			makeModelStatic = "true";
		}

		String fixItemSize = FIX_ITEM_SIZE.getDefault();

		if (fixItemSize.equals("on")) {
			fixItemSize = "true";
		}

		store.setDefault(FIX_ITEM_SIZE.toString(), new Boolean(fixItemSize));

		store.setDefault(SUPPRESS_MOUSE_OVER_EFFECT.toString(), new Boolean(
				makeModelStatic));

		store.setDefault(LEVEL_OF_DETAIL.toString(), LEVEL_OF_DETAIL
				.getDefault());

		store.setDefault(PRINTED_DPI_SELECTED.toString(), PRINTED_DPI_SELECTED
				.getDefault());

		store.setDefault(PRINTED_DPIS.toString(), PRINTED_DPIS.getDefault());

		store.setDefault(PRINTED_DPI_DEFAULT.toString(), PRINTED_DPI_DEFAULT
				.getDefault());

		store.setDefault(FILTER_ITEMS.toString(), FILTER_ITEMS.getDefault());

		store.setDefault(FILTER_HAS_ITEMS.toString(), FILTER_HAS_ITEMS
				.getDefault());

		store.setDefault(IGNORE_ITEMS.toString(), IGNORE_ITEMS.getDefault());

		store.setDefault(SHAPES_FILES.toString(), prependPath(SHAPES_FILES
				.getDefault()));

		store.setDefault(LEVELS_FILES.toString(), prependPath(LEVELS_FILES
				.getDefault()));

		store.setDefault(LOCALISATION_FILES.toString(),
				prependPath(LOCALISATION_FILES.getDefault()));

		store.setDefault(DEPENDENCIES_FILES.toString(),
				prependPath(DEPENDENCIES_FILES.getDefault()));

		store.setDefault(SYSTEM_INFO_FILES.toString(),
				prependPath(SYSTEM_INFO_FILES.getDefault()));

		store.setDefault(COLOURS_FILES.toString(), prependPath(COLOURS_FILES
				.getDefault()));

		store.setDefault(BORDER_STYLES_FILES.toString(),
				prependPath(BORDER_STYLES_FILES.getDefault()));

		store.setDefault(BORDER_SHAPES_FILES.toString(),
				prependPath(BORDER_SHAPES_FILES.getDefault()));

		store.setDefault(PATTERNS_FILES.toString(), prependPath(PATTERNS_FILES
				.getDefault()));

		store.setDefault(S12_XML_FILES.toString(), prependPath(S12_XML_FILES
				.getDefault()));

		store.setDefault(SHAPES_FILES_SELECTED.toString(),
				prependPath(SHAPES_FILES_SELECTED.getDefault()));

		store.setDefault(LEVELS_FILES_SELECTED.toString(),
				prependPath(LEVELS_FILES_SELECTED.getDefault()));

		store.setDefault(LOCALISATION_FILES_SELECTED.toString(),
				prependPath(LOCALISATION_FILES_SELECTED.getDefault()));

		store.setDefault(DEPENDENCIES_FILES_SELECTED.toString(),
				prependPath(DEPENDENCIES_FILES_SELECTED.getDefault()));

		store.setDefault(SYSTEM_INFO_FILES_SELECTED.toString(),
				prependPath(SYSTEM_INFO_FILES_SELECTED.getDefault()));

		store.setDefault(COLOURS_FILES_SELECTED.toString(),
				prependPath(COLOURS_FILES_SELECTED.getDefault()));

		store.setDefault(BORDER_STYLES_FILES_SELECTED.toString(),
				prependPath(BORDER_STYLES_FILES_SELECTED.getDefault()));

		store.setDefault(BORDER_SHAPES_FILES_SELECTED.toString(),
				prependPath(BORDER_SHAPES_FILES_SELECTED.getDefault()));

		store.setDefault(PATTERNS_FILES_SELECTED.toString(),
				prependPath(PATTERNS_FILES_SELECTED.getDefault()));

		store.setDefault(S12_XML_FILES_SELECTED.toString(),
				prependPath(S12_XML_FILES_SELECTED.getDefault()));

		store.setDefault(SHAPES_FILES_DEFAULT.toString(),
				prependPath(SHAPES_FILES_DEFAULT.getDefault()));

		store.setDefault(SHAPES_FILES_DEFAULT.toString(),
				prependPath(SHAPES_FILES_DEFAULT.getDefault()));

		store.setDefault(LEVELS_FILES_DEFAULT.toString(),
				prependPath(LEVELS_FILES_DEFAULT.getDefault()));

		store.setDefault(LOCALISATION_FILES_DEFAULT.toString(),
				prependPath(LOCALISATION_FILES_DEFAULT.getDefault()));

		store.setDefault(DEPENDENCIES_FILES_DEFAULT.toString(),
				prependPath(DEPENDENCIES_FILES_DEFAULT.getDefault()));

		store.setDefault(SYSTEM_INFO_FILES_DEFAULT.toString(),
				prependPath(SYSTEM_INFO_FILES_DEFAULT.getDefault()));

		store.setDefault(COLOURS_FILES_DEFAULT.toString(),
				prependPath(COLOURS_FILES_DEFAULT.getDefault()));

		store.setDefault(BORDER_STYLES_FILES_DEFAULT.toString(),
				prependPath(BORDER_STYLES_FILES_DEFAULT.getDefault()));

		store.setDefault(BORDER_SHAPES_FILES_DEFAULT.toString(),
				prependPath(BORDER_SHAPES_FILES_DEFAULT.getDefault()));

		store.setDefault(PATTERNS_FILES_DEFAULT.toString(),
				prependPath(PATTERNS_FILES_DEFAULT.getDefault()));

		store.setDefault(S12_XML_FILES_DEFAULT.toString(),
				prependPath(S12_XML_FILES_DEFAULT.getDefault()));

		store.setDefault(ADVANCED_OPTIONS.toString(), ADVANCED_OPTIONS
				.getDefault());
	}

	/**
	 * Adds the absolute location of the System Model Generator to the filenames
	 * 
	 * @param filenames
	 *            the String representing the "|" separated relative filenames
	 * @return the String representing the "|" separated absolute filenames
	 */
	private String prependPath(String filenames) {
		return Helper.relative2AbsolutePaths(filenames, smgFolder, SEPARATOR);
	}

}
