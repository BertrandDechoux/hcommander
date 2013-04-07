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

import org.apache.hadoop.conf.Configuration;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames="help", commandDescription="Print usage of a selected command.")
public class HelpCommand implements Command {
	private final HCommander hCommander;
	
	@Parameter(names="-about", description="Name of the command for which help is required.")
	String about;

	public HelpCommand(HCommander hCommander) {
		this.hCommander = hCommander;
	}

	/* (non-Javadoc)
	 * @see com.hcommander.Command#execute(java.lang.String, org.apache.hadoop.conf.Configuration)
	 */
	@Override
	public int execute(String commandName, Configuration configuration)
			throws Exception {
		hCommander.printUsage(about);
		return 0;
	}

}
