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
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// TODO: test LazySingleton?
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
        assertFalse(things.ap.get() == things.ap.get());
        assertTrue(things.al.get() == things.al.get());
        assertTrue(things.bl.get() == things.bl.get());
        assertTrue(things.cl.get() == things.cl.get());
        assertTrue(things.dl.get() == things.dl.get());
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
}
