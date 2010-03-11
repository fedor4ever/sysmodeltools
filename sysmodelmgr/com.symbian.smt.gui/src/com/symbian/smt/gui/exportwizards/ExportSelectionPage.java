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

package com.symbian.smt.gui.exportwizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.Logger;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.nature.Nature;

public class ExportSelectionPage extends WizardPage {
	private Text text;
	private List list;

	/**
	 * @param pageName
	 */
	protected ExportSelectionPage(IStructuredSelection selection) {
		super("wizardPage");
		setTitle("System Model Manager Export Wizard");
		setDescription("Select the project you wish to export the diagram from, select the location to export to and then click finish");
		setPageComplete(false);
	}

	private void canFinish() {
		if (text.getText().trim().length() > 0
				&& list.getSelectionIndex() != -1) {
			setPageComplete(true);
		} else {
			setPageComplete(false);
		}
	}

	public boolean copyFile() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot workspaceRoot = workspace.getRoot();

		IProject project = workspaceRoot.getProject(list.getItem(list
				.getSelectionIndex()));

		IScopeContext projectScope = new ProjectScope(project);
		PersistentDataStore store = new PersistentDataStore(projectScope
				.getNode(Activator.PLUGIN_ID));

		IFile projectFile = project.getFile(store.getOutputFilename());

		File toFile = new File(text.getText());

		if (toFile.isDirectory()) {
			MessageDialog.openError(getShell(), "Error", toFile.toString()
					+ " is a directory");
			Logger.log(toFile.toString() + " is a directory");
			return false;
		}

		if (!projectFile.exists()) {
			MessageDialog.openError(getShell(), "Error", "The project "
					+ list.getItem(list.getSelectionIndex())
					+ " does not contain a System Model Diagram");
			return false;
		}

		if (toFile.exists()) {
			if (!MessageDialog.openConfirm(getShell(), "Overwrite?",
					"Do you wish to overwrite the file " + toFile.toString()
							+ "?")) {
				return false;
			}
		}

		try {
			FileChannel in = new FileInputStream(projectFile.getRawLocation()
					.toOSString()).getChannel();
			FileChannel out = new FileOutputStream(text.getText()).getChannel();

			try {
				in.transferTo(0, in.size(), out);
				in.close();
				out.close();
			} catch (IOException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
				Logger.log(e.getMessage(), e);
				return false;
			}

		} catch (FileNotFoundException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
			Logger.log(e.getMessage(), e);
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FillLayout(SWT.VERTICAL));

		setControl(container);

		final Composite composite = new Composite(container, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);

		list = new List(composite, SWT.BORDER);
		final GridData gd_list = new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1);
		list.setLayoutData(gd_list);
		list.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				canFinish();
			}
		});

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot workspaceRoot = workspace.getRoot();

		for (IProject project : workspaceRoot.getProjects()) {
			if (project.isOpen()) {
				try {
					if (project.hasNature(Nature.ID)) {
						list.add(project.getName());
					}
				} catch (CoreException e) {
					Logger.log(e.getMessage(), e);
					MessageDialog
							.openError(getShell(), "Error", e.getMessage());
				}
			}
		}

		final Button browseButton = new Button(composite, SWT.NONE);
		browseButton.setText("Browse");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Create and open a file window
				FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);

				String[] filterNames = new String[1];

				filterNames[0] = "*.svg";

				dialog.setFilterExtensions(filterNames);

				String filename = dialog.open();

				if (filename != null) {
					text.setText(filename);
				}
			}
		});

		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				canFinish();
			}
		});
	}
}