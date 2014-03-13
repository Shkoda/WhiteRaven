package com.nightingale.application.guice;

import javafx.concurrent.Service;

public interface ICommandProvider {

    <T extends Service> T get(Class<T> type);
}
