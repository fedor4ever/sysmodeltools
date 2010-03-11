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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A helper class for general usage.
 * 
 * @author barbararosi-schwartz
 * 
 */
public final class Helper {

	/**
	 * Concatenates the strings in the provided array using the provided
	 * separator and returns the concatenated string.
	 * 
	 * @param separator
	 *            the separator to be used for the concatenation
	 * @param items
	 *            the array of strings to be concatenated
	 * @return the concatenated String or an empty String if the provided array
	 *         is null or empty.
	 */
	public static String concatenateString(String separator, String[] items) {
		StringBuilder joinedString = new StringBuilder();

		if (items != null) {
			for (String item : items) {
				joinedString.append(item);
				joinedString.append(separator);
			}

			if (joinedString.length() > 0) {
				joinedString.deleteCharAt(joinedString.length() - 1);
			}
		}

		return joinedString.toString();
	}

	/**
	 * Adds the absolute location represented by the provided rootLocation to
	 * the filenames
	 * 
	 * @param filenames
	 *            the String representing the relative filenames, separated by
	 *            the provided separator
	 * @param rootLocation
	 *            the String representing the location of the root of the
	 *            filesystem that will turn the relative paths into absolute
	 *            ones
	 * @param separator
	 *            the String representing the separator that separates the file
	 *            names
	 * @return the String representing the separator separated absolute
	 *         filenames
	 */
	public static String relative2AbsolutePaths(String filenames,
			String rootLocation, String separator) {
		String[] relativeNames = Helper.splitString(filenames, separator);
		String[] absoluteNames = new String[relativeNames.length];
		int i = 0;

		for (String name : relativeNames) {
			if (name.startsWith(".")) {
				try {
					name = new File(rootLocation + name).getCanonicalPath();
				} catch (IOException e) {
					Logger.log(e.getMessage(), e);
				}
			}

			absoluteNames[i] = name;
			i++;
		}

		return concatenateString(separator, absoluteNames);
	}

	/**
	 * Splits the provided concatenated string into its constituent parts based
	 * upon the provided separator and returns an array of the constituents. If
	 * the provided input is null or empty, returns an empty array.
	 * 
	 * @param concatenatedString
	 *            a String containing components separated by the given
	 *            separator
	 * @param separator
	 *            the separator used
	 * @return an array of String with the separate components or an empty array
	 *         if <code>concatenatedString</code> is null or empty
	 */
	public static String[] splitString(String concatenatedString,
			String separator) {
		if (separator.equals("|")) {
			separator = "\\|";
		}

		if (concatenatedString != null && concatenatedString.length() > 0) {
			return concatenatedString.split(separator);
		} else {
			String[] empty = {};
			return empty;
		}
	}

	/**
	 * Converts the List of String objects into a String array. The opposite
	 * conversion is also provided by this class with method
	 * toListofStrings(String []).
	 * 
	 * @param list
	 *            the java.util.List of String objects to be converted (may be
	 *            empty but cannot be null)
	 * @return the corresponding String array or an empty array if list is
	 *         empty.
	 * @see #toListofStrings(String [])
	 */
	public static final String[] toArrayOfStrings(List<String> list) {
		if (list == null) {
			throw new IllegalArgumentException("Parameter list cannot be null.");
		}

		String[] array = new String[list.size()];

		return list.toArray(array);
	}

	/**
	 * Converts the provided ResourcesEnums array into a java.util.List
	 * containing the args of the provided ResourceEnums.
	 * 
	 * @param array
	 *            the array of ResourcesEnums objects to be converted (may be
	 *            empty but cannot be null)
	 * @return the corresponding List of String args or an empty List if array
	 *         is empty
	 */
	public static final List<String> toListOfStrings(ResourcesEnums[] array) {
		if (array == null) {
			throw new IllegalArgumentException(
					"Parameter array cannot be null.");
		}

		ArrayList<String> arrayList = new ArrayList<String>();

		for (ResourcesEnums re : array) {
			arrayList.add(re.arg());
		}

		return arrayList;
	}

	/**
	 * Converts the provided String array into a java.util.List. The opposite
	 * conversion is also provided by this class with method
	 * toArrayOfStrings(List).
	 * 
	 * @param array
	 *            the array of String objects to be converted (may be empty but
	 *            cannot be null)
	 * @return the corresponding List of String objects or an empty List if
	 *         array is empty
	 * @see #toArrayOfStrings(List<String>)
	 */
	public static final List<String> toListOfStrings(String[] array) {
		if (array == null) {
			throw new IllegalArgumentException(
					"Parameter array cannot be null.");
		}

		ArrayList<String> arrayList = new ArrayList<String>();

		for (String s : array) {
			arrayList.add(s);
		}

		return arrayList;
	}

}
