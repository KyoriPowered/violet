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

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

import static org.junit.Assert.assertEquals;

public class DuplexTest {

  @Test
  public void test() {
    final Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.bind(Thing.class).annotatedWith(Names.named("p0")).toInstance(new Thing("p0"));

        final DuplexBinder binder = DuplexBinder.create(this.binder());
        binder.install(new DuplexModule() {
          @Override
          protected void configure() {
            this.bind(Thing.class).annotatedWith(Names.named("d0")).toInstance(new Thing("d0"));
            this.expose(Thing.class).annotatedWith(Names.named("d0"));
          }
        });
        binder.install(new DuplexModule() {
          @Override
          protected void configure() {
            this.bind(Thing.class).annotatedWith(Names.named("d1")).toInstance(new Thing("d1"));
            this.expose(Thing.class).annotatedWith(Names.named("d1"));
            this.install(new DuplexModule() {
              @Override
              protected void configure() {
                this.bind(Thing.class).annotatedWith(Names.named("d2")).toInstance(new Thing("d2"));
                this.expose(Thing.class).annotatedWith(Names.named("d2"));
              }
            });
          }
        });
      }
    });
    final Things things = injector.getInstance(Things.class);
    assertEquals("p0", things.p0.id);
    assertEquals("d0", things.d0.id);
    assertEquals("d1", things.d1.id);
    assertEquals("d2", things.d2.id);
  }

  private static class Thing {

    final String id;

    Thing(final String id) {
      this.id = id;
    }
  }

  private static class Things {

    @Inject @Named("p0") Thing p0;
    @Inject @Named("d0") Thing d0;
    @Inject @Named("d1") Thing d1;
    @Inject @Named("d2") Thing d2;
  }
}
