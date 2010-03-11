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

package com.symbian.smt.gui.smtwidgets;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public class FilterWidget extends Composite {
	private Text text = null;
	private List list;
	private Button modifyButton;

	/**
	 * Creates a FilterWidget composite object
	 * 
	 * @return void
	 */
	public FilterWidget(final Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());

		final Composite gridLayoutComposite = new Composite(this, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayoutComposite.setLayout(gridLayout);

		list = new List(gridLayoutComposite, SWT.BORDER);
		final GridData gd_list = new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1);
		gd_list.widthHint = 439;
		list.setLayoutData(gd_list);
		list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				int index = list.getSelectionIndex();

				if (index != -1) {
					String filterSelected = list.getItem(index);
					text.setText(filterSelected);
					text.setFocus();
					modifyButton.setEnabled(true);
				}
			}
		});

		final Label filterLabel = new Label(gridLayoutComposite, SWT.NONE);
		filterLabel.setText("Filter");

		text = new Text(gridLayoutComposite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.setFocus();
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (text.getText().length() == 0) {
					modifyButton.setEnabled(false);
				}
			}
		});

		final Composite buttonsComposite = new Composite(gridLayoutComposite,
				SWT.NONE);
		final RowLayout rowLayout = new RowLayout();
		rowLayout.spacing = 30;
		rowLayout.justify = true;
		buttonsComposite.setLayout(rowLayout);
		final GridData gd_buttonsComposite = new GridData(SWT.CENTER, SWT.FILL,
				true, false, 2, 1);
		buttonsComposite.setLayoutData(gd_buttonsComposite);

		final Button addFilterButton = new Button(buttonsComposite, SWT.NONE);
		final RowData rd_addFilterButton = new RowData();
		rd_addFilterButton.width = 75;
		addFilterButton.setLayoutData(rd_addFilterButton);
		addFilterButton.setText("Add");
		addFilterButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				// Adds the text entered in the filter text to the list of
				// filter items
				String filter = text.getText().trim();

				if (filter.length() > 0) {
					// Check that text has been entered and that it does not
					// already exist in the table
					Boolean doesExistInList = false;

					for (String s : list.getItems()) {
						if (s.equals(filter)) {
							doesExistInList = true;
						}
					}

					if (!doesExistInList) {
						// If it doesn't exist in the table then add it and
						// clear the text box
						list.add(filter);
						text.setText("");
						modifyButton.setEnabled(false);
					} else {
						MessageDialog
								.openError(
										parent.getShell(),
										"Error",
										filter
												+ " already exists in the list of filters.");
					}
				}

				text.setFocus();
			}
		});

		final Button removeFilterButton = new Button(buttonsComposite, SWT.NONE);
		final RowData rd_removeFilterButton = new RowData();
		rd_removeFilterButton.width = 75;
		removeFilterButton.setLayoutData(rd_removeFilterButton);
		removeFilterButton.setText("Remove");
		removeFilterButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int listIndex = list.getSelectionIndex();

				if (listIndex != -1) {
					// If an item has been selected in the table then remove it
					list.remove(listIndex);
					text.setText("");
					modifyButton.setEnabled(false);
				} else {
					MessageDialog.openInformation(parent.getShell(), "Info",
							"You need to select an item to remove.");
				}

				text.setFocus();
			}
		});

		modifyButton = new Button(buttonsComposite, SWT.NONE);
		final RowData rd_modifyButton = new RowData();
		rd_modifyButton.width = 75;
		modifyButton.setLayoutData(rd_modifyButton);
		modifyButton.setText("Modify");
		modifyButton.setEnabled(false);
		modifyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				// Modifies a table entry in place
				int index = list.getSelectionIndex();

				String filter = text.getText().trim();

				if (filter.length() > 0) {
					// Check filter text has been entered
					Boolean doesExistInList = false;

					for (String s : list.getItems()) {
						// Check that the entry doesn't already exist in the
						// table
						if (s.equals(filter)) {
							doesExistInList = true;
						}
					}

					if (!doesExistInList) {
						// If it doesn't already exist in the table then add it
						list.remove(index);
						list.add(filter, index);
						text.setText("");
						modifyButton.setEnabled(false);
					}
				}

				text.setFocus();
			}
		});
	}

	/**
	 * Returns the filter items
	 * 
	 * @return String[]
	 */
	public String[] getFilterItems() {
		return list.getItems();
	}

	/**
	 * Sets the filter items
	 * 
	 * @param filterItems
	 *            A list containing filters.
	 * @return void
	 */
	public void setFilterItems(String[] filterItems) {
		if (filterItems != null)
			list.setItems(filterItems);
	}
}
