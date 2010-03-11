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
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class IgnoreWidget extends Composite {
	private Combo itemTypeCombo;
	private Text itemNameText;
	private Table ignoreItemsTable;
	private Button modifyButton;

	/**
	 * Creates an IgnoreWidget composite object
	 * 
	 * @return void
	 */
	public IgnoreWidget(final Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());

		final Composite gridLayoutComposite = new Composite(this, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gridLayoutComposite.setLayout(gridLayout);

		ignoreItemsTable = new Table(gridLayoutComposite, SWT.FULL_SELECTION
				| SWT.BORDER);
		ignoreItemsTable.setHeaderVisible(true);
		ignoreItemsTable.setLinesVisible(true);
		final GridData gd_ignoreItemsTable = new GridData(SWT.FILL, SWT.FILL,
				true, true, 4, 1);
		ignoreItemsTable.setLayoutData(gd_ignoreItemsTable);
		ignoreItemsTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				// When an item in the table is selected populate the item type
				// combo and the
				// item name text with the relevant information
				TableItem itemSelected = ignoreItemsTable
						.getItem(ignoreItemsTable.getSelectionIndex());

				int index = 0;

				for (String item : itemTypeCombo.getItems()) {
					if (item.equals(itemSelected.getText(0))) {
						itemTypeCombo.select(index);
					}
					index++;
				}

				itemNameText.setText(itemSelected.getText(1));
				itemNameText.setFocus();
				modifyButton.setEnabled(true);
			}
		});

		final TableColumn itemTypeTableColumn = new TableColumn(
				ignoreItemsTable, SWT.NONE);
		itemTypeTableColumn.setWidth(100);
		itemTypeTableColumn.setText("Item Type");

		final TableColumn itemNameTableColumn = new TableColumn(
				ignoreItemsTable, SWT.NONE);
		itemNameTableColumn.setWidth(269);
		itemNameTableColumn.setText("Item Name");

		final Label itemTypeLabel = new Label(gridLayoutComposite, SWT.NONE);
		itemTypeLabel.setText("Item Type");

		itemTypeCombo = new Combo(gridLayoutComposite, SWT.READ_ONLY);
		itemTypeCombo.setLayoutData(new GridData());
		itemTypeCombo.setItems(new String[] { "layer", "block", "subblock",
				"collection", "component" });
		itemTypeCombo.select(0);

		final Label itemNameLabel = new Label(gridLayoutComposite, SWT.NONE);
		itemNameLabel.setLayoutData(new GridData());
		itemNameLabel.setText("Item Name");

		itemNameText = new Text(gridLayoutComposite, SWT.BORDER);
		final GridData gd_itemNameText = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		itemNameText.setLayoutData(gd_itemNameText);
		itemNameText.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (itemNameText.getText().length() == 0) {
					modifyButton.setEnabled(false);
				}
			}
		});

		final Composite buttonsComposite = new Composite(gridLayoutComposite,
				SWT.NONE);
		final GridData gd_buttonsComposite = new GridData(SWT.CENTER,
				SWT.CENTER, false, false, 4, 1);
		buttonsComposite.setLayoutData(gd_buttonsComposite);
		final RowLayout rowLayout = new RowLayout();
		rowLayout.spacing = 30;
		buttonsComposite.setLayout(rowLayout);

		final Button addButton = new Button(buttonsComposite, SWT.NONE);
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				// Check to see if an item had been selected and item text has
				// been entered
				if (itemTypeCombo.getText().length() > 0
						&& itemNameText.getText().trim().length() > 0) {

					// Check that combination of type and text doesn't already
					// exist in the table
					Boolean alreadyExistsInTable = false;

					for (TableItem item : ignoreItemsTable.getItems()) {
						if (item.getText(0).equals(itemTypeCombo.getText())
								&& item.getText(1).equalsIgnoreCase(
										itemNameText.getText().trim())) {
							alreadyExistsInTable = true;
						}
					}

					// Add to the table if does not already exist
					if (!alreadyExistsInTable) {
						final TableItem newItemTableItem = new TableItem(
								ignoreItemsTable, SWT.BORDER);
						newItemTableItem.setText(0, itemTypeCombo.getText());
						newItemTableItem.setText(1, itemNameText.getText());

						// Clear the text entry
						itemNameText.setText("");
						modifyButton.setEnabled(false);
					} else {
						MessageDialog
								.openError(
										parent.getShell(),
										"Error",
										itemTypeCombo.getText()
												+ " "
												+ itemNameText.getText()
												+ " already exists in the list of items to ignore.");
					}
				}

				itemNameText.setFocus();
			}
		});

		final RowData rd_addButton = new RowData();
		rd_addButton.width = 75;
		addButton.setLayoutData(rd_addButton);
		addButton.setText("Add");

		final Button removeButton = new Button(buttonsComposite, SWT.NONE);
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				if (ignoreItemsTable.getSelectionIndex() >= 0) {
					ignoreItemsTable.remove(ignoreItemsTable
							.getSelectionIndex());

					// Clear the text entry
					itemNameText.setText("");
					modifyButton.setEnabled(false);
				} else {
					MessageDialog
							.openInformation(parent.getShell(), "Info",
									"You need to select an item from the list before you can remove it.");
				}

				itemNameText.setFocus();
			}
		});

		final RowData rd_removeButton = new RowData();
		rd_removeButton.width = 75;
		removeButton.setLayoutData(rd_removeButton);
		removeButton.setText("Remove");

		modifyButton = new Button(buttonsComposite, SWT.NONE);
		modifyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				// Check to see if an item had been selected and item text has
				// been entered
				if (itemNameText.getText().length() > 0) {

					// Check that combination of type and text doesn't already
					// exist in the table
					Boolean alreadyExistsInTable = false;

					for (TableItem item : ignoreItemsTable.getItems()) {
						if (item.getText(0).equals(itemTypeCombo.getText())
								&& item.getText(1).equals(
										itemNameText.getText())) {
							alreadyExistsInTable = true;
						}
					}

					// Add to the table if does not already exist
					if (!alreadyExistsInTable) {
						TableItem itemSelected = ignoreItemsTable
								.getItem(ignoreItemsTable.getSelectionIndex());

						itemSelected.setText(0, itemTypeCombo.getText());
						itemSelected.setText(1, itemNameText.getText());

						// Clear the text entry
						itemNameText.setText("");
						modifyButton.setEnabled(false);
					}
				}

				itemNameText.setFocus();
			}
		});

		final RowData rd_modifyButton = new RowData();
		rd_modifyButton.width = 75;
		modifyButton.setLayoutData(rd_modifyButton);
		modifyButton.setText("Modify");
		modifyButton.setEnabled(false);
	}

	/**
	 * Returns a list of the ignore items
	 * 
	 * @return List<String>
	 */
	public List<String[]> getIgnoreItems() {
		ArrayList<String[]> ignoreItems = new ArrayList<String[]>();

		for (TableItem item : ignoreItemsTable.getItems()) {
			String[] itemData = { item.getText(0), item.getText(1) };
			ignoreItems.add(itemData);
		}

		return ignoreItems;
	}

	/**
	 * Sets the ignore items
	 * 
	 * @param ignoreItems
	 *            An ArrayList containing 2 element lists. The first element
	 *            contains the item type and the second element contains the
	 *            item text.
	 * @return void
	 */
	public void setIgnoreItems(List<String[]> ignoreItems) {
		ignoreItemsTable.removeAll();
		for (String[] ignoreItem : ignoreItems) {
			final TableItem newItemTableItem = new TableItem(ignoreItemsTable,
					SWT.BORDER);
			newItemTableItem.setText(0, ignoreItem[0]);
			newItemTableItem.setText(1, ignoreItem[1]);
		}
	}
}
