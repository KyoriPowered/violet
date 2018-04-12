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

import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: test VDuplexBinder and VPrivateBinder
class VBinderTest {
  @Test
  void testInSet() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.inSet(Thing.class).addBinding().to(ThingA.class);
        this.install(new AbstractModule() {
          @Override
          protected void configure() {
            this.inSet(Thing.class).addBinding().to(ThingB.class);
          }
        });
      }
    });
    final Things things = injector.getInstance(Things.class);
    assertEquals(2, things.things.size());
  }

  @Test
  void testExposed() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        DuplexBinder.create(this.binder()).install(new DuplexModule() {
          @Override
          protected void configure() {
            this.bindAndExpose(Thing.class).to(ThingA.class);
            this.bindAndExpose(Thing.class).annotatedWith(ThingAnnotation.class).to(ThingB.class);
          }
        });
      }
    });
    final ExposeThings things = injector.getInstance(ExposeThings.class);
    assertEquals(ThingA.class, things.a.getClass());
    assertEquals(ThingB.class, things.b.getClass());
  }

  @Test
  void testFactory() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.installFactory(FooThingFactory.class, builder -> builder.implement(FooThing.class, AssistedFooThing.class));
      }
    });
    final FooThings things = injector.getInstance(FooThings.class);
    assertEquals(100, things.factory.create(100).value());
  }

  @Test
  void testOptionalDefault() {
    assertEquals(4, Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.bindOptional(FooThing.class).setDefault().to(FooThing4.class);
      }
    }).getInstance(FooThing.class).value());
  }

  @Test
  void testOptionalBound() {
    assertEquals(8, Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.bindOptional(FooThing.class).setDefault().to(FooThing4.class);
      }
    }, new AbstractModule() {
      @Override
      protected void configure() {
        this.bindOptional(FooThing.class).setBinding().to(FooThing8.class);
      }
    }).getInstance(FooThing.class).value());
  }

  @Test
  void testOptionalLinked() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.bind(FooThing.class).to(FooThing4.class);
        this.bindOptional(FooThing.class);
      }
    });
    assertEquals(4, injector.getInstance(InjectedFooThing.class).thing.value());
    assertEquals(true, injector.getInstance(InjectedOptionalFooThing.class).optional.isPresent());
    assertEquals(4, injector.getInstance(InjectedOptionalFooThing.class).optional.get().value());
  }

  private interface Thing {}
  private static class ThingA implements Thing {}
  private static class ThingB implements Thing {}
  @BindingAnnotation
  @Retention(RetentionPolicy.RUNTIME)
  private @interface ThingAnnotation {}

  private static class Things {
    @Inject Set<Thing> things;
  }

  private static class ExposeThings {
    @Inject Thing a;
    @Inject @ThingAnnotation Thing b;
  }

  private interface FooThing {
    int value();
  }

  private static class FooThing4 implements FooThing { @Override public int value() { return 4; } }
  private static class FooThing8 implements FooThing { @Override public int value() { return 8; } }

  private static class AssistedFooThing implements FooThing {
    final int value;

    @Inject
    private AssistedFooThing(@Assisted final int value) {
      this.value = value;
    }

    @Override
    public int value() {
      return this.value;
    }
  }

  private interface FooThingFactory {
    FooThing create(final int value);
  }

  private static class FooThings {
    @Inject FooThingFactory factory;
  }

  private static class InjectedFooThing {
    @Inject FooThing thing;
  }

  private static class InjectedOptionalFooThing {
    @Inject Optional<FooThing> optional;
  }
}
