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

import java.util.List;

public class RPCRequest {

    public String jsonrpc;

    public String method;

    public List<Object> params;

    public Object id;

    public RPCRequest() {
    }

    public RPCRequest(String jsonrpc, String method, List<Object> params, Object id) {
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.params = params;
        this.id = id;
    }

    @Override
    public String toString() {
        String smethod = method == null  ? "null" : method;
        String sid = id == null  ? "" : ("#" + id.toString());
        StringBuilder sparams = new StringBuilder(); // No Java 8, no String.join... :(
        if(params != null) {
            for (Integer i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                sparams.append(param.toString());
                if (i < params.size() - 1) {
                    sparams.append(", ");
                }
            }
        }
        return smethod + "(" + sparams.toString() + ")" + sid;
    }
}
