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

import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedElementBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import net.kyori.violet.builder.ForwardingAnnotatedBindingBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.annotation.Annotation;

/**
 * A helper class to allow {@link VPrivateBinder#bindAndExpose} to bind and expose with
 * an annotation at the same time.
 *
 * @param <T> the type
 * @see VPrivateBinder#bindAndExpose(Class)
 * @see VPrivateBinder#bindAndExpose(TypeLiteral)
 */
// TODO(kashike): better name?
final class BindAndExposeBindingBuilder<T> implements ForwardingAnnotatedBindingBuilder<T> {
  private final AnnotatedBindingBuilder<T> bind;
  private final AnnotatedElementBuilder expose;

  BindAndExposeBindingBuilder(final AnnotatedBindingBuilder<T> bind, final AnnotatedElementBuilder expose) {
    this.bind = bind;
    this.expose = expose;
  }

  @Override
  public @NonNull AnnotatedBindingBuilder<T> builder() {
    return this.bind;
  }

  @Override
  public LinkedBindingBuilder<T> annotatedWith(final Class<? extends Annotation> annotationType) {
    this.expose.annotatedWith(annotationType);
    return this.bind.annotatedWith(annotationType);
  }

  @Override
  public LinkedBindingBuilder<T> annotatedWith(final Annotation annotation) {
    this.expose.annotatedWith(annotation);
    return this.bind.annotatedWith(annotation);
  }
}
