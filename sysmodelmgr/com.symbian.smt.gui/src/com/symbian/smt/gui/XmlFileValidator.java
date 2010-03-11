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
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author barbararosi-schwartz
 * 
 */
public class XmlFileValidator {

	protected static String smgFolder = ""; // The location of the System Model
											// Generator

	public XmlFileValidator() {
		final ResourceBundle resourceBundle = ResourceBundle.getBundle(
				"location", Locale.getDefault(), this.getClass()
						.getClassLoader());

		smgFolder = resourceBundle.getString("location");
	}

	protected Document createDocument(final String filePath)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true);
		domFactory.setValidating(false);

		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File schemaFile = getSchemaFile();
		
		// schemaFile could be null if there is no schema for a specific file
		// (as is the case for the System Definition xml file)
		if (schemaFile != null && schemaFile.exists()) {
			domFactory.setSchema(schemaFactory.newSchema(getSchemaFile()));
		}

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

		Document doc = builder.parse(filePath);

		return doc;
	}

	/**
	 * The default implementation of this method
	 * returns null, meaning that this Validator
	 * is not aware of any schema definition for the 
	 * associated resource.
	 * 
	 * @return	the appropriate schema definition file
	 * 			or null if there is no schema.
	 */
	protected File getSchemaFile() {
		return null;
	}

	public String validateXml(String filePath) {
		String errorMessage = null;

		try {
			createDocument(filePath);
		} catch (ParserConfigurationException e) {
			errorMessage = e.getMessage();
		} catch (SAXException e) {
			errorMessage = e.getMessage();
		} catch (IOException e) {
			errorMessage = e.getMessage();
		}

		return errorMessage;
	}
}
