package com.pz.supportchat.commons.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;

public class Contact extends RealmObject {

    @Index
    private String name;
    private String mddress;
    private String mesource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMddress() {
        return mddress;
    }

    public void setMddress(String mddress) {
        this.mddress = mddress;
    }

    public String getMesource() {
        return mesource;
    }

    public void setMesource(String mesource) {
        this.mesource = mesource;
    }
}
