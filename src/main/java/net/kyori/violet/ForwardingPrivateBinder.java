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

import com.google.inject.Key;
import com.google.inject.PrivateBinder;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedElementBuilder;
import net.kyori.blizzard.NonNull;

/**
 * A private binder which forwards all its method calls to another private binder.
 */
public interface ForwardingPrivateBinder extends ForwardingBinder, PrivateBinder {

  /**
   * Gets the forwarded private binder that methods are forwarded to.
   *
   * @return the forwarded private binder
   */
  @NonNull
  @Override
  PrivateBinder binder();

  @Override
  default void expose(final Key<?> key) {
    this.binder().expose(key);
  }

  @Override
  default AnnotatedElementBuilder expose(final Class<?> type) {
    return this.binder().expose(type);
  }

  @Override
  default AnnotatedElementBuilder expose(final TypeLiteral<?> type) {
    return this.binder().expose(type);
  }

  @Override
  default PrivateBinder withSource(final Object source) {
    return this.binder().withSource(source);
  }

  @Override
  default PrivateBinder skipSources(final Class... classesToSkip) {
    return this.binder().skipSources(classesToSkip);
  }
}
