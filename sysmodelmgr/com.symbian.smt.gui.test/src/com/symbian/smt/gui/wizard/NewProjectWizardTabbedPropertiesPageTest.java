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
package com.symbian.smt.gui.wizard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.symbian.smt.gui.Helper;
import com.symbian.smt.gui.TestConstants;
import com.symbian.smt.gui.preferences.SmmPreferencesInitializer;
import com.symbian.smt.gui.smtwidgets.AdvancedOptionsWidget;
import com.symbian.smt.gui.smtwidgets.BuildControlWidget;
import com.symbian.smt.gui.smtwidgets.FilterWidget;
import com.symbian.smt.gui.smtwidgets.IgnoreWidget;
import com.symbian.smt.gui.smtwidgets.ModelControlWidget;
import com.symbian.smt.gui.smtwidgets.ModelLabelsWidget;
import com.symbian.smt.gui.smtwidgets.ValidModelDefinedListener;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent.Type;
import com.symbian.smt.gui.smtwidgets.resources.ResourcesWidget;

public class NewProjectWizardTabbedPropertiesPageTest extends TestCase {

	private static final String SEPARATOR = "|";

	private IStructuredSelection selection;
	private Shell shell;
	private String smgFolder;
	private NewSMTProjectWizard wizard;
	private WizardDialog wizardDialog;
	private IWorkbench workbench;

	private String[] convertFilenamesToAbsolute(String[] relativeFilenames) {
		String[] absoluteFilenames = new String[relativeFilenames.length];

		for (int i = 0; i < relativeFilenames.length; i++) {
			String relativeFilename = relativeFilenames[i];

			if (relativeFilename.equals("Auto")) {
				absoluteFilenames[i] = relativeFilename;
			} else {
				String absoluteFilename = Helper.relative2AbsolutePaths(
						relativeFilename, smgFolder, SEPARATOR);
				absoluteFilenames[i] = absoluteFilename;
			}
		}

		return absoluteFilenames;
	}

	protected void setUp() throws Exception {
		// Initialise the default values
		SmmPreferencesInitializer initialiser = new SmmPreferencesInitializer();

		initialiser.initializeDefaultPreferences();

		smgFolder = initialiser.getSmgFolder();
		wizard = new NewSMTProjectWizard();
		selection = new StructuredSelection(StructuredSelection.EMPTY);
		workbench = PlatformUI.getWorkbench();
		shell = workbench.getActiveWorkbenchWindow().getShell();

		wizard.init(workbench, selection);

		wizardDialog = new WizardDialog(shell, wizard);

		wizardDialog.setBlockOnOpen(false);
		wizardDialog.open();
	}

	protected void tearDown() throws Exception {
		IWorkspace ws = ResourcesPlugin.getWorkspace();

		ws.getRoot().delete(true, true, null);
		wizardDialog.close();
		wizard.dispose();

		wizard = null;
		wizardDialog = null;
		selection = null;
		shell = null;
		smgFolder = null;
		workbench = null;
	}

	private void testAdvancedOptionsWidgetInitialised(
			AdvancedOptionsWidget widget) {
		String message = "Error in widget's advanced options.";
		Object[] expectedValues = new String[] {};
		Object[] actualValues = widget.getAdvancedOptions();
		testWidgetValues(message, expectedValues, actualValues);
	}

	private void testBuildControlWidgetInitialised(BuildControlWidget widget) {
		String message = "Error in widget's output filename.";
		String expectedValue = "Model.svg";
		String actualValue = widget.getOutputFilename();
		testWidgetValue(message, expectedValue, actualValue);

		// We do not test for the warning level here because it is not
		// initialised in the BuildControlWidget when the container
		// is either the wizard or the properties page (this happens
		// only in preferences)
	}

	public void testCreateControl() {
		IWizardPage[] pages = wizard.getPages();
		NewProjectWizardTabbedPropertiesPage page = (NewProjectWizardTabbedPropertiesPage) pages[2];
		TabFolder tf = page.getTabFolder();

		assertNotNull(tf);
		TabItem[] items = tf.getItems();

		assertEquals(7, items.length);

		TabItem item = items[0];
		assertTrue(item.getControl() instanceof ModelLabelsWidget);
		assertEquals("Labels", item.getText());

		item = items[1];
		assertTrue(item.getControl() instanceof ResourcesWidget);
		assertEquals("Resources", item.getText());

		item = items[2];
		assertTrue(item.getControl() instanceof ModelControlWidget);
		assertEquals("Model Control", item.getText());

		item = items[3];
		assertTrue(item.getControl() instanceof FilterWidget);
		assertEquals("Filters", item.getText());

		item = items[4];
		assertTrue(item.getControl() instanceof IgnoreWidget);
		assertEquals("Ignore List", item.getText());

		item = items[5];
		assertTrue(item.getControl() instanceof BuildControlWidget);
		assertEquals("Build Options", item.getText());

		item = items[6];
		assertTrue(item.getControl() instanceof AdvancedOptionsWidget);
		assertEquals("Advanced Options", item.getText());
	}

