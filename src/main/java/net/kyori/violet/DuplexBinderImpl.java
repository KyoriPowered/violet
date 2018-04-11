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
import com.google.inject.Module;
import com.google.inject.PrivateBinder;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

final class DuplexBinderImpl implements DuplexBinder {
  // These sources should be skipped when identifying calling code.
  private static final Class<?>[] SKIPPED_SOURCES = new Class<?>[]{
    ForwardingBinder.class,
    ForwardingPrivateBinder.class,
    ForwardingDuplexBinder.class,
    DuplexBinder.class,
    DuplexBinderImpl.class,
    DuplexModule.class
  };
  private static final ThreadLocal<DuplexBinderImpl> ACTIVE_BINDER = new ThreadLocal<>();
  private final Binder publicBinder;
  private final PrivateBinder privateBinder;

  DuplexBinderImpl(final Binder publicBinder, final PrivateBinder privateBinder) {
    this.publicBinder = publicBinder.skipSources(SKIPPED_SOURCES);
    // Special case DuplexBinder to prevent creation of a new DuplexBinderImpl when skipping sources
    this.privateBinder = (privateBinder instanceof DuplexBinder ? ((DuplexBinder) privateBinder).binder() : privateBinder).skipSources(SKIPPED_SOURCES);
  }

  @Override
  public @NonNull Binder publicBinder() {
    return this.publicBinder;
  }

  @Override
  public @NonNull PrivateBinder binder() {
    return this.privateBinder;
  }

  @Override
  public void install(final Module module) {
    final /* @Nullable */ DuplexBinderImpl activeBinder = ACTIVE_BINDER.get();
    ACTIVE_BINDER.set(this);
    try {
      this.privateBinder.install(module);
    } finally {
      ACTIVE_BINDER.set(activeBinder);
    }
  }

  static @Nullable DuplexBinder activeBinder(final @NonNull Binder binder) {
    if(binder instanceof DuplexBinder) {
      return (DuplexBinder) binder;
    }
    final /* @Nullable */ DuplexBinderImpl activeBinder = ACTIVE_BINDER.get();
    if(activeBinder != null && activeBinder.privateBinder == binder) {
      return activeBinder;
    }
    return null;
  }
}
