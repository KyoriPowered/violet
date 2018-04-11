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

import com.google.inject.Inject;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Supplier;

import javax.inject.Provider;

/**
 * A lazily-loaded dependency.
 *
 * <p>A {@code Lazy} loads its value on the first call to {@link #get()}, and remembers that value for
 * all subsequent calls to {@link #get()}.</p>
 *
 * @param <T> the type to provide
 */
// https://github.com/google/guice/issues/852
public class Lazy<T> implements Provider<T>, Supplier<T> {
  private final Provider<T> provider;
  private T value;
  private boolean loaded;

  @Inject
  public Lazy(final @NonNull Provider<T> provider) {
    this.provider = provider;
  }

  @Override
  public T get() {
    if(!this.loaded) {
      this.value = this.provider.get();
      this.loaded = true;
    }
    return this.value;
  }
}
