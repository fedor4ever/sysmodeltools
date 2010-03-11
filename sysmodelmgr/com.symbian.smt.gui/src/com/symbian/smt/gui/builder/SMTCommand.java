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

package com.symbian.smt.gui.builder;

import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.BORDER_SHAPES_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.BORDER_STYLES_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.COLOURS_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.COPYRIGHT_TEXT;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.DEPENDENCIES_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.DISTRIBUTION_TEXT;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.FILTER_HAS_ITEMS;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.FIX_ITEM_SIZE;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.HIGHTLIGHT_CORE_OS;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.IGNORE_ITEMS;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.LEVELS_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.LEVEL_OF_DETAIL;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.LOCALISATION_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.MODEL_NAME;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.MODEL_VERSION;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.MODEL_VERSION_TEXT;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.OUTPUT_FILE;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.PATTERNS_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.PRINTED_DPI;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.S12_XML_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SHAPES_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SUPPRESS_MOUSE_OVER_EFFECT;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SYSTEM_DEFINITION_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SYSTEM_INFO_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SYSTEM_NAME;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SYSTEM_VERSION;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.TEMPDIR;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.WARNING_LEVEL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.widgets.Display;
import org.xml.sax.SAXException;

import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.Logger;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.SystemDefinition;
import com.symbian.smt.gui.SystemDefinitionValidationException;
import com.symbian.smt.gui.SystemDefinitionValidationFatalException;
import com.symbian.smt.gui.views.ConsoleOutput;

public class SMTCommand {

	private static String SMG_FOLDER = ""; // The location of the System Model
											// Generator
	private static String SMT_COMMAND = ""; // The perl script to run for the
											// System Model Generator
	final static String TEMP_FOLDER = ".svg_temp"; // Folder names stating with
													// a . will not be displayed
													// in the project navigator
	private IFolder svgTempFolder;
	private IProject project;
	private ArrayList<String> command = new ArrayList<String>();;

	private PersistentDataStore defaultStore;
	private PersistentDataStore instanceStore;
	private PersistentDataStore projectStore;

	private Pattern ampersandPattern = Pattern.compile("&",
			Pattern.CASE_INSENSITIVE);
	private Pattern lessThanPattern = Pattern.compile("<",
			Pattern.CASE_INSENSITIVE);
	private Pattern greaterThanPattern = Pattern.compile(">",
			Pattern.CASE_INSENSITIVE);
	private Pattern singleQuotePattern = Pattern.compile("'",
			Pattern.CASE_INSENSITIVE);
	private Pattern doubleQuotePattern = Pattern.compile("\"",
			Pattern.CASE_INSENSITIVE);

	public SMTCommand(IProject project) {
		this.project = project;
		svgTempFolder = project.getFolder(TEMP_FOLDER);

		final ResourceBundle resourceBundle = ResourceBundle.getBundle(
				"location", Locale.getDefault(), this.getClass()
						.getClassLoader());

		SMG_FOLDER = resourceBundle.getString("location");

		try {
			SMT_COMMAND = new File(SMG_FOLDER + File.separator + "SysModGen.pl")
					.getCanonicalPath();
		} catch (IOException e) {
			Logger.log(e.getMessage(), e);
		}
	}

