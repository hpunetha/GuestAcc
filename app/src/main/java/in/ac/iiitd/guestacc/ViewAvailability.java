package in.ac.iiitd.guestacc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewAvailability extends AppCompatActivity {

    RecyclerView Main_recyclerView;
    roomadapter Main_adapter;
    private int counter = 0;


    List<room> List_of_rooms;
    ClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_availability);





        List_of_rooms = new ArrayList<>();

        Main_recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Main_recyclerView.setHasFixedSize(true);

        Main_recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List_of_rooms.add(
                new room(
                        1,
                        "Boys Hostel associated_room_id",
                        "Available: 0",
                        1500,
                        R.drawable.samplea));

        List_of_rooms.add(
                new room(
                        1,
                        "Girls Hostel associated_room_id",
                        "Available: 0",
                        1500,
                        R.drawable.sampleb));

        List_of_rooms.add(
                new room(
                        1,
                        "Faculty Residence",
                        "Available: 0",
                        2500,
                        R.drawable.samplec));

        List_of_rooms.add(
                new room(
                        1,
                        "Faculty Rooms",
                        "Available: 0",
                        2500,
                        R.drawable.sampled));
        List_of_rooms.add(
                new room(
                        1,
                        "Boys Hostel 2",
                        "Available: 0",
                        1500,
                        R.drawable.sampled));

        List_of_rooms.add(
                new room(
                        1,
                        "Boys Hostel 2",
                        "Available: 0",
                        1500,
                        R.drawable.sampled));
        List_of_rooms.add(
                new room(
                        1,
                        "Boys Hostel 2",
                        "Available: 0",
                        1500,
                        R.drawable.sampled));



        counter++;
        Main_adapter = new roomadapter(this, List_of_rooms, listener);
        Main_recyclerView.setAdapter(Main_adapter);
    }
}

