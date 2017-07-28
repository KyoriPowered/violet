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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class LazyTest {

  @Test
  public void testLazy() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.bind(Thing.class).to(ThingA.class);
        this.bindLazy(Thing.class).annotatedWith(Names.named("b")).to(ThingB.class);
        this.bindLazy(Thing.class).annotatedWith(Names.named("c")).to(ThingC.class);
      }
    });
    final Things things = injector.getInstance(Things.class);
    assertNotSame(things.ap.get(), things.ap.get());
    assertSame(things.al.get(), things.al.get());
    assertSame(things.bl.get(), things.bl.get());
    assertSame(things.cl.get(), things.cl.get());
    assertSame(things.dl.get(), things.dl.get());
  }

  @Before
  public void resetCount() {
    SingletonThing.CONSTRUCTION_COUNT.set(0);
    AnnotatedSingletonThing.CONSTRUCTION_COUNT.set(0);
  }

  @Test
  public void testSingletonBoundScope() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.bindScope(LazySingleton.class, LazySingleton.SCOPE);
      }
    });

    assertEquals(AnnotatedSingletonThing.CONSTRUCTION_COUNT.get(), 0);
    final AnnotatedSingletonThing a = injector.getInstance(AnnotatedSingletonThing.class);
    assertEquals(AnnotatedSingletonThing.CONSTRUCTION_COUNT.get(), 1);
    final AnnotatedSingletonThing b = injector.getInstance(AnnotatedSingletonThing.class);
    assertEquals(AnnotatedSingletonThing.CONSTRUCTION_COUNT.get(), 1);
    assertSame(a, b);
  }

  @Test
  public void testSingletonBoundScopeProvider() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.bindScope(LazySingleton.class, LazySingleton.SCOPE);
      }
    });

    assertEquals(AnnotatedSingletonThing.CONSTRUCTION_COUNT.get(), 0);
    final AnnotatedSingletonThingProvider provider = injector.getInstance(AnnotatedSingletonThingProvider.class);
    assertEquals(AnnotatedSingletonThing.CONSTRUCTION_COUNT.get(), 0);
    final AnnotatedSingletonThing ap = provider.provider.get();
    assertEquals(AnnotatedSingletonThing.CONSTRUCTION_COUNT.get(), 1);
    final AnnotatedSingletonThing bp = provider.provider.get();
    assertEquals(AnnotatedSingletonThing.CONSTRUCTION_COUNT.get(), 1);
    assertSame(ap, bp);
  }

  @Test
  public void testSingletonInScope() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      public void configure() {
        this.bind(SingletonThing.class).in(LazySingleton.SCOPE);
      }
    });

    assertEquals(SingletonThing.CONSTRUCTION_COUNT.get(), 0);
    final SingletonThing a = injector.getInstance(SingletonThing.class);
    assertEquals(SingletonThing.CONSTRUCTION_COUNT.get(), 1);
    final SingletonThing b = injector.getInstance(SingletonThing.class);
    assertEquals(SingletonThing.CONSTRUCTION_COUNT.get(), 1);
    assertSame(a, b);
  }

  @Test
  public void testSingletonInScopeProvider() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.bind(SingletonThing.class).in(LazySingleton.SCOPE);
      }
    });

    assertEquals(SingletonThing.CONSTRUCTION_COUNT.get(), 0);
    final SingletonThingProvider provider = injector.getInstance(SingletonThingProvider.class);
    assertEquals(SingletonThing.CONSTRUCTION_COUNT.get(), 0);
    final SingletonThing a = provider.provider.get();
    assertEquals(SingletonThing.CONSTRUCTION_COUNT.get(), 1);
    final SingletonThing b = provider.provider.get();
    assertEquals(SingletonThing.CONSTRUCTION_COUNT.get(), 1);
    assertSame(a, b);
  }

  private interface Thing {}
  private static class ThingA implements Thing {}
  private static class ThingB implements Thing {}
  private static class ThingC implements Thing {}
  private static class ThingD {}

  private static class Things {

    @Inject Provider<Thing> ap;
    @Inject Lazy<Thing> al;
    @Inject @Named("b") Lazy<Thing> bl;
    @Inject @Named("c") Lazy<Thing> cl;
    @Inject Lazy<ThingD> dl;
  }

  // not annotated
  private static class SingletonThing {

    static final AtomicInteger CONSTRUCTION_COUNT = new AtomicInteger();

    private SingletonThing() {
      CONSTRUCTION_COUNT.incrementAndGet();
    }
  }

  private static class SingletonThingProvider {

    @Inject Provider<SingletonThing> provider;
  }

  @LazySingleton
  private static class AnnotatedSingletonThing {

    static final AtomicInteger CONSTRUCTION_COUNT = new AtomicInteger();

    private AnnotatedSingletonThing() {
      CONSTRUCTION_COUNT.incrementAndGet();
    }
  }

  private static class AnnotatedSingletonThingProvider {

    @Inject Provider<AnnotatedSingletonThing> provider;
  }
}
