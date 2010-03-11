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
package com.symbian.smt.gui.smtwidgets;

import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.preferences.SmmPreferencesInitializer;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent.Type;

public class ModelControlWidgetTest extends TestCase {

	private Dialog dialog;
	private PersistentDataStore instanceStore;
	private ModelControlWidget modelControlWidget;
	private int validModelDefinedPassNumber = 1;
	
	protected void setUp() throws Exception {
		// Initialise the default values
		SmmPreferencesInitializer initialiser = new SmmPreferencesInitializer();

		initialiser.initializeDefaultPreferences();

		
		IWorkbench workbench = PlatformUI.getWorkbench();
		Shell shell = workbench.getActiveWorkbenchWindow().getShell();
		
		dialog = new Dialog(shell) {
			protected Control createContents(Composite parent) {
				Composite c = new Composite(parent, SWT.NONE);
				c.setLayout(new FillLayout());
				modelControlWidget = new ModelControlWidget(c, SWT.NONE);
				
				return c;
			}
		};

		dialog.setBlockOnOpen(false);
		dialog.open();
	}

	protected void tearDown() throws Exception {
		dialog.close();
		dialog = null;
		modelControlWidget.dispose();
		modelControlWidget = null;
	
		validModelDefinedPassNumber = 1;
	}

	public final void testAddModelListener() {
		List<ValidModelDefinedListener> listeners = modelControlWidget.getModelListeners();
		
		assertTrue (listeners.size() == 0);
		
		ValidModelDefinedListener listener = new ValidModelDefinedListener() {
			public void validModelDefined(ValidModelEvent event) {
				// Do nothing
				;
			}
		};
		
		modelControlWidget.addModelListener(listener);
		
		listeners = modelControlWidget.getModelListeners();
		
		assertTrue (listeners.size() == 1);
	}

	public final void testCheckDpi() {
		IScopeContext defaultScope = new DefaultScope();
		 IEclipsePreferences defaultNode = defaultScope
		 .getNode(Activator.PLUGIN_ID);
		 IScopeContext instanceScope = new InstanceScope();
		 IEclipsePreferences instanceNode = instanceScope
		 .getNode(Activator.PLUGIN_ID);
		 instanceStore = new PersistentDataStore(
		 instanceNode, defaultNode);
		 
		modelControlWidget.initialisePrintedDpi(instanceStore);
		
		modelControlWidget.addModelListener(new ValidModelDefinedListener() {
			public void validModelDefined(ValidModelEvent event) {
				String message = event.getMessage();
				Type type = event.getType();
				
				if (validModelDefinedPassNumber == 1) {
					assertEquals("", message);
					assertEquals(Type.SUCCESS, type);
					validModelDefinedPassNumber = 2;
				}
				else if (validModelDefinedPassNumber == 2) {
					assertEquals("", message);
					assertEquals(Type.SUCCESS, type);
					validModelDefinedPassNumber = 3;
				}
				else {
					assertEquals("The DPI value must be an integer value or empty (screen resolution).", message);
					assertEquals(Type.ERROR, type);
				}
			}
		});
		
		Combo c = modelControlWidget.getPrintedDpiCombo();
		
		c.setText("2400");
		
		// TODO:BRS:The next test should fail. The validation code needs to cater for negative numbers 
		// (what about floats as opposed to integers?)
		c.setText("-1200");
		
		c.setText("test");
	}
	
	public final void testGetFixItemSize() {
		Button b = modelControlWidget.getFixItemSizeButton();
		
		b.setSelection(true);
		
		Boolean actual = modelControlWidget.getFixItemSize();
		
		assertTrue(actual);
		b.setSelection(false);
		
		actual = modelControlWidget.getFixItemSize();
		
		assertFalse(actual);
	}

	public final void testGetPrintedDpis() {
		Combo c = modelControlWidget.getPrintedDpiCombo();
		
		c.setItems(new String[] {"1200", "300", "600"});
		
		String[] actual = modelControlWidget.getPrintedDpis();
		String[] expected = {"300", "600", "1200"};
		
		testPrintedDpiComboValues(expected, actual);
		
		c.setText("2400");
		
		actual = modelControlWidget.getPrintedDpis();
		expected = new String[] {"300", "600", "1200", "2400"};
		
		testPrintedDpiComboValues(expected, actual);
	}

	public final void testGetSelectedPrintedDpi() {
		Combo c = modelControlWidget.getPrintedDpiCombo();
		String expected = "300";
		
		c.setText(expected);
		
		String actual = modelControlWidget.getSelectedPrintedDpi();
		
		assertEquals(expected, actual);
	}

