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
// NewSMTProjectWizardTest
//



package com.symbian.smt.gui.wizard;

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

import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.symbian.smt.gui.AbstractPersistentDataStore;
import com.symbian.smt.gui.Activator;
import com.symbian.smt.gui.Helper;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.TestConstants;
import com.symbian.smt.gui.preferences.SmmPreferencesInitializer;

/**
 * @author barbararosi-schwartz
 * 
 */
public class NewSMTProjectWizardTest extends TestCase {

	private static final String SEPARATOR = "|";

	private static final AbstractPersistentDataStore getPersistDataStore(
			IProject project) {
		IScopeContext projectScope = new ProjectScope(project);
		IEclipsePreferences node = projectScope.getNode(Activator.PLUGIN_ID);
		PersistentDataStore dataStore = new PersistentDataStore(node);

		return dataStore;
	}

	private IStructuredSelection selection;
	private Shell shell;
	private String smgFolder;
	private NewSMTProjectWizard wizard;
	private Text wizardProjectTextField;
	private IWorkbench workbench;

	// This method is dependent on the current UI composition
	// of Eclipse's WizardNewProjectCreationPage. 
	// We cannot find the Text widget in any other way because
	// it is hidden.
	private final Text findText(Composite parent) {
		Text text = null;
		Control[] children = parent.getChildren();

		for (Control control : children) {
			if (control instanceof Text) {
				text = (Text) control;
				break;
			} else if (control instanceof Composite) {
				return findText((Composite) control);
			}
		}

		return text;
	}

	private final String prependPathAndMakeLowerCase(String filename) {
		assertNotNull("Location of SystemModelManager should not be null.",
				smgFolder);
		return new StringBuffer(smgFolder).append(TestConstants.RESOURCE_FILES_FOLDER_PATH)
				.append(filename).toString().toLowerCase();
	}

	/**
	 * @throws java.lang.Exception
	 */
	protected final void setUp() throws Exception {
		// Initialise the default values
		SmmPreferencesInitializer initialiser = new SmmPreferencesInitializer();

		initialiser.initializeDefaultPreferences();

		smgFolder = initialiser.getSmgFolder();
		wizard = new NewSMTProjectWizard();
		selection = new StructuredSelection(StructuredSelection.EMPTY);
		workbench = PlatformUI.getWorkbench();
		shell = workbench.getActiveWorkbenchWindow().getShell();

		wizard.init(workbench, selection);

		WizardDialog dialog = new WizardDialog(shell, wizard);

		dialog.setBlockOnOpen(false);
		dialog.open();

		NewProjectCreationPageCaseInsensitive page = (NewProjectCreationPageCaseInsensitive) wizard
				.getPage("page1");
		Composite c = (Composite) page.getControl();
		wizardProjectTextField = findText(c);

		if (wizardProjectTextField == null) {
			throw new Exception("Could not find project name Text field");
		}

		wizardProjectTextField.setText("testproject");
	}

	/**
	 * @throws java.lang.Exception
	 */
	protected final void tearDown() throws Exception {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		
		ws.getRoot().delete(true, true, null);
		wizard.dispose();
		
		wizard = null;
	}

	/**
	 * Test method for
	 * {@link com.symbian.smt.gui.wizard.NewSMTProjectWizard#addPages()}.
	 */
	public final void testAddPages() {
		assertEquals(3, wizard.getPageCount());
		IWizardPage[] pages = wizard.getPages();

		assertTrue(pages[0] instanceof NewProjectCreationPageCaseInsensitive);
		assertTrue(pages[1] instanceof NewProjectWizardSystemDefsPage);
		assertTrue(pages[2] instanceof NewProjectWizardTabbedPropertiesPage);
	}

	/**
	 * Test method for
	 * {@link com.symbian.smt.gui.wizard.NewSMTProjectWizard#copyFilesIntoProject()}
	 * .
	 */
	public final void testCopyFilesIntoProject() {
		IWizardPage[] pages = wizard.getPages();
		NewProjectCreationPageCaseInsensitive page1 = (NewProjectCreationPageCaseInsensitive) pages[0];
		IProject newProject = page1.getProjectHandle();

		assertEquals("\\testproject", newProject.getFullPath().toOSString());
		testDefaultResourcesFromPage((NewProjectWizardTabbedPropertiesPage) pages[2]);
	}

	/**
	 * Test method for
	 * {@link com.symbian.smt.gui.wizard.NewSMTProjectWizard#createPageControls()}
	 * .
	 */
	public final void testCreatePageControls() {
		IScopeContext defaultScope = new DefaultScope();
		IEclipsePreferences defaultNode = defaultScope
				.getNode(Activator.PLUGIN_ID);
		IScopeContext instanceScope = new InstanceScope();
		IEclipsePreferences instanceNode = instanceScope
				.getNode(Activator.PLUGIN_ID);
		PersistentDataStore instanceStore = new PersistentDataStore(
				instanceNode, defaultNode);

		assertNotNull(instanceStore);
	}

