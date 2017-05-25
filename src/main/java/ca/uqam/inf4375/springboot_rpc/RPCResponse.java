/*
 * Copyright 2017 Alexandre Terrasa <alexandre@moz-code.org>.
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

public class RPCResponse {

    public String jsonrpc;

    public Object result;

    public Object error;

    public Object id;

    public RPCResponse(String jsonrpc, Object result, Object error, Object id) {
        this.jsonrpc = jsonrpc;
        this.result = result;
        this.error = error;
        this.id = id;
    }

    @Override
    public String toString() {
        String sresult = result == null ? "null" : result.toString();
        String serror = error == null ? "null" : error.toString();
        String sid = id == null ? "" : "#" + id.toString();
        return "result: " + sresult + "; error: " + serror + ";" + sid;
    }
}
