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

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ROTEncoderTest {

    ROTEncoder encoder;

    @Before
    public void setUp() {
        encoder = new ROTEncoder();
    }

    @Test(expected=IllegalArgumentException.class)
    public void testROT0() {
        encoder.encode(0, "foo");
    }

    @Test
    public void testROT1() {
        String message = "Hello World!";
        String expected = "Ifmmp Xpsme!";
        String encoded = encoder.encode(1, message);
        String decoded = encoder.decode(1, encoded);
        assertEquals(expected, encoded);
        assertEquals(message, decoded);
    }

    @Test
    public void testROT10() {
        String message = "Hello World!";
        String expected = "Rovvy Gybvn!";
        String encoded = encoder.encode(10, message);
        String decoded = encoder.decode(10, encoded);
        assertEquals(expected, encoded);
        assertEquals(message, decoded);
    }

    @Test
    public void testROT13() {
        String message = "Hello World!";
        String expected = "Uryyb Jbeyq!";
        String encoded = encoder.encode(13, message);
        String decoded = encoder.decode(13, encoded);
        assertEquals(expected, encoded);
        assertEquals(message, decoded);
    }

    @Test
    public void testROT25() {
        String message = "Hello World!";
        String expected = "Gdkkn Vnqkc!";
        String encoded = encoder.encode(25, message);
        String decoded = encoder.decode(25, encoded);
        assertEquals(expected, encoded);
        assertEquals(message, decoded);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testROT26() {
        encoder.encode(26, "foo");
    }

    @Test
    public void testReset() {
        encoder.encode(1, "foo");
        encoder.encode(2, "foo");
        encoder.encode(9, "foo");
        encoder.reset();
        Integer expected = 0;
        Integer result = encoder.getCount();
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetCount() {
        encoder.encode(1, "foo");
        encoder.encode(2, "foo");
        encoder.encode(9, "foo");
        Integer expected = 3;
        Integer result = encoder.getCount();
        assertEquals(expected, result);
    }

}
