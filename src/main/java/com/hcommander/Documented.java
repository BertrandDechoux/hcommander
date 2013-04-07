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

/**
 * {@link Command} might want to provide a more complete documentation of their
 * behavior when their description is too brief.
 */
public interface Documented {

	/** Return an extended documentation of the command. */
	String getExtendedDocumentation();

}
