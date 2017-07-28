/*
 * This file is part of violet, licensed under the MIT License.
 *
 * Copyright (c) 2017 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.violet;

import com.google.common.reflect.TypeToken;
import com.google.inject.TypeLiteral;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EvenMoreTypesTest {

  @Test
  public void testLiteralToToken() {
    final TypeLiteral<Map<String, List<String>>> literal = new TypeLiteral<Map<String, List<String>>>() {};
    final TypeToken<Map<String, List<String>>> token = EvenMoreTypes.token(literal);
    assertEquals(literal.getType(), token.getType());
  }

  @Test
  public void testTokenToLiteral() {
    final TypeToken<Map<String, List<String>>> token = new TypeToken<Map<String, List<String>>>() {};
    final TypeLiteral<Map<String, List<String>>> literal = EvenMoreTypes.literal(token);
    assertEquals(token.getType(), literal.getType());
  }
}
