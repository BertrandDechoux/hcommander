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

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * On one hand, using {@link ToolRunner} for configuring MapReduce job is a
 * must. On the other, when using tool such as Cascading, having a clean command
 * line interface where the parameters are explicited and checked is also a
 * great help. This class bridges the gap for you.
 * 
 * Here is the canonical use :
 * 
 * <pre>
 * public static void main(String[] args) throws Exception {
 *     Tool hCommander = new HCommander(getCommands());
 *     int resultCode = ToolRunner.run(hCommander, args);
 *     System.exit(resultCode);
 * }
 * </pre>
 */
public class HCommander extends Configured implements Tool {
	private final JCommander jCommander = new JCommander();
	private final JCommanderHelper<Command> helper;

	/**
	 * Sort the commands by their identifier keyword and use them for interpreting the command line.
	 * @param commands
	 */
	public HCommander(Iterable<? extends Command> commands) {
		helper = new JCommanderHelper<Command>(jCommander, commands);
		jCommander.addCommand(new HelpCommand(this));
	}

	/**
	 * Parse the remaining arguments with {@link JCommander} and launch the
	 * selected {@link Command}.
	 */
	@Override
	public int run(String[] otherArgs) throws Exception {
		if (otherArgs.length == 0) {
			printUsage(null);
			return 1;
		}

		try {
			jCommander.parse(otherArgs);
		} catch (ParameterException e) {
			System.out.println(e.getMessage());
			System.out.println();
			printUsage(jCommander.getParsedCommand());
			return 2;
		}

		String commandName = jCommander.getParsedCommand();
		Command command = helper.getCommand(commandName);
		return command.execute(commandName, getConf());
	}

	/**
	 * Print the usage of the command of the provided command, if any, else
	 * print the short usage.
	 */
	public void printUsage(String commandName) {
		if (commandName == null) {
			helper.shortUsage();
			System.out.println("For more information about one command, use the 'help -about [command]' command.");
		} else {
			jCommander.usage(commandName);
			Command cliCommand = helper.getCommand(commandName);
			if (cliCommand instanceof Documented) {
				System.out.println();
				System.out.println(((Documented) cliCommand)
						.getExtendedDocumentation());
			}
		}
	}


}