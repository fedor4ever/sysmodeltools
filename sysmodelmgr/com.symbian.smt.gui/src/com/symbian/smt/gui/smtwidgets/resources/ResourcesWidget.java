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

package com.symbian.smt.gui.smtwidgets.resources;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;

import com.symbian.smt.gui.Helper;
import com.symbian.smt.gui.ResourcesEnums;

public class ResourcesWidget extends Composite {

	/**
	 * This is the content provider for the list of resource types.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class ResourceFilesContentProvider implements
			IStructuredContentProvider {

		public void dispose() {
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ResourcesWidgetHelper
					.toArrayOfCheckableResourceFilenames((java.util.List<CheckableResourceFilename>) inputElement);
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	/**
	 * This is the label provider for the list of of resource types.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class ResourceFilesLabelProvider implements ITableLabelProvider {

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			CheckableResourceFilename crf = (CheckableResourceFilename) element;

			return (columnIndex == 0) ? crf.getFilename() : null;
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

	}

	/**
	 * This is the content provider for the list of resource types.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class ResourceTypesContentProvider implements
			IStructuredContentProvider {

		public void dispose() {
		}

		public Object[] getElements(Object inputElement) {
			return (ResourcesEnums[]) inputElement;
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	/**
	 * This is the label provider for the list of of resource types.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class ResourceTypesLabelProvider implements ILabelProvider {

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			return ((ResourcesEnums) element).arg();
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

	}

	/**
	 * The Map that caches all defined resource files, keyed by the
	 * ResourcesEnums enums.
	 */
	private final HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap = new HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>>();

	/**
	 * The viewer associated with the Resource Files List widget.
	 */
	private CheckboxTableViewer resourceFilesViewer;

	/**
	 * The viewer associated with the Resource Types List widget.
	 */
	private ListViewer resourceTypesViewer;

