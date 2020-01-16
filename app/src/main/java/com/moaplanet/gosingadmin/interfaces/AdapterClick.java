package com.moaplanet.gosingadmin.interfaces;

/**
 * Adapter 에서 클릭시 콜백 리스터
 */
public interface AdapterClick<T> {
    void click(T model);

    void click(T model, int type);
}
