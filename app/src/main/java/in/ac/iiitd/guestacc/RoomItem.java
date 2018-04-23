package in.ac.iiitd.guestacc;

import java.util.HashMap;

public class RoomItem
    {
        public String id;

        public String roomTag;
        public String roomName;
        public String roomPref;
        public int mMaleCount;
        public int mFemaleCount;
        public int mRoomType;       // 0-> Room , 1-> Flat
        public int mRoomPrice;

        public RoomItem(String roomTag, String roomName, String roomPref,int malecount,int femalecount,int mroomtype, int mroomprice )
        {
            this.roomTag=roomTag;
            this.roomName=roomName;
            this.roomPref=roomPref;
            this.mMaleCount=malecount;
            this.mFemaleCount=femalecount;
            this.mRoomType=mroomtype;
            this.mRoomPrice=mroomprice;

        }

        public RoomItem() {

            this.mMaleCount=0;
            this.mFemaleCount=0;
            this.mRoomType=0;
            this.mRoomPrice=1500;
        }

        @Override
        public String toString() {
            return roomTag;
        }
    }

