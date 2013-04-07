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
 * A marker interface that should be used to tag classes containing {@see
 * Parameter}. That way, reuse is simplified when using {@see
 * ParametersDelegate} : all implementations of this interface are potential
 * candidates.
 */
public interface Parameterizable {

}
