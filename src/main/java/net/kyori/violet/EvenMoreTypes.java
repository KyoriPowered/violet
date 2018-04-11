/*
 * This file is part of violet, licensed under the MIT License.
 *
 * Copyright (c) 2017-2018 KyoriPowered
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
import com.google.inject.internal.MoreTypes;
import com.google.inject.util.Types;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @see Types
 * @see MoreTypes
 */
public final class EvenMoreTypes {
  private EvenMoreTypes() {
  }

  /**
   * Converts a type token to a type literal.
   *
   * @param token the type token
   * @param <T> the type
   * @return the type literal
   */
  public static <T> @NonNull TypeLiteral<T> literal(final @NonNull TypeToken<T> token) {
    return (TypeLiteral<T>) TypeLiteral.get(token.getType());
  }

  /**
   * Converts a type literal to a type token.
   *
   * @param literal the type literal
   * @param <T> the type
   * @return the type token
   */
  public static <T> @NonNull TypeToken<T> token(final @NonNull TypeLiteral<T> literal) {
    return (TypeToken<T>) TypeToken.of(literal.getType());
  }
}
