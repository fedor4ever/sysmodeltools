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

import java.util.List;

/**
 * PersistentSettings interface class. This is used to define the setters and
 * getters for the persistent plug-in and project level data stores
 * 
 * @return void
 */
public interface PersistentSettings {

	/**
	 * Gets the list of advanced options from the persistent data store
	 * 
	 * @return String
	 */
	public String[] getAdvancedOptions();

	/**
	 * Gets the border shapes file location from the persistent data store
	 * 
	 * @return String
	 */
	String[] getBorderShapesFiles();

	/**
	 * Gets the border styles file location from the persistent data store
	 * 
	 * @return String
	 */
	String[] getBorderStylesFiles();

	/**
	 * Gets the colours file location from the persistent data store
	 * 
	 * @return String
	 */
	String[] getColoursFiles();

	/**
	 * Gets the copyright text from the persistent data store
	 * 
	 * @return String
	 */
	String getCopyrightText();

	/**
	 * Gets the System Model Managers default border shapes files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultBorderShapesFiles();

	/**
	 * Gets the System Model Managers default border styles files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultBorderStylesFiles();

	/**
	 * Gets the System Model Managers default colours files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultColoursFiles();

	/**
	 * Gets the System Model Managers default dependencies files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultDependenciesFiles();

	/**
	 * Gets the System Model Managers default distribution text from the plug-in
	 * default persistent data store
	 * 
	 * @return String
	 */
	public String getDefaultDistributionText();

	/**
	 * Gets the System Model Managers default levels files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultLevelsFiles();

	/**
	 * Gets the System Model Managers default localisation files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultLocalisationFiles();

	/**
	 * Gets the System Model Managers default model version text from the
	 * plug-in default persistent data store
	 * 
	 * @return String
	 */
	public String getDefaultModelVersionText();

	/**
	 * Gets the System Model Managers default patterns files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultPatternsFiles();

	/**
	 * Gets the System Model Managers default printed DPI from the plug-in
	 * default persistent data store
	 * 
	 * @return String
	 */
	public String getDefaultPrintedDpi();

	/**
	 * Gets the System Model Managers default S12 XML files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultS12XmlFiles();

	/**
	 * Gets the System Model Managers default shapes files location from the
	 * plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultShapesFiles();

	/**
	 * Gets the System Model Managers default system info files location from
	 * the plug-in default persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getDefaultSystemInfoFiles();

	/**
	 * Gets the dependencies file location from the persistent data store
	 * 
	 * @return String[]
	 */
	String[] getDependenciesFiles();

	/**
	 * Gets the distribution text values from the persistent data store
	 * 
	 * @return String[]
	 */
	String[] getDistributionTexts();

	/**
	 * Gets the list of filter items from the persistent data store
	 * 
	 * @return String
	 */
	String[] getFilterItems();

	/**
	 * Gets the fix item size option from the persistent data store
	 * 
	 * @return a Boolean value indicating whether or not the fix item size
	 *         option is checked
	 */
	public Boolean getFixItemSize();

	/**
	 * Gets the highlight core OS option from the persistent data store
	 * 
	 * @return String
	 */
	Boolean getHighlightCoreOS();

	/**
	 * Gets the list of ignore items from the persistent data store
	 * 
	 * @return String
	 */
	List<String[]> getIgnoreItems();

	/**
	 * Gets the level of detail from the persistent data store
	 * 
	 * @return String
	 */
	String getLevelOfDetail();

	/**
	 * Gets the levels file location from the persistent data store
	 * 
	 * @return String
	 */
	String[] getLevelsFiles();

	/**
	 * Gets the localisation file location from the persistent data store
	 * 
	 * @return String
	 */
	String[] getLocalisationFiles();

	/**
	 * Gets the model name from the persistent data store
	 * 
	 * @return String
	 */
	String getModelName();

	/**
	 * Gets the model version from the persistent data store
	 * 
	 * @return String
	 */
	String getModelVersion();

	/**
	 * Gets the model version text values from the persistent data store
	 * 
	 * @return String[]
	 */
	String[] getModelVersionTexts();

