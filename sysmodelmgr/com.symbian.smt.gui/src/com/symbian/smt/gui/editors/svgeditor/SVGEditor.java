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



package com.symbian.smt.gui.editors.svgeditor;

import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;

public class SVGEditor extends org.eclipse.ui.part.EditorPart {

	public static final String ID = "com.symbian.smt.gui.editors.svgeditor"; //$NON-NLS-1$

	private String filename;
	private Browser browser;

	@Override
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		browser.setUrl(filename);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// The save button is disabled
	}

	@Override
	public void doSaveAs() {
		// Not implementing at this stage
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		try {
			if (input instanceof IFileEditorInput) {
				IFileEditorInput file = (IFileEditorInput) input;
				URI uri = file.getFile().getLocationURI();
				filename = uri.toURL().toString();
			}
		} catch (MalformedURLException mue) {
			throw new PartInitException("Bad URL", mue);
		}

		if (!input.exists()) {
			throw new PartInitException("Input file " + input.getName()
					+ " does not exist");
		}

		setSite(site);
		setInput(input);
		setPartName(input.getName());
	}

	@Override
	public boolean isDirty() {
		// The user is not able to edit the image
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public void print() {
		browser.execute("window.print();");
	}

	public void refresh() {
		browser.refresh();
	}

	@Override
	public void setFocus() {
		// Set the focus to the browser. The editor will not function properly
		// (won't open files or change tabs) if you don't set the focus to
		// something

		browser.setFocus();
	}
}
