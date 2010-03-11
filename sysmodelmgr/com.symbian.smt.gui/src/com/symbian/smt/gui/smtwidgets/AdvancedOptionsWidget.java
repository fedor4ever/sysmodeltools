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
import java.util.Iterator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
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
import org.eclipse.ui.actions.SelectionProviderAction;

import com.symbian.smt.gui.Helper;

/**
 * This widget contains all the functionality to handle assignment of generic
 * command line parameters.
 * <p>
 * It allows to add, remove and edit command line options, as well as changing
 * their order.
 * </p>
 * NB: This class is in need of refactoring using the common classes that are 
 * also being utilised in the ResourcesWidget class.
 * 
 * @author barbararosi-schwartz
 */
public class AdvancedOptionsWidget extends Composite {

	/**
	 * This is the parent of all actions that act on the command line options
	 * from the list of assigned options.
	 * <p>
	 * It caches the Button that is the presentation proxy for the action and
	 * manages the enabled state of the Button to be consistent with its own
	 * enablement state.
	 * </p>
	 * 
	 * @author barbararosi-schwartz
	 * 
	 */
	private abstract class AbstractOptionAction extends SelectionProviderAction {
		protected Button actionProxy;

		/**
		 * The constructor sets the text on the Button that is the visual proxy
		 * of this action and caches the button for later usage.
		 * 
		 * @param text
		 *            the text that represents both the name of the action and
		 *            the label on the corresponding Button
		 * @param button
		 *            the Button that acts as the visual proxy of this action.
		 */
		private AbstractOptionAction(String text, Button button) {
			super(viewer, text);

			this.actionProxy = button;
			actionProxy.setText(text);
		}

		/**
		 * The default implementation of this method does nothing.
		 */
		@Override
		public void dispose() {
			super.dispose();
		}

		/**
		 * The default implementation of this method does nothing.
		 */
		@Override
		public void run() {
			super.run();
		};

		/**
		 * The default implementation of this method does nothing.
		 */
		@Override
		public void selectionChanged(IStructuredSelection selection) {
			super.selectionChanged(selection);
		}

		/**
		 * Sets the enablement state of the proxy Button to be the same as the
		 * enablement state of the action, the latter being managed by a call to
		 * super.setEnabled().
		 */
		@Override
		public final void setEnabled(boolean enabled) {
			actionProxy.setEnabled(enabled);
			super.setEnabled(enabled);
		}
	}

	/**
	 * This is the action that adds a new command line option to the list of
	 * assigned options.
	 * 
	 * @author barbararosi-schwartz
	 * 
	 */
	private class AddOptionAction extends AbstractOptionAction {

		/**
		 * The option that has been entered by the user or null if the user
		 * cancelled the operation.
		 */
		private String newOption = null;

		private AddOptionAction(Button button) {
			super("Add...", button);
			setEnabled(true);
		}

		/**
		 * Returns the option that was entered by the user.
		 * 
		 * @return the option that was entered by the user (or null if the user
		 *         cancelled the operation)
		 */
		String getNewOption() {
			return newOption;
		}

		/**
		 * Creates and displays an InputDialogWithWarning that collects the new
		 * option entered by the user. The dialog is equipped with a
		 * DialogInputValidator object that automatically performs validation on
		 * the user's input.
		 * <p>
		 * When the dialog is dismissed, the action changes the model to reflect
		 * the new addition.
		 * </p>
		 */
		@Override
		public void run() {
			InputDialogWithWarning dialog = new InputDialogWithWarning(viewer
					.getControl().getShell(), "Add Option",
					"Please enter the required command-line option", "",
					new DialogInputValidator());

			int result = dialog.open();

			if (result == Window.CANCEL) {
				newOption = null;
				return;
			} else {
				newOption = dialog.getValue().trim();

				java.util.List<String> model = Helper
						.toListOfStrings((String[]) viewer.getInput());
				model.add(newOption);
				setAdvancedOptions(Helper.toArrayOfStrings(model));
			}
		}

		/**
		 * This action is always enabled.
		 */
		@Override
		public void selectionChanged(IStructuredSelection selection) {
		}
	}

