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



package com.symbian.smt.gui;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

/**
 * @author barbararosi-schwartz
 * 
 */
public class OpenFileActionProvider extends CommonActionProvider {

	private OpenFileAction openFileAction;
	private IActionBars actionBars;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.ActionGroup#dispose()
	 */
	@Override
	public void dispose() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		if ((shell != null) && (! shell.isDisposed())) {
			actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, null);
		}
		
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars
	 * )
	 */
	@Override
	public void fillActionBars(IActionBars actionBars) {
		this.actionBars = actionBars;

		try {
			if (openFileAction.isEnabled()) {
				actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN,
						openFileAction);
			}
		} catch (Exception ignore) {
			// Consuming CoreException that is thrown because the URI is missing
			// for resources that are URLs
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	@Override
	public void fillContextMenu(IMenuManager menu) {
		try {
			if (openFileAction.isEnabled()) {
				menu.appendToGroup(ICommonMenuConstants.GROUP_OPEN,
						openFileAction);
			}
		} catch (Exception ignore) {
			// Consuming CoreException that is thrown because the URI is missing
			// for resources that are URLs
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator
	 * .ICommonActionExtensionSite)
	 */
	@Override
	public void init(ICommonActionExtensionSite site) {
		ICommonViewerSite viewSite = site.getViewSite();

		if (viewSite instanceof ICommonViewerWorkbenchSite) {
			ICommonViewerWorkbenchSite workbenchSite = (ICommonViewerWorkbenchSite) viewSite;

			openFileAction = new OpenFileAction(workbenchSite.getPage(),
					workbenchSite.getSelectionProvider());
		}
	}

}