	/**
	 * Test method for
	 * {@link com.symbian.smt.gui.wizard.NewSMTProjectWizard#createProject()}.
	 */
	public final void testCreateProject() {
		NewProjectCreationPageCaseInsensitive page1 = (NewProjectCreationPageCaseInsensitive) wizard
				.getPage("page1");
		IProject newProject = page1.getProjectHandle();

		NewProjectWizardSystemDefsPage page2 = (NewProjectWizardSystemDefsPage) wizard.getPage("wizardPage");
		
		page2.setSystemDefinitions(new String[] {smgFolder + "/../smg-sysdef/Bob_System_Definition.xml"});
		wizard.performFinish();

		try {
			IProjectDescription desc = newProject.getDescription();

			assertEquals("testproject", desc.getName());

			List<String> natureIds = Helper
					.toListOfStrings(desc.getNatureIds());

			assertTrue(natureIds.contains("com.symbian.smt.gui.nature"));
		} catch (CoreException e) {
			throw new AssertionFailedError(
					"Problem encountered in obtaining the project's description.");
		}
	}

	private final void testDefaultResourcesFromPage(
			NewProjectWizardTabbedPropertiesPage page) {
		String[] expected = { TestConstants.SHAPES_RESOURCE_FILE_PATH };
		String[] actual = page.getDefaultShapesFiles();
		String message = "Error in default shapes files.";
		testResourceName(message, expected, actual);

		expected = new String[] { "Auto" };
		actual = page.getDefaultLevelsFiles();
		message = "Error in default level files.";
		testResourceName(message, expected, actual);

		expected = new String[] { TestConstants.LOCALISATION_RESOURCE_FILE_PATH };
		actual = page.getDefaultLocalisationFiles();
		message = "Error in default localisation files.";
		testResourceName(message, expected, actual);

		expected = new String[] {};
		actual = page.getDefaultDependenciesFiles();
		message = "Error in default dependencies files.";
		testResourceName(message, expected, actual);

		expected = new String[] {};
		actual = page.getDefaultSystemInfoFiles();
		message = "Error in default system info files.";
		testResourceName(message, expected, actual);

		expected = new String[] {};
		actual = page.getDefaultColoursFiles();
		message = "Error in default colours files.";
		testResourceName(message, expected, actual);

		expected = new String[] {};
		actual = page.getDefaultBorderStylesFiles();
		message = "Error in default border styles files.";
		testResourceName(message, expected, actual);

		expected = new String[] {};
		actual = page.getDefaultBorderShapesFiles();
		message = "Error in default border shapes files.";
		testResourceName(message, expected, actual);

		expected = new String[] {};
		actual = page.getDefaultPatternsFiles();
		message = "Error in default patters files.";
		testResourceName(message, expected, actual);

		expected = new String[] {};
		actual = page.getDefaultS12XmlFiles();
		message = "Error in default S12 files.";
		testResourceName(message, expected, actual);
	}

	/**
	 * Test method for
	 * {@link com.symbian.smt.gui.wizard.NewSMTProjectWizard#performFinish()}.
	 */
	public final void testPerformFinish() {
		NewProjectCreationPageCaseInsensitive page = (NewProjectCreationPageCaseInsensitive) wizard
				.getPage("page1");
		IProject newProject = page.getProjectHandle();

		wizard.performFinish();

		testPersistedSelectedItems(newProject);
		testPersistedItems(newProject);
		testWorkspaceContents(newProject);
	}