	/**
	 * This is the content provider for the list of assigned command line
	 * options.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class AdvancedOptionsContentProvider implements
			IStructuredContentProvider {

		public void dispose() {
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			if (! (inputElement instanceof String[])) {
				throw new IllegalArgumentException("Argument is not of type String[].");
			}
			
			String[] items = (String[]) inputElement;

			return items;
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	/**
	 * This is the label provider for the list of assigned command line options.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class AdvancedOptionsLabelProvider implements ILabelProvider {

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			return element.toString();
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

	}

	/**
	 * This is the validator that is utilised by the InputDialogWithWarning
	 * presented by the AddOptionAction.
	 * 
	 * @author barbararosi-schwartz
	 * 
	 */
	private class DialogInputValidator implements IInputValidatorWithWarning {

		private java.util.List<String> listElements = new ArrayList<String>();

		private DialogInputValidator() {
			listElements = Helper.toListOfStrings((String[]) viewer.getInput());
		}

		/**
		 * User input is invalid if:
		 * <ol>
		 * <li>input is not empty</li>
		 * <li>input is already present in the list</li>
		 * </ol>
		 *
		 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
		 */
		public String isValid(String newText) {
			if (newText.trim().length() == 0) {
				return "";
			}

			if (listElements.contains(newText.trim())) {
				return "Option [" + newText + "] is already in the list.";
			}

			return null;
		}

		/**
		 * User input generates a warning if it is one of the options contained
		 * in the dangerousOptions list.
		 * 
		 * @see com.symbian.smt.gui.smtwidgets.IInputValidatorWithWarning#isWarning(java.lang.String)
		 */
		public String isWarning(String newText) {
			if (dangerousOptions.contains(newText)) {
				return "Warning: option [" + newText
						+ "] may cause the model build process to fail.";
			}

			return null;
		}

	}

	/**
	 * This is the action that edits a command line option that already exists
	 * in the list of assigned options.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class EditOptionAction extends AbstractOptionAction {

		private EditOptionAction(Button button) {
			super("Edit...", button);
			setEnabled(false);
		}

		/**
		 * Creates and displays an InputDialogWithWarning, initialised with the
		 * currently selected option. The dialog is equipped with a
		 * DialogInputValidator object that automatically performs validation on
		 * the user's input.
		 * <p>
		 * When the dialog is dismissed, the action changes the model to reflect
		 * the option modification.
		 * </p>
		 */
		@Override
		public void run() {
			String initialValue = (String) ((StructuredSelection) getSelection())
					.getFirstElement();
			InputDialogWithWarning dialog = new InputDialogWithWarning(viewer
					.getControl().getShell(), "Add Option",
					"Please enter the required command-line option",
					initialValue, new DialogInputValidator());

			int result = dialog.open();
			String editedOption = null;

			if (result == Window.CANCEL) {
				return;
			} else {
				editedOption = dialog.getValue().trim();

				java.util.List<String> model = Helper
						.toListOfStrings((String[]) viewer.getInput());
				int index = model.indexOf(initialValue);
				model.set(index, editedOption);
				setAdvancedOptions(Helper.toArrayOfStrings(model));
			}
		}

