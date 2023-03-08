package com.github.ziazi.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class CachedActivity {

        @PrimaryKey
        public int id;

        @ColumnInfo(name = "activity")
        public String activity;

        @ColumnInfo(name = "accessibility")
        public String accessibility;

        @ColumnInfo(name = "type")
        public String type;

        @ColumnInfo(name = "participants")
        public String participants;

        @ColumnInfo(name = "price")
        public String price;

        @ColumnInfo(name = "key")
        public String key;

        @ColumnInfo(name= "link")
        public String link;

        public CachedActivity match(BoredActivity data){
                this.id= data.hashCode();
                this.activity= data.activity;
                this.accessibility= data.accessibility;
                this.type= data.type;
                this.participants= data.participants;
                this.price= data.price;
                this.key= data.key;
                this.link= data.link;
                return this;
        }

        public BoredActivity revert(){
                BoredActivity preimage= new BoredActivity();
                preimage.activity= activity;
                preimage.accessibility= accessibility;
                preimage.participants= participants;
                preimage.key= key;
                preimage.link= link;
                preimage.price= price;
                preimage.type= type;
                return preimage;
        }



}
