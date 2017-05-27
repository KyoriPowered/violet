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

import javax.annotation.Nonnull;

/**
 * A duplex binder which forwards all its method calls to another duplex binder.
 */
public interface ForwardingDuplexBinder extends ForwardingPrivateBinder, DuplexBinder {

    /**
     * Gets the forwarded duplex binder that methods are forwarded to.
     *
     * @return the forwarded duplex binder
     */
    @Nonnull
    @Override
    DuplexBinder binder();

    @Nonnull
    @Override
    default Binder publicBinder() {
        return this.binder().publicBinder();
    }

    @Override
    default DuplexBinder withSource(final Object source) {
        return this.binder().withSource(source);
    }

    @Override
    default DuplexBinder skipSources(final Class... classesToSkip) {
        return this.binder().skipSources(classesToSkip);
    }
}
