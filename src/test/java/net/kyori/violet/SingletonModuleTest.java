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

import com.google.inject.CreationException;
import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class SingletonModuleTest {
  @Test
  void testDuplicateBindExpectedException() {
    try {
      Guice.createInjector(new TestAbstractModule(), new TestAbstractModule());
    } catch(final CreationException expected) {
      if(expected.getMessage().contains("A binding to " + Thing.class.getName() + " was already configured at ")) {
        return;
      }
    }
    fail("expected duplicate binding creation exception");
  }

  @Test
  void testInjectorCreation() {
    Guice.createInjector(new TestSingletonModule(), new TestSingletonModule());
  }

  @Test
  void testModuleInstallation() {
    Guice.createInjector(new com.google.inject.AbstractModule() {
      @Override
      protected void configure() {
        this.install(new TestSingletonModule());
        this.install(new TestSingletonModule());
      }
    });
  }

  private interface Thing {}
  private static class ThingA implements Thing {}

  private static final class TestSingletonModule extends SingletonModule {
    @Override
    protected void configure() {
      this.bind(Thing.class).toInstance(new ThingA());
    }
  }

  private static final class TestAbstractModule extends com.google.inject.AbstractModule {
    @Override
    protected void configure() {
      this.bind(Thing.class).toInstance(new ThingA());
    }
  }
}