	public void testDispose() {
		IWizardPage[] pages = wizard.getPages();
		NewProjectWizardTabbedPropertiesPage page = (NewProjectWizardTabbedPropertiesPage) pages[2];
		TabFolder tf = page.getTabFolder();
		TabItem[] items = tf.getItems();

		ModelControlWidget mcw = (ModelControlWidget) items[2].getControl();

		page.dispose();
		List<ValidModelDefinedListener> listeners = mcw.getModelListeners();
		assertFalse(listeners.contains(page));

		BuildControlWidget bcw = (BuildControlWidget) items[5].getControl();

		page.dispose();
		listeners = bcw.getModelListeners();
		assertFalse(listeners.contains(page));
	}

	private void testFilterWidgetInitialised(FilterWidget widget) {
		String message = "Error in widget's filter items.";
		String[] expectedValues = new String[] { "*" };
		String[] actualValues = widget.getFilterItems();
		testWidgetValues(message, expectedValues, actualValues);
	}

	private final void testIgnoreItemsValues(String message,
			List<String[]> expected, List<String[]> actual) {
		assertEquals(message, expected.size(), actual.size());

		Iterator<String[]> expectedIter = expected.iterator();
		Iterator<String[]> actualIter = actual.iterator();

		while (expectedIter.hasNext() && actualIter.hasNext()) {
			String[] expectedArray = expectedIter.next();
			String[] actualArray = actualIter.next();

			for (int i = 0; i < expectedArray.length; i++) {
				String expectedValue = expectedArray[i];
				String actualValue = actualArray[i];

				assertEquals(message, expectedValue, actualValue);
			}
		}
	}

	private void testIgnoreWidgetInitialised(IgnoreWidget widget) {
		String message = "Error in widget's ignored items.";
		List<String[]> expectedIgnoredItems = new ArrayList<String[]>();
		expectedIgnoredItems.add(new String[] { "layer",
				"Tools and Utils and SDKENG" });
		expectedIgnoredItems.add(new String[] { "layer", "MISC" });
		expectedIgnoredItems.add(new String[] { "block", "Techview" });

		List<String[]> actualIgnoredItems = widget.getIgnoreItems();
		testIgnoreItemsValues(message, expectedIgnoredItems, actualIgnoredItems);
	}

	public void testInitialize() {
		IWizardPage[] pages = wizard.getPages();
		NewProjectWizardTabbedPropertiesPage page = (NewProjectWizardTabbedPropertiesPage) pages[2];
		TabFolder tf = page.getTabFolder();

		assertNotNull(tf);
		TabItem[] items = tf.getItems();

		assertEquals(7, items.length);

		TabItem item = items[0];
		assertTrue(item.getControl() instanceof ModelLabelsWidget);
		testModelLabelsWidgetInitialised((ModelLabelsWidget) item.getControl());

		item = items[1];
		assertTrue(item.getControl() instanceof ResourcesWidget);
		testResourcesWidgetInitialised((ResourcesWidget) item.getControl());

		item = items[2];
		assertTrue(item.getControl() instanceof ModelControlWidget);
		testModelControlWidgetInitialised((ModelControlWidget) item
				.getControl());
		ModelControlWidget mcw = (ModelControlWidget) item.getControl();
		List<ValidModelDefinedListener> listeners = mcw.getModelListeners();
		assertTrue(listeners.contains(page));

		item = items[3];
		assertTrue(item.getControl() instanceof FilterWidget);
		testFilterWidgetInitialised((FilterWidget) item.getControl());

		item = items[4];
		assertTrue(item.getControl() instanceof IgnoreWidget);
		testIgnoreWidgetInitialised((IgnoreWidget) item.getControl());

		item = items[5];
		assertTrue(item.getControl() instanceof BuildControlWidget);
		testBuildControlWidgetInitialised((BuildControlWidget) item
				.getControl());
		BuildControlWidget bcw = (BuildControlWidget) item.getControl();
		listeners = bcw.getModelListeners();
		assertTrue(listeners.contains(page));

		item = items[6];
		assertTrue(item.getControl() instanceof AdvancedOptionsWidget);
		testAdvancedOptionsWidgetInitialised((AdvancedOptionsWidget) item
				.getControl());
	}

