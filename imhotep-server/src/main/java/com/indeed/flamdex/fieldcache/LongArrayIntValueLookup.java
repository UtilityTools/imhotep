/*
 * Copyright (C) 2014 Indeed Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package com.indeed.flamdex.fieldcache;

import com.indeed.flamdex.api.IntValueLookup;

/**
 * @author jsgroth
 */
public final class LongArrayIntValueLookup implements IntValueLookup {
    private long[] lookupArray;
    private final long min;
    private final long max;

    public LongArrayIntValueLookup(long[] lookupArray) {
        this.lookupArray = lookupArray;
        long tmin = Long.MAX_VALUE;
        long tmax = Long.MIN_VALUE;
        for (final long val : lookupArray) {
            tmin = Math.min(tmin, val);
            tmax = Math.max(tmax, val);
        }
        min = tmin;
        max = tmax;
    }

    public LongArrayIntValueLookup(long[] lookupArray, long min, long max) {
        this.lookupArray = lookupArray;
        this.min = min;
        this.max = max;
    }

    @Override
    public long getMin() {
        return min;
    }

    @Override
    public long getMax() {
        return max;
    }

    @Override
    public final void lookup(int[] docIds, long[] values, int n) {
        for (int i = 0; i < n; ++i) {
            values[i] = lookupArray[docIds[i]];
        }
    }

    @Override
    public long memoryUsed() {
        return 8L * lookupArray.length;
    }

    @Override
    public void close() {
        lookupArray = null;
    }
}