	public final void testGetSuppressMouseOverEffect() {
		Button b = modelControlWidget.getSuppressMouseOverEffectButton();
		
		b.setSelection(true);
		
		Boolean actual = modelControlWidget.getSuppressMouseOverEffect();
		
		assertTrue(actual);
		b.setSelection(false);
		
		actual = modelControlWidget.getSuppressMouseOverEffect();
		
		assertFalse(actual);
	}

	public final void testInitialisePrintedDpi() {
		IScopeContext defaultScope = new DefaultScope();
		 IEclipsePreferences defaultNode = defaultScope
		 .getNode(Activator.PLUGIN_ID);
		 IScopeContext instanceScope = new InstanceScope();
		 IEclipsePreferences instanceNode = instanceScope
		 .getNode(Activator.PLUGIN_ID);
		 instanceStore = new PersistentDataStore(
		 instanceNode, defaultNode);
		 
		modelControlWidget.initialisePrintedDpi(instanceStore);
		
		Combo printedDpiCombo = modelControlWidget.getPrintedDpiCombo();
		String[] actualItems = printedDpiCombo.getItems();
		String[] expectedItems = {"300", "600"};
		
		testPrintedDpiComboValues(expectedItems, actualItems);
		
		String actualSelectedItem = printedDpiCombo.getText();
		String expectedSelectedItem = "600";
		
		assertEquals(expectedSelectedItem, actualSelectedItem);
	}

	public final void testModelControlWidget() {
		Combo combo = modelControlWidget.getPrintedDpiCombo();
		
		assertNotNull(combo);
		
		Button button = modelControlWidget.getFixItemSizeButton();
		
		assertNotNull(button);
		
		Label label = modelControlWidget.getPrintedDpiLabel();
		
		assertNotNull(label);
		assertEquals("Printed Resolution (DPI)", label.getText());
		
		label = modelControlWidget.getSuppressMouseOverEffectsLabel();
		
		assertNotNull(label);
		assertEquals("Suppress Mouseover Effects", label.getText());
		
		label = modelControlWidget.getFixedItemSizeLabel();
		
		assertNotNull(label);
		assertEquals("Fix Items Size", label.getText());
	}

	private void testPrintedDpiComboValues(String[] expectedItems, String[] actualItems) {
		assertEquals(expectedItems.length, actualItems.length);
		
		for (int i = 0; i < expectedItems.length; i++) {
			assertEquals(expectedItems[i], actualItems[i]);
		}
	}

	public final void testRemoveModelListener() {
		ValidModelDefinedListener listener = new ValidModelDefinedListener() {
			public void validModelDefined(ValidModelEvent event) {
				// Do nothing
				;
			}
		};
		
		modelControlWidget.addModelListener(listener);
		
		List<ValidModelDefinedListener> listeners = modelControlWidget.getModelListeners();
		
		assertTrue (listeners.size() == 1);
		
		modelControlWidget.removeModelListener(listener);

		listeners = modelControlWidget.getModelListeners();
		
		assertTrue (listeners.size() == 0);
	}

	public final void testSetFixItemSize() {
		Button b = modelControlWidget.getFixItemSizeButton();
		
		modelControlWidget.setFixItemSize(true);
		
		assertTrue(b.getSelection());
		
		modelControlWidget.setFixItemSize(false);
		
		assertFalse(b.getSelection());
	}

	public final void testSetPrintedDpis() {
		Combo c = modelControlWidget.getPrintedDpiCombo();
		
		modelControlWidget.setPrintedDpis(new String[] {"1200", "300", "600"});
		
		String[] actual = c.getItems();
		String[] expected = {"1200", "300", "600"};
		
		testPrintedDpiComboValues(expected, actual);
		
		c.setText("2400");
		
		actual = modelControlWidget.getPrintedDpis();
		expected = new String[] {"300", "600", "1200", "2400"};
		
		testPrintedDpiComboValues(expected, actual);
	}

	public final void testSetSelectedPrintedDpi() {
		Combo c = modelControlWidget.getPrintedDpiCombo();
		
		// Testing selection prior to setting items in the Combo
		String selected = "300";
		
		modelControlWidget.setSelectedPrintedDpi(selected);
		
		String actual = c.getText();
		
		assertEquals("", actual);
		
		// Testing selection after having set items in the Combo
		c.setItems(new String[] {"300", "600", "1200"});
		modelControlWidget.setSelectedPrintedDpi(selected);
		
		actual = c.getText();
		
		assertEquals(selected, actual);
	}

	public final void testSetSuppressMouseOverEffect() {
		Button b = modelControlWidget.getSuppressMouseOverEffectButton();
		
		modelControlWidget.setSuppressMouseOverEffect(true);
		
		assertTrue(b.getSelection());
		
		modelControlWidget.setSuppressMouseOverEffect(false);
		
		assertFalse(b.getSelection());
	}
}
