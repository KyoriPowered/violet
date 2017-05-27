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
import com.google.inject.Module;
import com.google.inject.PrivateModule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkState;

/**
 * A module which has access to a {@link DuplexBinder#binder() private} and
 * {@link DuplexBinder#publicBinder() public} environment.
 *
 * <p>A duplex module does not create a new {@link DuplexBinder} automatically,
 * and shares a common {@link DuplexBinder#publicBinder() public binder}.</p>
 *
 * <p>While a duplex module is similar to {@link PrivateModule}, it has a few differences:</p>
 * <ul>
 *     <li>they full access to the public environment, rather than limited (exposure) access</li>
 *     <li>they can be nested within other duplex modules, and inherit from the public environment</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>
 * public class MyModule extends DuplexModule {
 *     {@literal @}Override
 *     protected void configure() {
 *         this.bind(Thing.class).to(ThingImpl.class);
 *         this.expose(Thing.class);
 *     }
 * }
 *
 * // and to use, in a public environment:
 * DuplexBinder.create(this.binder())
 *     .install(new MyModule());
 * </pre>
 *
 * @see PrivateModule
 */
// https://github.com/google/guice/issues/369#issuecomment-48217990
public abstract class DuplexModule implements Module, VDuplexBinder {

    @Nullable private DuplexBinder binder;

    @Override
    public void configure(final Binder binder) {
        checkState(this.binder == null, "Re-entry is not allowed.");
        this.binder = DuplexBinderImpl.activeBinder(binder);
        // a null binder means that we aren't being installed into a DuplexBinder
        if(this.binder == null) {
            binder.skipSources(DuplexModule.class).addError(
                "%s was installed into %s but must be installed into %s",
                this.getClass().getName(), binder.getClass().getName(), DuplexBinder.class.getName()
            );
            return;
        }
        try {
            this.configure();
        } finally {
            this.binder = null;
        }
    }

    /**
     * Configures a {@link Binder} via the exposed methods.
     */
    protected abstract void configure();

    @Nonnull
    @Override
    public DuplexBinder binder() {
        checkState(this.binder != null, "The binder can only be used inside configure()");
        return this.binder;
    }
}
