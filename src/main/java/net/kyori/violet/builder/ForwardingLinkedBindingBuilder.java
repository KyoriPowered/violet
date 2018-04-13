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
package net.kyori.violet.builder;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.binder.ScopedBindingBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * A linked binding builder which forwards all its method calls to another linked binding builder.
 */
public interface ForwardingLinkedBindingBuilder<T> extends ForwardingScopedBindingBuilder, LinkedBindingBuilder<T> {
  /**
   * Gets the forwarded linked binding builder that methods are forwarded to.
   *
   * @return the forwarded linked binding builder
   */
  @Override
  @NonNull LinkedBindingBuilder<T> builder();

  @Override
  default ScopedBindingBuilder to(final Class<? extends T> implementation) {
    return this.builder().to(implementation);
  }

  @Override
  default ScopedBindingBuilder to(final TypeLiteral<? extends T> implementation) {
    return this.builder().to(implementation);
  }

  @Override
  default ScopedBindingBuilder to(final Key<? extends T> targetKey) {
    return this.builder().to(targetKey);
  }

  @Override
  default void toInstance(final T instance) {
    this.builder().toInstance(instance);
  }

  @Override
  default ScopedBindingBuilder toProvider(final Provider<? extends T> provider) {
    return this.builder().toProvider(provider);
  }

  @Override
  default ScopedBindingBuilder toProvider(final javax.inject.Provider<? extends T> provider) {
    return this.builder().toProvider(provider);
  }

  @Override
  default ScopedBindingBuilder toProvider(final Class<? extends javax.inject.Provider<? extends T>> providerType) {
    return this.builder().toProvider(providerType);
  }

  @Override
  default ScopedBindingBuilder toProvider(final TypeLiteral<? extends javax.inject.Provider<? extends T>> providerType) {
    return this.builder().toProvider(providerType);
  }

  @Override
  default ScopedBindingBuilder toProvider(final Key<? extends javax.inject.Provider<? extends T>> providerKey) {
    return this.builder().toProvider(providerKey);
  }

  @Override
  default <S extends T> ScopedBindingBuilder toConstructor(final Constructor<S> constructor) {
    return this.builder().toConstructor(constructor);
  }

  @Override
  default <S extends T> ScopedBindingBuilder toConstructor(final Constructor<S> constructor, final TypeLiteral<? extends S> type) {
    return this.builder().toConstructor(constructor, type);
  }

  @Override
  default void in(final Class<? extends Annotation> scopeAnnotation) {
    this.builder().in(scopeAnnotation);
  }

  @Override
  default void in(final Scope scope) {
    this.builder().in(scope);
  }

  @Override
  default void asEagerSingleton() {
    this.builder().asEagerSingleton();
  }
}
