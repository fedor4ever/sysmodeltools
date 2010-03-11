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

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.actions.SelectionProviderAction;

/**
 * This is the parent of all actions that act on the command line options from
 * the list of assigned options.
 * <p>
 * It caches the Button that is the presentation proxy for the action and
 * manages the enabled state of the Button to be consistent with its own
 * enablement state.
 * </p>
 * 
 * @author barbararosi-schwartz
 * 
 */
public abstract class AbstractMultipleEntriesWidgetAction extends
		SelectionProviderAction {

	protected Button actionProxy;
	protected ISelectionProvider selectionProvider;

	/**
	 * The constructor sets the text on the Button that is the visual proxy of
	 * this action and caches the button for later usage.
	 * 
	 * @param text
	 *            the text that represents both the name of the action and the
	 *            label on the corresponding Button
	 * @param button
	 *            the Button that acts as the visual proxy of this action.
	 */
	public AbstractMultipleEntriesWidgetAction(ISelectionProvider provider,
			String text, Button button) {
		super(provider, text);

		if (provider == null) {
			throw new IllegalArgumentException(
					"ISelectionProvider cannot be null.");
		}

		if (button == null) {
			throw new IllegalArgumentException(
					"Button proxy object cannot be null.");
		}

		this.selectionProvider = provider;
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
