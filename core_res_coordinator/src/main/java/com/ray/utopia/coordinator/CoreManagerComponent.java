package com.ray.utopia.coordinator;

import android.app.Application;

import com.ray.utopia.core.CoreManagerImpl;
import com.ray.utopia.data.UnitModule;
import com.ray.utopia.data.database.DatabaseModule;
import com.ray.utopia.data.user.UserDaoModule;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
        ContextModule.class,
        UnitModule.class,
        DatabaseModule.class,
        UserDaoModule.class,
})
public interface CoreManagerComponent {

    void inject(CoreManagerImpl impl);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application app);

        CoreManagerComponent build();
    }
}
//@Component.Factory
//interface Factory {
//    CoreManagerComponent create(@BindsInstance Application app);
//}