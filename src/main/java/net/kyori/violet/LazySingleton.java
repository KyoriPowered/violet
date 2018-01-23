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

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.ScopeAnnotation;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.internal.SingletonScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Apply this to implementation classes when you want only one instance
 * (per {@link Injector}) to be reused for all injections for that binding.
 *
 * <p>Unlike {@link Singleton}, {@code LazySingleton}s are <b>not</b> eager
 * when in {@link Stage#PRODUCTION}.</p>
 *
 * @see Singleton
 */
@Retention(RetentionPolicy.RUNTIME)
@ScopeAnnotation
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LazySingleton {

  /**
   * A scope that enables lazy singletons.
   *
   * @see SingletonScope
   */
  Scope SCOPE = new Scope() {
    @Override
    public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
      return Scopes.SINGLETON.scope(key, unscoped);
    }

    @Override
    public String toString() {
      return "LazySingleton.SCOPE";
    }
  };
}
