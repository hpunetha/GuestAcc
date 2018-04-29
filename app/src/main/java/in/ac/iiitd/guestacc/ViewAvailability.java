package in.ac.iiitd.guestacc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by hpunetha on 2/25/2018.
 */
public class ViewAvailability extends AppCompatActivity {


    //static Context context;
    RecyclerView Main_recyclerView;
    roomadapter Main_adapter;
    private int counter = 0;

    DataSnapshot mBookings,mRoomDetails;
    List<Room> mAllRoomAndFlats ;

    static int mRoomNum;
    static int mMaleNum,mFemNum;
    static String mRecToDate,mRecFromDate;


    static TextView mTextErrorMsg;
    List<Room> list_of_rooms;
    ClickListener listener;
    FirebaseDatabase mDatabase;
    static int mBHCount=0,mGHCount=0,mFRFCount=0,mFRRCount=0,mOthCount=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_availability);
        mBHCount=0;mGHCount=0;mFRFCount=0;mFRRCount=0;mOthCount=0;

        mRecFromDate = getIntent().getStringExtra("from_date");
        mRecToDate = getIntent().getStringExtra("to_date");
        mRoomNum = getIntent().getIntExtra("rooms",0);
        mMaleNum = getIntent().getIntExtra("males",0);
        mFemNum = getIntent().getIntExtra("females",0);

       mTextErrorMsg = (TextView)findViewById(R.id.textViewErrorMsg);

        Log.d("From => ",mRecFromDate);
        Log.d("To => ",mRecToDate);
        Log.d("Rooms ",String.valueOf(mRoomNum));
        Log.d("Males ",String.valueOf(mMaleNum));
        Log.d("Females ",String.valueOf(mFemNum));

        mAllRoomAndFlats =  new ArrayList<Room>();
        mDatabase = FirebaseDatabase.getInstance();

        list_of_rooms = new ArrayList<>();

        Main_recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Main_recyclerView.setHasFixedSize(true);

        Main_recyclerView.setLayoutManager(new LinearLayoutManager(this));



        DatabaseReference myRef = mDatabase.getReference("bookings");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
               mBookings = dataSnapshot;

               Log.w("Bookings => ",mBookings.toString());
               for (DataSnapshot mBookdate : dataSnapshot.getChildren())
               {
                   String temp = mBookdate.getKey();
                   if (temp !=null)
                   {
                      Log.d("Hashmap val ",temp);

                      if (mRecFromDate.equalsIgnoreCase(temp))
                      {
                          getAllAvailableRooms();

                      }
                      else
                      {
                          getAllAvailableRooms();
                      }
                   }
                   else
                   {
                       getAllAvailableRooms();
                   }



               }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





/*
        list_of_rooms.add(
                new Room(
                        "bh",
                        "Boys Hostel Room",
                        "Available: 2",
                        1500,
                        R.drawable.samplea));

        list_of_rooms.add(
                new Room(
                        "gh",
                        "Girls Hostel Room",
                        "Available: 2",
                        1500,
                        R.drawable.sampleb));

        list_of_rooms.add(
                new Room(
                        "frf",
                        "Faculty Resi. Flats",
                        "Available: 2",
                        2500,
                        R.drawable.samplec));

        list_of_rooms.add(
                new Room(
                        "frr",
                        "Faculty Resi. Rooms",
                        "Available: 3",
                        1500,
                        R.drawable.sampled));
        list_of_rooms.add(
                new Room(
                        "bh3",
                        "Boys Hostel 2",
                        "Available: 0",
                        1500,
                        R.drawable.sampled));

*/

//       Log.w("ONCreateList => ",mAllRoomAndFlats.toString());
//        for (Room r:mAllRoomAndFlats)
//        {
//            list_of_rooms.add(r);
//            Log.w("Final Room ->",r.toString());
//        }


//        counter++;
//        Main_adapter = new roomadapter(this, list_of_rooms, listener);
//        Main_recyclerView.setAdapter(Main_adapter);
    }

    private void getAllAvailableRooms()
    {
        DatabaseReference myRef = mDatabase.getReference("room_details");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean flagbh=false,flaggh=false,flagfrr=false,flagfrf=false,flagoth=false;

                for (DataSnapshot dst : dataSnapshot.getChildren())
                {
                    //Room temproom = new Room();
                    String id = dst.child("class").getValue().toString();
                    String title= dst.child("location").getValue().toString();
                    double price = Double.parseDouble(dst.child("price").getValue().toString());
                    int avail =1;
                    int image;
                    String availability = getString(R.string.avail) +String.valueOf(avail);
                    switch (id){
                        case "bh":
                            image = R.drawable.samplea;
                            callAddFunc(id,title,availability,price,image,flagbh);
                            flagbh=true;
                            mBHCount++;
                            break;
                        case "gh":
                            image = R.drawable.sampleb;
                            callAddFunc(id,title,availability,price,image,flaggh);
                            flaggh=true;
                            mGHCount++;
                            break;
                        case "frr":
                            image = R.drawable.samplec;
                            callAddFunc(id,title,availability,price,image,flagfrr);
                            flagfrr=true;
                            mFRRCount++;
                            break;
                        case "frf":
                            image = R.drawable.sampled;
                            callAddFunc(id,title,availability,price,image,flagfrf);
                            flagfrf=true;
                            mFRFCount++;

                            break;
                        default:
                            image = R.drawable.sampled;
                            callAddFunc(id,title,availability,price,image,flagoth);
                            flagoth=true;
                            mOthCount++;

                    }


                    Log.w("Rooms",id);
                    Log.w("Rooms",title);
                    Log.w("Rooms",String.valueOf(price));


                }



                String avail=getString(R.string.avail);

                for(Room r: mAllRoomAndFlats)
                {

                    switch (r.getId())
                    {
                        case "bh":
                            avail= getString(R.string.avail) + String.valueOf(mBHCount);
                            break;
                        case "gh":
                            avail = getString(R.string.avail) + String.valueOf(mGHCount);
                            break;
                        case "frr":
                            avail= getString(R.string.avail) + String.valueOf(mFRRCount);
                            break;
                        case "frf":
                            avail= getString(R.string.avail) + String.valueOf(mFRFCount);
                            break;
                        default:
                            avail= getString(R.string.avail) + String.valueOf(mOthCount);

                    }
                    r.setAvailability(avail);
                    list_of_rooms.add(r);


                }
                Log.w("ArrayList => ",mAllRoomAndFlats.toString());
                counter++;
                Main_adapter = new roomadapter(ViewAvailability.this, list_of_rooms, listener);
                Main_recyclerView.setAdapter(Main_adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void callAddFunc(String id, String title, String availability, double price, int image, boolean flag) {


        if (flag == false) {
            Room temproom = new Room(id,title,availability,price,image);
            mAllRoomAndFlats.add(temproom);
        }
    }

//    public static void errorMessageShow(String msg)
//    {
//        mTextErrorMsg.setText(msg);
//    }


}

