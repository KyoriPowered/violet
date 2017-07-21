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

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import static org.junit.Assert.assertEquals;

public class TypeArgumentTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConcreteTypeRejected() {
    new TypeArgument<String>(new TypeToken<String>() {}) {};
  }

  @Test
  public <T> void testCaptureTypeParameter() throws NoSuchMethodException {
    final TypeVariable<?> expected = TypeArgumentTest.class.getDeclaredMethod("testCaptureTypeParameter").getTypeParameters()[0];
    final Type actual = new TypeArgument<T>(new TypeLiteral<T>() {}) {}.actual.getType();
    assertEquals(expected, actual);
  }
}