	private void testModelControlWidgetInitialised(ModelControlWidget widget) {
		String message = "Error in widget's fix item size.";
		Boolean expectedValueB = false;
		Boolean actualValueB = widget.getFixItemSize();
		testWidgetValue(message, expectedValueB, actualValueB);

		message = "Error in widget's highlight core OS.";
		expectedValueB = true;
		actualValueB = widget.getHighlightCoreOS();
		testWidgetValue(message, expectedValueB, actualValueB);

		message = "Error in widget's level of detail.";
		String expectedValue = "component";
		String actualValue = widget.getLevelOfDetail();
		testWidgetValue(message, expectedValue, actualValue);

		message = "Error in widget's printed dpi values.";
		String[] expectedValues = new String[] { "300", "600" };
		String[] actualValues = widget.getPrintedDpis();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected printed dpi.";
		expectedValue = "600";
		actualValue = widget.getSelectedPrintedDpi();
		testWidgetValue(message, expectedValue, actualValue);

		message = "Error in widget's suppress mouse over effect.";
		expectedValueB = true;
		actualValueB = widget.getSuppressMouseOverEffect();
		testWidgetValue(message, expectedValueB, actualValueB);
	}

	private void testModelLabelsWidgetInitialised(ModelLabelsWidget widget) {
		String message = "Error in widget's copyright text.";
		String expectedValue = "Symbian Software Ltd.";
		String actualValue = widget.getCopyrightText();
		testWidgetValue(message, expectedValue, actualValue);

		message = "Error in widget's distribution text values.";
		String[] expectedValues = new String[] { "secret", "confidential",
				"internal", "unrestricted" };
		String[] actualValues = widget.getDistributionTexts();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's model name.";
		expectedValue = "System Model";
		actualValue = widget.getModelName();
		testWidgetValue(message, expectedValue, actualValue);

		message = "Error in widget's model version.";
		expectedValue = "1";
		actualValue = widget.getModelVersion();
		testWidgetValue(message, expectedValue, actualValue);

		message = "Error in widget's model version text values.";
		expectedValues = new String[] { "draft", "build", "issued" };
		actualValues = widget.getModelVersionTexts();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected distribution text.";
		expectedValue = "secret";
		actualValue = widget.getSelectedDistributionText();
		testWidgetValue(message, expectedValue, actualValue);

		message = "Error in widget's selected model version text.";
		expectedValue = "draft";
		actualValue = widget.getSelectedModelVersionText();
		testWidgetValue(message, expectedValue, actualValue);

		message = "Error in widget's system name.";
		expectedValue = "Symbian OS";
		actualValue = widget.getSystemName();
		testWidgetValue(message, expectedValue, actualValue);

		message = "Error in widget's system version.";
		expectedValue = "Future";
		actualValue = widget.getSystemVersion();
		testWidgetValue(message, expectedValue, actualValue);
	}

	private void testPageMessageAndState1(
			NewProjectWizardTabbedPropertiesPage page) {
		String errorMessage = page.getErrorMessage();
		assertTrue(errorMessage == null);

		String message = page.getMessage();
		assertTrue(message == null);

		int messageType = page.getMessageType();
		assertEquals(DialogPage.NONE, messageType);

		boolean isPageComplete = page.isPageComplete();
		assertTrue(isPageComplete);
	}

	private void testPageMessageAndState2(
			NewProjectWizardTabbedPropertiesPage page) {
		String errorMessage = page.getErrorMessage();
		assertEquals("Error message one.", errorMessage);

		String message = page.getMessage();
		assertTrue(message == null);

		int messageType = page.getMessageType();
		assertEquals(DialogPage.NONE, messageType);

		boolean isPageComplete = page.isPageComplete();
		assertFalse(isPageComplete);
	}

