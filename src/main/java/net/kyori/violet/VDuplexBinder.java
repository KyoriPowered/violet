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

import net.kyori.blizzard.NonNull;

/**
 * An extension of a {@link DuplexBinder} to provide additional helper methods.
 *
 * @see DuplexModule
 */
public interface VDuplexBinder extends ForwardingDuplexBinder, VPrivateBinder {

  /**
   * Creates a wrapped duplex binder.
   *
   * @param binder the duplex binder
   * @return a wrapped duplex binder
   */
  @NonNull
  static VDuplexBinder of(@NonNull final DuplexBinder binder) {
    // avoid re-wrapping
    if(binder instanceof VDuplexBinder) {
      return (VDuplexBinder) binder;
    }
    return new VDuplexBinderImpl(binder);
  }

  @Override
  default VDuplexBinder withSource(final Object source) {
    return of(ForwardingDuplexBinder.super.withSource(source));
  }

  @Override
  default VDuplexBinder skipSources(final Class... classesToSkip) {
    return of(ForwardingDuplexBinder.super.skipSources(classesToSkip));
  }
}

final class VDuplexBinderImpl implements VDuplexBinder {

  // These sources should be skipped when identifying calling code.
  private static final Class<?>[] SKIPPED_SOURCES = new Class<?>[]{
    ForwardingBinder.class,
    ForwardingPrivateBinder.class,
    ForwardingDuplexBinder.class,
    VBinder.class,
    VBinderImpl.class,
    VPrivateBinder.class,
    VPrivateBinderImpl.class,
    VDuplexBinder.class,
    VDuplexBinderImpl.class
  };
  @NonNull private final DuplexBinder binder;

  VDuplexBinderImpl(@NonNull final DuplexBinder binder) {
    this.binder = binder.skipSources(SKIPPED_SOURCES);
  }

  @NonNull
  @Override
  public DuplexBinder binder() {
    return this.binder;
  }
}
