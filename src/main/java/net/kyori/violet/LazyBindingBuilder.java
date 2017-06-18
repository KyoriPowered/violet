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

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.util.Types;
import net.kyori.violet.builder.ForwardingLinkedBindingBuilder;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;

final class LazyBindingBuilder<T> implements AnnotatedBindingBuilder<T>, ForwardingLinkedBindingBuilder<T> {

  private final Binder binder;
  private final AnnotatedBindingBuilder<Lazy<T>> builder;
  private final Key<T> key;

  LazyBindingBuilder(final Binder binder, final Key<T> key) {
    this.binder = binder.skipSources(LazyBindingBuilder.class);
    this.builder = this.binder.bind((TypeLiteral<Lazy<T>>) TypeLiteral.get(Types.newParameterizedType(Lazy.class, key.getTypeLiteral().getType())));
    this.key = key;
  }

  @Nonnull
  @Override
  public LinkedBindingBuilder<T> builder() {
    return this.binder.bind(this.key);
  }

  @Override
  public LinkedBindingBuilder<T> annotatedWith(final Class<? extends Annotation> annotationType) {
    return this.annotatedWith(this.builder.annotatedWith(annotationType), Key.get(this.key.getTypeLiteral(), annotationType));
  }

  @Override
  public LinkedBindingBuilder<T> annotatedWith(final Annotation annotation) {
    return this.annotatedWith(this.builder.annotatedWith(annotation), Key.get(this.key.getTypeLiteral(), annotation));
  }

  private LinkedBindingBuilder<T> annotatedWith(final LinkedBindingBuilder<Lazy<T>> builder, final Key<T> key) {
    final Provider<T> provider = this.binder.getProvider(key);
    builder.toProvider(() -> new Lazy<>(provider));
    return (ForwardingLinkedBindingBuilder<T>) () -> this.binder.bind(key);
  }
}

