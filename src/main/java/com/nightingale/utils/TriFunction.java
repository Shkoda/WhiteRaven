package com.nightingale.utils;

/**
 * Created by Nightingale on 15.04.2014.
 */
public interface TriFunction<Arg1, Arg2, Arg3, Result> {
    Result apply(Arg1 arg1, Arg2 arg2, Arg3 arg3);
}
