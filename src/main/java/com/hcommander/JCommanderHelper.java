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

import java.util.Map;

import com.beust.jcommander.JCommander;

/**
 * Wrapper around {@link JCommander} in order to fix a few shortcomings not
 * really related to {@link HCommander}.
 */
public class JCommanderHelper<C> {
	private final JCommander jCommander;

	public JCommanderHelper(JCommander jCommander,
			Iterable<? extends C> commands) {
		this.jCommander = jCommander;
		addAll(commands);
	}

	/**
	 * Add all commands and check for the existence of a least one.
	 */
	private void addAll(Iterable<? extends C> commands) {
		for (C command : commands) {
			// TODO patch for error on duplicated command name
			jCommander.addCommand(command);
		}
		if (jCommander.getCommands().isEmpty()) {
			throw new IllegalArgumentException("No commands were provided.");
		}
	}

	/**
	 * {@link JCommander} usage is too verbose. This alternative (a hack really)
	 * allows not to print each command options when listing all commands.
	 */
	public void shortUsage() {
		Map<String, JCommander> commands = jCommander.getCommands();
		int length = getMaxKeyLength(commands);
		StringBuffer shortDescription = new StringBuffer(
				"Usage: [options] [command] [command options]\r\n  Commands:\r\n");
		for (String commandName : commands.keySet()) {
			shortCommandUsage(length, shortDescription, commandName);
		}
		System.out.println(shortDescription.toString());
	}

	/**
	 * Almost the same as {@link JCommander} but without the options.
	 */
	private void shortCommandUsage(int maxKeyLength,
			StringBuffer shortDescription, String commandName) {
		shortDescription.append("    ");
		shortDescription.append(commandName);
		for (int i = 0; i < 4 + commandName.length(); i++) {
			shortDescription.append(' ');
		}
		String commandDescription = jCommander
				.getCommandDescription(commandName);
		if (commandDescription != null && !commandDescription.isEmpty()) {
			shortDescription.append(commandDescription);
		} else {
			shortDescription.append("<no description>");
		}
		shortDescription.append("\r\n");
	}

	/**
	 * Return the maximum length of the map keys.
	 */
	private int getMaxKeyLength(Map<String, ?> m) {
		int length = 0;
		for (String key : m.keySet()) {
			length = Math.max(length, key.length());
		}
		return length;
	}

	/**
	 * Return the command by name.
	 */
	@SuppressWarnings("unchecked")
	public C getCommand(String commandName) {
		JCommander internalJCommander = jCommander.getCommands().get(
				commandName);
		return (C) internalJCommander.getObjects().get(0);
	}

}
