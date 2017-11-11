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
import com.google.inject.Module;
import net.kyori.blizzard.NonNull;
import net.kyori.blizzard.Nullable;

import static com.google.common.base.Preconditions.checkState;

/**
 * A support class for {@link Module}s which reduces repetition and results in
 * a more readable configuration.
 *
 * @see com.google.inject.AbstractModule
 */
public abstract class AbstractModule implements Module, VBinder {

  @Nullable private Binder binder;

  @Override
  public final void configure(final Binder binder) {
    checkState(this.binder == null, "Re-entry is not allowed.");
    this.binder = binder;
    try {
      this.configure();
    } finally {
      this.binder = null;
    }
  }

  /**
   * Configures a {@link Binder} via the exposed methods.
   */
  protected abstract void configure();

  @NonNull
  @Override
  public Binder binder() {
    checkState(this.binder != null, "The binder can only be used inside configure()");
    return this.binder;
  }
}
