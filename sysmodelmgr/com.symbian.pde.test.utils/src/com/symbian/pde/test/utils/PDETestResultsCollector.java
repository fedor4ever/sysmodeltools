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
package com.symbian.pde.test.utils;

import org.eclipse.jdt.internal.junit.model.ITestRunListener2;
import org.eclipse.jdt.internal.junit.model.RemoteTestRunnerClient;

public final class PDETestResultsCollector {
    private static PDETestListener pdeTestListener;

    private String suiteName;

    private PDETestResultsCollector(String suite) {
        suiteName = suite;
    }

    private void run(int port) throws InterruptedException {
        pdeTestListener = new PDETestListener(this, suiteName);
        new RemoteTestRunnerClient().startListening(new ITestRunListener2[] {pdeTestListener}, port);
        System.out.println("Listening on port " + port + " for test suite " + suiteName + " results ...");
        synchronized (this) {
            wait();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: PDETestResultsCollector <test suite name> <port number>");
            System.exit(0);
        }

        try {
            new PDETestResultsCollector(args[0]).run(Integer.parseInt(args[1]));
        } catch (Throwable th) {
            th.printStackTrace();
        }

        if (pdeTestListener != null && pdeTestListener.failed()) {
            System.exit(1);
        }
    }
}
