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
// ${file_name}
// 
//

package com.symbian.smt.gui.smtwidgets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.symbian.smt.gui.Logger;
import com.symbian.smt.gui.XmlFileValidator;

/**
 * @author barbararosi-schwartz
 * 
 */
public class SystemDefinitionFileSelectionValidator extends
		XmlFileValidator implements IXmlFileInputValidator {

	private List<String> sysdefFilenames;
	private InputStream urlInputStream;

	public SystemDefinitionFileSelectionValidator(List<String> filenames) {
		super();
		
		this.sysdefFilenames = filenames;
	}

	private void closeInputStream() {
		if (urlInputStream != null) {
			try {
				urlInputStream.close();
			} catch (IOException ignore) {
				Logger.log(ignore.getMessage(), ignore);
			}
			urlInputStream = null;
		}
	}

	protected Document createDocument(final String filePath)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true);
		domFactory.setValidating(false);

		DocumentBuilder builder = domFactory.newDocumentBuilder();

		ErrorHandler errorHandler = new ErrorHandler() {
			public void error(SAXParseException exception)
					throws SAXParseException {
				throw exception;
			}

			public void fatalError(SAXParseException exception)
					throws SAXParseException {
				throw exception;
			}

			public void warning(SAXParseException exception)
					throws SAXParseException {
				throw exception;
			}
		};

		builder.setErrorHandler(errorHandler);
		Document doc = null;

		try {
			if (isUrl(filePath)) {
				if (urlInputStream != null) {
					doc = builder.parse(urlInputStream);
					closeInputStream();
				}
			} else {
				doc = builder.parse(filePath);
			}
		} catch (InvalidPathException e) {
			// This cannot happen as it has already been checked while the user
			// was typing in
		}

		return doc;
	}

	private String isFilePathValid(String filePath) {
		return isFileReadable(filePath);
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

		try {
			URL fileURL = new URL(filePath.trim());
			URLConnection connection = fileURL.openConnection();
			String contentType = connection.getContentType();

			if (contentType == null) {
				return "System definition resource at specified URL cannot be read.";
			}
			
			if (!contentType.endsWith("xml")) {
				return "Specified URL is not an XML document.";
			}

			urlInputStream = connection.getInputStream();

			if (urlInputStream == null) {
				errorMessage = "System definition resource at specified URL cannot be read.";
			}
		} catch (IllegalArgumentException e) {
			closeInputStream();
			errorMessage = "System definition resource at specified URL cannot be read.";
		} catch (MalformedURLException e) {
			closeInputStream();
			errorMessage = "Specified URL is not a valid URL.";
		} catch (IOException e) {
			closeInputStream();
			errorMessage = "System definition resource at specified URL cannot be reached.";
		}

		return errorMessage;
	}

	private String isUrlValid(String url) {
		String errorMessage = null;

		try {
			URL inputUrl = new URL(url);
			inputUrl.openConnection();
		} catch (MalformedURLException e) {
			closeInputStream();
			errorMessage = "Specified URL is not a valid URL.";
		} catch (IOException e) {
			closeInputStream();
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
		if ((sysdefFilenames != null) && (sysdefFilenames.contains(filePath))) {
			return "The selected file has already been assigned.";
		}

		// Then check that path is appropriate
		try {
			if (isUrl(filePath)) {
				errorMessage = isUrlValid(filePath);
			} else {
				errorMessage = isFilePathValid(filePath);
			}
		} catch (InvalidPathException e) {
			errorMessage = e.getMessage();
		}

		return errorMessage;
	}
}
