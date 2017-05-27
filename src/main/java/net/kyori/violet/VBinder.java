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
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.multibindings.Multibinder;

import java.util.Set;

import javax.annotation.Nonnull;

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
    @Nonnull
    static VBinder of(@Nonnull final Binder binder) {
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
     * @param key the key to bind
     * @param <T> the type
     * @return a new multibinder
     * @see Multibinder#newSetBinder(Binder, Key)
     */
    @Nonnull
    default <T> Multibinder<T> inSet(@Nonnull final Key<T> key) {
        return Multibinder.newSetBinder(this.binder(), key);
    }

    /**
     * Creates a new multibinder that collects instances of {@code key}'s type in a {@link Set} that is
     * itself bound with the annotation (if any) of the key.
     *
     * @param type the type to bind
     * @param <T> the type
     * @return a new multibinder
     * @see Multibinder#newSetBinder(Binder, TypeLiteral)
     */
    @Nonnull
    default <T> Multibinder<T> inSet(@Nonnull final TypeLiteral<T> type) {
        return this.inSet(Key.get(type));
    }

    /**
     * Creates a new multibinder that collects instances of {@code key}'s type in a {@link Set} that is
     * itself bound with the annotation (if any) of the key.
     *
     * @param type the type to bind
     * @param <T> the type
     * @return a new multibinder
     * @see Multibinder#newSetBinder(Binder, Class)
     */
    @Nonnull
    default <T> Multibinder<T> inSet(@Nonnull final Class<T> type) {
        return this.inSet(Key.get(type));
    }

    /**
     * Creates a binding builder for a lazily-loaded type.
     *
     * @param key the key to bind
     * @param <T> the type
     * @return an annotated binding builder
     * @see Lazy
     */
    @Nonnull
    default <T> AnnotatedBindingBuilder<T> bindLazy(@Nonnull final Key<T> key) {
        return new LazyBindingBuilder<>(this.binder(), key);
    }

    /**
     * Creates a binding builder for a lazily-loaded type.
     *
     * @param type the type to bind
     * @param <T> the type
     * @return an annotated binding builder
     * @see Lazy
     */
    @Nonnull
    default <T> AnnotatedBindingBuilder<T> bindLazy(@Nonnull final TypeLiteral<T> type) {
        return this.bindLazy(Key.get(type));
    }

    /**
     * Creates a binding builder for a lazily-loaded type.
     *
     * @param type the type to bind
     * @param <T> the type
     * @return an annotated binding builder
     * @see Lazy
     */
    @Nonnull
    default <T> AnnotatedBindingBuilder<T> bindLazy(@Nonnull final Class<T> type) {
        return this.bindLazy(Key.get(type));
    }
}

final class VBinderImpl implements VBinder {

    // These sources should be skipped when identifying calling code.
    private static final Class<?>[] SKIPPED_SOURCES = new Class<?>[]{
        ForwardingBinder.class,
        VBinder.class,
        VBinderImpl.class
    };
    @Nonnull private final Binder binder;

    VBinderImpl(@Nonnull final Binder binder) {
        this.binder = binder.skipSources(SKIPPED_SOURCES);
    }

    @Nonnull
    @Override
    public Binder binder() {
        return this.binder;
    }
}
