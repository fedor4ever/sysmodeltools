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

import static com.symbian.smt.gui.ResourcesEnums.BORDER_SHAPES;
import static com.symbian.smt.gui.ResourcesEnums.BORDER_STYLES;
import static com.symbian.smt.gui.ResourcesEnums.COLOURS;
import static com.symbian.smt.gui.ResourcesEnums.DEPENDENCIES;
import static com.symbian.smt.gui.ResourcesEnums.LEVELS;
import static com.symbian.smt.gui.ResourcesEnums.LOCALISATION;
import static com.symbian.smt.gui.ResourcesEnums.PATTERNS;
import static com.symbian.smt.gui.ResourcesEnums.S12_XML;
import static com.symbian.smt.gui.ResourcesEnums.SHAPES;
import static com.symbian.smt.gui.ResourcesEnums.SYSTEM_INFO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;

public class ManageResources {

	private static final List<String> RESOURCE_FOLDER_NAMES = Helper
			.toListOfStrings(ResourcesEnums.values());

	public static final String IS_URL = "isUrl";
	public static final String URL_STRING = "urlString";

	/**
	 * Deletes a resource, if it exists within a specified folder within the
	 * project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param folderName
	 *            The folder in which the resource is to be updated
	 * @return void
	 */
	private static void deleteResources(IProject project, String folderName) {
		// Create/Get the folder in the project
		IFolder folder = getFolderInProject(project, folderName);

		try {
			for (IResource res : folder.members()) {
				res.delete(true, null);
			}
		} catch (CoreException e) {
			Logger.log(e.getMessage(), e);
		}
	}