	private void testResourcesWidgetInitialised(ResourcesWidget widget) {
		String message = "Error in widget's border shapes files.";
		String[] expectedValues = new String[] {};
		String[] actualValues = widget.getBorderShapesFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's border styles files.";
		expectedValues = new String[] {};
		actualValues = widget.getBorderStylesFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's colour files.";
		expectedValues = convertFilenamesToAbsolute(new String[] { TestConstants.COLOUR_RESOURCE_FILE_PATH });
		actualValues = widget.getColoursFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's dependencies files.";
		expectedValues = new String[] {};
		actualValues = widget.getDependenciesFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's level files.";
		expectedValues = convertFilenamesToAbsolute(new String[] { "Auto",
				"./../SystemModelGenerator/resources/auxiliary/Levels.xml",
				"./../SystemModelGenerator/resources/auxiliary/Levels91.xml" });
		actualValues = widget.getLevelsFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's localisation files.";
		expectedValues = convertFilenamesToAbsolute(new String[] { TestConstants.LOCALISATION_RESOURCE_FILE_PATH });
		actualValues = widget.getLocalisationFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's pattern files.";
		expectedValues = new String[] {};
		actualValues = widget.getPatternsFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's S12 XML files.";
		expectedValues = new String[] {};
		actualValues = widget.getS12XmlFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected border shapes files.";
		expectedValues = new String[] {};
		actualValues = widget.getSelectedBorderShapesFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected border styles files.";
		expectedValues = new String[] {};
		actualValues = widget.getSelectedBorderStylesFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected colour files.";
		expectedValues = new String[] {};
		actualValues = widget.getSelectedColoursFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected dependencies files.";
		expectedValues = new String[] {};
		actualValues = widget.getSelectedDependenciesFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected levels files.";
		expectedValues = new String[] { "Auto" };
		actualValues = widget.getSelectedLevelsFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected localisation files.";
		expectedValues = convertFilenamesToAbsolute(new String[] { TestConstants.LOCALISATION_RESOURCE_FILE_PATH });
		actualValues = widget.getSelectedLocalisationFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected pattern files.";
		expectedValues = new String[] {};
		actualValues = widget.getSelectedPatternsFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected S12 XML files.";
		expectedValues = new String[] {};
		actualValues = widget.getSelectedS12XmlFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected shapes files.";
		expectedValues = convertFilenamesToAbsolute(new String[] { TestConstants.SHAPES_RESOURCE_FILE_PATH });
		actualValues = widget.getSelectedShapesFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's selected system info files.";
		expectedValues = new String[] {};
		actualValues = widget.getSelectedSystemInfoFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's shapes files.";
		expectedValues = convertFilenamesToAbsolute(new String[] {
				"./../SystemModelGenerator/resources/auxiliary/Shapes.xml",
				"./../SystemModelGenerator/resources/auxiliary/Example-shapes.xml" });
		actualValues = widget.getShapesFiles();
		testWidgetValues(message, expectedValues, actualValues);

		message = "Error in widget's system info files.";
		expectedValues = convertFilenamesToAbsolute(new String[] { TestConstants.SYSTEM_INFO_RESOURCE_FILE_PATH });
		actualValues = widget.getSystemInfoFiles();
		testWidgetValues(message, expectedValues, actualValues);
	}

	public void testValidModelDefined() {
		IWizardPage[] pages = wizard.getPages();
		NewProjectWizardTabbedPropertiesPage page = (NewProjectWizardTabbedPropertiesPage) pages[2];

		wizardDialog.showPage(page);

		Boolean isValid = true;
		String eventMessage = "";
		Type type = Type.SUCCESS;
		ValidModelEvent event = new ValidModelEvent(isValid, eventMessage, type);

		page.validModelDefined(event);
		testPageMessageAndState1(page);

		isValid = false;
		eventMessage = "Error message one.";
		type = Type.ERROR;
		event = new ValidModelEvent(isValid, eventMessage, type);

		page.validModelDefined(event);
		testPageMessageAndState2(page);

		isValid = true;
		eventMessage = "";
		type = Type.SUCCESS;
		event = new ValidModelEvent(isValid, eventMessage, type);

		page.validModelDefined(event);
		testPageMessageAndState1(page);
	}

	private final void testWidgetValue(String message, Object expected,
			Object actual) {
		assertEquals(message, expected, actual);
	}

	private final void testWidgetValues(String message, Object[] expected,
			Object[] actual) {
		assertEquals(message, expected.length, actual.length);

		for (int i = 0; i < expected.length; i++) {
			assertEquals(message, expected[i], actual[i]);
		}
	}

}
