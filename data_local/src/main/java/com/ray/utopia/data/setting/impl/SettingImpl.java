package com.ray.utopia.data.setting.impl;

import android.content.SharedPreferences;

import com.ray.utopia.base.rxjava.BaseSingleObserver;
import com.ray.utopia.data.setting.Setting;
import com.ray.utopia.data.setting.SettingKey;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

abstract class SettingImpl<T> extends Setting<T> {
    @SuppressWarnings("WeakerAccess")
    protected final SettingKey<T> mKey;
    @SuppressWarnings("WeakerAccess")
    protected final SharedPreferences mSp;
    @SuppressWarnings("WeakerAccess")
    protected final BehaviorSubject<T> mSubject;
    @SuppressWarnings("WeakerAccess")
    protected final Scheduler mScheduler;
    private final SingleObserver<T> mWriteObserver = new WriteObserver();
    private Disposable mDisposable;

    SettingImpl(SharedPreferences sp, SettingKey<T> key, Scheduler scheduler) {
        mSp = sp;
        mKey = key;
        mScheduler = scheduler;
        mSubject = BehaviorSubject.create();
    }

    @Override
    public T get() {
        T value = mSubject.getValue();
        if (value == null) {
            value = read(mKey.getKey(), mKey.getDefValue());
        }
        return value;
    }

    @Override
    public void set(T value) {
        Single.create(
                (SingleOnSubscribe<T>) emitter -> {
                    write(mKey.getKey(), value);
                    emitter.onSuccess(value);
                })
                .subscribeOn(mScheduler)
                .subscribe(mWriteObserver);
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        mSubject.subscribeOn(mScheduler)
                .subscribe(observer);
    }

    protected abstract T read(String key, T defValue);

    protected abstract void write(String key, T value);

    private final class WriteObserver extends BaseSingleObserver<T> {

        @Override
        public void onSubscribe(Disposable d) {
            Disposable prev = mDisposable;
            mDisposable = d;
            if (prev != null && !prev.isDisposed()) {
                prev.dispose();
            }
        }

        @Override
        public void onSuccess(T value) {
            mSubject.onNext(value);
        }
    }
}