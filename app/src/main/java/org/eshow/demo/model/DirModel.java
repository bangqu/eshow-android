package org.eshow.demo.model;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2018-6-8 0008.
 */

public class DirModel implements Comparable<DirModel> {

    public String name;
    public String path;

    @Override
    public int compareTo(@NonNull DirModel o) {
        return name.compareToIgnoreCase(o.name);
    }
}
