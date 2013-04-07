/**
 *  Copyright 2013 Bertrand Dechoux
 *  
 *  This file is part of the HCommander project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at

 *      http://www.apache.org/licenses/LICENSE-2.0

 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.hcommander;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.fest.assertions.Assertions;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Test {
	
	public static void doNotCareAboutOsStuff() {
		System.setProperty("line.separator", "\n");
	}
	
	public static void redirectSystemOutTo(String filename) throws FileNotFoundException {
		System.setOut(new PrintStream(new FileOutputStream("target/"+filename)));
	}
	
	public static void checkContent(String filename) throws Exception {
		sameContent("target/"+filename, "src/test/resources/"+filename);
	}
	
	public static void sameContent(String actualOutputPath, String expectedOuputPath) throws Exception {
		String actualContent = contentOf(actualOutputPath);
		String expectedContent = contentOf(expectedOuputPath);
		Assertions.assertThat(actualContent).isEqualTo(expectedContent);
	}

	private static String contentOf(String filePath) throws Exception {
		return Files.toString(new File(filePath), Charsets.UTF_8);
	} 

}