	/**
	 * Creates a ResourcesWidget composite object
	 * 
	 * @return void
	 */
	public ResourcesWidget(final Composite parent, int style) {
		super(parent, style);

		initialiseMaps();
		this.setLayout(new FillLayout());

		// The Composite that contains all widgets
		final Composite gridLayoutComposite = new Composite(this, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;

		gridLayoutComposite.setLayout(gridLayout);

		// The SashForm that contains the resource types and resource files
		// widgets
		// side by side
		SashForm sash = new SashForm(gridLayoutComposite, SWT.HORIZONTAL);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

		sash.setLayoutData(gd);
		createResourceTypesComposite(sash);
		createResourceFilesComposite(sash);
		sash.setWeights(new int[] { 30, 70 });
		createButtonsComposite(gridLayoutComposite);
	}

	private void addAddResourceButton(Composite parent) {
		// The "Add" button
		final Button addResourceFileButton = new Button(parent, SWT.NONE);
		RowData rd = new RowData();
		rd.width = 75;

		addResourceFileButton.setLayoutData(rd);

		// The action that backs the "Add" button
		final AddResourceFileAction addResourceFileAction = new AddResourceFileAction(
				addResourceFileButton, resourceFilesViewer, resourceFilesMap,
				resourceTypesViewer);

		// When button is pressed, listener invokes the action's run() method,
		// then ensures that the newly added file is checked, guaranteeing that
		// all rules around multiple checked files are respected.
		// Finally refreshes the List of assigned options and set the selection
		// appropriately.
		// If the newly added file failed validation during the action's run()
		// method,
		// none of the operations above is taken and all is left as it was prior
		// to
		// the request to add a new file.
		addResourceFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				addResourceFileAction.run();
				resourceFilesViewer.refresh();

				String newFileLocation = addResourceFileAction
						.getNewFileLocation();

				// newFile could be null if an error was detected in the
				// validations
				// performed in the action's run() method.
				if (newFileLocation != null) {
					ResourcesEnums type = ResourcesWidgetHelper
							.getSelectedResourceType(resourceTypesViewer);
					CheckableResourceFilename crf = ResourcesWidgetHelper
							.filename2checkableFilename(newFileLocation, type,
									resourceFilesMap);

					handleMultipleCheckRules(crf);

					StructuredSelection oldSel = (StructuredSelection) resourceFilesViewer
							.getSelection();

					StructuredSelection newSel = (newFileLocation == null) ? oldSel
							: new StructuredSelection(newFileLocation);

					resourceFilesViewer.setSelection(newSel);
				}
			}
		});
	}

	private void addMoveDownResourceButton(Composite parent) {
		// The "Move Down" button
		final Button moveResourceFileDownButton = new Button(parent, SWT.NONE);
		RowData rd = new RowData();
		rd.width = 75;

		moveResourceFileDownButton.setLayoutData(rd);

		// The action that backs the "Move Down" button
		final MoveResourceFileDownAction moveResourceFileDownAction = new MoveResourceFileDownAction(
				moveResourceFileDownButton, resourceFilesViewer,
				resourceFilesMap, resourceTypesViewer);

		// When button is pressed, listener invokes the action's run() method,
		// then refreshes the List of assigned options and set the selection
		// appropriately
		moveResourceFileDownButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				moveResourceFileDownAction.run();
				resourceFilesViewer.refresh();

				StructuredSelection newSel = new StructuredSelection(
						moveResourceFileDownAction.getMovedCheckableFilename());

				resourceFilesViewer.setSelection(newSel);
			}
		});
	}

	private void addMoveUpResourceButton(Composite parent) {
		// The "Move Up" button
		final Button moveResourceFileUpButton = new Button(parent, SWT.NONE);
		RowData rd = new RowData();
		rd.width = 75;

		moveResourceFileUpButton.setLayoutData(rd);

		// The action that backs the "Move Up" button
		final MoveResourceFileUpAction moveResourceFileUpAction = new MoveResourceFileUpAction(
				moveResourceFileUpButton, resourceFilesViewer,
				resourceFilesMap, resourceTypesViewer);

		// When button is pressed, listener invokes the action's run() method,
		// then refreshes the List of assigned options and set the selection
		// appropriately
		moveResourceFileUpButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				moveResourceFileUpAction.run();
				resourceFilesViewer.refresh();

				StructuredSelection newSel = new StructuredSelection(
						moveResourceFileUpAction.getMovedCheckableFilename());

				resourceFilesViewer.setSelection(newSel);
			}
		});

	}

	private void addRemoveResourceButton(Composite parent) {
		// The "Remove" button
		final Button removeResourceFileButton = new Button(parent, SWT.NONE);
		RowData rd = new RowData();
		rd.width = 75;

		removeResourceFileButton.setLayoutData(rd);

		// The action that backs the "Remove" button
		final RemoveResourceFileAction removeResourceFileAction = new RemoveResourceFileAction(
				removeResourceFileButton, resourceFilesViewer,
				resourceFilesMap, resourceTypesViewer);

		// When button is pressed, listener invokes the action's run() method,
		// then refreshes the List of assigned options and set the selection
		// appropriately
		removeResourceFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				removeResourceFileAction.run();
				resourceFilesViewer.refresh();

				Object firstElement = resourceFilesViewer.getElementAt(0);
				StructuredSelection ssel = (firstElement == null) ? new StructuredSelection(
						StructuredSelection.EMPTY)
						: new StructuredSelection(firstElement);

				resourceFilesViewer.setSelection(ssel);
			}
		});
	}

	private void checkFilesInResourceFilesTable(String[] filenames,
			ResourcesEnums resourceType) {
		if (filenames == null) {
			throw new IllegalArgumentException(
					"Argument filenames cannot be null.");
		}

		java.util.List<String> listOfFilenames = Helper
				.toListOfStrings(filenames);
		java.util.List<CheckableResourceFilename> checkableFilenames = resourceFilesMap
				.get(resourceType);

		if (checkableFilenames == null) {
			throw new IllegalArgumentException(
					"Could not find any elements of type [" + resourceType
							+ "] in resourceFilesMap.");
		}

		CheckableResourceFilename[] viewerElementsToBeChecked = new CheckableResourceFilename[filenames.length];
		int i = 0;

		for (String filename : listOfFilenames) {

			CheckableResourceFilename checkableFilename = ResourcesWidgetHelper
					.filename2checkableFilename(filename, resourceType,
							resourceFilesMap);

			checkableFilename.setChecked(true);

			viewerElementsToBeChecked[i] = checkableFilename;
			i++;
		}
		
		IStructuredSelection ssel = (IStructuredSelection) resourceTypesViewer.getSelection();
		if (!ssel.isEmpty()) {
			ResourcesEnums selectedType = (ResourcesEnums) ssel.getFirstElement();
			if (selectedType.equals(resourceType)) {
				resourceFilesViewer.setCheckedElements(viewerElementsToBeChecked);
			}
		}
		
		
	}

	private void createButtonsComposite(Composite parent) {
		// The Composite that contains all buttons in a horizontal stack
		final Composite buttonsComposite = new Composite(parent, SWT.NONE);
		final RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.spacing = 5;
		rowLayout.wrap = false;
		rowLayout.fill = true;

		buttonsComposite.setLayout(rowLayout);

		GridData gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false,
				1, 1);

		buttonsComposite.setLayoutData(gd);
		addAddResourceButton(buttonsComposite);
		addRemoveResourceButton(buttonsComposite);
		addMoveUpResourceButton(buttonsComposite);
		addMoveDownResourceButton(buttonsComposite);
	}

	private void createResourceFilesComposite(SashForm sash) {
		// The Composite that contains the resource files table, along with a
		// label
		Composite resourceFilesComposite = new Composite(sash, SWT.NONE);
		GridData gd = new GridData(SWT.BEGINNING, SWT.FILL, false, true, 1, 1);

		resourceFilesComposite.setLayoutData(gd);
		resourceFilesComposite.setLayout(new GridLayout());

		Label label = new Label(resourceFilesComposite, SWT.NONE);
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 1, 1);

		label.setLayoutData(gd);
		label.setText("Resource Files");

		// The Table that contains all assigned resource files for the selected
		// resource type
		final Table table = new Table(resourceFilesComposite, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.CHECK
				| SWT.FULL_SELECTION);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

		table.setLayoutData(gd);

		// The resource type viewer associated with the list
		resourceFilesViewer = new CheckboxTableViewer(table);

		resourceFilesViewer
				.setContentProvider(new ResourceFilesContentProvider());
		resourceFilesViewer.setLabelProvider(new ResourceFilesLabelProvider());

		resourceFilesViewer.addCheckStateListener(new ICheckStateListener() {

			// When the user changed the checked state of the checkbox in the
			// table, set
			// or unset the isChecked attribute in the corresponding
			// CheckableResourceFilename object
			public void checkStateChanged(CheckStateChangedEvent event) {
				CheckableResourceFilename checkableFilename = (CheckableResourceFilename) event
						.getElement();
				boolean isSelected = event.getChecked();

				if (isSelected) {
					handleMultipleCheckRules(checkableFilename);
				} else {
					handleNoCheckRules(checkableFilename);
				}
			}

		});
	}

	private void createResourceTypesComposite(SashForm sash) {
		// The Composite that contains the resource types list, along with a
		// label
		Composite resourceTypesComposite = new Composite(sash, SWT.NONE);
		GridData gd = new GridData(SWT.BEGINNING, SWT.FILL, false, true, 1, 1);

		resourceTypesComposite.setLayoutData(gd);
		resourceTypesComposite.setLayout(new GridLayout());

		Label l = new Label(resourceTypesComposite, SWT.NONE);
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 1, 1);

		l.setLayoutData(gd);
		l.setText("Resource Types");

		// The List that contains all possible resource types
		final List list = new List(resourceTypesComposite, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

		list.setLayoutData(gd);

		// The resource type viewer associated with the list
		resourceTypesViewer = new ListViewer(list);

		resourceTypesViewer
				.setContentProvider(new ResourceTypesContentProvider());
		resourceTypesViewer.setLabelProvider(new ResourceTypesLabelProvider());
		resourceTypesViewer.setInput(ResourcesEnums.values());

		resourceTypesViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection ssel = (IStructuredSelection) event
								.getSelection();
						if (ssel.isEmpty()) {
							return;
						}
						ResourcesEnums selectedType = (ResourcesEnums) ssel.getFirstElement();

						resourceFilesViewer.setInput(ResourcesWidgetHelper
								.getCheckableResourceFilenames(selectedType,
										resourceFilesMap));
						
						resourceFilesViewer
								.setCheckedElements(ResourcesWidgetHelper
										.getCheckedResourceFilenames(
												selectedType, resourceFilesMap));
					}
				});
	}

	@Override
	public void dispose() {
		resourceFilesMap.clear();
		super.dispose();
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getBorderShapesFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.BORDER_SHAPES,
				resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getBorderStylesFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.BORDER_STYLES,
				resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getColoursFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.COLOURS,
				resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getDependenciesFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.DEPENDENCIES,
				resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getLevelsFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.LEVELS,
				resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getLocalisationFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.LOCALISATION,
				resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getPatternsFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.PATTERNS,
				resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getS12XmlFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.S12_XML,
				resourceFilesMap);
	}

	/**
	 * Returns the border shapes file or an empty string if no border shapes
	 * file has been specified
	 * 
	 * @return String
	 */
	public String[] getSelectedBorderShapesFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(
				ResourcesEnums.BORDER_SHAPES, resourceFilesMap);
	}

	/**
	 * Returns the border styles file or an empty string if no border styles
	 * file has been specified
	 * 
	 * @return String
	 */
	public String[] getSelectedBorderStylesFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(
				ResourcesEnums.BORDER_STYLES, resourceFilesMap);
	}

	/**
	 * Returns the colours file or an empty string if no colours file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getSelectedColoursFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(
				ResourcesEnums.COLOURS, resourceFilesMap);
	}

	/**
	 * Returns the dependencies file or an empty string if no dependencies file
	 * has been specified
	 * 
	 * @return String
	 */
	public String[] getSelectedDependenciesFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(
				ResourcesEnums.DEPENDENCIES, resourceFilesMap);
	}

	/**
	 * Returns the levels file or an empty string if no levels file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getSelectedLevelsFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(ResourcesEnums.LEVELS,
				resourceFilesMap);
	}

	/**
	 * Returns the localisation file or an empty string if no localisation file
	 * has been specified
	 * 
	 * @return String
	 */
	public String[] getSelectedLocalisationFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(
				ResourcesEnums.LOCALISATION, resourceFilesMap);
	}

	/**
	 * Returns the patterns file or an empty string if no patterns file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getSelectedPatternsFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(
				ResourcesEnums.PATTERNS, resourceFilesMap);
	}

	/**
	 * Returns the patterns file or an empty string if no patterns file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getSelectedS12XmlFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(
				ResourcesEnums.S12_XML, resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getSelectedShapesFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(ResourcesEnums.SHAPES,
				resourceFilesMap);
	}

	/**
	 * Returns the system info file or an empty string if no system info file
	 * has been specified
	 * 
	 * @return String
	 */
	public String[] getSelectedSystemInfoFiles() {
		return ResourcesWidgetHelper.getCheckedFilenames(
				ResourcesEnums.SYSTEM_INFO, resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getShapesFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.SHAPES,
				resourceFilesMap);
	}

	/**
	 * Returns the shapes file or an empty string if no shapes file has been
	 * specified
	 * 
	 * @return String
	 */
	public String[] getSystemInfoFiles() {
		return ResourcesWidgetHelper.getFilenames(ResourcesEnums.SYSTEM_INFO,
				resourceFilesMap);
	}

	/**
	 * Checks the designated resource file and sets its <code>isChecked</code>
	 * attribute to true. Handles business rules around the acceptability of
	 * having multiple checked files.
	 * <p>
	 * The rules are as follows:
	 * <ul>
	 * <li>If resource type is DEPENDENCIES or SHAPES, only allow one check at a
	 * time: uncheck all other files and remove them from
	 * <code>selectedResourceFilesMap</code></li>
	 * <li>In all other cases, allow multiple checks</li>
	 * </ul>
	 * </p>
	 * 
	 * @param resourceFile
	 *            the CheckableResourceFilename object that has been checked by
	 *            the user
	 */
	private void handleMultipleCheckRules(
			CheckableResourceFilename checkedCheckableFilename) {
		ResourcesEnums selectedResourceType = ResourcesWidgetHelper
				.getSelectedResourceType(resourceTypesViewer);
		java.util.List<CheckableResourceFilename> checkableFilenames = ResourcesWidgetHelper
				.getCheckableResourceFilenames(selectedResourceType,
						resourceFilesMap);

		checkedCheckableFilename.setChecked(true);

		switch (selectedResourceType) {
		// Only one file can be checked at a time, therefore uncheck all others
		case DEPENDENCIES:
		case SHAPES:
			for (CheckableResourceFilename checkableFilename : checkableFilenames) {
				if (!checkableFilename.equals(checkedCheckableFilename)) {
					checkableFilename.setChecked(false);
				}
			}

			resourceFilesViewer
					.setCheckedElements(new CheckableResourceFilename[] { checkedCheckableFilename });
			break;

		// If multiple files are checked and they contain the same elements,
		// prevent checking from happening as it will generate an error in
		// the build. Produce an explanatory error dialog.
		case BORDER_SHAPES:
		case PATTERNS:
			// TODO:BRS:This piece of code is unfinished. If it is required to check 
			// that there are no duplicate items across 
			// the defined XML files, check similar behaviour in ResourceFileValidator.
			// If ok
			// resourceFilesViewer.setChecked(resourceFile, true);
			// selectedResourceFiles.add(resourceFile);
			// else
			// MessageDialog.openError("Checked files contain the same element and the process will fail.\n"
			// +
			// "Please remove duplicate elements or uncheck one or more of the other files.");
			// resourceFilesViewer.setChecked(resourceFile, false);

			resourceFilesViewer.setChecked(checkedCheckableFilename, true);
			break;

		// If "Auto" is checked, uncheck everything else. If another option is
		// checked, uncheck "Auto".
		case LEVELS:
			if (checkedCheckableFilename.getFilename().equals("Auto")) {
				for (CheckableResourceFilename checkableFilename : checkableFilenames) {
					if (!checkableFilename.equals(checkedCheckableFilename)) {
						checkableFilename.setChecked(false);
					}
				}

				resourceFilesViewer
						.setCheckedElements(new CheckableResourceFilename[] { checkedCheckableFilename });
			} else {
				resourceFilesViewer.setChecked(checkedCheckableFilename, true);

				CheckableResourceFilename autocfn = ResourcesWidgetHelper
						.filename2checkableFilename("Auto",
								ResourcesEnums.LEVELS, resourceFilesMap);

				if (autocfn.isChecked()) {
					autocfn.setChecked(false);
					resourceFilesViewer.setChecked(autocfn, false);
				}
			}

			break;

		// No special rules, proceed with the operation.
		case BORDER_STYLES:
		case COLOURS:
		case LOCALISATION:
		case S12_XML:
		case SYSTEM_INFO:
			resourceFilesViewer.setChecked(checkedCheckableFilename, true);
			break;

		default:
			throw new IllegalArgumentException("Unknown resource type ["
					+ selectedResourceType + "]");
		}
	}

	/**
	 * Unchecks the designated CheckableResourceFilename object. Handles
	 * business rules around the acceptability of having no checked files.
	 * <p>
	 * The rules are as follows:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * </p>
	 * 
	 * @param selectedCheckableFilename
	 *            the file that has been unchecked by the user
	 */
	private void handleNoCheckRules(
			CheckableResourceFilename uncheckedCheckableFilename) {
		uncheckedCheckableFilename.setChecked(false);
		resourceFilesViewer.setChecked(uncheckedCheckableFilename, false);
	}

	private void initialiseMaps() {
		for (ResourcesEnums type : ResourcesEnums.values()) {
			resourceFilesMap.put(type, null);
		}
	}

	private void populateResourceFilesTable(String[] filenames,
			ResourcesEnums resourceType) {
		java.util.List<String> listOfFilenames = Helper
				.toListOfStrings(filenames);
		java.util.List<CheckableResourceFilename> checkableFilenames = new ArrayList<CheckableResourceFilename>();

		for (String filename : listOfFilenames) {
			CheckableResourceFilename crf = new CheckableResourceFilename(
					filename);
			checkableFilenames.add(crf);
		}

		IStructuredSelection ssel = (IStructuredSelection) resourceTypesViewer.getSelection();
		if (!ssel.isEmpty()) {
			ResourcesEnums selectedType = (ResourcesEnums) ssel.getFirstElement();
			if (selectedType.equals(resourceType)) {
				resourceFilesViewer.setInput(checkableFilenames);
			}
		}
		resourceFilesMap.put(resourceType, checkableFilenames);
	}

	public void setBorderShapesFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.BORDER_SHAPES);
	}

	public void setBorderStylesFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.BORDER_STYLES);
	}

	public void setColoursFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.COLOURS);
	}

	public void setDependenciesFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.DEPENDENCIES);
	}

	public void setLevelsFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.LEVELS);
	}

	public void setLocalisationFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.LOCALISATION);
	}

	public void setPatternsFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.PATTERNS);
	}

	public void setS12XmlFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.S12_XML);
	}

	public void setSelectedBorderShapesFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.BORDER_SHAPES);
	}

	public void setSelectedBorderStylesFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.BORDER_STYLES);
	}

	public void setSelectedColoursFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.COLOURS);
	}

	public void setSelectedDependenciesFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.DEPENDENCIES);
	}

	public void setSelectedLevelsFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.LEVELS);
	}

	public void setSelectedLocalisationFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.LOCALISATION);
	}

	public void setSelectedPatternsFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.PATTERNS);
	}

	public void setSelectedS12XmlFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.S12_XML);
	}

	public void setSelectedShapesFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.SHAPES);
	}

	public void setSelectedSystemInfoFiles(String[] filenames) {
		checkFilesInResourceFilesTable(filenames, ResourcesEnums.SYSTEM_INFO);
	}

	public void setShapesFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.SHAPES);
	}

	public void setSystemInfoFiles(String[] filenames) {
		populateResourceFilesTable(filenames, ResourcesEnums.SYSTEM_INFO);
	}

}