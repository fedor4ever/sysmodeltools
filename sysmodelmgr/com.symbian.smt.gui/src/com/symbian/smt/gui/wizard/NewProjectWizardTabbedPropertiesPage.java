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
// ${file_name}
// 
//

package com.symbian.smt.gui.wizard;

import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.smtwidgets.AdvancedOptionsWidget;
import com.symbian.smt.gui.smtwidgets.BuildControlWidget;
import com.symbian.smt.gui.smtwidgets.FilterWidget;
import com.symbian.smt.gui.smtwidgets.IgnoreWidget;
import com.symbian.smt.gui.smtwidgets.ModelControlWidget;
import com.symbian.smt.gui.smtwidgets.ModelLabelsWidget;
import com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent;
import com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget;

public class NewProjectWizardTabbedPropertiesPage extends WizardPage implements
		ValidModelDefinedListener {

	// Custom composite SWT widgets
	private ModelLabelsWidget modelLabelsWidget;
	private ResourcesWidget resourcesWidget;
	private ModelControlWidget modelControlWidget;
	private FilterWidget filterWidget;
	private IgnoreWidget ignoreWidget;
	private BuildControlWidget buildWidget;
	private AdvancedOptionsWidget advancedOptionsWidget;
	private TabFolder tabFolder;

	/**
	 * Creates a wizard page for entering optional System Model information
	 * 
	 * @return void
	 */
	protected NewProjectWizardTabbedPropertiesPage(ISelection selection) {
		super("wizardPage");
		setTitle("System Model Manager Wizard");
		setDescription("Enter any optional configuration information for the System Model Diagram");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		container.setLayout(new FillLayout(SWT.VERTICAL));

		setControl(container);

		tabFolder = new TabFolder(container, SWT.NONE);

		final TabItem labelsTabItem = new TabItem(tabFolder, SWT.NONE);
		labelsTabItem.setText("Labels");
		modelLabelsWidget = new ModelLabelsWidget(tabFolder, SWT.NONE);
		labelsTabItem.setControl(modelLabelsWidget);

		final TabItem resourcesTabItem = new TabItem(tabFolder, SWT.NONE);
		resourcesTabItem.setText("Resources");
		resourcesWidget = new ResourcesWidget(tabFolder, SWT.NONE);
		resourcesTabItem.setControl(resourcesWidget);

		final TabItem modelControlTabItem = new TabItem(tabFolder, SWT.NONE);
		modelControlTabItem.setText("Model Control");
		modelControlWidget = new ModelControlWidget(tabFolder, SWT.NONE);
		modelControlTabItem.setControl(modelControlWidget);

		final TabItem filtersTabItem = new TabItem(tabFolder, SWT.NONE);
		filtersTabItem.setText("Filters");
		filterWidget = new FilterWidget(tabFolder, SWT.NONE);
		filtersTabItem.setControl(filterWidget);

		final TabItem ignoreListTabItem = new TabItem(tabFolder, SWT.NONE);
		ignoreListTabItem.setText("Ignore List");
		ignoreWidget = new IgnoreWidget(tabFolder, SWT.NONE);
		ignoreListTabItem.setControl(ignoreWidget);

		final TabItem buildControlTabItem = new TabItem(tabFolder, SWT.NONE);
		buildControlTabItem.setText("Build Options");
		buildWidget = new BuildControlWidget(tabFolder, SWT.NONE, false);
		buildControlTabItem.setControl(buildWidget);

		final TabItem advancedOptionsTabItem = new TabItem(tabFolder, SWT.NONE);
		advancedOptionsTabItem.setText("Advanced Options");
		advancedOptionsWidget = new AdvancedOptionsWidget(tabFolder, SWT.NONE);
		advancedOptionsTabItem.setControl(advancedOptionsWidget);
	}

	@Override
	public void dispose() {
		if (buildWidget != null) {
			buildWidget.removeModelListener(this);
		}
		
		if (modelControlWidget != null) {
			modelControlWidget.removeModelListener(this);
		}
		
		super.dispose();
	}

	/**
	 * Returns the advanced options or an empty string array if no advanced
	 * options have been specified
	 * 
	 * @return String the advanced options
	 */
	public String[] getAdvancedOptions() {
		return advancedOptionsWidget.getAdvancedOptions();
	}

	/**
	 * Returns the shapes files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getBorderShapesFiles() {
		return resourcesWidget.getBorderShapesFiles();
	}

	// Build Options

	/**
	 * Returns the shapes files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getBorderStylesFiles() {
		return resourcesWidget.getBorderStylesFiles();
	}

	// Model Labels Setters & Getters

	/**
	 * Returns the shapes files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getColoursFiles() {
		return resourcesWidget.getColoursFiles();
	}

	/**
	 * Returns the copyright text from the Model Labels widget
	 * 
	 * @return String
	 */
	public String getCopyrightText() {
		return modelLabelsWidget.getCopyrightText();
	}

	/**
	 * Returns the border shapes files or an empty string if no border shapes
	 * file has been specified
	 * 
	 * @return String
	 */
	public String[] getDefaultBorderShapesFiles() {
		return resourcesWidget.getSelectedBorderShapesFiles();
	}

	/**
	 * Returns the border styles files or an empty string if no border styles
	 * file has been specified
	 * 
	 * @return String[]
	 */
	public String[] getDefaultBorderStylesFiles() {
		return resourcesWidget.getSelectedBorderStylesFiles();
	}

	/**
	 * Returns the colours files or an empty string if no colours file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getDefaultColoursFiles() {
		return resourcesWidget.getSelectedColoursFiles();
	}

	/**
	 * Returns the dependencies files or an empty string if no dependencies file
	 * has been specified
	 * 
	 * @return String[]
	 */
	public String[] getDefaultDependenciesFiles() {
		return resourcesWidget.getSelectedDependenciesFiles();
	}

	/**
	 * Returns the default distribution text value from the Model Labels widget
	 * 
	 * @return String
	 */
	public String getDefaultDistributionText() {
		return modelLabelsWidget.getSelectedDistributionText();
	}

	/**
	 * Returns the levels files or an empty string if no levels file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getDefaultLevelsFiles() {
		return resourcesWidget.getSelectedLevelsFiles();
	}

	/**
	 * Returns the localisation files or an empty string if no localisation file
	 * has been specified
	 * 
	 * @return String[]
	 */
	public String[] getDefaultLocalisationFiles() {
		return resourcesWidget.getSelectedLocalisationFiles();
	}

	/**
	 * Returns the default model version text value from the Model Label widget
	 * 
	 * @return String
	 */
	public String getDefaultModelVersionText() {
		return modelLabelsWidget.getSelectedModelVersionText();
	}

	/**
	 * Returns the patterns files or an empty string if no patterns file has
	 * been specified
	 * 
	 * @return String[]
	 */
	public String[] getDefaultPatternsFiles() {
		return resourcesWidget.getSelectedPatternsFiles();
	}

	/**
	 * Returns the default printed DPI value from the Model Control widget
	 * 
	 * @return String
	 */
	public String getDefaultPrintedDpi() {
		return modelControlWidget.getSelectedPrintedDpi();
	}

	/**
	 * Returns the S12 XML files or an empty string if no system info file has
	 * been specified
	 * 
	 * @return String[]
	 */
	public String[] getDefaultS12XmlFiles() {
		return resourcesWidget.getSelectedS12XmlFiles();
	}

	/**
	 * Returns the shapes files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getDefaultShapesFiles() {
		return resourcesWidget.getSelectedShapesFiles();
	}

	/**
	 * Returns the system info files or an empty string if no system info file
	 * has been specified
	 * 
	 * @return String[]
	 */
	public String[] getDefaultSystemInfoFiles() {
		return resourcesWidget.getSelectedSystemInfoFiles();
	}

	/**
	 * Returns the shapes files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getDependenciesFiles() {
		return resourcesWidget.getDependenciesFiles();
	}

	// Model Control Setters & Getters

	/**
	 * Returns the distribution text values from the Model Labels widget
	 * 
	 * @return String[]
	 */
	public String[] getDistributionTexts() {
		return modelLabelsWidget.getDistributionTexts();
	}

	/**
	 * Returns the filter items from the Filter Widget
	 * 
	 * @return String[]
	 */
	public String[] getFilterItems() {
		return filterWidget.getFilterItems();
	}

	/**
	 * Returns a boolean value from the Model Control widget indicating if the
	 * Fix Item Size button has been selected
	 * 
	 * @return a Boolean value specifying whether or not the item size is fixed.
	 */
	public Boolean getFixItemSize() {
		return modelControlWidget.getFixItemSize();
	}

	/**
	 * Returns a boolean value from the Model Control widget indicating if the
	 * Highlight Core OS button has been selected
	 * 
	 * @return Boolean
	 */
	public Boolean getHighlightCoreOS() {
		return modelControlWidget.getHighlightCoreOS();
	}

	/**
	 * Returns a list of the ignore items from the Ignore Items Widget
	 * 
	 * @return List<String>
	 */
	public List<String[]> getIgnoreItems() {
		return ignoreWidget.getIgnoreItems();
	}

	/**
	 * Returns the level of detail from the Model Control widget
	 * 
	 * @return String
	 */
	public String getLevelOfDetail() {
		return modelControlWidget.getLevelOfDetail();
	}

	/**
	 * Returns the levels files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getLevelsFiles() {
		return resourcesWidget.getLevelsFiles();
	}

	/**
	 * Returns the shapes files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getLocalisationFiles() {
		return resourcesWidget.getLocalisationFiles();
	}

	/**
	 * Returns the model name from the Model Labels widget
	 * 
	 * @return String
	 */
	public String getModelName() {
		return modelLabelsWidget.getModelName();
	}

	/**
	 * Returns the text for the model version from the Model Labels widget
	 * 
	 * @return String
	 */
	public String getModelVersion() {
		return modelLabelsWidget.getModelVersion();
	}

	/**
	 * Returns the model version text values from the Model Labels widget
	 * 
	 * @return String[]
	 */
	public String[] getModelVersionTexts() {
		return modelLabelsWidget.getModelVersionTexts();
	}

	public String getOutputFilename() {
		return buildWidget.getOutputFilename();
	}

	/**
	 * Returns the shapes files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getPatternsFiles() {
		return resourcesWidget.getPatternsFiles();
	}

	/**
	 * Returns the printed DPI values from the Model Control widget
	 * 
	 * @return String
	 */
	public String[] getPrintedDpis() {
		return modelControlWidget.getPrintedDpis();
	}

	/**
	 * Returns the S12 XML files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getS12XmlFiles() {
		return resourcesWidget.getS12XmlFiles();
	}

	/**
	 * Returns the selected distribution text value from the Model Labels widget
	 * 
	 * @return String
	 */
	public String getSelectedDistributionText() {
		return modelLabelsWidget.getSelectedDistributionText();
	}

	/**
	 * Returns the selected model version text value from the Model Label widget
	 * 
	 * @return String
	 */
	public String getSelectedModelVersionText() {
		return modelLabelsWidget.getSelectedModelVersionText();
	}

	/**
	 * Returns the selected printed DPI value from the Model Control widget
	 * 
	 * @return String
	 */
	public String getSelectedPrintedDpi() {
		return modelControlWidget.getSelectedPrintedDpi();
	}

	// Resources Setters & Getters

	/**
	 * Returns the shapes files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getShapesFiles() {
		return resourcesWidget.getShapesFiles();
	}

	/**
	 * Returns a boolean value indicating if the Suppress Mouseover Effects
	 * button has been selected
	 * 
	 * @return Boolean
	 */
	public Boolean getSuppressMouseOverEffect() {
		return modelControlWidget.getSuppressMouseOverEffect();
	}

	/**
	 * Returns the shapes files or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String[]
	 */
	public String[] getSystemInfoFiles() {
		return resourcesWidget.getSystemInfoFiles();
	}

	/**
	 * Returns the system name from the Model Labels widget
	 * 
	 * @return String
	 */
	public String getSystemName() {
		return modelLabelsWidget.getSystemName();
	}

	/**
	 * Returns the text for the system version from the Model Labels widget
	 * 
	 * @return String
	 */
	public String getSystemVersion() {
		return modelLabelsWidget.getSystemVersion();
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 *
	 * @return the tabFolder
	 */
	public TabFolder getTabFolder() {
		return tabFolder;
	}

	public void initialize(PersistentDataStore store) {
		filterWidget.setFilterItems(store.getFilterHasItems());

		ignoreWidget.setIgnoreItems(store.getIgnoreItems());

		// Model Control
		modelControlWidget.initialisePrintedDpi(store);
		modelControlWidget.setLevelOfDetail(store.getLevelOfDetail());
		modelControlWidget.setHighlightCoreOS(store.getHighlightCoreOS());
		modelControlWidget.setSuppressMouseOverEffect(store
				.getSuppressMouseOverEffect());
		modelControlWidget.setFixItemSize(store.getFixItemSize());

		// Model Labels
		modelLabelsWidget.initialiseModelVersionText(store);
		modelLabelsWidget.initialiseDistributionText(store);
		modelLabelsWidget.setModelVersion(store.getModelVersion());
		modelLabelsWidget.setSystemVersion(store.getSystemVersion());
		modelLabelsWidget.setCopyrightText(store.getCopyrightText());
		modelLabelsWidget.setModelName(store.getModelName());
		modelLabelsWidget.setSystemName(store.getSystemName());

		// All files
		resourcesWidget.setBorderShapesFiles(store.getBorderShapesFiles());
		resourcesWidget.setBorderStylesFiles(store.getBorderStylesFiles());
		resourcesWidget.setColoursFiles(store.getColoursFiles());
		resourcesWidget.setDependenciesFiles(store.getDependenciesFiles());
		resourcesWidget.setLevelsFiles(store.getLevelsFiles());
		resourcesWidget.setLocalisationFiles(store.getLocalisationFiles());
		resourcesWidget.setPatternsFiles(store.getPatternsFiles());
		resourcesWidget.setShapesFiles(store.getShapesFiles());
		resourcesWidget.setSystemInfoFiles(store.getSystemInfoFiles());
		resourcesWidget.setS12XmlFiles(store.getS12XmlFiles());

		// Default files
		resourcesWidget.setSelectedBorderShapesFiles(store
				.getSelectedBorderShapesFiles());
		resourcesWidget.setSelectedBorderStylesFiles(store
				.getSelectedBorderStylesFiles());
		resourcesWidget
				.setSelectedColoursFiles(store.getSelectedColoursFiles());
		resourcesWidget.setSelectedDependenciesFiles(store
				.getSelectedDependenciesFiles());
		resourcesWidget.setSelectedLevelsFiles(store.getSelectedLevelsFiles());
		resourcesWidget.setSelectedLocalisationFiles(store
				.getSelectedLocalisationFiles());
		resourcesWidget.setSelectedPatternsFiles(store
				.getSelectedPatternsFiles());
		resourcesWidget.setSelectedShapesFiles(store.getSelectedShapesFiles());
		resourcesWidget.setSelectedSystemInfoFiles(store
				.getSelectedSystemInfoFiles());
		resourcesWidget.setSelectedS12XmlFiles(store.getSelectedS12XmlFiles());

		// Advanced Options
		advancedOptionsWidget.setAdvancedOptions(store.getAdvancedOptions());

		buildWidget.setOutputFilename(store.getOutputFilename());

		// Adding this class as listener of model valid events
		buildWidget.addModelListener(this);
		modelControlWidget.addModelListener(this);
	}

	public void setAdvancedOptions(String[] options) {
		advancedOptionsWidget.setAdvancedOptions(options);
	}

	public void setBorderShapesFiles(String[] filenames) {
		resourcesWidget.setBorderShapesFiles(filenames);
	}

	public void setBorderStylesFiles(String[] filenames) {
		resourcesWidget.setBorderStylesFiles(filenames);
	}

	public void setColoursFiles(String[] filenames) {
		resourcesWidget.setColoursFiles(filenames);
	}

	/**
	 * Sets the text for the copyright text in the Model Labels widget
	 * 
	 * @param copyrightText
	 *            String to be used for the copyright text
	 * @return void
	 */
	public void setCopyrightText(String copyrightText) {
		modelLabelsWidget.setCopyrightText(copyrightText);
	}

	public void setDefaultBorderShapesFiles(String[] filenames) {
		resourcesWidget.setSelectedBorderShapesFiles(filenames);
	}

	public void setDefaultBorderStylesFiles(String[] filenames) {
		resourcesWidget.setSelectedBorderStylesFiles(filenames);
	}

	public void setDefaultColoursFiles(String[] filenames) {
		resourcesWidget.setSelectedColoursFiles(filenames);
	}

	public void setDefaultDependenciesFiles(String[] filenames) {
		resourcesWidget.setSelectedDependenciesFiles(filenames);
	}

	/**
	 * Sets the default distribution text value in the Model Labels widget
	 * 
	 * @param distributionText
	 *            The default distribution text value to set.
	 * @return void
	 */
	public void setDefaultDistributionText(String distributionText) {
		modelLabelsWidget.setSelectedDistributionText(distributionText);
	}

	public void setDefaultLevelsFiles(String[] filenames) {
		resourcesWidget.setSelectedLevelsFiles(filenames);
	}

	public void setDefaultLocalisationFiles(String[] filenames) {
		resourcesWidget.setSelectedLocalisationFiles(filenames);
	}

	/**
	 * Sets the default model version text value in the Model Label widget
	 * 
	 * @param modelVersionText
	 *            The default model version text value to set.
	 * @return void
	 */
	public void setDefaultModelVersionText(String modelVersionText) {
		modelLabelsWidget.setSelectedModelVersionText(modelVersionText);
	}

	public void setDefaultPatternsFiles(String[] filenames) {
		resourcesWidget.setSelectedPatternsFiles(filenames);
	}

	/**
	 * Sets the default printed DPI value in the Model Control widget
	 * 
	 * @param dpi
	 *            The default printed DPI value to set.
	 * @return void
	 */
	public void setDefaultPrintedDpi(String dpi) {
		modelControlWidget.setSelectedPrintedDpi(dpi);
	}

	public void setDefaultS12XmlFiles(String[] filenames) {
		resourcesWidget.setSelectedS12XmlFiles(filenames);
	}

	public void setDefaultShapesFiles(String[] filenames) {
		resourcesWidget.setSelectedShapesFiles(filenames);
	}

	public void setDefaultSystemInfoFiles(String[] filenames) {
		resourcesWidget.setSelectedSystemInfoFiles(filenames);
	}

	public void setDependenciesFiles(String[] filenames) {
		resourcesWidget.setDependenciesFiles(filenames);
	}

	/**
	 * Sets the distribution text values in the Model Labels widget
	 * 
	 * @param distributionTexts
	 *            The distribution text values to set.
	 * @return void
	 */
	public void setDistributionTexts(String[] distributionTexts) {
		modelLabelsWidget.setDistributionTexts(distributionTexts);
	}

	/**
	 * Sets the filter items in the the Filter Widget
	 * 
	 * @param filterItems
	 *            A list containing filters.
	 * @return void
	 */
	public void setFilterItems(String[] filterItems) {
		filterWidget.setFilterItems(filterItems);
	}

	/**
	 * Sets the value for the Fix Item Size button in the Model Control widget
	 * 
	 * @param value
	 *            Boolean value indicating if the item size is fixed or not
	 * @return void
	 */
	public void setFixItemSize(Boolean value) {
		modelControlWidget.setFixItemSize(value);
	}

	/**
	 * Sets the value for the Highlight Core OS button in the Model Control
	 * widget
	 * 
	 * @param HighlightCoreOS
	 *            Boolean value indicating if the Core OS section is to be
	 *            highlighted
	 * @return void
	 */
	public void setHighlightCoreOS(Boolean value) {
		modelControlWidget.setHighlightCoreOS(value);
	}

	/**
	 * Sets the ignore items in the Ignore Items Widget
	 * 
	 * @param ignoreItems
	 *            an List containing 2 element lists. The first element contains
	 *            the item type and the second element contains the item text.
	 * @return void
	 */
	public void setIgnoreItems(List<String[]> ignoreItems) {
		ignoreWidget.setIgnoreItems(ignoreItems);
	}

	/**
	 * Sets the level of detail in the Model Control widget
	 * 
	 * @param level
	 *            The level of detail to set.
	 * @return void
	 */
	public void setLevelOfDetail(String level) {
		modelControlWidget.setLevelOfDetail(level);
	}

	public void setLevelsFiles(String[] filenames) {
		resourcesWidget.setLevelsFiles(filenames);
	}

	public void setLocalisationFiles(String[] filenames) {
		resourcesWidget.setLocalisationFiles(filenames);
	}

	/**
	 * Sets the text for the model name in the Model Labels widget
	 * 
	 * @param modelName
	 *            String to be used for the model name
	 * @return void
	 */
	public void setModelName(String modelName) {
		modelLabelsWidget.setModelName(modelName);
	}

	/**
	 * Sets the text for the model version in the Model Labels widget
	 * 
	 * @param modelVersion
	 *            String to be used for the model version
	 * @return void
	 */
	public void setModelVersion(String modelVersion) {
		modelLabelsWidget.setModelVersion(modelVersion);
	}

	/**
	 * Sets the values for the model version text in the Model Labels widget
	 * 
	 * @param modelVersionTexts
	 *            String array to be used for the model version text values
	 * @return void
	 */
	public void setModelVersionTexts(String[] modelVersionTexts) {
		modelLabelsWidget.setModelVersionTexts(modelVersionTexts);
	}

	public void setPatternsFiles(String[] filenames) {
		resourcesWidget.setPatternsFiles(filenames);
	}

	/**
	 * Sets the printed DPI values in the Model Control widget
	 * 
	 * @param dpi
	 *            The printed DPI values to set.
	 * @return void
	 */
	public void setPrintedDpis(String[] dpis) {
		modelControlWidget.setPrintedDpis(dpis);
	}

	public void setS12XmlFiles(String[] filenames) {
		resourcesWidget.setS12XmlFiles(filenames);
	}

	/**
	 * Sets the selected distribution text value in the Model Labels widget
	 * 
	 * @param distributionText
	 *            The selected distribution text value to set.
	 * @return void
	 */
	public void setSelectedDistributionText(String distributionText) {
		modelLabelsWidget.setSelectedDistributionText(distributionText);
	}

	/**
	 * Sets the selected model version text value in the Model Label widget
	 * 
	 * @param modelVersionText
	 *            The selected model version text value to set.
	 * @return void
	 */
	public void setSelectedModelVersionText(String modelVersionText) {
		modelLabelsWidget.setSelectedModelVersionText(modelVersionText);
	}

	// Filter Items Getters & Setters

	/**
	 * Sets the selected printed DPI value in the Model Control widget
	 * 
	 * @param dpi
	 *            The selected printed DPI value to set.
	 * @return void
	 */
	public void setSelectedPrintedDpi(String dpi) {
		modelControlWidget.setSelectedPrintedDpi(dpi);
	}

	public void setShapesFiles(String[] filenames) {
		resourcesWidget.setShapesFiles(filenames);
	}

	// Ignore Items Getters & Setters

	/**
	 * Sets the value for the Suppress Mouseover Effects button
	 * 
	 * @param suppressMouseOverEffect
	 *            Boolean value indicating if the Suppress Mouseover Effects is
	 *            it be used when generating the diagram
	 * @return void
	 */
	public void setSuppressMouseOverEffect(Boolean suppressMouseOverEffect) {
		modelControlWidget.setSuppressMouseOverEffect(suppressMouseOverEffect);
	}

	public void setSystemInfoFiles(String[] filenames) {
		resourcesWidget.setSystemInfoFiles(filenames);
	}

	/**
	 * Sets the text for the system name in the Model Labels widget
	 * 
	 * @param systemName
	 *            String to be used for the system name
	 * @return void
	 */
	public void setSystemName(String systemName) {
		modelLabelsWidget.setSystemName(systemName);
	}

	/**
	 * Sets the text for the system version in the Model Labels widget
	 * 
	 * @param systemVersion
	 *            String to be used for the system version
	 * @return void
	 */
	public void setSystemVersion(String systemVersion) {
		modelLabelsWidget.setSystemVersion(systemVersion);
	}

	/**
	 * This is called by the observed object when a change is made and controls
	 * wizard flow
	 * 
	 * @param event
	 *            the ValidModelEvent object created by the observer object and
	 *            indicating if the wizard page is complete
	 * @return void
	 */
	public void validModelDefined(ValidModelEvent event) {
		Boolean isValid = event.isValid();

		setPageComplete(isValid);

		if (isValid) {
			setErrorMessage(null);
		} else {
			setErrorMessage(event.getMessage());
		}
	}
}
