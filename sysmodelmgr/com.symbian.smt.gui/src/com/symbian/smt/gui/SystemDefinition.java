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

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SystemDefinition {

	/**
	 * Determines whether the System Definition file is likely to be valid for the System Model Generator
	 * Even if this function fails the SMG may still accept the file.
	 * @param filename - the path to the System Definition file
	 * @throws SystemDefinitionValidationException - Thrown if the parsing of the document fails. If the document parses
	 * correctly but does not appear to be a System Definition file a {@link SystemDefinitionValidationFatalException} is thrown.
	 */
	public static void checkValidSystemDefinitionFile(String filename) throws SystemDefinitionValidationException {
		Document doc;
		try {
			doc = createDocument(filename);
		} catch (ParserConfigurationException e) {
			throw new SystemDefinitionValidationException("Problem found when parsing "+filename+".", e);
		} catch (SAXException e) {
			throw new SystemDefinitionValidationException("Problem found when parsing "+filename+".", e);
		} catch (IOException e) {
			throw new SystemDefinitionValidationException("Problem found when reading "+filename+".", e);
		}

		// Check that the file is a system definition file
		if (doc.getDoctype() != null && !doc.getDoctype().getName().equals("SystemDefinition")) {
			throw new SystemDefinitionValidationFatalException(filename + " has the doctype "+doc.getDoctype()+". Where specified, the doctype should be \"SystemDefinition\".");
		}
	}

	public static int coreOSType(String filename)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		Document doc = createDocument(filename);

		int type = 0;

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		XPathExpression expr = xpath
				.compile("count(//layer[@name='Hardware']) > 0");
		Object result = expr.evaluate(doc, XPathConstants.BOOLEAN);

		if ((Boolean) result) {
			type = 1;
		}

		expr = xpath.compile("count(//layer[@name='HAL']) > 0");
		Object result2 = expr.evaluate(doc, XPathConstants.BOOLEAN);

		if ((Boolean) result2) {
			type = 2;
		}

		return type;
	}

	private static Document createDocument(final String filename)
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
		Document doc = builder.parse(filename);

		return doc;
	}

}
