package com.almosky.almoskylaundryApp.di.component;


import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.di.scope.ActivityScope;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.UtilsPref;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent extends AppComponent {
    void inject(BaseActivity baseActivity);

    void inject(UtilsPref utilsPref);

    void inject(AppPrefes appPrefes);
}