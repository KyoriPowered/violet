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
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.annotation.Annotation;

/**
 * A wrapper around a {@link Multibinder} to reduce code duplication.
 *
 * @param <T> the element type
 * @see Multibinder
 */
public class SetBinder<T> {
  protected final Multibinder<T> binder;

  public SetBinder(final @NonNull Binder binder, final @NonNull TypeLiteral<T> type) {
    this(Multibinder.newSetBinder(binder, type));
  }

  public SetBinder(final @NonNull Binder binder, final @NonNull TypeLiteral<T> type, final @NonNull Class<? extends Annotation> annotation) {
    this(Multibinder.newSetBinder(binder, type, annotation));
  }

  public SetBinder(final @NonNull Binder binder, final @NonNull TypeLiteral<T> type, final @NonNull Annotation annotation) {
    this(Multibinder.newSetBinder(binder, type, annotation));
  }

  public SetBinder(final @NonNull Binder binder, final @NonNull Class<T> type) {
    this(Multibinder.newSetBinder(binder, type));
  }

  public SetBinder(final @NonNull Binder binder, final @NonNull Class<T> type, final @NonNull Class<? extends Annotation> annotation) {
    this(Multibinder.newSetBinder(binder, type, annotation));
  }

  public SetBinder(final @NonNull Binder binder, final @NonNull Class<T> type, final @NonNull Annotation annotation) {
    this(Multibinder.newSetBinder(binder, type, annotation));
  }

  public SetBinder(final @NonNull Binder binder, final @NonNull Key<T> key) {
    this(Multibinder.newSetBinder(binder, key));
  }

  public SetBinder(final @NonNull Multibinder<T> binder) {
    this.binder = binder;
  }

  /**
   * Returns a binding builder used to add a new element in the set.
   *
   * <p>Each bound element must have a distinct value.</p>
   *
   * @return a binding builder
   * @see Multibinder#addBinding()
   */
  public @NonNull LinkedBindingBuilder<T> addBinding() {
    return this.binder.addBinding();
  }
}
