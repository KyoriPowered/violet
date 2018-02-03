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
import com.google.inject.util.Types;
import net.kyori.blizzard.NonNull;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A collection of methods for constructing common parameterized type literals.
 */
public interface TypeLiterals {
  /**
   * Creates a type literal representing a {@link Collection} whose elements are of type {@code type}.
   *
   * @param type the type of element
   * @param <T> the type
   * @return a type literal
   * @see Types#collectionOf(Type)
   */
  @NonNull
  static <T> TypeLiteral<Collection<T>> collectionOf(@NonNull final Class<T> type) {
    return collectionOf(TypeLiteral.get(type));
  }

  /**
   * Creates a type literal representing a {@link Collection} whose elements are of type {@code type}.
   *
   * @param type the type of element
   * @param <T> the type
   * @return a type literal
   * @see Types#collectionOf(Type)
   */
  @NonNull
  static <T> TypeLiteral<Collection<T>> collectionOf(@NonNull final TypeLiteral<T> type) {
    return new FriendlyTypeLiteral<Collection<T>>() {}.where(new TypeArgument<T>(type) {});
  }

  /**
   * Creates a type literal representing a {@link List} whose elements are of type {@code type}.
   *
   * @param type the type of element
   * @param <T> the type
   * @return a type literal
   * @see Types#listOf(Type)
   */
  @NonNull
  static <T> TypeLiteral<List<T>> listOf(@NonNull final Class<T> type) {
    return listOf(TypeLiteral.get(type));
  }

  /**
   * Creates a type literal representing a {@link List} whose elements are of type {@code type}.
   *
   * @param type the type of element
   * @param <T> the type
   * @return a type literal
   * @see Types#listOf(Type)
   */
  @NonNull
  static <T> TypeLiteral<List<T>> listOf(@NonNull final TypeLiteral<T> type) {
    return new FriendlyTypeLiteral<List<T>>() {}.where(new TypeArgument<T>(type) {});
  }

  /**
   * Creates a type literal representing a {@link Map} whose keys are of type {@code key} and values
   * are of type {@code value}.
   *
   * @param key the map key type
   * @param value the map value type
   * @param <K> the key type
   * @param <V> the value type
   * @return a type literal
   * @see Types#mapOf(Type, Type)
   */
  @NonNull
  static <K, V> TypeLiteral<Map<K, V>> mapOf(@NonNull final Class<K> key, @NonNull final Class<V> value) {
    return mapOf(TypeLiteral.get(key), TypeLiteral.get(value));
  }

  /**
   * Creates a type literal representing a {@link Map} whose keys are of type {@code key} and values
   * are of type {@code value}.
   *
   * @param key the map key type
   * @param value the map value type
   * @param <K> the key type
   * @param <V> the value type
   * @return a type literal
   * @see Types#mapOf(Type, Type)
   */
  @NonNull
  static <K, V> TypeLiteral<Map<K, V>> mapOf(@NonNull final Class<K> key, @NonNull final TypeLiteral<V> value) {
    return mapOf(TypeLiteral.get(key), value);
  }

  /**
   * Creates a type literal representing a {@link Map} whose keys are of type {@code key} and values
   * are of type {@code value}.
   *
   * @param key the map key type
   * @param value the map value type
   * @param <K> the key type
   * @param <V> the value type
   * @return a type literal
   * @see Types#mapOf(Type, Type)
   */
  @NonNull
  static <K, V> TypeLiteral<Map<K, V>> mapOf(@NonNull final TypeLiteral<K> key, @NonNull final Class<V> value) {
    return mapOf(key, TypeLiteral.get(value));
  }

  /**
   * Creates a type literal representing a {@link Map} whose keys are of type {@code key} and values
   * are of type {@code value}.
   *
   * @param key the map key type
   * @param value the map value type
   * @param <K> the key type
   * @param <V> the value type
   * @return a type literal
   * @see Types#mapOf(Type, Type)
   */
  @NonNull
  static <K, V> TypeLiteral<Map<K, V>> mapOf(@NonNull final TypeLiteral<K> key, @NonNull final TypeLiteral<V> value) {
    return new FriendlyTypeLiteral<Map<K, V>>() {}.where(new TypeArgument<K>(key) {}, new TypeArgument<V>(value) {});
  }

  /**
   * Creates a type literal representing a {@link Set} whose elements are of type {@code type}.
   *
   * @param type the type of element
   * @param <T> the type
   * @return a type literal
   * @see Types#setOf(Type)
   */
  @NonNull
  static <T> TypeLiteral<Set<T>> setOf(@NonNull final Class<T> type) {
    return setOf(TypeLiteral.get(type));
  }

  /**
   * Creates a type literal representing a {@link Set} whose elements are of type {@code type}.
   *
   * @param type the type of element
   * @param <T> the type
   * @return a type literal
   * @see Types#setOf(Type)
   */
  @NonNull
  static <T> TypeLiteral<Set<T>> setOf(@NonNull final TypeLiteral<T> type) {
    return new FriendlyTypeLiteral<Set<T>>() {}.where(new TypeArgument<T>(type) {});
  }
}