	/**
	 * Gets the patterns file locations from the persistent data store
	 * 
	 * @return String[]
	 */
	String[] getPatternsFiles();

	/**
	 * Gets the printed DPI values from the persistent data store
	 * 
	 * @return String[]
	 */
	public String[] getPrintedDpis();

	public String[] getS12XmlFiles();

	/**
	 * Gets the user selected border shapes file location from the persistent
	 * data store
	 * 
	 * @return String
	 */
	String[] getSelectedBorderShapesFiles();

	/**
	 * Gets the user selected border styles file location from the persistent
	 * data store
	 * 
	 * @return String
	 */
	String[] getSelectedBorderStylesFiles();

	/**
	 * Gets the user selected colours file location from the persistent data
	 * store
	 * 
	 * @return String
	 */
	String[] getSelectedColoursFiles();

	/**
	 * Gets the user selected dependencies file location from the persistent
	 * data store
	 * 
	 * @return String
	 */
	String[] getSelectedDependenciesFiles();

	/**
	 * Gets the selected distribution text value from the persistent data store
	 * 
	 * @return String
	 */
	public String getSelectedDistributionText();

	/**
	 * Gets the user selected levels file location from the persistent data
	 * store
	 * 
	 * @return String
	 */
	String[] getSelectedLevelsFiles();

	/**
	 * Gets the user selected localisation file location from the persistent
	 * data store
	 * 
	 * @return String
	 */
	String[] getSelectedLocalisationFiles();

	/**
	 * Gets the selected model version text value from the persistent data store
	 * 
	 * @return String
	 */
	public String getSelectedModelVersionText();

	/**
	 * Gets the user selected patterns file location from the persistent data
	 * store
	 * 
	 * @return String
	 */
	String[] getSelectedPatternsFiles();

	/**
	 * Gets the selected printed DPI value from the persistent data store
	 * 
	 * @return String
	 */
	public String getSelectedPrintedDpi();

	public String[] getSelectedS12XmlFiles();

	/**
	 * Gets the user selected shapes file location from the persistent data
	 * store
	 * 
	 * @return String
	 */
	String[] getSelectedShapesFiles();

	/**
	 * Gets the user selected system info file location from the persistent data
	 * store
	 * 
	 * @return String
	 */
	String[] getSelectedSystemInfoFiles();

	/**
	 * Gets the shapes file location from the persistent data store
	 * 
	 * @return String
	 */
	String[] getShapesFiles();

	/**
	 * Gets a boolean value indicating if the Suppress Mouseover Effect button
	 * has been selected from the persistent data store
	 * 
	 * @return Boolean
	 */
	Boolean getSuppressMouseOverEffect();

	/**
	 * Gets the list of system definition files from the persistent data store
	 * 
	 * @return String[]
	 */
	String[] getSystemDefinitionFiles();

	/**
	 * Gets the system info file location from the persistent data store
	 * 
	 * @return String
	 */
	String[] getSystemInfoFiles();

	/**
	 * Gets the system name from the persistent data store
	 * 
	 * @return String
	 */
	String getSystemName();

	/**
	 * Gets the system version from the persistent data store
	 * 
	 * @return String
	 */
	String getSystemVersion();

	/**
	 * Gets the warning level to use from the persistent data store
	 * 
	 * @return String
	 */
	String getWarningLevel();

	/**
	 * Writes the list of advanced options to the persistent data store
	 * 
	 * @param advancedOptionsList
	 *            List of advanced options
	 * @return void
	 */
	public void setAdvancedOptions(String[] options);

	/**
	 * Writes the border shapes file location to the persistent data store
	 * 
	 * @param borderShapesFile
	 *            Location of the border shapes file
	 * @return void
	 */
	void setBorderShapesFiles(String[] borderShapesFiles);

	/**
	 * Writes the border styles file location to the persistent data store
	 * 
	 * @param borderStylesFile
	 *            Location of the border styles file
	 * @return void
	 */
	void setBorderStylesFiles(String[] borderStylesFiles);

