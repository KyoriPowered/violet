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

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A {@link TypeParameter} which contains the type argument.
 *
 * <p>Rather than constructing both a {@link TypeParameter} and {@link TypeToken} for
 * use in {@link TypeToken#where(TypeParameter, TypeToken)}, we can simply construct
 * a single {@code TypeArgument} which is capable of providing both types.</p>
 *
 * @param <T> the type
 * @see TypeToken#where(TypeParameter, TypeToken)
 */
public abstract class TypeArgument<T> extends TypeParameter<T> {
  /**
   * The actual type.
   */
  final @NonNull TypeToken<? extends T> actual;

  public TypeArgument(final @NonNull Key<? extends T> key) {
    this(key.getTypeLiteral());
  }

  public TypeArgument(final @NonNull Class<? extends T> actual) {
    this(TypeToken.of(actual));
  }

  public TypeArgument(final @NonNull TypeLiteral<? extends T> actual) {
    this(EvenMoreTypes.token(actual));
  }

  public TypeArgument(final @NonNull TypeToken<? extends T> actual) {
    this.actual = actual;
  }
}
