/*
 * Copyright 2010 Luke Daley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovyx.remote.transport.local

import groovyx.remote.*
import groovyx.remote.server.Receiver
import groovyx.remote.client.Transport

class LocalTransport implements Transport {

	final ClassLoader classLoader
	final Receiver receiver
	
	LocalTransport(Receiver receiver, ClassLoader classLoader) {
		this.receiver = receiver
		this.classLoader = classLoader
	}

	Result send(CommandChain commandChain) throws IOException {
		def commandBytes = new ByteArrayOutputStream()
		commandChain.writeTo(commandBytes)
		
		def resultBytes = new ByteArrayOutputStream()
		receiver.execute(new ByteArrayInputStream(commandBytes.toByteArray()), resultBytes)
	
		Result.readFrom(new ByteArrayInputStream(resultBytes.toByteArray()), classLoader)
	}
}