	/**
	 * Generates the command line string for the System Model Toolkit
	 * 
	 * @return List<String> Arguments for the CLI
	 */
	public List<String> generateCommand() {
		// Set up access to the persistent data stores
		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);
		defaultStore = new PersistentDataStore(defaultNode);

		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);
		instanceStore = new PersistentDataStore(instanceNode, defaultNode);

		IScopeContext projectScope = new ProjectScope(project);
		IEclipsePreferences projectNode = projectScope
				.getNode(Activator.PLUGIN_ID);
		projectStore = new PersistentDataStore(projectNode);

		// Required to use the SMT script
		command.add("perl");
		command.add(SMT_COMMAND);

		// Add the system definition files
		String[] sysDefFiles = projectStore.getSystemDefinitionFiles();
		
		// The while loop below protects against concurrency conditions which
		// are encountered when the sys def file is a URL resource and
		// when we are creating a new project via the NewSMTProjectWizard.
		while (sysDefFiles.length == 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException ignore) {
			}
			
			sysDefFiles = projectStore.getSystemDefinitionFiles();
		}

		command.add(SYSTEM_DEFINITION_FILES.arg());

		// Check that the system definition files are valid
		// Only fatal errors cause the build attempt to be aborted.
		//
		for (String filename : sysDefFiles) {
			try {
				SystemDefinition.checkValidSystemDefinitionFile(filename);
			} catch (SystemDefinitionValidationFatalException e1) {
				writeToConsoleOutput("Error: " + filename
						+ " is not a valid system definition file:\n"
						+ e1.getMessage());
				Logger.log("Validation of system definition file ("+filename+") failed.", e1);
				return null;
			} catch (SystemDefinitionValidationException e1) {
				Logger.log("Validation of system definition file ("+filename+") failed.", e1);
			} 
		}

		// There may be multiple system definition files, if there are they need
		// to be joined with a ,
		if (sysDefFiles.length == 1) {
			command.add(prepareArg(sysDefFiles[0]));
		} else {
			StringBuilder sysDefJoined = new StringBuilder();

			for (String file : sysDefFiles) {
				sysDefJoined.append(file);
				sysDefJoined.append(",");
			}

			command.add(prepareArg(sysDefJoined.toString()));
		}

		// Add the resources
		// Default files in this context mean file the user selected
		handleResource(SHAPES_FILES, projectStore.getSelectedShapesFiles());
		handleResource(LEVELS_FILES, projectStore.getSelectedLevelsFiles());
		handleResource(SYSTEM_INFO_FILES, projectStore
				.getSelectedSystemInfoFiles());
		handleResource(DEPENDENCIES_FILES, projectStore
				.getSelectedDependenciesFiles());
		handleResource(COLOURS_FILES, projectStore.getSelectedColoursFiles());
		handleResource(BORDER_SHAPES_FILES, projectStore
				.getSelectedBorderShapesFiles());
		handleResource(PATTERNS_FILES, projectStore.getSelectedPatternsFiles());
		handleResource(LOCALISATION_FILES, projectStore
				.getSelectedLocalisationFiles());
		handleResource(BORDER_STYLES_FILES, projectStore
				.getSelectedBorderStylesFiles());
		handleResource(S12_XML_FILES, projectStore.getSelectedS12XmlFiles());

		// Add the model labels
		command.add(COPYRIGHT_TEXT.arg());
		command.add(prepareArg(projectStore.getCopyrightText()));

		command.add(SYSTEM_NAME.arg());
		command.add(prepareArg(projectStore.getSystemName()));

		command.add(DISTRIBUTION_TEXT.arg());
		command.add(prepareArg(projectStore.getSelectedDistributionText()));

		command.add(MODEL_NAME.arg());
		command.add(prepareArg(projectStore.getModelName()));

		command.add(MODEL_VERSION.arg());
		command.add(prepareArg(projectStore.getModelVersion()));

		command.add(MODEL_VERSION_TEXT.arg());
		command.add(prepareArg(projectStore.getSelectedModelVersionText()));

		command.add(SYSTEM_VERSION.arg());
		command.add(prepareArg(projectStore.getSystemVersion()));

		// Add the model control settings
		command.add(HIGHTLIGHT_CORE_OS.arg());
		if (projectStore.getHighlightCoreOS().toString().equalsIgnoreCase(
				"true")) {
			command.add("on");
		} else {
			command.add("false");
		}

		command.add(LEVEL_OF_DETAIL.arg());
		command.add(prepareArg(projectStore.getLevelOfDetail()));

		String dpi = projectStore.getSelectedPrintedDpi();

		// The dpi option is to be added only if the user
		// selected or typed in an option other than ""
		if ((dpi != null) && (!dpi.equals(""))) {
			command.add(PRINTED_DPI.arg());
			command.add(prepareArg(dpi));
		}

		if (projectStore.getSuppressMouseOverEffect()) {
			command.add(SUPPRESS_MOUSE_OVER_EFFECT.arg());
		}

		// The fix item size option is to be added only if the user
		// checked the corresponding check box
		if (projectStore.getFixItemSize()) {
			command.add(FIX_ITEM_SIZE.arg());
			command.add("fixed");
		}

		// Filter has Items
		command.add(FILTER_HAS_ITEMS.arg());

		String[] filterHasItems = projectStore.getFilterHasItems();

		// No command line argument if there are no filter-has keywords
		// If there are multiple filter has items, they need to be joined with a
		// ,
		if (filterHasItems.length > 0) {
			if (filterHasItems.length == 1) {
				command.add(prepareArg(filterHasItems[0]));
			} else {
				StringBuilder filterItemsJoined = new StringBuilder();

				for (String filter : filterHasItems) {
					filterItemsJoined.append(filter);
					filterItemsJoined.append(",");
				}

				filterItemsJoined.deleteCharAt(filterItemsJoined.length() - 1);
				command.add(prepareArg(filterItemsJoined.toString()));
			}
		}

		// Ignore Items
		List<String[]> ignoreItems = projectStore.getIgnoreItems();

		StringBuilder ignoreItemsJoined = new StringBuilder();

		for (String[] ignoreItem : ignoreItems) {
			ignoreItemsJoined.append(ignoreItem[0]);
			ignoreItemsJoined.append(":");
			ignoreItemsJoined.append(ignoreItem[1]);
			ignoreItemsJoined.append(";");
		}

		command.add(IGNORE_ITEMS.arg());
		command.add(prepareArg(ignoreItemsJoined.toString()));

		// Set the temp folder to use
		command.add(TEMPDIR.arg());
		command.add(prepareArg(svgTempFolder.getLocation().toString()));

		// Set the warning level
		command.add(WARNING_LEVEL.arg());
		command.add(instanceStore.getWarningLevel());

		// Set the output name
		command.add(OUTPUT_FILE.arg());

		File file = new File(project.getLocationURI().getPath());
		command.add(file.getAbsolutePath() + File.separator
				+ projectStore.getOutputFilename());

		// Advanced Options
		// They are added at the very end of the command line and only if
		// defined by the user.
		String[] options = projectStore.getAdvancedOptions();

		if ((options != null) && (options.length > 0)) {
			for (String option : options) {
				command.addAll(prepareAdvancedOption(option.trim()));
			}
		}

		return command;
	}


	private List<String> prepareAdvancedOption(String option) {
		List<String> options = new ArrayList<String>();

		String optionValue = null;
		String argumentValue = null;
		
		if (option.indexOf(" ") > 0) {
			optionValue = option.substring(0, option.indexOf(" "));
			argumentValue = option.substring(option.indexOf(" ")).trim();
		} else {
			optionValue = option;
		}
		
		while (optionValue.startsWith("-")) {
			optionValue = optionValue.substring(1);
		}

		options.add("--" + optionValue);
		
		if (argumentValue != null && argumentValue.length() != 0) {
			options.add(argumentValue);
		}
		
		return options;
	}
	
	private void handleResource(SystemModelGeneratorEnumsForCLI option,
			String[] selectedFiles) {
		// In the cases below where we have no selected files, we need to define
		// a "" string for compatibility with SMG, which does not like an empty array.
		switch (option) {
		case BORDER_SHAPES_FILES:
		case BORDER_STYLES_FILES:
		case COLOURS_FILES:
		case LOCALISATION_FILES:
		case PATTERNS_FILES:
		case SHAPES_FILES:
		case SYSTEM_INFO_FILES:
			if (selectedFiles.length == 0) {
				selectedFiles = new String[1];
				selectedFiles[0] = "\"\"";
			}

			break;

		case LEVELS_FILES:
			if (selectedFiles.length == 0) {
				selectedFiles = new String[1];
				selectedFiles[0] = "\"\"";
			} else {
				if (selectedFiles[0].equals("Auto")) {
					selectedFiles = new String[] {};
				}
			}

			break;

		case DEPENDENCIES_FILES:
		case S12_XML_FILES:
			if (selectedFiles.length == 0) {
				selectedFiles = new String[] {};
			}

			break;

		default:
			throw new IllegalArgumentException("Unknown option [" + option
					+ "]");

		}

		for (String file : selectedFiles) {
			command.add(option.arg());
			command.add((file.equals("\"\"")) ? file : prepareArg(file));
		}
	}

	private String prepareArg(String arg) {
		// Escape any XML entities
		arg = replace(arg, ampersandPattern.matcher(arg), "&amp;");
		arg = replace(arg, lessThanPattern.matcher(arg), "&lt;");
		arg = replace(arg, greaterThanPattern.matcher(arg), "&gt;");
		arg = replace(arg, singleQuotePattern.matcher(arg), "&apos;");
		arg = replace(arg, doubleQuotePattern.matcher(arg), "&quot;");
		arg = arg.trim();

		// Escape any unicode characters
		StringBuffer result = new StringBuffer();

		// Get chars as characters may be multibyte
		for (char theChar : arg.toCharArray()) {
			if ((int) theChar > 127) {
				// Turn into XML unicode entity
				result.append("&#x" + Integer.toHexString((int) theChar) + ";");
			} else {
				// Characters < 128 should be the same in all code pages, we
				// don't escape these for aesthetic reasons
				result.append(theChar);
			}
		}

		return "\"" + result.toString() + "\"";
	}

	private String replace(String arg, Matcher m, String replacement) {
		m.reset();

		StringBuffer result = new StringBuffer();

		while (m.find()) {
			m.appendReplacement(result, replacement);
		}

		m.appendTail(result);

		return result.toString();
	}

	private void writeToConsoleOutput(final String string) {
		// Writes a string to the console output view

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				ConsoleOutput.addText(string);
			}
		});
	}
}