		/**
		 * Enabled if we have exactly one selection in the list.
		 */
		@Override
		public void selectionChanged(IStructuredSelection selection) {
			if (selection.size() != 1) {
				setEnabled(false);
				return;
			}

			setEnabled(true);
		}
	}

	/**
	 * This is the action that moves a command line option down by one position
	 * in the list of assigned options.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class MoveOptionDownAction extends AbstractOptionAction {

		/**
		 * The option that has been moved by the user
		 */
		private String movedOption = null;

		private MoveOptionDownAction(Button button) {
			super("Move Down", button);
			setEnabled(false);
		}

		/**
		 * Returns the option that was moved by the user.
		 * 
		 * @return the option that was moved by the user
		 */
		String getMovedOption() {
			return movedOption;
		}

		/**
		 * Moves the selected option down by one position in the model.
		 */
		@Override
		public void run() {
			movedOption = (String) ((StructuredSelection) getSelection())
					.getFirstElement();
			java.util.List<String> model = Helper
					.toListOfStrings((String[]) viewer.getInput());
			int oldIndex = model.indexOf(movedOption);
			model.remove(oldIndex);
			int newIndex = oldIndex + 1;
			model.add(newIndex, movedOption);
			setAdvancedOptions(Helper.toArrayOfStrings(model));
		}

		/**
		 * Enabled if the list has exactly one selection and if the selection is
		 * not the last element in the list.
		 */
		@Override
		public void selectionChanged(IStructuredSelection selection) {
			if (selection.size() != 1) {
				setEnabled(false);
				return;
			}

			boolean enabled = true;
			String selectedElement = (String) selection.getFirstElement();
			String lastElement = (String) viewer.getElementAt(viewer.getList()
					.getItemCount() - 1);

			if (lastElement != null && selectedElement.equals(lastElement)) {
				enabled = false;
			}

			setEnabled(enabled);
		}
	}

	/**
	 * This is the action that moves a command line option up by one position in
	 * the list of assigned options.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class MoveOptionUpAction extends AbstractOptionAction {

		/**
		 * The option that has been moved by the user
		 */
		private String movedOption = null;

		private MoveOptionUpAction(Button button) {
			super("Move Up", button);
			setEnabled(false);
		}

		/**
		 * Returns the option that was moved by the user.
		 * 
		 * @return the option that was moved by the user
		 */
		String getMovedOption() {
			return movedOption;
		}

		/**
		 * Moves the selected option down by one position in the model.
		 */
		@Override
		public void run() {
			movedOption = (String) ((StructuredSelection) getSelection())
					.getFirstElement();
			java.util.List<String> model = Helper
					.toListOfStrings((String[]) viewer.getInput());
			int oldIndex = model.indexOf(movedOption);
			model.remove(oldIndex);
			int newIndex = oldIndex - 1;
			model.add(newIndex, movedOption);
			setAdvancedOptions(Helper.toArrayOfStrings(model));
		}

		/**
		 * Enabled if the list has exactly one selection and if the selection is
		 * not the first element in the list.
		 */
		@Override
		public void selectionChanged(IStructuredSelection selection) {
			if (selection.size() != 1) {
				setEnabled(false);
				return;
			}

			boolean enabled = true;
			String selectedElement = (String) selection.getFirstElement();
			String firstElement = (String) viewer.getElementAt(0);

			if (firstElement != null && selectedElement.equals(firstElement)) {
				enabled = false;
			}

			setEnabled(enabled);
		}
	}

	/**
	 * This is the action that removes a command line option from the list of
	 * assigned options.
	 * 
	 * @author barbararosi-schwartz
	 */
	private class RemoveOptionAction extends AbstractOptionAction {

		private RemoveOptionAction(Button button) {
			super("Remove", button);
			setEnabled(false);
		}

		/**
		 * Removes the selected options from the model.
		 */
		@Override
		public void run() {
			StructuredSelection ssel = (StructuredSelection) getSelection();
			java.util.List<String> model = Helper
					.toListOfStrings((String[]) viewer.getInput());

			@SuppressWarnings("unchecked")
			Iterator<String> iter = ssel.iterator();

			while (iter.hasNext()) {
				String to_be_removed = (String) iter.next();
				model.remove(to_be_removed);
			}

			setAdvancedOptions(Helper.toArrayOfStrings(model));
		}

		/**
		 * Enabled if we have at least one selection in the list.
		 */
		@Override
		public void selectionChanged(IStructuredSelection selection) {
			if (selection.isEmpty()) {
				setEnabled(false);
				return;
			}

			setEnabled(true);
		}
	}

	/**
	 * The List of all command line options that may override values entered
	 * elsewhere.
	 */
	private static final ArrayList<String> dangerousOptions = new ArrayList<String>();

	static {
		dangerousOptions.add("clean");
		dangerousOptions.add("compress");
		dangerousOptions.add("log");
		dangerousOptions.add("model");
		dangerousOptions.add("output");
		dangerousOptions.add("tempdir");
	}

	/**
	 * The viewer associated with the List widget.
	 */
	private ListViewer viewer;

	/**
	 * Creates an AdvancedOptionsWidget composite object
	 * 
	 * @return void
	 */
	public AdvancedOptionsWidget(final Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());

		// The Composite that contains all widgets
		final Composite gridLayoutComposite = new Composite(this, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayoutComposite.setLayout(gridLayout);

		final Label label = new Label(gridLayoutComposite, SWT.NONE);
		GridData gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false,
				2, 1);

		label.setText("Additional Command-Line Parameters");
		label.setLayoutData(gd);

		// The List that contains all assigned command line options
		final List list = new List(gridLayoutComposite, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.widthHint = 200;

		list.setLayoutData(gd);

		viewer = new ListViewer(list);

		viewer.setContentProvider(new AdvancedOptionsContentProvider());
		viewer.setLabelProvider(new AdvancedOptionsLabelProvider());

		// The Composite that contains all buttons in a vertical stack
		final Composite buttonsComposite = new Composite(gridLayoutComposite,
				SWT.NONE);
		final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.spacing = 5;
		rowLayout.wrap = false;
		rowLayout.fill = true;

		buttonsComposite.setLayout(rowLayout);

		gd = new GridData(SWT.RIGHT, SWT.BEGINNING, false, true, 1, 1);
		buttonsComposite.setLayoutData(gd);

		// The "Add" button
		final Button addOptionButton = new Button(buttonsComposite, SWT.NONE);
		RowData rd = new RowData();
		rd.width = 75;

		addOptionButton.setLayoutData(rd);

		// The action that backs the "Add" button
		final AddOptionAction addOptionAction = new AddOptionAction(
				addOptionButton);

		// When button is pressed, listener invokes the action's run() method,
		// then refresh
		// the List of assigned options and set the selection appropriately
		addOptionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				addOptionAction.run();
				StructuredSelection oldSel = (StructuredSelection) viewer
						.getSelection();
				viewer.refresh();

				String newOption = addOptionAction.getNewOption();
				StructuredSelection newSel = (newOption == null) ? oldSel
						: new StructuredSelection(newOption);

				viewer.setSelection(newSel);
			}
		});

		// The "Edit" button
		final Button editOptionButton = new Button(buttonsComposite, SWT.NONE);
		rd = new RowData();
		rd.width = 75;

		editOptionButton.setLayoutData(rd);

		// The action that backs the "Edit" button
		final EditOptionAction editOptionAction = new EditOptionAction(
				editOptionButton);

		// When button is pressed, listener invokes the action's run() method,
		// then refresh
		// the List of assigned options and set the selection appropriately
		editOptionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				editOptionAction.run();
				StructuredSelection oldSel = (StructuredSelection) viewer
						.getSelection();

				viewer.refresh();
				viewer.setSelection(oldSel);
			}
		});

		// The "Remove" button
		final Button removeOptionButton = new Button(buttonsComposite, SWT.NONE);
		rd = new RowData();
		rd.width = 75;

		removeOptionButton.setLayoutData(rd);

		// The action that backs the "Remove" button
		final RemoveOptionAction removeOptionAction = new RemoveOptionAction(
				removeOptionButton);

		// When button is pressed, listener invokes the action's run() method,
		// then refreshes the List of assigned options and set the selection
		// appropriately
		removeOptionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				removeOptionAction.run();
				viewer.refresh();

				// If the viewer has at least one element, we set the current
				// selection to that element. 
				Object firstElement = viewer.getElementAt(0);
				StructuredSelection ssel = (firstElement == null) ? new StructuredSelection(
						StructuredSelection.EMPTY)
						: new StructuredSelection(firstElement);

				viewer.setSelection(ssel);
			}
		});

		// The "Move Up" button
		final Button moveOptionUpButton = new Button(buttonsComposite, SWT.NONE);
		rd = new RowData();
		rd.width = 75;

		moveOptionUpButton.setLayoutData(rd);

		// The action that backs the "Move Up" button
		final MoveOptionUpAction moveOptionUpAction = new MoveOptionUpAction(
				moveOptionUpButton);

		// When button is pressed, listener invokes the action's run() method,
		// then refreshes the List of assigned options and set the selection
		// appropriately
		moveOptionUpButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				moveOptionUpAction.run();
				viewer.refresh();

				StructuredSelection newSel = new StructuredSelection(
						moveOptionUpAction.getMovedOption());

				viewer.setSelection(newSel);
			}
		});

		// The "Move Down" button
		final Button moveOptionDownButton = new Button(buttonsComposite,
				SWT.NONE);
		rd = new RowData();
		rd.width = 75;

		moveOptionDownButton.setLayoutData(rd);

		// The action that backs the "Move Down" button
		final MoveOptionDownAction moveOptionDownAction = new MoveOptionDownAction(
				moveOptionDownButton);

		// When button is pressed, listener invokes the action's run() method,
		// then refreshes the List of assigned options and set the selection
		// appropriately
		moveOptionDownButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				moveOptionDownAction.run();
				viewer.refresh();

				StructuredSelection newSel = new StructuredSelection(
						moveOptionDownAction.getMovedOption());

				viewer.setSelection(newSel);
			}
		});
	}

	/**
	 * Returns the advanced options
	 * 
	 * @return String[]
	 */
	public String[] getAdvancedOptions() {
		return (String[]) viewer.getInput();
	}

	/**
	 * Sets the advanced options
	 * 
	 * @param options
	 *            A list containing advanced options.
	 * @return void
	 */
	public void setAdvancedOptions(String[] options) {
		if (options != null) {
			viewer.setInput(options);
		}
	}

}