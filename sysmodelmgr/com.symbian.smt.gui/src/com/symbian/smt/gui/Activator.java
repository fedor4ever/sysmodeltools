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

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.symbian.smt.gui";

	// The shared instance
	private static Activator plugin;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	private SmmResourceChangeListener resourceListener;

	private SmmPartListener partListener;
	private IWindowListener windowListener;

	private IPageListener pageListener;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		PlatformUI.getWorkbench().getDecoratorManager().setEnabled(
				"com.symbian.smt.gui.outofsyncdecorator", true);

		resourceListener = new SmmResourceChangeListener();
		partListener = new SmmPartListener();

		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				resourceListener,
				IResourceChangeEvent.POST_CHANGE
						| IResourceChangeEvent.PRE_DELETE
						| IResourceChangeEvent.PRE_BUILD);

		pageListener = new IPageListener() {

			public void pageActivated(IWorkbenchPage page) {
				page.addPartListener(partListener);
			}

			public void pageClosed(IWorkbenchPage page) {
				page.removePartListener(partListener);
			}

			public void pageOpened(IWorkbenchPage page) {
				page.addPartListener(partListener);
			}
		};

		windowListener = new IWindowListener() {
			public void windowActivated(IWorkbenchWindow window) {
				window.addPageListener(pageListener);
				if (window.getActivePage() != null) {
					window.getActivePage().addPartListener(partListener);
				}
			}

			public void windowClosed(IWorkbenchWindow window) {
				window.removePageListener(pageListener);
				if (window.getActivePage() != null) {
					window.getActivePage().removePartListener(partListener);
				}
			}

			public void windowDeactivated(IWorkbenchWindow window) {
				window.removePageListener(pageListener);
				if (window.getActivePage() != null) {
					window.getActivePage().removePartListener(partListener);
				}
			}

			public void windowOpened(IWorkbenchWindow window) {
				window.addPageListener(pageListener);
				if (window.getActivePage() != null) {
					window.getActivePage().addPartListener(partListener);
				}
			}
		};

		
		final IWorkbench workbench = PlatformUI.getWorkbench();
		
		workbench.addWindowListener(windowListener);
		
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				workbenchWindow.addPageListener(pageListener);
				if (workbenchWindow.getActivePage() != null) {
					workbenchWindow.getActivePage().addPartListener(
							partListener);
				}
			}

		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
}
