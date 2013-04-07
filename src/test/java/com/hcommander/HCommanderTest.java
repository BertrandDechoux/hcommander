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

import static com.hcommander.Test.checkContent;
import static com.hcommander.Test.doNotCareAboutOsStuff;
import static com.hcommander.Test.redirectSystemOutTo;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.hcommander.commands.DummyCommand;
import com.hcommander.commands.DummyDocumentedCommand;
import com.hcommander.commands.DummyParameterizableCommand;
import com.hcommander.commands.ErrorCommand;

public class HCommanderTest {
	
	@Before
	public void setup() {
		doNotCareAboutOsStuff();
	}
	
	@Ignore("A patch should be submitted")
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailWhenDuplicateIdentifierKeyword() {
		Command command = new DummyCommand();
		new HCommander(Arrays.asList(command,command));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailWhenNoCommandIsProvided() {
		new HCommander(Collections.<Command>emptyList());
	}
	
	@Test
	public void shouldFailWhenNoCommandIsSelected() throws Exception {
		redirectSystemOutTo("no-command-selected.txt");
		
		List<? extends Command> commands = Arrays.asList(new DummyCommand());
		HCommander hCommander = new HCommander(commands);
		int status = hCommander.run(new String[0]);
		
		assertThat(status).isNotEqualTo(0);
		checkContent("no-command-selected.txt");
	}

	@Test
	public void shouldFailWhenNonexistentCommandIsSelected() throws Exception {
		redirectSystemOutTo("nonexistent-command-selected.txt");
		
		List<? extends Command> commands = Arrays.asList(new DummyCommand());
		HCommander hCommander = new HCommander(commands);
		int status = hCommander.run(new String[] { "unknown" });
		
		assertThat(status).isNotEqualTo(0);
		checkContent("nonexistent-command-selected.txt");
	}
	
	@Test
	public void shouldSucceedWhenCommandIsSelected() throws Exception {
		List<? extends Command> commands = Arrays.asList(new DummyCommand());
		HCommander hCommander = new HCommander(commands);
		int status = hCommander.run(new String[] { "test" });
		
		assertThat(status).isEqualTo(0);
	}
	
	@Test
	public void shouldFailWhenRequiredParamterIsNotProvided() throws Exception {
		redirectSystemOutTo("absent-required-parameter.txt");
		
		List<? extends Command> commands = Arrays.asList(new DummyParameterizableCommand());
		HCommander hCommander = new HCommander(commands);
		int status = hCommander.run(new String[] { "test" });
		
		assertThat(status).isNotEqualTo(0);
		checkContent("absent-required-parameter.txt");
	}
	
	@Test
	public void shouldFailWhenExtraParameterNotExpected() throws Exception {
		redirectSystemOutTo("extra-parameter.txt");
		
		List<? extends Command> commands = Arrays.asList(new DummyDocumentedCommand());
		HCommander hCommander = new HCommander(commands);
		int status = hCommander.run(new String[] { "test", "-param", "value" });
		
		assertThat(status).isNotEqualTo(0);
		checkContent("extra-parameter.txt");
	}
	
	@Test
	public void shouldSucceedWhenRequiredParameterIsProvided() throws Exception {
		List<? extends Command> commands = Arrays.asList(new DummyParameterizableCommand());
		HCommander hCommander = new HCommander(commands);
		int status = hCommander.run(new String[] { "test", "-param", "value" });
		
		assertThat(status).isEqualTo(0);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailWhenErrorDuringRun() throws Exception {
		List<? extends Command> commands = Arrays.asList(new ErrorCommand());
		HCommander hCommander = new HCommander(commands);
		hCommander.run(new String[] { "test" });
	}
	
	@Test
	public void shouldDisplayHelpOfCommand() throws Exception {
		redirectSystemOutTo("help-on-help.txt");
		
		List<? extends Command> commands = Arrays.asList(new DummyCommand());
		HCommander hCommander = new HCommander(commands);
		int status = hCommander.run(new String[] { "help", "-about", "help" });
		
		assertThat(status).isEqualTo(0);
		checkContent("help-on-help.txt");
	}
	
	@Test
	public void shouldDisplayShortDescriptionAsFullHelp() throws Exception {
		redirectSystemOutTo("full-help.txt");
		
		List<? extends Command> commands = Arrays.asList(new DummyCommand());
		HCommander hCommander = new HCommander(commands);
		int status = hCommander.run(new String[] { "help" });
		
		assertThat(status).isEqualTo(0);
		checkContent("full-help.txt");
	}

}
