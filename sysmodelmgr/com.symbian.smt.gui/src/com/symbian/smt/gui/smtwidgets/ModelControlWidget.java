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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.symbian.smt.gui.Helper;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent.Type;

public class ModelControlWidget extends Composite implements
		ValidModelObservable {
	private Combo levelOfDetailCombo;
	private Combo printedDpiCombo;
	private Button highlightCoreOsButton;
	private Button suppressMouseOverEffectButton;
	private Button fixItemSizeButton;
	private Label fixedItemSizeLabel;
	private Label suppressMouseOverEffectsLabel;
	private Label printedDpiLabel;
	
	private ArrayList<ValidModelDefinedListener> listeners = new ArrayList<ValidModelDefinedListener>();

	/**
	 * Creates a ModelControlWidget composite object
	 * 
	 * @return void
	 */
	public ModelControlWidget(final Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());

		final Composite gridLayoutComposite = new Composite(this, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 10;
		gridLayout.horizontalSpacing = 20;
		gridLayout.numColumns = 2;
		gridLayoutComposite.setLayout(gridLayout);

		final Label levelOfDetailLabel = new Label(gridLayoutComposite,
				SWT.NONE);
		levelOfDetailLabel.setText("Level of Detail");

		levelOfDetailCombo = new Combo(gridLayoutComposite, SWT.READ_ONLY);
		GridData data = new GridData(200, SWT.DEFAULT);
		data.horizontalAlignment = SWT.LEFT;
		levelOfDetailCombo.setLayoutData(data);
		levelOfDetailCombo.setItems(new String[] { "layer", "block",
				"subblock", "collection", "component" });

		printedDpiLabel = new Label(gridLayoutComposite, SWT.NONE);
		printedDpiLabel.setText("Printed Resolution (DPI)");

		printedDpiCombo = new Combo(gridLayoutComposite, SWT.NONE);
		data = new GridData(50, SWT.DEFAULT);
		data.horizontalAlignment = SWT.LEFT;

		printedDpiCombo.setLayoutData(data);

		final Label highlightCoreOs = new Label(gridLayoutComposite, SWT.NONE);
		highlightCoreOs.setText("Highlight Core OS");

		highlightCoreOsButton = new Button(gridLayoutComposite, SWT.CHECK);

		suppressMouseOverEffectsLabel = new Label(gridLayoutComposite,
				SWT.NONE);
		suppressMouseOverEffectsLabel.setText("Suppress Mouseover Effects");

		suppressMouseOverEffectButton = new Button(gridLayoutComposite,
				SWT.CHECK);

		fixedItemSizeLabel = new Label(gridLayoutComposite, SWT.NONE);
		fixedItemSizeLabel.setText("Fix Items Size");

		fixItemSizeButton = new Button(gridLayoutComposite, SWT.CHECK);
	}

	/**
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelObservable#addModelListener(ValidModelDefinedListener)
	 */
	public void addModelListener(ValidModelDefinedListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	private void checkDpi() {
		if (listeners.size() > 0) {
			Boolean modelDefined = true;
			String message = "";
			Type type = Type.SUCCESS;

			String text = printedDpiCombo.getText().trim();

			if (text.length() > 0) {
				try {
					Integer i = new Integer(text);
					
					if (i < 0) {
						throw new NumberFormatException();
					}				
				} catch (NumberFormatException nfe) {
					modelDefined = false;
					message = "The DPI value must be a positive integer less than 2147483648 or empty.";
					type = Type.ERROR;
				}
			}

			ValidModelEvent event = new ValidModelEvent(modelDefined, message,
					type);

			for (ValidModelDefinedListener listener : listeners) {
				listener.validModelDefined(event);
			}
		}
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 * 
	 * @return the fixedItemSizeLabel
	 */
	public Label getFixedItemSizeLabel() {
		return fixedItemSizeLabel;
	}

	/**
	 * Returns a Boolean indicating if the Fix Items Size button has been
	 * selected
	 * 
	 * @return Boolean
	 */
	public Boolean getFixItemSize() {
		return fixItemSizeButton.getSelection();
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 * 
	 * @return the fixItemSizeButton
	 */
	public Button getFixItemSizeButton() {
		return fixItemSizeButton;
	}

	/**
	 * Returns a boolean value indicating if the Highlight Core OS button has
	 * been selected
	 * 
	 * @return Boolean
	 */
	public Boolean getHighlightCoreOS() {
		return highlightCoreOsButton.getSelection();
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 * 
	 * @return the highlightCoreOsButton
	 */
	public Button getHighlightCoreOsButton() {
		return highlightCoreOsButton;
	}

	/**
	 * Returns the level of detail
	 * 
	 * @return String
	 */
	public String getLevelOfDetail() {
		return levelOfDetailCombo.getText();
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 * 
	 * @return the levelOfDetailCombo
	 */
	public Combo getLevelOfDetailCombo() {
		return levelOfDetailCombo;
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 * 
	 * @return the listeners
	 */
	public ArrayList<ValidModelDefinedListener> getListeners() {
		return listeners;
	}

	/**
	 * This method is for testing purposes only.
	 * 
	 * @return
	 */
	public List<ValidModelDefinedListener> getModelListeners() {
		List<ValidModelDefinedListener> l;

		synchronized (listeners) {
			l = new ArrayList<ValidModelDefinedListener>(listeners);
		}

		return l;
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 * 
	 * @return the printedDpiCombo
	 */
	public Combo getPrintedDpiCombo() {
		return printedDpiCombo;
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 * 
	 * @return the printedDpiLabel
	 */
	public Label getPrintedDpiLabel() {
		return printedDpiLabel;
	}

	/**
	 * Returns the printed DPI values. We need to take into account both the
	 * list items and the item that the user may have typed in. We also need to
	 * make sure that all items in the pulldown are sorted.
	 * 
	 * @return String the list of all current DPI values, including the one that
	 *         the user may have typed in.
	 */
	public String[] getPrintedDpis() {
		Collection<Integer> c = new HashSet<Integer>();
		List<String> items = Helper.toListOfStrings(printedDpiCombo.getItems());

		for (String item : items) {
			c.add(new Integer(item));
		}

		String text = printedDpiCombo.getText().trim();

		if (text.length() > 0) {
			if (!items.contains(text)) {
				c.add(new Integer(text));
			}
		}

		Integer[] allNumbers = new Integer[c.size()];
		List<Integer> sortedNumbers = sort(c.toArray(allNumbers));
		List<String> dpis = new ArrayList<String>();

		for (Integer number : sortedNumbers) {
			dpis.add(number.toString());
		}

		return dpis.toArray(new String[dpis.size()]);
	}

	/**
	 * Returns the selected printed DPI value
	 * 
	 * @return String
	 */
	public String getSelectedPrintedDpi() {
		return printedDpiCombo.getText();
	}

	/**
	 * Returns a boolean value indicating if the Suppress Mouseover Effects
	 * button has been selected
	 * 
	 * @return Boolean
	 */
	public Boolean getSuppressMouseOverEffect() {
		return suppressMouseOverEffectButton.getSelection();
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 * 
	 * @return the suppressMouseOverEffectButton
	 */
	public Button getSuppressMouseOverEffectButton() {
		return suppressMouseOverEffectButton;
	}

	/**
	 * This method is for PDE JUnit testing purposes.
	 * 
	 * @return the suppressMouseOverEffectsLabel
	 */
	public Label getSuppressMouseOverEffectsLabel() {
		return suppressMouseOverEffectsLabel;
	}

	public void initialisePrintedDpi(PersistentDataStore store) {
		printedDpiCombo.setItems(store.getPrintedDpis());
		printedDpiCombo.setText(store.getSelectedPrintedDpi());

		printedDpiCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkDpi();
			}
		});
	}

	/**
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelObservable#removeModelListener(ValidModelDefinedListener)
	 */
	public void removeModelListener(ValidModelDefinedListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	/**
	 * Sets the value for the Fix Items Size button
	 * 
	 * @param fixItemSize
	 *            Boolean indicating if the Fix Items Size is to be used when
	 *            generating the diagram
	 * @return void
	 */
	public void setFixItemSize(Boolean fixItemSize) {
		fixItemSizeButton.setSelection(fixItemSize);
	}

	/**
	 * Sets the value for the Highlight Core OS button
	 * 
	 * @param HighlightCoreOS
	 *            Boolean value indicating if the Core OS section is to be
	 *            highlighted
	 * @return void
	 */
	public void setHighlightCoreOS(Boolean HighlightCoreOS) {
		highlightCoreOsButton.setSelection(HighlightCoreOS);
	}

	/**
	 * Sets the level of detail
	 * 
	 * @param level
	 *            The level of detail to set.
	 * @return void
	 */
	public void setLevelOfDetail(String level) {
		String[] items = levelOfDetailCombo.getItems();
		int index = 0;

		for (String item : items) {
			if (item.equals(level)) {
				break;
			}
			index++;
		}

		levelOfDetailCombo.select(index);
	}

	/**
	 * Sets the printed DPI values
	 * 
	 * @param dpi
	 *            The DPI values to populate the combo box with.
	 * @return void
	 */
	public void setPrintedDpis(String[] dpis) {
		printedDpiCombo.setItems(dpis);
	}

	/**
	 * Sets the selected printed DPI value
	 * 
	 * @param dpi
	 *            The DPI value to set as selected.
	 * @return void
	 */
	public void setSelectedPrintedDpi(String dpi) {
		String[] items = printedDpiCombo.getItems();
		int index = 0;

		for (String item : items) {
			if (item.equals(dpi)) {
				break;
			}

			index++;
		}

		printedDpiCombo.select(index);
	}

	/**
	 * Sets the value for the Suppress Mouseover Effects button
	 * 
	 * @param suppressMouseOverEffect
	 *            Boolean value indicating if the Suppress Mouseover Effects is
	 *            it be used when generating the diagram
	 * @return void
	 */
	public void setSuppressMouseOverEffect(Boolean suppressMouseOverEffect) {
		suppressMouseOverEffectButton.setSelection(suppressMouseOverEffect);
	}

	private List<Integer> sort(Integer[] numbers) {
		ArrayList<Integer> al = new ArrayList<Integer>();

		for (int i = 0; i < numbers.length; i++) {
			al.add(i, (numbers[i]));
		}

		List<Integer> list = Collections.synchronizedList(al);
		Collections.sort(list);

		return list;
	}
	
}
