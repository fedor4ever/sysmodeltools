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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import com.symbian.smt.gui.Helper;
import com.symbian.smt.gui.Logger;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent.Type;

public class SystemDefinitionFilesWidget extends Composite implements
		ValidModelObservable {
	private List systemDefinitionFilesList = null;
	private ArrayList<ValidModelDefinedListener> listeners = new ArrayList<ValidModelDefinedListener>();

	/**
	 * Creates a SystemDefinitionFilesWidget composite object
	 * 
	 * @return void
	 */
	public SystemDefinitionFilesWidget(final Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());
		this.setRedraw(true);

		final Composite compositeMainGridLayout = new Composite(this, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		compositeMainGridLayout.setLayout(gridLayout);

		systemDefinitionFilesList = new List(compositeMainGridLayout,
				SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		systemDefinitionFilesList.setBackground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_WHITE));
		final GridData gd_list = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_list.widthHint = 271;
		systemDefinitionFilesList.setLayoutData(gd_list);

		systemDefinitionFilesList.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				CheckRequiredInformationPresent();
			}
		});

		final Composite compositeUpDownButtons = new Composite(
				compositeMainGridLayout, SWT.NONE);
		final GridData gd_compositeUpDownButtons = new GridData();
		compositeUpDownButtons.setLayoutData(gd_compositeUpDownButtons);
		compositeUpDownButtons.setLayout(new RowLayout(SWT.VERTICAL));

		final Button upButton = new Button(compositeUpDownButtons, SWT.FLAT);
		final RowData rd_upButton = new RowData();
		rd_upButton.height = 26;
		rd_upButton.width = 26;
		upButton.setLayoutData(rd_upButton);
		upButton.setImage(getUpArrowImage());
		upButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				// Reorder the system definition files in the list by swapping
				// the item
				// selected with the item above
				int index = systemDefinitionFilesList.getSelectionIndex();

				if (index != -1 && index - 1 >= 0) {
					String item = systemDefinitionFilesList.getItem(index);
					String itemAbove = systemDefinitionFilesList
							.getItem(index - 1);

					systemDefinitionFilesList.setItem(index - 1, item);
					systemDefinitionFilesList.setItem(index, itemAbove);

					systemDefinitionFilesList.setSelection(index - 1);

				}
				systemDefinitionFilesList.setFocus();
			}
		});

		final Button downButton = new Button(compositeUpDownButtons, SWT.FLAT);
		final RowData rd_downButton = new RowData();
		rd_downButton.height = 26;
		rd_downButton.width = 26;
		downButton.setLayoutData(rd_downButton);
		downButton.setImage(getDownArrowImage());
		downButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				// Reorder the system definition files in the list by swapping
				// the item
				// selected with the item below
				int index = systemDefinitionFilesList.getSelectionIndex();

				if (index != -1
						&& index + 1 < systemDefinitionFilesList.getItemCount()) { // index
																					// is
																					// 0
																					// relative,
																					// getItemCount
																					// is
																					// not
					String item = systemDefinitionFilesList.getItem(index);
					String itemBelow = systemDefinitionFilesList
							.getItem(index + 1);

					systemDefinitionFilesList.setItem(index + 1, item);
					systemDefinitionFilesList.setItem(index, itemBelow);

					systemDefinitionFilesList.setSelection(index + 1);
				}
				systemDefinitionFilesList.setFocus();
			}
		});

		final Composite compositeAddRemoveButtons = new Composite(
				compositeMainGridLayout, SWT.NONE);
		final GridData gd_compositeAddRemoveButtons = new GridData(SWT.CENTER,
				SWT.CENTER, false, false, 2, 1);
		compositeAddRemoveButtons.setLayoutData(gd_compositeAddRemoveButtons);
		final RowLayout rowLayout = new RowLayout();
		rowLayout.spacing = 30;
		compositeAddRemoveButtons.setLayout(rowLayout);

		final Button addSystemDefinitionButton = new Button(
				compositeAddRemoveButtons, SWT.NONE);

		addSystemDefinitionButton.setText("Add System Definition File");
		addSystemDefinitionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				java.util.List<String> currentItems = Helper
						.toListOfStrings(systemDefinitionFilesList.getItems());
				SystemDefinitionFileSelectionValidator validator = new SystemDefinitionFileSelectionValidator(
						currentItems);
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				String dialogTitle = "New System Definition File";
				String dialogMessage = "Enter the path or URL to the system definition file";
				String initialPath = "";
				String[] filterNames = { "*.xml" };
				XmlFileSelectionDialog dialog = new XmlFileSelectionDialog(
						shell, dialogTitle, dialogMessage, initialPath,
						filterNames, validator);

				dialog.open();

				if (dialog.getReturnCode() == Dialog.CANCEL) {
					return;
				}

				String filename = dialog.getValue();

				if (filename != null && (filename.length() != 0)) {
					systemDefinitionFilesList.add(filename);
				}

				systemDefinitionFilesList.setFocus();
			}
		});

		final Button removeSystemDefinitionButton = new Button(
				compositeAddRemoveButtons, SWT.NONE);
		removeSystemDefinitionButton.setText("Remove System Definition File");
		removeSystemDefinitionButton
				.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						int listIndex = systemDefinitionFilesList
								.getSelectionIndex();

						if (listIndex != -1) {
							// Remove the system definition file from the list
							systemDefinitionFilesList.remove(listIndex);
						}
						systemDefinitionFilesList.setFocus();
					}
				});
	}

	/**
	 * Registers a listener object, which will be notified if the conditions for
	 * proceeding have been met.
	 * 
	 * @param listener
	 *            A ValidModelDefinedListener object
	 * @return void
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelObservable#addModelListener(ValidModelDefinedListener)
	 */
	public void addModelListener(ValidModelDefinedListener listener) {
		listeners.add(listener);
	}

	// Check that at least one system definition file has been specified. Any
	// registered
	// model listeners are notified with a ValidModelEvent.
	private void CheckRequiredInformationPresent() {
		if (listeners.size() > 0) {
			Boolean modelDefined = true;
			String message = "";
			Type type = Type.SUCCESS;

			if (systemDefinitionFilesList.getItems().length == 0) {
				modelDefined = false;
				message = "You must specify at least 1 system definition xml file to proceed.";
				type = Type.ERROR;
			}

			ValidModelEvent event = new ValidModelEvent(modelDefined, message,
					type);

			for (ValidModelDefinedListener listener : listeners) {
				listener.validModelDefined(event);
			}
		}
	}

	private Image getDownArrowImage() {
		return new Image(getDisplay(),
				getImageAsStream("icons/Arrow_down_icons_24px.png"));
	}

	private InputStream getImageAsStream(String imageLocation) {
		Bundle bundle = Platform.getBundle("com.symbian.smt.gui");
		Path path = new Path(imageLocation);
		URL imageURL = FileLocator.find(bundle, path, null);

		try {
			return imageURL.openStream();
		} catch (IOException e) {
			Logger.log(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Returns a list of the system definition files
	 * 
	 * @return String[]
	 */
	public String[] getSystemDefinitions() {
		String[] sysDefs = systemDefinitionFilesList.getItems();
		return sysDefs;
	}

	private Image getUpArrowImage() {
		return new Image(getDisplay(),
				getImageAsStream("icons/Arrow_Up_icons_24px.png"));
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
	 * Sets the system definition files
	 * 
	 * @param sysDefs
	 *            A list containing system definition files
	 * @return void
	 */
	public void setSystemDefinitions(String[] sysDefs) {
		systemDefinitionFilesList.setItems(sysDefs);
	}
}
