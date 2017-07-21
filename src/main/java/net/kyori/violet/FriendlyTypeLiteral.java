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

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;

import java.lang.reflect.Type;

import javax.annotation.Nonnull;

/**
 * Represents a generic type {@code T}.
 *
 * <p>An extension of {@link TypeLiteral} with some helpful methods.</p>
 *
 * @param <T> the type
 */
public abstract class FriendlyTypeLiteral<T> extends TypeLiteral<T> {

  /**
   * Creates a type literal by substituting formal type variables with the given actual type arguments.
   *
   * @param args the actual type arguments
   * @return a type literal
   * @see Types#newParameterizedType(Type, Type...)
   * @see TypeToken#where(TypeParameter, TypeToken)
   */
  // https://github.com/google/guice/issues/657
  @Nonnull
  public TypeLiteral<T> where(@Nonnull final TypeArgument<?>... args) {
    // convert this literal into a token so we can use the type resolver
    TypeToken<T> token = EvenMoreTypes.token(this);
    for(final TypeArgument arg : args) {
      token = token.where(arg, arg.actual);
    }
    return EvenMoreTypes.literal(token);
  }
}
