package com.nightingale.model.tasks;

import java.io.Serializable;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface ITaskGraphModel extends Serializable {
    void reset(ITaskGraphModel other);

}
