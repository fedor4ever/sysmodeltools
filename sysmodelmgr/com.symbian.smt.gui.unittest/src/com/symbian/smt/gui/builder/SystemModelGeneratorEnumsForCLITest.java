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

import junit.framework.Assert;
import org.junit.Test;

import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.BORDER_SHAPES_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SYSTEM_DEFINITION_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.COPYRIGHT_TEXT;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.DISTRIBUTION_TEXT;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.MODEL_NAME;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.MODEL_VERSION;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.MODEL_VERSION_TEXT;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SYSTEM_NAME;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SYSTEM_VERSION;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.HIGHTLIGHT_CORE_OS;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.LEVEL_OF_DETAIL;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SUPPRESS_MOUSE_OVER_EFFECT;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.LEVELS_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.LOCALISATION_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SHAPES_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.DEPENDENCIES_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.SYSTEM_INFO_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.COLOURS_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.BORDER_STYLES_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.PATTERNS_FILES;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.FILTER_HAS_ITEMS;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.IGNORE_ITEMS;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.WARNING_LEVEL;
import static com.symbian.smt.gui.builder.SystemModelGeneratorEnumsForCLI.TEMPDIR;

public class SystemModelGeneratorEnumsForCLITest {
	// System Definition Files
	@Test
	public final void testSystemDefinitionFiles() {
		Assert.assertTrue(SYSTEM_DEFINITION_FILES.arg().equals("--sysdef"));
	}

	// Model Labels	
	@Test
	public final void testCopyrightText() {
		Assert.assertTrue(COPYRIGHT_TEXT.arg().equals("--copyright"));
	}
	
	@Test
	public final void testDistributionText() {
		Assert.assertTrue(DISTRIBUTION_TEXT.arg().equals("--distribution"));
	}
	
	@Test
	public final void testModelName() {
		Assert.assertTrue(MODEL_NAME.arg().equals("--model_name"));
	}
	
	@Test
	public final void testModelVersion() {
		Assert.assertTrue(MODEL_VERSION.arg().equals("--model_version"));
	}
	
	@Test
	public final void testModelVersionText() {
		Assert.assertTrue(MODEL_VERSION_TEXT.arg().equals("--model_version_type"));
	}
	
	@Test
	public final void 	testSystemName() {
		Assert.assertTrue(SYSTEM_NAME.arg().equals("--system_name"));
	}
	
	@Test
	public final void 	testSystemVersion() {
		Assert.assertTrue(SYSTEM_VERSION.arg().equals("--system_version"));
	}
	
	// Model Control	
	@Test
	public final void testHighlightCoreOS() {
		Assert.assertTrue(HIGHTLIGHT_CORE_OS.arg().equals("--coreos"));
	}
	
	@Test
	public final void testLevelOfDetail() {
		Assert.assertTrue(LEVEL_OF_DETAIL.arg().equals("--detail"));
	}
	
	@Test
	public final void 	testMakeModelStatic() {
		Assert.assertTrue(SUPPRESS_MOUSE_OVER_EFFECT.arg().equals("--static"));
	}
	
	// Resources
	@Test
	public final void testShapesFile() {
		Assert.assertTrue(SHAPES_FILES.arg().equals("--shapes"));
	}
	
	@Test
	public final void testLevelsFile() {
		Assert.assertTrue(LEVELS_FILES.arg().equals("--levels"));
	}
	
	@Test
	public final void testLocalisationFile() {
		Assert.assertTrue(LOCALISATION_FILES.arg().equals("--localize"));
	}
	
	@Test
	public final void testDependenciesFile() {
		Assert.assertTrue(DEPENDENCIES_FILES.arg().equals("--deps"));
	}
	
	@Test
	public final void 	testSystemInfoFile() {
		Assert.assertTrue(SYSTEM_INFO_FILES.arg().equals("--sysinfo"));
	}
	
	@Test
	public final void testColoursFile() {
		Assert.assertTrue(COLOURS_FILES.arg().equals("--color"));
	}
	
	@Test
	public final void testBorderStylesFile() {
		Assert.assertTrue(BORDER_STYLES_FILES.arg().equals("--border-style"));
	}

	@Test
	public final void testBorderShapesFile() {
		Assert.assertTrue(BORDER_SHAPES_FILES.arg().equals("--border-shape"));
	}

	@Test
	public final void testPatternsFile() {
		Assert.assertTrue(PATTERNS_FILES.arg().equals("--pattern"));
	}
	
	// Filter has Items 
	@Test
	public final void testFilterItems() {
		Assert.assertTrue(FILTER_HAS_ITEMS.arg().equals("--filter-has"));
	}

	// Ignore Items
	@Test
	public final void testIgnoreItems() {
		Assert.assertTrue(IGNORE_ITEMS.arg().equals("--ignore"));
	}
	
	// Warning Level
	@Test
	public final void testWarningLevel() {
		Assert.assertTrue(WARNING_LEVEL.arg().equals("-w"));
	}
	
	// Temp Dir
	@Test
	public final void testTempDir() {
		Assert.assertTrue(TEMPDIR.arg().equals("--tempdir"));
	}

}
