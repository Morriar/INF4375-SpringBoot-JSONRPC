/*
 * Copyright 2016 Alexandre Terrasa <alexandre@moz-code.org>.
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
package ca.uqam.inf4375.springboot_rpc;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class ROTController {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ROTController.class, args);
    }

    // Supported RPC Version
    public static final String RPC_VERSION = "2.0";

    // ROT encoder instance used for the encode / decode services
    public ROTEncoder rot = new ROTEncoder();

    // Process JSON-RPC requests
    @RequestMapping(value = "/rpc", method = RequestMethod.POST)
    public RPCResponse rpc(@RequestBody RPCRequest request) {
        // Check that the request was correctly deserialized
        if (request == null) {
            return error(request, -32700, "Request is not a valid JSON-RPC request");
        }

        // Check RPC version
        if (request.jsonrpc == null || !request.jsonrpc.equals(RPC_VERSION)) {
            return error(request, -32600, "Unsupported JSON-RPC version `" + request.jsonrpc + "`, accept only `" + RPC_VERSION + "`");
        }

        // Check that a method name is provided
        if (request.method == null) {
            return error(request, -32600, "No method provided");
        }

        // Here we protect the call so if anything goes wrong, the server will not crash
        try {
            return call(request);
        } catch (IllegalArgumentException e) {
            // Error related to methods arguments
            return error(request, -32602, e.getMessage());
        } catch (Exception e) {
            // We catch all exception and return them as a valid JSON-RPC response.
            // In real-life, this is bad because you can leak sensible internal data...
            return error(request, -32000, e.getMessage());
        }
    }

    // Execute RPC call
    public RPCResponse call(RPCRequest request) {
        switch (request.method) {
            case "encode":
                checkEncodeParams(request);
                Integer move = (Integer) request.params.get(0);
                String message = (String) request.params.get(1);
                String result = rot.encode(move, message);
                return response(request, result);
            case "decode":
                checkEncodeParams(request);
                move = (Integer) request.params.get(0);
                message = (String) request.params.get(1);
                result = rot.decode(move, message);
                return response(request, result);
            case "count":
                checkEmptyParams(request);
                return response(request, rot.getCount());
            case "reset":
                checkEmptyParams(request);
                rot.reset();
                return response(request, null);
        }
        return error(request, -32601, "Method `" + request.method + "` not found");
    }

    // Check arguments for the encode/decode methods
    //
    // * Does nothing if everything is ok.
    // * Throws IllegalArgumentException if the arguments are invalid.
    public void checkEncodeParams(RPCRequest request) {
        if (request.params == null || request.params.size() != 2) {
            throw new IllegalArgumentException("Two arguments expected for method `" + request.method + "`");
        }
        if (!(request.params.get(0) instanceof Integer)) {
            throw new IllegalArgumentException("First argument expected to be an Integer for method `" + request.method + "`");
        }
        if (!(request.params.get(1) instanceof String)) {
            throw new IllegalArgumentException("Second argument expected to be a String for method `" + request.method + "`");
        }
    }

    // Check arguments for the count/reset methods
    //
    // * Does nothing if everything is ok.
    // * Throws IllegalArgumentException if the arguments are not empty.
    public void checkEmptyParams(RPCRequest request) {
        if (request.params != null && !request.params.isEmpty()) {
            throw new IllegalArgumentException("No arguments expected for method `" + request.method + "`");
        }
    }

    // Return a valid result as a RPCResponse
    public RPCResponse response(RPCRequest request, Object result) {
        return new RPCResponse(RPC_VERSION, result, null, request.id);
    }

    // Return an error message as a RPCResponse
    public RPCResponse error(RPCRequest request, Integer code, String message) {
        return new RPCResponse(RPC_VERSION, null, new RPCError(code, message), request.id);
    }

}
