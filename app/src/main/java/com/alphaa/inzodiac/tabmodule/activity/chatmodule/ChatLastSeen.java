package com.alphaa.inzodiac.tabmodule.activity.chatmodule;

import java.io.Serializable;

/**
 * Created by Ravi Birla on 23,January,2019
 */
public class ChatLastSeen implements Serializable {
    public String chatstatus = "";
    public Object chatlastdate;

    public String getChatstatus() {
        return chatstatus;
    }

    public void setChatstatus(String chatstatus) {
        this.chatstatus = chatstatus;
    }

    public Object getChatlastdate() {
        return chatlastdate;
    }

    public void setChatlastdate(Object chatlastdate) {
        this.chatlastdate = chatlastdate;
    }
}