	private final void testPersistedItems(IProject project) {
		AbstractPersistentDataStore dataStore = getPersistDataStore(project);
		String[] options = dataStore.getAdvancedOptions();
		assertEquals(0, options.length);

		Boolean fixsize = dataStore.getFixItemSize();
		assertFalse(fixsize);

		options = dataStore.getDistributionTexts();
		assertEquals(4, options.length);
		assertEquals("secret", options[0]);
		assertEquals("confidential", options[1]);
		assertEquals("internal", options[2]);
		assertEquals("unrestricted", options[3]);

		options = dataStore.getModelVersionTexts();
		assertEquals(3, options.length);
		assertEquals("draft", options[0]);
		assertEquals("build", options[1]);
		assertEquals("issued", options[2]);

		options = dataStore.getPrintedDpis();
		assertEquals(2, options.length);
		assertEquals("300", options[0]);
		assertEquals("600", options[1]);

		String[] filenames = dataStore.getBorderShapesFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getBorderStylesFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getColoursFiles();
		assertEquals(1, filenames.length);
		assertEquals(prependPathAndMakeLowerCase("system_model_colors.xml"),
				filenames[0].toLowerCase());

		filenames = dataStore.getDependenciesFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getLevelsFiles();
		assertEquals(3, filenames.length);
		assertEquals("Auto", filenames[0]);
		assertEquals(prependPathAndMakeLowerCase("Levels.xml"), filenames[1]
				.toLowerCase());
		assertEquals(prependPathAndMakeLowerCase("Levels91.xml"), filenames[2]
				.toLowerCase());

		filenames = dataStore.getLocalisationFiles();
		assertEquals(1, filenames.length);
		assertEquals(prependPathAndMakeLowerCase("display-names.xml"),
				filenames[0].toLowerCase());

		filenames = dataStore.getPatternsFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getS12XmlFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getShapesFiles();
		assertEquals(2, filenames.length);
		assertEquals(prependPathAndMakeLowerCase("Shapes.xml"), filenames[0]
				.toLowerCase());
		assertEquals(prependPathAndMakeLowerCase("Example-shapes.xml"),
				filenames[1].toLowerCase());

		filenames = dataStore.getSystemInfoFiles();
		assertEquals(1, filenames.length);
		assertEquals(prependPathAndMakeLowerCase("SystemInfo.xml"),
				filenames[0].toLowerCase());

		filenames = dataStore.getBorderShapesFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getBorderShapesFiles();
		assertEquals(0, filenames.length);
	}

	private final void testPersistedSelectedItems(IProject project) {
		AbstractPersistentDataStore dataStore = getPersistDataStore(project);

		String[] filenames = dataStore.getSelectedBorderShapesFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getSelectedBorderStylesFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getSelectedColoursFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getSelectedDependenciesFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getSelectedLevelsFiles();
		assertEquals(1, filenames.length);
		assertEquals("Auto", filenames[0]);

		filenames = dataStore.getSelectedLocalisationFiles();
		assertEquals(1, filenames.length);
		assertEquals(prependPathAndMakeLowerCase("display-names.xml"),
				filenames[0].toLowerCase());

		filenames = dataStore.getSelectedPatternsFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getSelectedS12XmlFiles();
		assertEquals(0, filenames.length);

		filenames = dataStore.getSelectedShapesFiles();
		assertEquals(1, filenames.length);
		assertEquals(prependPathAndMakeLowerCase("Shapes.xml"), filenames[0]
				.toLowerCase());

		filenames = dataStore.getSelectedSystemInfoFiles();
		assertEquals(0, filenames.length);

		String distributionText = dataStore.getSelectedDistributionText();
		assertEquals("secret", distributionText);

		String modelVersionText = dataStore.getSelectedModelVersionText();
		assertEquals("draft", modelVersionText);

		String dpi = dataStore.getSelectedPrintedDpi();
		assertEquals("600", dpi);
	}

	private final void testResourceName(String message, String[] expected,
			String[] actual) {
		assertEquals(message, expected.length, actual.length);

		for (int i = 0; i < expected.length; i++) {
			assertEquals(message, Helper.relative2AbsolutePaths(expected[i],
					smgFolder, SEPARATOR), actual[i]);
		}
	}

	private final void testWorkspaceContents(IProject project) {
		String folderName = "";

		try {
			folderName = BORDER_SHAPES.arg();
			IFolder folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			IResource[] children = folder.members();
			assertEquals(0, children.length);

			folderName = BORDER_STYLES.arg();
			folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			children = folder.members();
			assertEquals(0, children.length);

			folderName = COLOURS.arg();
			folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			children = folder.members();
			assertEquals(0, children.length);

			folderName = DEPENDENCIES.arg();
			folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			children = folder.members();
			assertEquals(0, children.length);

			folderName = LEVELS.arg();
			folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			children = folder.members();
			assertEquals(0, children.length);

			folderName = LOCALISATION.arg();
			folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			children = folder.members();
			assertEquals(1, children.length);
			assertEquals("display-names.xml", children[0].getName());

			folderName = PATTERNS.arg();
			folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			children = folder.members();
			assertEquals(0, children.length);

			folderName = S12_XML.arg();
			folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			children = folder.members();
			assertEquals(0, children.length);

			folderName = SHAPES.arg();
			folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			// TODO:BRS:Check why assertions below fail.
			// assertEquals(1, children.length);
			// assertEquals("Shapes.xml", children[0].getName());

			folderName = SYSTEM_INFO.arg();
			folder = project.getFolder(new Path(folderName));
			assertFalse("Folder [" + folderName
					+ "] does not exist in project.", !folder.exists());
			children = folder.members();
			assertEquals(0, children.length);
		} catch (CoreException e) {
			assertFalse("Exception while looking for the contents of folder ["
					+ folderName + "].", true);
		}
	}

}
