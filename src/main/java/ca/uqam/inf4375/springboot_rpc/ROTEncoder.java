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

public class ROTEncoder {

    // Number of encoded messages
    private Integer count;

    public ROTEncoder() {
        reset();
    }

    // Encode `message` by a rotation of `rot`
    public String encode(Integer rot, String message) {
        if (rot < 1 || rot > 25) {
            throw new IllegalArgumentException("Expected rotation between 1 and 25, got `" + rot + "`");
        }

        StringBuilder result = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (c >= 'a' && c < 'z' - rot) {
                c += rot;
            } else if (c >= 'A' && c < 'Z' - rot) {
                c += rot;
            } else if (c >= 'z' - rot && c <= 'z') {
                c -= 26 - rot;
            } else if (c >= 'Z' - rot && c <= 'Z') {
                c -= 26 - rot;
            }
            result.append(c);
        }
        count++;
        return result.toString();
    }

    // Decode a `message` previously encoded by a rotation of `rot`
    public String decode(Integer rot, String message) {
        return encode(26 - rot, message);
    }

    // Reset `counter` to 0
    public final void reset() {
        count = 0;
    }

    public Integer getCount() {
        return count;
    }
}
