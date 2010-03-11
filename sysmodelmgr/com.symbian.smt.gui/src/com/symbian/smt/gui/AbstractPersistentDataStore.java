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

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPersistentDataStore implements PersistentSettings {

	final static String SEPARATOR = "|";

	private void concatenateAndWriteString(PersistentSettingsEnums theEnum,
			String separator, String[] items) {
		write(theEnum, Helper.concatenateString(separator, items));
	}

	/**
	 * Gets the list of advanced options from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getAdvancedOptions() {
		return readAndSplitString(PersistentSettingsEnums.ADVANCED_OPTIONS,
				SEPARATOR);
	}

	/**
	 * Gets the border shapes file location from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getBorderShapesFiles() {
		return readAndSplitString(PersistentSettingsEnums.BORDER_SHAPES_FILES,
				SEPARATOR);
	}

	/**
	 * Gets the border styles file location from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getBorderStylesFiles() {
		return readAndSplitString(PersistentSettingsEnums.BORDER_STYLES_FILES,
				SEPARATOR);
	}

	/**
	 * Gets the colours file location from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getColoursFiles() {
		return readAndSplitString(PersistentSettingsEnums.COLOURS_FILES,
				SEPARATOR);
	}

	/**
	 * Gets the copyright text from the persistent data store
	 * 
	 * @return String
	 */
	public String getCopyrightText() {
		return read(PersistentSettingsEnums.COPYRIGHT_TEXT);
	}

	/**
	 * Gets the System Model Managers default border shapes files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultBorderShapesFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.BORDER_SHAPES_FILES_DEFAULT, SEPARATOR);
	}

	/**
	 * Gets the System Model Managers default border styles files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultBorderStylesFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.BORDER_STYLES_FILES_DEFAULT, SEPARATOR);
	}

	/**
	 * Gets the System Model Managers default colours files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultColoursFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.COLOURS_FILES_DEFAULT, SEPARATOR);
	}

	/**
	 * Gets the System Model Managers default dependencies files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultDependenciesFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.DEPENDENCIES_FILES_DEFAULT, SEPARATOR);
	}

	/**
	 * Gets the System Model Managers default distribution text from the plug-in
	 * default persistent data store
	 * 
	 * @return String
	 */
	public String getDefaultDistributionText() {
		return read(PersistentSettingsEnums.DISTRIBUTION_TEXT_DEFAULT);
	}

	/**
	 * Gets the System Model Managers default levels files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultLevelsFiles() {
		return readAndSplitString(PersistentSettingsEnums.LEVELS_FILES_DEFAULT,
				SEPARATOR);
	}

	/**
	 * Gets the System Model Managers default localisation files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultLocalisationFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.LOCALISATION_FILES_DEFAULT, SEPARATOR);
	}

	/**
	 * Gets the default model version text from the persistent data store
	 * 
	 * @return String
	 */
	public String getDefaultModelVersionText() {
		return read(PersistentSettingsEnums.MODEL_VERSION_TEXT_DEFAULT);
	}

	/**
	 * Gets the System Model Managers default patterns files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultPatternsFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.PATTERNS_FILES_DEFAULT, SEPARATOR);
	}

	/**
	 * Gets the System Model Managers default printed DPI from the plug-in
	 * default persistent data store
	 * 
	 * @return String
	 */
	public String getDefaultPrintedDpi() {
		return read(PersistentSettingsEnums.PRINTED_DPI_DEFAULT);
	}

	/**
	 * Gets the System Model Managers default S12 XML files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultS12XmlFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.S12_XML_FILES_DEFAULT, SEPARATOR);
	}

	/**
	 * Gets the System Model Managers default shapes files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultShapesFiles() {
		return readAndSplitString(PersistentSettingsEnums.SHAPES_FILES_DEFAULT,
				SEPARATOR);
	}

	/**
	 * Gets the System Model Managers default system info files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultSystemInfoFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.SYSTEM_INFO_FILES_DEFAULT, SEPARATOR);
	}

	/**
	 * Gets the dependencies files location from the persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDependenciesFiles() {
		return readAndSplitString(PersistentSettingsEnums.DEPENDENCIES_FILES,
				SEPARATOR);
	}

	/**
	 * Gets the distribution text values from the persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDistributionTexts() {
		return readAndSplitString(PersistentSettingsEnums.DISTRIBUTION_TEXTS,
				SEPARATOR);
	}

	/**
	 * Gets the list of filter has items from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getFilterHasItems() {
		return readAndSplitString(PersistentSettingsEnums.FILTER_HAS_ITEMS,
				SEPARATOR);
	}

	/**
	 * Gets the list of filter items from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getFilterItems() {
		return readAndSplitString(PersistentSettingsEnums.FILTER_ITEMS,
				SEPARATOR);
	}

	/**
	 * Gets the fix item size option from the persistent data store
	 * 
	 * @return a Boolean value indicating whether or not the fix item size
	 *         option is checked
	 */
	public Boolean getFixItemSize() {
		String fixItemSize = read(PersistentSettingsEnums.FIX_ITEM_SIZE);

		if (fixItemSize.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the highlight core OS option from the persistent data store
	 * 
	 * @return String
	 */
	public Boolean getHighlightCoreOS() {
		String highlightCoreOS = read(PersistentSettingsEnums.HIGHTLIGHT_CORE_OS);

		if (highlightCoreOS.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the list of ignore items from the persistent data store
	 * 
	 * @return List<String[]>
	 */
	public List<String[]> getIgnoreItems() {
		ArrayList<String[]> ignoreItems = new ArrayList<String[]>();

		String result = read(PersistentSettingsEnums.IGNORE_ITEMS);

		if (result != null && result.length() > 0) {
			// Split on ;'s
			for (String ignoreItem : result.split(";")) {
				if (ignoreItem != null && ignoreItem.length() > 0) {
					// Then split on !'s
					String[] itemData = ignoreItem.split(":");
					// and add to the list
					ignoreItems.add(itemData);
				}
			}
		}
		return ignoreItems;
	}

	/**
	 * Gets the level of detail from the persistent data store
	 * 
	 * @return String
	 */
	public String getLevelOfDetail() {
		return read(PersistentSettingsEnums.LEVEL_OF_DETAIL);
	}

	/**
	 * Gets the levels file location from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getLevelsFiles() {
		return readAndSplitString(PersistentSettingsEnums.LEVELS_FILES,
				SEPARATOR);
	}

	/**
	 * Gets the localisation file location from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getLocalisationFiles() {
		return readAndSplitString(PersistentSettingsEnums.LOCALISATION_FILES,
				SEPARATOR);
	}

	/**
	 * Gets the model name from the persistent data store
	 * 
	 * @return String
	 */
	public String getModelName() {
		return read(PersistentSettingsEnums.MODEL_NAME);
	}

	/**
	 * Gets the model version from the persistent data store
	 * 
	 * @return String
	 */
	public String getModelVersion() {
		return read(PersistentSettingsEnums.MODEL_VERSION);
	}

	/**
	 * Gets the model version texts from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getModelVersionTexts() {
		return readAndSplitString(PersistentSettingsEnums.MODEL_VERSION_TEXTS,
				SEPARATOR);
	}

	public String getOutputFilename() {
		return read(PersistentSettingsEnums.OUTPUT_NAME);
	}

	/**
	 * Gets the patterns file location from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getPatternsFiles() {
		return readAndSplitString(PersistentSettingsEnums.PATTERNS_FILES,
				SEPARATOR);
	}

	/**
	 * Gets the selected printed DPI value from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getPrintedDpis() {
		return readAndSplitString(PersistentSettingsEnums.PRINTED_DPIS,
				SEPARATOR);
	}

	public String[] getS12XmlFiles() {
		return readAndSplitString(PersistentSettingsEnums.S12_XML_FILES,
				SEPARATOR);
	}

	public String[] getSelectedBorderShapesFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.BORDER_SHAPES_FILES_SELECTED, SEPARATOR);
	}

	public String[] getSelectedBorderStylesFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.BORDER_STYLES_FILES_SELECTED, SEPARATOR);
	}

	public String[] getSelectedColoursFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.COLOURS_FILES_SELECTED, SEPARATOR);
	}

	public String[] getSelectedDependenciesFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.DEPENDENCIES_FILES_SELECTED, SEPARATOR);
	}

	/**
	 * Gets the selected distribution text value from the persistent data store
	 * 
	 * @return String
	 */
	public String getSelectedDistributionText() {
		return read(PersistentSettingsEnums.DISTRIBUTION_TEXT_SELECTED);
	}

	public String[] getSelectedLevelsFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.LEVELS_FILES_SELECTED, SEPARATOR);
	}

	public String[] getSelectedLocalisationFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.LOCALISATION_FILES_SELECTED, SEPARATOR);
	}

	/**
	 * Gets the selected model version text value from the persistent data store
	 * 
	 * @return String
	 */
	public String getSelectedModelVersionText() {
		return read(PersistentSettingsEnums.MODEL_VERSION_TEXT_SELECTED);
	}

	public String[] getSelectedPatternsFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.PATTERNS_FILES_SELECTED, SEPARATOR);
	}

	/**
	 * Gets the selected printed DPI value from the persistent data store
	 * 
	 * @return String
	 */
	public String getSelectedPrintedDpi() {
		return read(PersistentSettingsEnums.PRINTED_DPI_SELECTED);
	}

	public String[] getSelectedS12XmlFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.S12_XML_FILES_SELECTED, SEPARATOR);
	}

	public String[] getSelectedShapesFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.SHAPES_FILES_SELECTED, SEPARATOR);
	}

	public String[] getSelectedSystemInfoFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.SYSTEM_INFO_FILES_SELECTED, SEPARATOR);
	}

	/**
	 * Gets the shapes file location from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getShapesFiles() {
		return readAndSplitString(PersistentSettingsEnums.SHAPES_FILES,
				SEPARATOR);
	}

	public Boolean getSuppressMouseOverEffect() {
		String makeModelStatic = read(PersistentSettingsEnums.SUPPRESS_MOUSE_OVER_EFFECT);

		if (makeModelStatic.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the list of system definition files from the persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getSystemDefinitionFiles() {
		return readAndSplitString(
				PersistentSettingsEnums.SYSTEM_DEFINITION_FILES, SEPARATOR);
	}

	/**
	 * Gets the system info file location from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getSystemInfoFiles() {
		return readAndSplitString(PersistentSettingsEnums.SYSTEM_INFO_FILES,
				SEPARATOR);
	}

	/**
	 * Gets the system name from the persistent data store
	 * 
	 * @return String
	 */
	public String getSystemName() {
		return read(PersistentSettingsEnums.SYSTEM_NAME);
	}

	/**
	 * Gets the system version from the persistent data store
	 * 
	 * @return String
	 */
	public String getSystemVersion() {
		return read(PersistentSettingsEnums.SYSTEM_VERSION);
	}

	/**
	 * Gets the warning level to use from the persistent data store
	 * 
	 * @return String
	 */
	public String getWarningLevel() {
		return read(PersistentSettingsEnums.WARNING_LEVELS);
	}

	public abstract String read(PersistentSettingsEnums key);

	private String[] readAndSplitString(PersistentSettingsEnums theEnum,
			String separator) {
		String result = read(theEnum);

		return Helper.splitString(result, separator);
	}

	/**
	 * Writes the list of advanced options to the persistent data store
	 * 
	 * @param advancedOptionsList
	 *            List of advanced options
	 * @return void
	 */
	public void setAdvancedOptions(String[] advancedOptionsList) {
		concatenateAndWriteString(PersistentSettingsEnums.ADVANCED_OPTIONS,
				SEPARATOR, advancedOptionsList);
	}

	/**
	 * Writes the border shapes file location to the persistent data store
	 * 
	 * @param borderShapesFile
	 *            Location of the border shapes file
	 * @return void
	 */
	public void setBorderShapesFiles(String[] borderShapesFile) {
		concatenateAndWriteString(PersistentSettingsEnums.BORDER_SHAPES_FILES,
				SEPARATOR, borderShapesFile);
	}

	/**
	 * Writes the border styles file location to the persistent data store
	 * 
	 * @param borderStylesFile
	 *            Location of the border styles file
	 * @return void
	 */
	public void setBorderStylesFiles(String[] borderStylesFile) {
		concatenateAndWriteString(PersistentSettingsEnums.BORDER_STYLES_FILES,
				SEPARATOR, borderStylesFile);
	}

	/**
	 * Writes the colours file location to the persistent data store
	 * 
	 * @param coloursFile
	 *            Location of the colours file
	 * @return void
	 */
	public void setColoursFiles(String[] coloursFile) {
		concatenateAndWriteString(PersistentSettingsEnums.COLOURS_FILES,
				SEPARATOR, coloursFile);
	}

	/**
	 * Writes the copyright text to the persistent data store
	 * 
	 * @param copyrightText
	 *            String to be used for the copyright text
	 * @return void
	 */
	public void setCopyrightText(String copyrightText) {
		write(PersistentSettingsEnums.COPYRIGHT_TEXT, copyrightText);
	}

	/**
	 * Writes the dependencies file location to the persistent data store
	 * 
	 * @param dependenciesFile
	 *            Location of the dependencies file
	 * @return void
	 */
	public void setDependenciesFiles(String[] dependenciesFile) {
		concatenateAndWriteString(PersistentSettingsEnums.DEPENDENCIES_FILES,
				SEPARATOR, dependenciesFile);
	}

	/**
	 * Writes the distribution text values to the persistent data store
	 * 
	 * @param distributionTexts
	 *            String[] to be used for the distribution text values
	 * @return void
	 */
	public void setDistributionTexts(String[] distributionTexts) {
		concatenateAndWriteString(PersistentSettingsEnums.DISTRIBUTION_TEXTS,
				SEPARATOR, distributionTexts);
	}

	/**
	 * Writes the list of filter has items to the persistent data store
	 * 
	 * @param filterHasItemsList
	 *            List of filter has names
	 * @return void
	 */
	public void setFilterHasItems(String[] filterHasItemsList) {
		concatenateAndWriteString(PersistentSettingsEnums.FILTER_HAS_ITEMS,
				SEPARATOR, filterHasItemsList);
	}

	/**
	 * Writes the list of filter items to the persistent data store
	 * 
	 * @param filterItemsList
	 *            List of filter names
	 * @return void
	 */
	public void setFilterItems(String[] filterItemsList) {
		concatenateAndWriteString(PersistentSettingsEnums.FILTER_ITEMS,
				SEPARATOR, filterItemsList);
	}

	/**
	 * Writes the fix item size option to the persistent data store
	 * 
	 * @param fixItemSize
	 *            Boolean to represent the fix item size option
	 * @return void
	 */
	public void setFixItemSize(Boolean fixItemSize) {
		write(PersistentSettingsEnums.FIX_ITEM_SIZE, fixItemSize.toString());
	}

	/**
	 * Writes the highlight core OS option to the persistent data store
	 * 
	 * @param highlightCoreOS
	 *            Boolean to represent the highlight core OS option
	 * @return void
	 */
	public void setHighlightCoreOS(Boolean highlightCoreOS) {
		write(PersistentSettingsEnums.HIGHTLIGHT_CORE_OS, highlightCoreOS
				.toString());
	}

	/**
	 * Writes the list of ignore items to the persistent data store
	 * 
	 * @param ignoreItems
	 *            List of ignore items
	 * @return void
	 */
	public void setIgnoreItems(List<String[]> ignoreItemsList) {
		StringBuilder ignoreItems = new StringBuilder();

		for (String[] ignoreItem : ignoreItemsList) {
			ignoreItems.append(ignoreItem[0]);
			ignoreItems.append(":");
			ignoreItems.append(ignoreItem[1]);
			ignoreItems.append(";");
		}

		write(PersistentSettingsEnums.IGNORE_ITEMS, ignoreItems.toString());
	}

	/**
	 * Writes the level of detail to the persistent data store
	 * 
	 * @param levelOfDetail
	 *            String to be used for the level of detail
	 * @return void
	 */
	public void setLevelOfDetail(String levelOfDetail) {
		write(PersistentSettingsEnums.LEVEL_OF_DETAIL, levelOfDetail);
	}

	/**
	 * Writes the levels file location to the persistent data store
	 * 
	 * @param levelsFile
	 *            Location of the levels file
	 * @return void
	 */
	public void setLevelsFiles(String[] levelsFile) {
		concatenateAndWriteString(PersistentSettingsEnums.LEVELS_FILES,
				SEPARATOR, levelsFile);
	}

	/**
	 * Writes the localisation file location to the persistent data store
	 * 
	 * @param localisationFile
	 *            Location of the localisation file
	 * @return void
	 */
	public void setLocalisationFiles(String[] localisationFiles) {
		concatenateAndWriteString(PersistentSettingsEnums.LOCALISATION_FILES,
				SEPARATOR, localisationFiles);
	}

	/**
	 * Writes the model name to the persistent data store
	 * 
	 * @param modelName
	 *            String to be used for the model name
	 * @return void
	 */
	public void setModelName(String modelName) {
		write(PersistentSettingsEnums.MODEL_NAME, modelName);
	}

	/**
	 * Writes the model version to the persistent data store
	 * 
	 * @param modelVersion
	 *            String to be used for the model version
	 * @return void
	 */
	public void setModelVersion(String modelVersion) {
		write(PersistentSettingsEnums.MODEL_VERSION, modelVersion);
	}

	/**
	 * Writes the model version text values to the persistent data store
	 * 
	 * @param modelVersionTexts
	 *            String[] to be used for the model version text values
	 * @return void
	 */
	public void setModelVersionTexts(String[] modelVersionTexts) {
		concatenateAndWriteString(PersistentSettingsEnums.MODEL_VERSION_TEXTS,
				SEPARATOR, modelVersionTexts);
	}

	public void setOutputFilename(String filename) {
		write(PersistentSettingsEnums.OUTPUT_NAME, filename);
	}

	/**
	 * Writes the patterns file location to the persistent data store
	 * 
	 * @param patternsFile
	 *            Location of the patterns file
	 * @return void
	 */
	public void setPatternsFiles(String[] patternsFile) {
		concatenateAndWriteString(PersistentSettingsEnums.PATTERNS_FILES,
				SEPARATOR, patternsFile);
	}

	/**
	 * Writes the printed DPI values to the persistent data store
	 * 
	 * @param dpi
	 *            String array to be used for the printed DPI values
	 * @return void
	 */
	public void setPrintedDpis(String[] dpis) {
		concatenateAndWriteString(PersistentSettingsEnums.PRINTED_DPIS,
				SEPARATOR, dpis);
	}

	public void setS12XmlFiles(String[] s12XmlFiles) {
		concatenateAndWriteString(PersistentSettingsEnums.S12_XML_FILES,
				SEPARATOR, s12XmlFiles);
	}

	public void setSelectedBorderShapesFiles(String[] borderShapesFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.BORDER_SHAPES_FILES_SELECTED,
				SEPARATOR, borderShapesFiles);
	}

	public void setSelectedBorderStylesFiles(String[] borderStylesFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.BORDER_STYLES_FILES_SELECTED,
				SEPARATOR, borderStylesFiles);
	}

	public void setSelectedColoursFiles(String[] coloursFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.COLOURS_FILES_SELECTED, SEPARATOR,
				coloursFiles);
	}

	public void setSelectedDependenciesFiles(String[] dependenciesFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.DEPENDENCIES_FILES_SELECTED, SEPARATOR,
				dependenciesFiles);
	}

	/**
	 * Writes the selected distribution text value to the persistent data store
	 * 
	 * @param distributionText
	 *            String to be used for the selected distribution text value
	 * @return void
	 */
	public void setSelectedDistributionText(String distributionText) {
		write(PersistentSettingsEnums.DISTRIBUTION_TEXT_SELECTED,
				distributionText);
	}

	public void setSelectedLevelsFiles(String[] levelsFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.LEVELS_FILES_SELECTED, SEPARATOR,
				levelsFiles);
	}

	public void setSelectedLocalisationFiles(String[] localisationFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.LOCALISATION_FILES_SELECTED, SEPARATOR,
				localisationFiles);
	}

	/**
	 * Writes the selected model version text value to the persistent data store
	 * 
	 * @param dpi
	 *            String to be used for the selected model version text value
	 * @return void
	 */
	public void setSelectedModelVersionText(String modelVersionText) {
		write(PersistentSettingsEnums.MODEL_VERSION_TEXT_SELECTED,
				modelVersionText);
	}

	public void setSelectedPatternsFiles(String[] patternsFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.PATTERNS_FILES_SELECTED, SEPARATOR,
				patternsFiles);
	}

	/**
	 * Writes the selected printed DPI value to the persistent data store
	 * 
	 * @param dpi
	 *            String to be used for the selected printed DPI value
	 * @return void
	 */
	public void setSelectedPrintedDpi(String dpi) {
		write(PersistentSettingsEnums.PRINTED_DPI_SELECTED, dpi);
	}

	public void setSelectedS12XmlFiles(String[] s12XmlFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.S12_XML_FILES_SELECTED, SEPARATOR,
				s12XmlFiles);
	}

	public void setSelectedShapesFiles(String[] shapesFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.SHAPES_FILES_SELECTED, SEPARATOR,
				shapesFiles);
	}

	public void setSelectedSystemInfoFiles(String[] systemInfoFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.SYSTEM_INFO_FILES_SELECTED, SEPARATOR,
				systemInfoFiles);
	}

	/**
	 * Writes the shapes file location to the persistent data store
	 * 
	 * @param shapesFile
	 *            Location of the shapes file
	 * @return void
	 */
	public void setShapesFiles(String[] shapesFile) {
		concatenateAndWriteString(PersistentSettingsEnums.SHAPES_FILES,
				SEPARATOR, shapesFile);
	}

	public void setSuppressMouseOverEffect(Boolean makeModelStatic) {
		write(PersistentSettingsEnums.SUPPRESS_MOUSE_OVER_EFFECT,
				makeModelStatic.toString());
	}

	/**
	 * Writes the list of system definition files to the persistent data store
	 * 
	 * @param sysDefFiles
	 *            List of the system definition file locations
	 * @return void
	 */
	public void setSystemDefinitionFiles(String[] sysDefFiles) {
		concatenateAndWriteString(
				PersistentSettingsEnums.SYSTEM_DEFINITION_FILES, SEPARATOR,
				sysDefFiles);
	}

	/**
	 * Writes the system info location to the persistent data store
	 * 
	 * @param systemInfoFile
	 *            Location of the system information file
	 * @return void
	 */
	public void setSystemInfoFiles(String[] systemInfoFile) {
		concatenateAndWriteString(PersistentSettingsEnums.SYSTEM_INFO_FILES,
				SEPARATOR, systemInfoFile);
	}

	/**
	 * Writes the system name to the persistent data store
	 * 
	 * @param systemName
	 *            String to be used for the system name
	 * @return void
	 */
	public void setSystemName(String systemName) {
		write(PersistentSettingsEnums.SYSTEM_NAME, systemName);
	}

	/**
	 * Writes the system version to the persistent data store
	 * 
	 * @param systemVersion
	 *            String to be used for the system version
	 * @return void
	 */
	public void setSystemVersion(String systemVersion) {
		write(PersistentSettingsEnums.SYSTEM_VERSION, systemVersion);
	}

	/**
	 * Writes the warning level to the persistent data store
	 * 
	 * @param warningLevel
	 *            An int between 1 and 3 indicating the level of warnings to be
	 *            produces
	 * @return void
	 */
	public void setWarningLevel(String warningLevel) {
		write(PersistentSettingsEnums.WARNING_LEVELS, warningLevel);
	}

	public abstract void write(PersistentSettingsEnums key, String value);
}
