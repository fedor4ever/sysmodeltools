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
// ResourceFileSelectionValidator
//



package com.symbian.smt.gui.smtwidgets.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.symbian.smt.gui.Logger;
import com.symbian.smt.gui.ResourceFileValidator;
import com.symbian.smt.gui.ResourcesEnums;
import com.symbian.smt.gui.smtwidgets.IXmlFileInputValidator;
import com.symbian.smt.gui.smtwidgets.InvalidPathException;

/**
 * @author barbararosi-schwartz
 * 
 */
public class ResourceFileSelectionValidator extends ResourceFileValidator
		implements IXmlFileInputValidator {

	private List<CheckableResourceFilename> existingFilenames;

	public ResourceFileSelectionValidator(ResourcesEnums selectedResourceType,
			List<CheckableResourceFilename> filenames) {
		super(selectedResourceType);

		this.existingFilenames = filenames;
	}

	/* (non-Javadoc)
	 * @see com.symbian.smt.gui.smtwidgets.IFileInputValidator#isFileReadable(java.lang.String)
	 */
	public String isFileReadable(String filePath) {
		String errorMessage = null;
		File inFile = new File(filePath);

		if (!inFile.canRead()) {
			return "Selected file cannot be read.";
		}

		return errorMessage;
	}

	/* (non-Javadoc)
	 * @see com.symbian.smt.gui.smtwidgets.IFileInputValidator#isUrl(java.lang.String)
	 */
	public boolean isUrl(String filePath) throws InvalidPathException {
		int index = filePath.indexOf(':');

		if (index == -1 || index == 1) {
			return false;
		} else if (index > 1) {
			return true;
		} else {
			throw new InvalidPathException("Unexpected file path format.");
		}
	}

	/* (non-Javadoc)
	 * @see com.symbian.smt.gui.smtwidgets.IFileInputValidator#isUrlResourceReadable(java.lang.String)
	 */
	public String isUrlResourceReadable(String filePath) {
		String errorMessage = null;
		InputStream urlInputStream = null;

		try {
			URL fileURL = new URL(filePath);
			URLConnection connection = fileURL.openConnection();
			String contentType = null;
			
			try {
				contentType = connection.getContentType();
			} catch (Exception e) {
				return "Resource at specified URL cannot be found.";
			}
			
			if (contentType == null) {
				return "Resource at specified URL cannot be found.";
			}

			if (!contentType.endsWith("xml")) {
				return "Specified URL is not an XML document.";
			}

			urlInputStream = connection.getInputStream();

			if (urlInputStream == null) {
				errorMessage = "Resource at specified URL cannot be read.";
			}
			/*
			 * Snippet below is just for diagnostics else { int bytes; while
			 * ((bytes = urlInputStream.available()) > 0) { byte[] b = new
			 * byte[bytes];
			 * 
			 * urlInputStream.read(b); String s = new String(b);
			 * 
			 * System.err.println(s); } }
			 */
		} catch (IllegalArgumentException e) {
			errorMessage = "Resource at specified URL cannot be read.";
		} catch (MalformedURLException e) {
			errorMessage = "Specified URL is not a valid URL.";
		} catch (IOException e) {
			errorMessage = "Resource at specified URL cannot be reached.";
		} finally {
			if (urlInputStream != null) {
				try {
					urlInputStream.close();
				} catch (IOException ignore) {
					Logger.log(ignore.getMessage(), ignore);
				}
				urlInputStream = null;
			}
		}

		return errorMessage;
	}

	private String isUrlValid(String url) {
		String errorMessage = null;

		try {
			URL inputUrl = new URL(url);
			inputUrl.openConnection();
		} catch (MalformedURLException e) {
			errorMessage = "Specified URL is not a valid URL.";
		} catch (IOException e) {
			errorMessage = "Resource at specified URL cannot be reached.";
		}

		return errorMessage;
	}

	/*
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
	 */
	public String isValid(String filePath) {
		if (filePath == null || filePath.length() == 0) {
			return "";
		} else {
			return validateResourceWhileUserTypes(filePath);
		}
	}

	/* (non-Javadoc)
	 * @see com.symbian.smt.gui.smtwidgets.IXmlFileInputValidator#isXmlValid(java.lang.String)
	 */
	public String isXmlValid(String filePath) {
		return validateXml(filePath);
	}

	private String validateResourceWhileUserTypes(String filePath) {
		String errorMessage = null;

		// First check path is not UNC
		if (filePath.startsWith(File.separator)) {
			return "UNC paths are not compatible with the System Model Manager";
		}

		// Then check that filename (path) is not duplicate
		// by checking name against table
		if (existingFilenames != null) {
			if (ResourcesWidgetHelper.contains(existingFilenames, filePath)) {
				return "The selected file has already been assigned.";
			}
		}
		
		// Then check that path is appropriate
		try {
			if (isUrl(filePath)) {
				errorMessage = isUrlValid(filePath);
			} else {
				errorMessage = isFileReadable(filePath);
			}
		} catch (InvalidPathException e) {
			errorMessage = e.getMessage();
		}

		return errorMessage;
	}
}
