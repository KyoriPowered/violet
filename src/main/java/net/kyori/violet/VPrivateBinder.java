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

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.PrivateBinder;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An extension of a {@link PrivateBinder} to provide additional helper methods.
 */
// Unfortunately it is not possible to create our own AbstractPrivateModule without
// making changes to guice itself, as guice special-cases PrivateModule
public interface VPrivateBinder extends ForwardingPrivateBinder, VBinder {
  /**
   * Creates a wrapped private binder.
   *
   * @param binder the private binder
   * @return a wrapped private binder
   */
  static @NonNull VPrivateBinder of(final @NonNull PrivateBinder binder) {
    if(binder instanceof VPrivateBinder) {
      return (VPrivateBinder) binder;
    }
    return new VPrivateBinderImpl(binder);
  }

  @Override
  default VPrivateBinder withSource(final Object source) {
    return of(ForwardingPrivateBinder.super.withSource(source));
  }

  @Override
  default VPrivateBinder skipSources(final Class... classesToSkip) {
    return of(ForwardingPrivateBinder.super.skipSources(classesToSkip));
  }

  /**
   * Creates a binding builder and exposes {@code key}.
   *
   * @param key the key to bind
   * @param <T> the type
   * @return a binding builder
   * @see Binder#bind(Key)
   * @see PrivateBinder#expose(Key)
   */
  default <T> @NonNull LinkedBindingBuilder<T> bindAndExpose(final @NonNull Key<T> key) {
    this.expose(key);
    return this.bind(key);
  }

  /**
   * Creates a binding builder and exposes {@code type}.
   *
   * @param type the type to bind
   * @param <T> the type
   * @return a binding builder
   * @see Binder#bind(Class)
   * @see PrivateBinder#expose(Class)
   */
  default <T> @NonNull AnnotatedBindingBuilder<T> bindAndExpose(final @NonNull Class<T> type) {
    return this.bindAndExpose(TypeLiteral.get(type));
  }

  /**
   * Creates a binding builder and exposes {@code type}.
   *
   * @param type the type to bind
   * @param <T> the type
   * @return a binding builder
   * @see Binder#bind(TypeLiteral)
   * @see PrivateBinder#expose(TypeLiteral)
   */
  default <T> @NonNull AnnotatedBindingBuilder<T> bindAndExpose(final @NonNull TypeLiteral<T> type) {
    return new BindAndExposeBindingBuilder<>(this.bind(type), this.expose(type));
  }
}
