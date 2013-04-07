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
import org.apache.hadoop.util.ToolRunner;

import com.beust.jcommander.Parameters;

/**
 * A program that can be parametrized and run by the {@link HCommander}.
 * It should be annotated with {@link Parameters} and should provide the command name.
 * @see Documented
 * @see Parameterizable
 */
public interface Command {
	
	/**
	 * Execute the command by providing the configuration with all the arguments
	 * overridden by {@link ToolRunner} if {@link HCommander} is
	 * called as expected.
	 **/
	int execute(String commandName, Configuration configuration) throws Exception;

}
