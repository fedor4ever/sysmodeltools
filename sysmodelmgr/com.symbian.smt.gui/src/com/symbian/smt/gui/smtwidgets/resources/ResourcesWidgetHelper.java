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
// ResourcesWidgetHelper
//



package com.symbian.smt.gui.smtwidgets.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;

import com.symbian.smt.gui.Helper;
import com.symbian.smt.gui.ResourcesEnums;

/**
 * @author barbararosi-schwartz
 * 
 */
public class ResourcesWidgetHelper {

	static boolean contains(
			java.util.List<CheckableResourceFilename> checkableFilenames,
			String filename) {
		if (checkableFilenames == null || filename == null) {
			throw new IllegalArgumentException("Arguments cannot be null.");
		}

		java.util.List<String> filenames = new ArrayList<String>();

		for (CheckableResourceFilename checkableFilename : checkableFilenames) {
			filenames.add(checkableFilename.getFilename());
		}

		return filenames.contains(filename);
	}

	static CheckableResourceFilename filename2checkableFilename(
			String name,
			ResourcesEnums resourceType,
			HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap) {
		java.util.List<CheckableResourceFilename> checkableFilenames = resourceFilesMap
				.get(resourceType);

		if (checkableFilenames == null) {
			throw new IllegalArgumentException(
					"Could not find any elements of type [" + resourceType
							+ "] in resourceFilesMap.");
		}

		CheckableResourceFilename checkableFilename = null;

		Iterator<CheckableResourceFilename> iter = checkableFilenames
				.iterator();

		while (iter.hasNext()) {
			CheckableResourceFilename crf = iter.next();

			if (crf.getFilename().equals(name)) {
				checkableFilename = crf;
				break;
			}

		}

		if (checkableFilename == null) {
			throw new IllegalArgumentException(
					"Could not find any file with name [" + name
							+ "] in resourceFilesMap.");
		}

		return checkableFilename;
	}

	static java.util.List<CheckableResourceFilename> getCheckableResourceFilenames(
			ResourcesEnums resourceType,
			HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap) {
		return resourceFilesMap.get(resourceType);
	}

	static String[] getCheckedFilenames(
			ResourcesEnums resourceType,
			HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap) {
		java.util.List<CheckableResourceFilename> checkableFilenames = getCheckableResourceFilenames(
				resourceType, resourceFilesMap);
		java.util.List<String> checkedNames = new ArrayList<String>();

		for (CheckableResourceFilename checkableFilename : checkableFilenames) {
			if (checkableFilename.isChecked()) {
				checkedNames.add(checkableFilename.getFilename());
			}
		}

		return Helper.toArrayOfStrings(checkedNames);
	}

	static CheckableResourceFilename[] getCheckedResourceFilenames(
			ResourcesEnums resourceType,
			HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap) {
		java.util.List<CheckableResourceFilename> checkableFilenames = getCheckableResourceFilenames(
				resourceType, resourceFilesMap);
		java.util.List<CheckableResourceFilename> checkedResourceFilenames = new ArrayList<CheckableResourceFilename>();

		for (CheckableResourceFilename checkableFilename : checkableFilenames) {
			if (checkableFilename.isChecked()) {
				checkedResourceFilenames.add(checkableFilename);
			}
		}

		return toArrayOfCheckableResourceFilenames(checkedResourceFilenames);
	}

	static String[] getFilenames(
			ResourcesEnums resourceType,
			HashMap<ResourcesEnums, java.util.List<CheckableResourceFilename>> resourceFilesMap) {
		java.util.List<CheckableResourceFilename> checkableFilenames = resourceFilesMap
				.get(resourceType);
		String[] filenames = new String[checkableFilenames.size()];
		int i = 0;

		for (CheckableResourceFilename checkableFilename : checkableFilenames) {
			filenames[i] = checkableFilename.getFilename();
			i++;
		}

		return filenames;
	}

	static ResourcesEnums getSelectedResourceType(ListViewer resourceTypesViewer) {
		return (ResourcesEnums) ((StructuredSelection) resourceTypesViewer
				.getSelection()).getFirstElement();
	}

	/**
	 * Converts the List of CheckableResourceFilename objects into a
	 * CheckableResourceFilename array. The opposite conversion is also provided
	 * by this class with method
	 * toListofCheckableResourceFilenames(CheckableResourceFilename []).
	 * 
	 * @param list
	 *            the java.util.List of CheckableResourceFilename objects to be
	 *            converted (may be empty but cannot be null)
	 * @return the corresponding CheckableResourceFilename array or an empty
	 *         array if list is empty.
	 * @see #toListofCheckableResourceFilenames(CheckableResourceFilename [])
	 */
	static final CheckableResourceFilename[] toArrayOfCheckableResourceFilenames(
			java.util.List<CheckableResourceFilename> list) {
		if (list == null) {
			throw new IllegalArgumentException("Parameter list cannot be null.");
		}

		CheckableResourceFilename[] array = new CheckableResourceFilename[list
				.size()];

		return list.toArray(array);
	}

}
