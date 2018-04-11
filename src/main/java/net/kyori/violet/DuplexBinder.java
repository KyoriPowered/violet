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
import com.google.inject.PrivateModule;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A duplex binder is a {@link PrivateBinder} with access to the enclosing (public) environment
 * via {@link #publicBinder()}.
 *
 * @see PrivateModule
 * @see DuplexModule
 */
public interface DuplexBinder extends ForwardingPrivateBinder {
  /**
   * Creates a new duplex binder.
   *
   * @param binder the enclosing (public) binder
   * @return a new duplex binder
   */
  static @NonNull DuplexBinder create(final @NonNull Binder binder) {
    if(binder instanceof DuplexBinder) {
      return (DuplexBinder) binder;
    }
    return new DuplexBinderImpl(binder, binder.newPrivateBinder());
  }

  /**
   * Gets the binder of the enclosing environment.
   *
   * @return the binder of the enclosing environment
   */
  @NonNull Binder publicBinder();

  @Override
  default DuplexBinder withSource(final Object source) {
    return new DuplexBinderImpl(this.publicBinder().withSource(source), this.binder().withSource(source));
  }

  @Override
  default DuplexBinder skipSources(final Class... classesToSkip) {
    return new DuplexBinderImpl(this.publicBinder().skipSources(classesToSkip), this.binder().skipSources(classesToSkip));
  }
}
