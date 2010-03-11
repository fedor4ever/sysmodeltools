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
import java.util.HashMap;

/**
 * @author barbararosi-schwartz
 * 
 */
public class ResourceFileValidator extends XmlFileValidator {
	/**
	 * The Map that caches all defined resource schema files, keyed by the
	 * ResourcesEnums enums.
	 */
	private static final HashMap<ResourcesEnums, String> resourceSchemaFilesMap = new HashMap<ResourcesEnums, String>();

	static {
		for (ResourcesEnums type : ResourcesEnums.values()) {
			switch (type) {
			case BORDER_SHAPES:
				resourceSchemaFilesMap
						.put(type,
								"./resources/xsd/Border-shapes.xsd");
				break;

			case BORDER_STYLES:
				resourceSchemaFilesMap
						.put(type,
								"./resources/xsd/Border-styles.xsd");
				break;

			case COLOURS:
				resourceSchemaFilesMap
						.put(type,
								"./resources/xsd/Colours.xsd");
				break;

			case DEPENDENCIES:
				// No need for schema validation
				break;

			case LEVELS:
				resourceSchemaFilesMap
						.put(type,
								"./resources/xsd/Levels.xsd");
				break;

			case LOCALISATION:
				resourceSchemaFilesMap
						.put(type,
								"./resources/xsd/Localisation.xsd");
				break;

			case PATTERNS:
				resourceSchemaFilesMap
						.put(type,
								"./resources/xsd/Patterns.xsd");
				break;

			case SHAPES:
				// TODO:BRS: Need to get the Shapes schema right before
				// validating with it.
				// resourceSchemaFilesMap.put(type,
				// "./../SystemModelGenerator/resources/xsd/Shapes.xsd");
				break;

			case SYSTEM_INFO:
				// No need for schema validation
				break;

			case S12_XML:
				// No need for schema validation
				break;

			default:
				throw new RuntimeException("Unknown resource type ["
						+ type.arg() + "]");
			}
		}
	}

	private ResourcesEnums selectedResourceType;

	/**
	 * 
	 */
	public ResourceFileValidator(ResourcesEnums selectedResourceType) {
		super();

		this.selectedResourceType = selectedResourceType;
	}

	// TODO:BRS:This method is incomplete and currently unused. It is related to 
	// comparing 2 different border shapes and making sure elements are not repeated 
	// between files (the same should apply to patterns).
	// The return type in the signature should also be changed to a list of border
	// shape item objects.
//	private void getBorderShapesItems(String filePath) {
//		try {
//			JAXBContext context = JAXBContext.newInstance(Values.class);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			// Values values = unmarshaller.unmarshal(File or URL);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//
//	}

	protected File getSchemaFile() {
		String schemaFilePath = resourceSchemaFilesMap
				.get(selectedResourceType);
		File schemaFile = new File(Helper.relative2AbsolutePaths(
				schemaFilePath, smgFolder, "|"));

		return schemaFile;
	}

	public String validateXml(String filePath) {
		String errorMessage = super.validateXml(filePath);

		if (errorMessage == null) {
			if (selectedResourceType.equals(ResourcesEnums.BORDER_SHAPES)
					|| selectedResourceType.equals(ResourcesEnums.PATTERNS)) {
				// Finally, if we have multiple files for this resource, check
				// about duplicates
				// TODO:BRS:This piece of code is unfinished. If it is required to check 
				// that there are no duplicate items across multiple files, use method 
				// getBorderShapesItems() above and create similar getPatternsItem() method
				// errorMessage = do special validation
			}
		}

		return errorMessage;
	}
}