	/**
	 * Returns an IFolder object for the specified folder and project. Creates
	 * the folder if it does not already exist.
	 * 
	 * @param project
	 *            The project to amend
	 * @param folderName
	 *            The folder name required
	 * @return IFolder
	 */
	private static IFolder getFolderInProject(IProject project,
			String folderName) {
		// Makes a folder in the project
		final IFolder folder = project.getFolder(new Path(folderName));

		// Check to see if the folder already exists before creating it
		if (!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				Logger.log(e.getMessage(), e);
			}
		}
		return folder;
	}

	/**
	 * Creates and returns an AbstractPersistentDataStore object, used for
	 * persising data.
	 * 
	 * @param project
	 *            The project to amend
	 * @return AbstractPersistentDataStore
	 */
	private static AbstractPersistentDataStore getPersistDataStore(
			IProject project) {
		IScopeContext projectScope = new ProjectScope(project);
		IEclipsePreferences node = projectScope.getNode(Activator.PLUGIN_ID);
		AbstractPersistentDataStore dataStore = new PersistentDataStore(node);

		return dataStore;
	}

	public static ResourcesEnums getResourceType(IFile file) {
		if (isResourceFile(file)) {
			String folderName = ((IFolder) file.getParent()).getName();

			return ResourcesEnums.getResourcesEnums(folderName);
		}

		return null;
	}

	public static String getResourceUrl(IFile file) throws CoreException {
		String urlString = null;
		IMarker[] messageMarkers = file.findMarkers(IMarker.TASK, false,
				IResource.DEPTH_ZERO);

		for (int i = 0; i < messageMarkers.length; i++) {
			IMarker marker = messageMarkers[i];

			if (marker.getAttribute(ManageResources.IS_URL, false)) {
				urlString = (String) marker.getAttribute(URL_STRING);
			}
		}

		return urlString;
	}

	public static boolean isLocalPath(String path) {
		int index = path.indexOf(':');

		if (index == -1 || index == 1) {
			return true;
		} else if (index > 1) {
			return false;
		} else {
			throw new RuntimeException("Unexpected file path format.");
		}
	}

	public static boolean isResourceFile(IFile file) {
		IContainer container = file.getParent();

		if (container instanceof IFolder) {
			IFolder folder = (IFolder) container;
			String folderName = folder.getName();

			if (RESOURCE_FOLDER_NAMES.contains(folderName)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isSystemDefinitionFile(IFile file) {
		IContainer container = file.getParent();

		if (container instanceof IProject) {
			return true;
		}

		return false;
	}

	/**
	 * Adds a file shortcut to the specified project
	 * 
	 * @param project
	 *            The project to amend
	 * @param folder
	 *            The folder to create the shortcut in
	 * @param filenameInOS
	 *            The file in the OS to create the shortcut to
	 * @param filenameInProject
	 *            The name to use for the shortcut
	 * @return void
	 */
	private static void makeFileShortcut(IProject project, String folder,
			String filenameInOS, String fileNameInProject) {
		IFile file;

		// Check to see if the file needs to go into a folder and create the
		// IFile object
		if (folder == null || folder.length() == 0) {
			file = project.getFile(fileNameInProject);
		} else {
			IFolder folder2 = project.getFolder(folder);
			file = folder2.getFile(fileNameInProject);
		}

		// If the file is not being linked to a file in the OS then create a new
		// empty file
		if (filenameInOS == null || filenameInOS.length() == 0) {
			filenameInOS = file.getRawLocation().toString();

			File newEmptyFile = new File(filenameInOS);

			try {
				newEmptyFile.createNewFile();
			} catch (IOException e) {
				Logger.log(e.getMessage(), e);
			}
		}

		// Create a link to the file
		try {
			if (isLocalPath(filenameInOS)) {
				IPath path = new Path(filenameInOS);
				file.createLink(path, IResource.ALLOW_MISSING_LOCAL, null);
			} else {
				file.create(null, false, null);
				IMarker marker = file.createMarker(IMarker.TASK);
				marker.setAttribute(IS_URL, true);
				marker.setAttribute(URL_STRING, filenameInOS);
			}

			FileValidationHelper.validateSysDefFile(file);
		} catch (CoreException e) {
			Logger.log(e.getMessage(), e);
		}
	}

	/**
	 * Updates the border shapes file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updateBorderShapesFiles(IProject project,
			String[] options) {
		updateResources(project, options, BORDER_SHAPES);

		// persist the data
		getPersistDataStore(project).setSelectedBorderShapesFiles(options);
	}

	/**
	 * Updates the border styles file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updateBorderStylesFiles(IProject project,
			String[] options) {
		updateResources(project, options, BORDER_STYLES);

		// persist the data
		getPersistDataStore(project).setSelectedBorderStylesFiles(options);
	}

	/**
	 * Updates the colours file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updateColoursFiles(IProject project, String[] options) {
		updateResources(project, options, COLOURS);

		// persist the data
		getPersistDataStore(project).setSelectedColoursFiles(options);
	}

	/**
	 * Updates the dependencies file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updateDependenciesFiles(IProject project,
			String[] options) {
		updateResources(project, options, DEPENDENCIES);

		// persist the data
		getPersistDataStore(project).setSelectedDependenciesFiles(options);
	}

	/**
	 * Updates the levels file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updateLevelsFiles(IProject project, String[] options) {
		updateResources(project, options, LEVELS);

		// persist the data
		getPersistDataStore(project).setSelectedLevelsFiles(options);
	}

	/**
	 * Updates the localisation file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updateLocalisationFiles(IProject project,
			String[] options) {
		updateResources(project, options, LOCALISATION);

		// persist the data
		getPersistDataStore(project).setSelectedLocalisationFiles(options);
	}

	/**
	 * Updates the patterns file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updatePatternsFiles(IProject project, String[] options) {
		updateResources(project, options, PATTERNS);

		// persist the data
		getPersistDataStore(project).setSelectedPatternsFiles(options);
	}

	/**
	 * Updates a resource within a specified folder within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param folderName
	 *            The folder in which the resource is to be updated
	 * @param option
	 *            Either a filename or AUTO_LEVEL
	 * @return void
	 */
	private static void updateResource(IProject project, String folderName,
			String option) {
		// The other arguments are checked by the callers
		if (folderName == null) {
			throw new IllegalArgumentException("Arguments cannot be null.");
		}

		// Create/Get the folder in the project
		IFolder folder = getFolderInProject(project, folderName);

		// If we were passed the empty string as the file name, just return,
		// otherwise
		// create the file
		if (option.length() > 0) {
			String newName;

			if (isLocalPath(option)) {
				String[] filenameParts = option.split("[\\\\/]");
				newName = filenameParts[filenameParts.length - 1];
			} else { // If option is a URL
				int beginIndex = option.lastIndexOf("/");
				newName = option.substring(beginIndex + 1, option.length());
			}

			if (!option.equals("Auto")) {
				// Create a link to the file
				try {
					IFile file = folder.getFile(newName);
					
					if (isLocalPath(option)) {
						IPath path = new Path(option);
						file.createLink(path, IResource.ALLOW_MISSING_LOCAL, null);
					}
					else {
						file.create(null, false, null);
						IMarker marker = file.createMarker(IMarker.TASK);
						marker.setAttribute(IS_URL, true);
						marker.setAttribute(URL_STRING, option);
					}

					// TODO:BRS:Remove if test when Shapes.xsd is available
					if (!folderName.equals("Shapes")) {
						FileValidationHelper.validateResourceFile(file);
					}
				} catch (CoreException e) {
					Logger.log(e.getMessage(), e);
				}
			}
		}
	}

	private static void updateResources(IProject project, String[] options,
			ResourcesEnums type) {
		if (project == null || options == null) {
			throw new IllegalArgumentException("Arguments cannot be null.");
		}

		String folderName = type.arg();

		// First delete any existing resources from their folder
		deleteResources(project, folderName);

		// If options is empty, we simply need to invoke updateResource() with
		// the empty
		// String to ensure that the corresponding folder is created and then
		// return.
		if (options.length == 0) {
			updateResource(project, folderName, "");
			return;
		}

		for (String option : options) {
			updateResource(project, folderName, option);
		}
	}

	/**
	 * Updates the S12 XML file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updateS12XmlFiles(IProject project, String[] options) {
		updateResources(project, options, S12_XML);

		// persist the data
		getPersistDataStore(project).setSelectedS12XmlFiles(options);
	}

	/**
	 * Updates the shapes file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updateShapesFiles(IProject project, String[] options) {
		updateResources(project, options, SHAPES);

		// persist the data
		getPersistDataStore(project).setSelectedShapesFiles(options);

	}

	/**
	 * Updates the system definition files within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param systemDefinitionFiles
	 *            The system definition files to be used by the project
	 * @param force
	 *            Forces new shortcuts to be created in the project for the
	 *            system definition files
	 * @return void
	 */
	public static void updateSystemDefinitionFiles(IProject project,
			String[] sysdefFiles, Boolean force) {
		HashMap<String, Integer> systemDefinitionFiles = new HashMap<String, Integer>();

		// Work out if we need to add any numbers to filenames to keep them
		// unique
		// It is not possible to have multiple files with the same name, so if
		// this does happen a
		// number is added to further instances, e.g afile, afile(2), afile(3)
		// etc.
		HashMap<String, Integer> systemDefinitionFilesCounts = new HashMap<String, Integer>();

		java.util.regex.Pattern p = Pattern.compile("\\((\\d+)\\)\\.xml",
				Pattern.CASE_INSENSITIVE);

		try {
			IResource[] members;
			members = project.members();
			for (IResource res : members) {

				java.util.regex.Matcher m = p.matcher(res.getName());

				if (m.find()) {
					int num = Integer.valueOf(m.group(1));
					String simpleName = m.replaceAll(".xml");
					if (!systemDefinitionFilesCounts.containsKey(simpleName
							.toLowerCase())
							|| systemDefinitionFilesCounts.get(simpleName
									.toLowerCase()) < ++num) {
						systemDefinitionFilesCounts.put(simpleName
								.toLowerCase(), ++num);
					}
				} else {
					if (!systemDefinitionFilesCounts.containsKey(res.getName()
							.toLowerCase())) {
						systemDefinitionFilesCounts.put(res.getName()
								.toLowerCase(), 2);
					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		// Get the existing system definition files and put them into a hashmap
		IScopeContext projectScope = new ProjectScope(project);
		PersistentDataStore projectStore = new PersistentDataStore(projectScope
				.getNode(Activator.PLUGIN_ID));

		for (String sysdefFile : projectStore.getSystemDefinitionFiles()) {
			systemDefinitionFiles.put(sysdefFile.toLowerCase(), 0);
		}

		for (String sysdefFile : sysdefFiles) {
			// If the sysdef file has not been added or removed set to 1
			if (systemDefinitionFiles.containsKey(sysdefFile.toLowerCase())
					&& force != true) {
				systemDefinitionFiles.put(sysdefFile.toLowerCase(), 1);
			}
			// Otherwise the file must be a new file
			else {
				systemDefinitionFiles.put(sysdefFile.toLowerCase(), 2);
			}
		}

		for (String sysdefFile : systemDefinitionFiles.keySet()) {
			// All files with a value of 0 must be deleted
			if (systemDefinitionFiles.get(sysdefFile) == 0) {
				IResource[] members;

				try {
					members = project.members();

					for (IResource res : members) {
						if (isLocalPath(sysdefFile)) {
							if (res.isLinked()) {
								if (sysdefFile.equalsIgnoreCase(res
										.getRawLocation().toOSString())) {
									res.delete(true, null);
								}
							}
						} else { // If it is a URL
							if (res instanceof IFile) {
								String urlString = getResourceUrl((IFile) res);

								if (urlString != null
										&& sysdefFile
												.equalsIgnoreCase(urlString)) {
									res.delete(true, null);
								}
							}
						}
					}

				} catch (CoreException e) {
					Logger.log(e.getMessage(), e);
				}
			} else if (systemDefinitionFiles.get(sysdefFile.toLowerCase()) == 2) {
				// All files with a value if 2 must be added
				String newName;

				if (isLocalPath(sysdefFile)) {
					String[] filenameParts = sysdefFile.split("[\\\\/]");
					newName = filenameParts[filenameParts.length - 1];
				} else { // If option is a URL
					int beginIndex = sysdefFile.lastIndexOf("/");
					newName = sysdefFile.substring(beginIndex + 1, sysdefFile
							.length());
				}

				int i = 1;

				if (systemDefinitionFilesCounts.containsKey(newName
						.toLowerCase())) {
					i = systemDefinitionFilesCounts.get(newName.toLowerCase());

					newName = newName.substring(0, newName.length() - 4)
							+ "("
							+ i
							+ ")"
							+ newName.substring(newName.length() - 4, newName
									.length());
				}

				systemDefinitionFilesCounts.put(newName.toLowerCase(), ++i);

				makeFileShortcut(project, null, sysdefFile, newName);
			}
			// Files with a value of 1 are unchanged so are ignored
		}

		// Persist the system definition files
		projectStore.setSystemDefinitionFiles(sysdefFiles);
	}

	/**
	 * Updates the system info file resources within the project.
	 * 
	 * @param project
	 *            The project to amend
	 * @param options
	 *            An array of filenames or the empty array if there are no files
	 * @return void
	 */
	public static void updateSystemInfoFiles(IProject project, String[] options) {
		updateResources(project, options, SYSTEM_INFO);

		// persist the data
		getPersistDataStore(project).setSelectedSystemInfoFiles(options);
	}

}