	/**
	 * Writes the colours file location to the persistent data store
	 * 
	 * @param coloursFile
	 *            Location of the colours file
	 * @return void
	 */
	void setColoursFiles(String[] coloursFiles);

	/**
	 * Writes the copyright text to the persistent data store
	 * 
	 * @param copyrightText
	 *            String to be used for the copyright text
	 * @return void
	 */
	void setCopyrightText(String copyrightText);

	/**
	 * Writes the dependencies file location to the persistent data store
	 * 
	 * @param dependenciesFile
	 *            Location of the dependencies file
	 * @return void
	 */
	void setDependenciesFiles(String[] dependenciesFiles);

	/**
	 * Writes the distribution text values to the persistent data store
	 * 
	 * @param distributionTexts
	 *            String array to be used for the distribution text values
	 * @return void
	 */
	void setDistributionTexts(String[] distributionTexts);

	/**
	 * Writes the list of filter items to the persistent data store
	 * 
	 * @param filterItemsList
	 *            List of filter names
	 * @return void
	 */
	void setFilterItems(String[] filterItemsList);

	/**
	 * Writes the fix item size option to the persistent data store
	 * 
	 * @param fixItemSize
	 *            Boolean to represent the fix item size option
	 * @return void
	 */
	public void setFixItemSize(Boolean fixItemSize);

	/**
	 * Writes the highlight core OS option to the persistent data store
	 * 
	 * @param highlightCoreOS
	 *            Boolean to represent the highlight core OS option
	 * @return void
	 */
	void setHighlightCoreOS(Boolean highlightCoreOS);

	/**
	 * Writes the list of ignore items to the persistent data store
	 * 
	 * @param ignoreItemsList
	 *            List of ignore items
	 * @return void
	 */
	void setIgnoreItems(List<String[]> ignoreItemsList);

	/**
	 * Writes the level of detail to the persistent data store
	 * 
	 * @param levelOfDetail
	 *            String to be used for the level of detail
	 * @return void
	 */
	void setLevelOfDetail(String levelOfDetail);

	/**
	 * Writes the levels file location to the persistent data store
	 * 
	 * @param levelsFile
	 *            Location of the levels file
	 * @return void
	 */
	void setLevelsFiles(String[] levelsFiles);

	/**
	 * Writes the localisation file location to the persistent data store
	 * 
	 * @param localisationFile
	 *            Location of the localisation file
	 * @return void
	 */
	void setLocalisationFiles(String[] localisationFiles);

	/**
	 * Writes the model name to the persistent data store
	 * 
	 * @param modelName
	 *            String to be used for the model name
	 * @return void
	 */
	void setModelName(String modelName);

	/**
	 * Writes the model version to the persistent data store
	 * 
	 * @param modelVersion
	 *            String to be used for the model version
	 * @return void
	 */
	void setModelVersion(String modelVersion);

	/**
	 * Writes the model version text values to the persistent data store
	 * 
	 * @param modelVersionTexts
	 *            String array to be used for the model version text values
	 * @return void
	 */
	void setModelVersionTexts(String[] modelVersionTexts);

	/**
	 * Writes the patterns file location to the persistent data store
	 * 
	 * @param patternsFile
	 *            Location of the patterns file
	 * @return void
	 */
	void setPatternsFiles(String[] patternsFiles);

	/**
	 * Writes the printed DPI values to the persistent data store
	 * 
	 * @param dpi
	 *            String array to be used for the printed DPI values
	 * @return void
	 */
	public void setPrintedDpis(String[] dpis);

	public void setS12XmlFiles(String[] s12XmlFiles);

	/**
	 * Writes the user selected border shapes file location to the persistent
	 * data store
	 * 
	 * @param borderShapesFile
	 *            Location of the default border shapes file
	 * @return void
	 */
	void setSelectedBorderShapesFiles(String[] borderShapesFiles);

	/**
	 * Writes the user selected border styles file location to the persistent
	 * data store
	 * 
	 * @param borderStylesFile
	 *            Location of the default border styles file
	 * @return void
	 */
	void setSelectedBorderStylesFiles(String[] borderStylesFile);

