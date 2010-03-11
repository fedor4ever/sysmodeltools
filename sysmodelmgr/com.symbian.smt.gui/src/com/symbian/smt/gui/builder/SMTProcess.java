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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import com.symbian.smt.gui.Logger;
import com.symbian.smt.gui.views.ConsoleOutput;

public class SMTProcess {

	/**
	 * Runs the command line base System Model Generator
	 * 
	 * @param List
	 *            <String> Arguments for the CLI
	 * @return int The exit code from the System Model Generator
	 */
	public int run(List<String> command) {
		int result = 0;

		// Reset the console
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				ConsoleOutput.reset();

			}
		});

		// Print the command line string to the console output
		StringBuilder commandLineString = new StringBuilder();

		for (String item : command) {
			commandLineString.append(item.concat(" "));
		}

		writeToConsoleOutput("Executing '" + commandLineString.toString() + "'");

		// First we check that Perl is available
		ProcessBuilder pbCheckPerl = new ProcessBuilder("perl", "-v");
		pbCheckPerl.redirectErrorStream(true);

		try {
			pbCheckPerl.start();
		} catch (IOException e) {
			writeToConsoleOutput("Error: Perl is not installed");
			return 9009; // Same exit code as Windows produces for a program not
							// found
		}

		ProcessBuilder pb = new ProcessBuilder(command);

		// Redirect STDERR to STDOUT
		pb.redirectErrorStream(true);

		try {
			// Start the process
			final Process p = pb.start();

			// Get and close the process STDIN
			OutputStream out = p.getOutputStream();
			out.close();

			// Create a reader to read from the process STDOUT
			BufferedReader inReader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			// Print STDOUT to the command output view
			String line;
			while ((line = inReader.readLine()) != null) {
				writeToConsoleOutput(line);
			}

			// Close the process STDOUT pipe when finished
			inReader.close();

			try {
				// Ensure the process has finished and get the exit code
				result = p.waitFor();
			} catch (InterruptedException e) {
				Logger.log(e.getMessage(), e);
			} finally {
				// Destroy the process
				p.destroy();
			}
		} catch (IOException e) {
			Logger.log(e.getMessage(), e);
		}

		return result;

	}

	private void writeToConsoleOutput(final String string) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				ConsoleOutput.addText(string);
			}
		});
	}
}
