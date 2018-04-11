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
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Set;
import java.util.function.Consumer;

/**
 * An extension of a {@link Binder} to provide additional helper methods.
 */
public interface VBinder extends ForwardingBinder {
  /**
   * Creates a wrapped binder.
   *
   * @param binder the binder
   * @return a wrapped binder
   */
  static @NonNull VBinder of(final @NonNull Binder binder) {
    // avoid re-wrapping
    if(binder instanceof VBinder) {
      return (VBinder) binder;
    }
    return new VBinderImpl(binder);
  }

  @Override
  default VBinder withSource(final Object source) {
    return of(ForwardingBinder.super.withSource(source));
  }

  @Override
  default VBinder skipSources(final Class... classesToSkip) {
    return of(ForwardingBinder.super.skipSources(classesToSkip));
  }

  @Override
  default VPrivateBinder newPrivateBinder() {
    return VPrivateBinder.of(ForwardingBinder.super.newPrivateBinder());
  }

  /**
   * Creates a new multibinder that collects instances of {@code key}'s type in a {@link Set} that is
   * itself bound with the annotation (if any) of the key.
   *
   * @param key the key to create a set binder for
   * @param <T> the type of object
   * @return a new multibinder
   * @see Multibinder#newSetBinder(Binder, Key)
   */
  default <T> @NonNull Multibinder<T> inSet(final @NonNull Key<T> key) {
    return Multibinder.newSetBinder(this.binder(), key);
  }

  /**
   * Creates a new multibinder that collects instances of {@code key}'s type in a {@link Set} that is
   * itself bound with the annotation (if any) of the key.
   *
   * @param type the type to create a set binder for
   * @param <T> the type of object
   * @return a new multibinder
   * @see Multibinder#newSetBinder(Binder, TypeLiteral)
   */
  default <T> @NonNull Multibinder<T> inSet(final @NonNull TypeLiteral<T> type) {
    return this.inSet(Key.get(type));
  }

  /**
   * Creates a new multibinder that collects instances of {@code key}'s type in a {@link Set} that is
   * itself bound with the annotation (if any) of the key.
   *
   * @param type the type to create a set binder for
   * @param <T> the type of object
   * @return a new multibinder
   * @see Multibinder#newSetBinder(Binder, Class)
   */
  default <T> @NonNull Multibinder<T> inSet(final @NonNull Class<T> type) {
    return this.inSet(Key.get(type));
  }

  /**
   * Installs a factory module for the specified key.
   *
   * @param key the key of the factory module to bind
   * @param <T> the type of object
   * @see FactoryModuleBuilder#build(Key)
   */
  default <T> void installFactory(final @NonNull Key<T> key) {
    this.install(new FactoryModuleBuilder().build(key));
  }

  /**
   * Installs a factory module for the specified type.
   *
   * @param type the type of the factory module to bind
   * @param <T> the type of object
   * @see FactoryModuleBuilder#build(TypeLiteral)
   */
  default <T> void installFactory(final @NonNull TypeLiteral<T> type) {
    this.installFactory(Key.get(type));
  }

  /**
   * Installs a factory module for the specified type.
   *
   * @param type the type of the factory module to bind
   * @param <T> the type of object
   * @see FactoryModuleBuilder#build(Class)
   */
  default <T> void installFactory(final @NonNull Class<T> type) {
    this.installFactory(Key.get(type));
  }

  /**
   * Installs a factory module for the specified key, and allow a consumer to provide additional configuration information.
   *
   * @param key the key of the factory module to bind
   * @param consumer the consumer used to provide additional configuration information
   * @param <T> the type of object
   * @see FactoryModuleBuilder#build(Key)
   */
  default <T> void installFactory(final @NonNull Key<T> key, final @NonNull Consumer<FactoryModuleBuilder> consumer) {
    final FactoryModuleBuilder builder = new FactoryModuleBuilder();
    consumer.accept(builder);
    this.install(builder.build(key));
  }

  /**
   * Installs a factory module for the specified type, and allow a consumer to provide additional configuration information.
   *
   * @param type the type of the factory module to bind
   * @param consumer the consumer used to provide additional configuration information
   * @param <T> the type of object
   * @see FactoryModuleBuilder#build(TypeLiteral)
   */
  default <T> void installFactory(final @NonNull TypeLiteral<T> type, final @NonNull Consumer<FactoryModuleBuilder> consumer) {
    this.installFactory(Key.get(type), consumer);
  }

  /**
   * Installs a factory module for the specified type, and allow a consumer to provide additional configuration information.
   *
   * @param type the type of the factory module to bind
   * @param consumer the consumer used to provide additional configuration information
   * @param <T> the type of object
   * @see FactoryModuleBuilder#build(TypeLiteral)
   */
  default <T> void installFactory(final @NonNull Class<T> type, final @NonNull Consumer<FactoryModuleBuilder> consumer) {
    this.installFactory(Key.get(type), consumer);
  }

  /**
   * Creates a binding builder for a lazily-loaded type.
   *
   * @param key the key to create a binding builder for a lazy {@code T}
   * @param <T> the type of object
   * @return an annotated binding builder
   * @see Lazy
   */
  default <T> @NonNull AnnotatedBindingBuilder<T> bindLazy(final @NonNull Key<T> key) {
    return new LazyBindingBuilder<>(this.binder(), key);
  }

  /**
   * Creates a binding builder for a lazily-loaded type.
   *
   * @param type the type to create a binding builder for a lazy {@code T}
   * @param <T> the type of object
   * @return an annotated binding builder
   * @see Lazy
   */
  default <T> @NonNull AnnotatedBindingBuilder<T> bindLazy(final @NonNull TypeLiteral<T> type) {
    return this.bindLazy(Key.get(type));
  }

  /**
   * Creates a binding builder for a lazily-loaded type.
   *
   * @param type the type to create a binding builder for a lazy {@code T}
   * @param <T> the type of object
   * @return an annotated binding builder
   * @see Lazy
   */
  default <T> @NonNull AnnotatedBindingBuilder<T> bindLazy(final @NonNull Class<T> type) {
    return this.bindLazy(Key.get(type));
  }
}
