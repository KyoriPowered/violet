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
import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.PrivateBinder;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Message;
import com.google.inject.spi.ModuleAnnotatedMethodScanner;
import com.google.inject.spi.ProvisionListener;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeListener;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

/**
 * A binder which forwards all its method calls to another binder.
 */
public interface ForwardingBinder extends Binder {

  /**
   * Gets the forwarded binder that methods are forwarded to.
   *
   * @return the forwarded binder
   */
  @Nonnull
  Binder binder();

  @Override
  default void bindInterceptor(final Matcher<? super Class<?>> classMatcher, final Matcher<? super Method> methodMatcher, final MethodInterceptor... interceptors) {
    this.binder().bindInterceptor(classMatcher, methodMatcher, interceptors);
  }

  @Override
  default void bindScope(final Class<? extends Annotation> annotationType, final Scope scope) {
    this.binder().bindScope(annotationType, scope);
  }

  @Override
  default <T> LinkedBindingBuilder<T> bind(final Key<T> key) {
    return this.binder().bind(key);
  }

  @Override
  default <T> AnnotatedBindingBuilder<T> bind(final TypeLiteral<T> typeLiteral) {
    return this.binder().bind(typeLiteral);
  }

  @Override
  default <T> AnnotatedBindingBuilder<T> bind(final Class<T> type) {
    return this.binder().bind(type);
  }

  @Override
  default AnnotatedConstantBindingBuilder bindConstant() {
    return this.binder().bindConstant();
  }

  @Override
  default <T> void requestInjection(final TypeLiteral<T> type, final T instance) {
    this.binder().requestInjection(type, instance);
  }

  @Override
  default void requestInjection(final Object instance) {
    this.binder().requestInjection(instance);
  }

  @Override
  default void requestStaticInjection(final Class<?>... types) {
    this.binder().requestStaticInjection(types);
  }

  @Override
  default void install(final Module module) {
    this.binder().install(module);
  }

  @Override
  default Stage currentStage() {
    return this.binder().currentStage();
  }

  @Override
  default void addError(final String message, final Object... arguments) {
    this.binder().addError(message, arguments);
  }

  @Override
  default void addError(final Throwable t) {
    this.binder().addError(t);
  }

  @Override
  default void addError(final Message message) {
    this.binder().addError(message);
  }

  @Override
  default <T> Provider<T> getProvider(final Key<T> key) {
    return this.binder().getProvider(key);
  }

  @Override
  default <T> Provider<T> getProvider(final Dependency<T> dependency) {
    return this.binder().getProvider(dependency);
  }

  @Override
  default <T> Provider<T> getProvider(final Class<T> type) {
    return this.binder().getProvider(type);
  }

  @Override
  default <T> MembersInjector<T> getMembersInjector(final TypeLiteral<T> typeLiteral) {
    return this.binder().getMembersInjector(typeLiteral);
  }

  @Override
  default <T> MembersInjector<T> getMembersInjector(final Class<T> type) {
    return this.binder().getMembersInjector(type);
  }

  @Override
  default void convertToTypes(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeConverter converter) {
    this.binder().convertToTypes(typeMatcher, converter);
  }

  @Override
  default void bindListener(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeListener listener) {
    this.binder().bindListener(typeMatcher, listener);
  }

  @Override
  default void bindListener(final Matcher<? super Binding<?>> bindingMatcher, final ProvisionListener... listeners) {
    this.binder().bindListener(bindingMatcher, listeners);
  }

  @Override
  default Binder withSource(final Object source) {
    return this.binder().withSource(source);
  }

  @Override
  default Binder skipSources(final Class... classesToSkip) {
    return this.binder().skipSources(classesToSkip);
  }

  @Override
  default PrivateBinder newPrivateBinder() {
    return this.binder().newPrivateBinder();
  }

  @Override
  default void requireExplicitBindings() {
    this.binder().requireExplicitBindings();
  }

  @Override
  default void disableCircularProxies() {
    this.binder().disableCircularProxies();
  }

  @Override
  default void requireAtInjectOnConstructors() {
    this.binder().requireAtInjectOnConstructors();
  }

  @Override
  default void requireExactBindingAnnotations() {
    this.binder().requireExactBindingAnnotations();
  }

  @Override
  default void scanModulesForAnnotatedMethods(final ModuleAnnotatedMethodScanner scanner) {
    this.binder().scanModulesForAnnotatedMethods(scanner);
  }
}