	/**
	 * Writes the user selected colours file location to the persistent data
	 * store
	 * 
	 * @param coloursFile
	 *            Location of the default colours file
	 * @return void
	 */
	void setSelectedColoursFiles(String[] coloursFile);

	/**
	 * Writes the user selected dependencies file location to the persistent
	 * data store
	 * 
	 * @param dependenciesFile
	 *            Location of the default dependencies file
	 * @return void
	 */
	void setSelectedDependenciesFiles(String[] dependenciesFile);

	/**
	 * Writes the selected distribution text value to the persistent data store
	 * 
	 * @param distributionText
	 *            String to be used for the selected distribution text value
	 * @return void
	 */
	public void setSelectedDistributionText(String distributionText);

	/**
	 * Writes the user selected levels file location to the persistent data
	 * store
	 * 
	 * @param levelsFile
	 *            Location of the default levels file
	 * @return void
	 */
	void setSelectedLevelsFiles(String[] levelsFile);

	/**
	 * Writes the user selected localisation file location to the persistent
	 * data store
	 * 
	 * @param localisationFile
	 *            Location of the default localisation file
	 * @return void
	 */
	void setSelectedLocalisationFiles(String[] localisationFile);

	/**
	 * Writes the selected model version text value to the persistent data store
	 * 
	 * @param modelVersionText
	 *            String to be used for the selected model version text value
	 * @return void
	 */
	public void setSelectedModelVersionText(String modelVersionText);

	/**
	 * Writes the user selected patterns file location to the persistent data
	 * store
	 * 
	 * @param patternsFile
	 *            Location of the default patterns file
	 * @return void
	 */
	void setSelectedPatternsFiles(String[] patternsFiles);

	/**
	 * Writes the selected printed DPI value to the persistent data store
	 * 
	 * @param dpi
	 *            String to be used for the selected printed DPI value
	 * @return void
	 */
	public void setSelectedPrintedDpi(String dpi);

	public void setSelectedS12XmlFiles(String[] s12XmlFiles);

	/**
	 * Writes the user selected shapes file location to the persistent data
	 * store
	 * 
	 * @param shapesFile
	 *            Location of the default shapes file
	 * @return void
	 */
	void setSelectedShapesFiles(String[] shapesFile);

	/**
	 * Writes the user selected system info location to the persistent data
	 * store
	 * 
	 * @param systemInfoFile
	 *            Location of the default system information file
	 * @return void
	 */
	void setSelectedSystemInfoFiles(String[] systemInfoFile);

	/**
	 * Writes the shapes file location to the persistent data store
	 * 
	 * @param shapesFile
	 *            Location of the shapes file
	 * @return void
	 */
	void setShapesFiles(String[] shapesFiles);

	/**
	 * Writes the value for the Suppress Mouseover Effect option
	 * 
	 * @param suppressMouseOverEffect
	 *            Boolean value indicating if the Suppress Mouseover Effect is
	 *            it be used when generating the diagram
	 * @return void
	 */
	public void setSuppressMouseOverEffect(Boolean suppressMouseOverEffect);

	/**
	 * Writes the list of system definition files to the persistent data store
	 * 
	 * @param sysDefFiles
	 *            List of the system definition file locations
	 * @return void
	 */
	void setSystemDefinitionFiles(String[] sysDefFiles);

	/**
	 * Writes the system info location to the persistent data store
	 * 
	 * @param systemInfoFile
	 *            Location of the system information file
	 * @return void
	 */
	void setSystemInfoFiles(String[] systemInfoFiles);

	/**
	 * Writes the system name to the persistent data store
	 * 
	 * @param systemName
	 *            String to be used for the system name
	 * @return void
	 */
	void setSystemName(String systemName);

	/**
	 * Writes the system version to the persistent data store
	 * 
	 * @param systemVersion
	 *            String to be used for the system version
	 * @return void
	 */
	void setSystemVersion(String systemVersion);

	/**
	 * Writes the warning level to the persistent data store
	 * 
	 * @param warningLevel
	 *            An int between 1 and 3 indicating the level of warnings to be
	 *            produces
	 * @return void
	 */
	void setWarningLevel(String warningLevel);
}
