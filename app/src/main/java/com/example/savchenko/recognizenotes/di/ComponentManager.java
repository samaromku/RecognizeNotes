package com.example.savchenko.recognizenotes.di;

import com.example.savchenko.recognizenotes.di.base.BaseComponent;
import com.example.savchenko.recognizenotes.di.base.BaseModule;
import com.example.savchenko.recognizenotes.di.base.ComponentBuilder;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Created by Andrey on 23.09.2017.
 */

public class ComponentManager {
//    private static AppComponent appComponent;
//
//    public static AppComponent getAppComponent() {
//        if(appComponent==null){
//            appComponent = DaggerAppComponent
//                    .builder()
//                    .appModule(new AppModule())
//                    .build();
//        }
//        return appComponent;
//    }
//
//
//    @Inject
//    Map<Class<?>, Provider<ComponentBuilder>>builders;
//    private Map<Class<?>, BaseComponent>components;
//
//    public void init(){
//        AppComponent appComponentNew =
//                DaggerAppComponent.builder().appModule(new AppModule()).build();
//        appComponentNew.injectComponentManager(this);
//        components = new HashMap<>();
//    }
//
//    public BaseComponent getPresenterComponent(Class<?>clazz){
//        return getPresenterComponent(clazz, null);
//    }
//
//
//    public BaseComponent getPresenterComponent(Class<?>clazz, BaseModule module){
//        BaseComponent component = components.get(clazz);
//        if(component==null){
//            ComponentBuilder builder = builders.get(clazz).get();
//            if(module!=null){
//                builder.module(module);
//            }
//            component = builder.build();
//            components.put(clazz, component);
//        }
//        return component;
//    }
//
//    public void releaseComponent(Class<?>clazz){
//        components.put(clazz, null);
//    }
}
