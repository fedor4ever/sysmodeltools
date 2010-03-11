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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.symbian.smt.gui.Helper;
import com.symbian.smt.gui.PersistentDataStore;
import com.symbian.smt.gui.smtwidgets.ValidModelEvent.Type;

public class ModelLabelsWidget extends Composite implements
		ValidModelObservable {
	private Combo distributionTextCombo;
	private Text modelVersionText;
	private Text systemVersionText;
	private Text copyrightTextText;
	private Combo modelVersionTextCombo;
	private Text modelNameText;
	private Text systemNameText;
	private ArrayList<ValidModelDefinedListener> listeners = new ArrayList<ValidModelDefinedListener>();

	/**
	 * Creates a ModelLabelsWidget composite object
	 * 
	 * @return void
	 */
	public ModelLabelsWidget(final Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());

		final Composite gridLayoutComposite = new Composite(this, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gridLayoutComposite.setLayout(gridLayout);

		final Label systemNameLabel = new Label(gridLayoutComposite, SWT.NONE);
		systemNameLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false));
		systemNameLabel.setText("System Name");

		systemNameText = new Text(gridLayoutComposite, SWT.BORDER);
		systemNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		final Label systemVersionLabel = new Label(gridLayoutComposite,
				SWT.NONE);
		systemVersionLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false));
		systemVersionLabel.setText("System Version");

		systemVersionText = new Text(gridLayoutComposite, SWT.BORDER);
		systemVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		final Label modelNameLabel = new Label(gridLayoutComposite, SWT.NONE);
		modelNameLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false));
		modelNameLabel.setText("Model Name");

		modelNameText = new Text(gridLayoutComposite, SWT.BORDER);
		modelNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		final Label modelVersionLabel = new Label(gridLayoutComposite, SWT.NONE);
		modelVersionLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false));
		modelVersionLabel.setText("Model Version");

		modelVersionText = new Text(gridLayoutComposite, SWT.BORDER);
		modelVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		final Label modelVersionTextLabel = new Label(gridLayoutComposite,
				SWT.NONE);
		modelVersionTextLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false));
		modelVersionTextLabel.setText("Model Version Text");

		modelVersionTextCombo = new Combo(gridLayoutComposite, SWT.NONE);
		modelVersionTextCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		final Label distributionTextLabel = new Label(gridLayoutComposite,
				SWT.NONE);
		distributionTextLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false));
		distributionTextLabel.setText("Distribution Text");

		distributionTextCombo = new Combo(gridLayoutComposite, SWT.NONE);
		distributionTextCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		final Label copyrightTextLabel = new Label(gridLayoutComposite,
				SWT.NONE);
		copyrightTextLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false));
		copyrightTextLabel.setText("Copyright Text");

		copyrightTextText = new Text(gridLayoutComposite, SWT.BORDER);
		copyrightTextText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
	}

	/**
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelObservable#addModelListener(ValidModelDefinedListener)
	 */
	public void addModelListener(ValidModelDefinedListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	private void checkDistributionText() {
		if (listeners.size() > 0) {
			Boolean modelDefined = true;
			String message = "";
			Type type = Type.SUCCESS;

			String text = distributionTextCombo.getText().trim();

			if (text.length() > 0) {
				// TODO:BRS:Specify any validation here, such as what to do with
				// the empty string
				// if (xxx) {
				// modelDefined = false;
				// message =
				// "";
				// type = Type.ERROR;
				// }
			}

			ValidModelEvent event = new ValidModelEvent(modelDefined, message,
					type);

			for (ValidModelDefinedListener listener : listeners) {
				listener.validModelDefined(event);
			}
		}
	}

	private void checkModelVersionText() {
		if (listeners.size() > 0) {
			Boolean modelDefined = true;
			String message = "";
			Type type = Type.SUCCESS;

			String text = modelVersionTextCombo.getText().trim();

			if (text.length() > 0) {
				// TODO:BRS:Specify any validation here, such as what to do with
				// the empty string
				// if (xxx) {
				// modelDefined = false;
				// message =
				// "";
				// type = Type.ERROR;
				// }
			}

			ValidModelEvent event = new ValidModelEvent(modelDefined, message,
					type);

			for (ValidModelDefinedListener listener : listeners) {
				listener.validModelDefined(event);
			}
		}
	}

	/**
	 * Returns the copyright text
	 * 
	 * @return String
	 */
	public String getCopyrightText() {
		return copyrightTextText.getText();
	}

	/**
	 * Returns the distribution text values
	 * 
	 * @return String[]
	 */
	public String[] getDistributionTexts() {
		List<String> items = Helper.toListOfStrings(distributionTextCombo
				.getItems());
		String text = distributionTextCombo.getText().trim();

		if (text.length() > 0) {
			if (!items.contains(text)) {
				items.add(0, text);
			}
		}

		return items.toArray(new String[items.size()]);
	}

	/**
	 * Returns the model name
	 * 
	 * @return String
	 */
	public String getModelName() {
		return modelNameText.getText();
	}

	/**
	 * Returns the text for the model version
	 * 
	 * @return String
	 */
	public String getModelVersion() {
		return modelVersionText.getText();
	}

	/**
	 * Returns the model version text values
	 * 
	 * @return String[]
	 */
	public String[] getModelVersionTexts() {
		List<String> items = Helper.toListOfStrings(modelVersionTextCombo
				.getItems());
		String text = modelVersionTextCombo.getText().trim();

		if (text.length() > 0) {
			if (!items.contains(text)) {
				items.add(0, text);
			}
		}

		return items.toArray(new String[items.size()]);
	}

	/**
	 * Returns the selected distribution text value
	 * 
	 * @return String
	 */
	public String getSelectedDistributionText() {
		return distributionTextCombo.getText();
	}

	/**
	 * Returns the selected model version text value
	 * 
	 * @return String
	 */
	public String getSelectedModelVersionText() {
		return modelVersionTextCombo.getText();
	}

	/**
	 * Returns the system name
	 * 
	 * @return String
	 */
	public String getSystemName() {
		return systemNameText.getText();
	}

	/**
	 * Returns the text for the system version
	 * 
	 * @return String
	 */
	public String getSystemVersion() {
		return systemVersionText.getText();
	}

	public void initialiseDistributionText(PersistentDataStore store) {
		distributionTextCombo.setItems(store.getDistributionTexts());
		distributionTextCombo.setText(store.getSelectedDistributionText());

		distributionTextCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkDistributionText();
			}
		});
	}

	public void initialiseModelVersionText(PersistentDataStore store) {
		modelVersionTextCombo.setItems(store.getModelVersionTexts());
		modelVersionTextCombo.setText(store.getSelectedModelVersionText());

		modelVersionTextCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkModelVersionText();
			}
		});
	}

	/**
	 * @see com.symbian.smt.gui.smtwidgets.ValidModelObservable#removeModelListener(ValidModelDefinedListener)
	 */
	public void removeModelListener(ValidModelDefinedListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	/**
	 * Sets the text for the copyright text
	 * 
	 * @param copyrightText
	 *            String to be used for the copyright text
	 * @return void
	 */
	public void setCopyrightText(String copyrightText) {
		copyrightTextText.setText(copyrightText);
	}

	/**
	 * Sets the values for the distribution text combo
	 * 
	 * @param distributionText
	 *            String array to be used for the distribution text values
	 * @return void
	 */
	public void setDistributionTexts(String[] distributionTexts) {
		distributionTextCombo.setItems(distributionTexts);
	}

	/**
	 * Sets the text for the model name
	 * 
	 * @param modelName
	 *            String to be used for the model name
	 * @return void
	 */
	public void setModelName(String modelName) {
		modelNameText.setText(modelName);
	}

	/**
	 * Sets the text for the model version
	 * 
	 * @param modelVersion
	 *            String to be used for the model version
	 * @return void
	 */
	public void setModelVersion(String modelVersion) {
		modelVersionText.setText(modelVersion);
	}

	/**
	 * Sets the items for the model version text combo
	 * 
	 * @param modelVersionTexts
	 *            values of the model version text
	 * @return void
	 */
	public void setModelVersionTexts(String[] modelVersionTexts) {
		modelVersionTextCombo.setItems(modelVersionTexts);
	}

	/**
	 * Sets the selected distribution text value
	 * 
	 * @param distributionText
	 *            The distribution text value to set as selected.
	 * @return void
	 */
	public void setSelectedDistributionText(String distributionText) {
		String[] items = distributionTextCombo.getItems();
		int index = 0;

		for (String item : items) {
			if (item.equals(distributionText)) {
				break;
			}

			index++;
		}

		distributionTextCombo.select(index);
	}

	/**
	 * Sets the selected model version text value
	 * 
	 * @param modelVersionText
	 *            The model version text value to set as selected.
	 * @return void
	 */
	public void setSelectedModelVersionText(String modelVersionText) {
		String[] items = modelVersionTextCombo.getItems();
		int index = 0;

		for (String item : items) {
			if (item.equals(modelVersionText)) {
				break;
			}

			index++;
		}

		modelVersionTextCombo.select(index);
	}

	/**
	 * Sets the text for the system name
	 * 
	 * @param systemName
	 *            String to be used for the system name
	 * @return void
	 */
	public void setSystemName(String systemName) {
		systemNameText.setText(systemName);
	}

	/**
	 * Sets the text for the system version
	 * 
	 * @param systemVersion
	 *            String to be used for the system version
	 * @return void
	 */
	public void setSystemVersion(String systemVersion) {
		systemVersionText.setText(systemVersion);
	}
}
